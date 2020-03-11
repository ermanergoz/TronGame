// Yusuf Erman ERGÖZ
// Tron
// 2018/12/24 17:29:18

package view;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import java.awt.*;

import java.util.*;

import java.util.List;

import java.util.stream.Collectors;

import model.Database;

import model.GameModel;

public class Dialog
{
    private GameModel model;

    public Dialog(GameModel model)
    {
        this.model = model;
    }


    public void showStartGameWindow()
    {
        String[] colors = {"Red Color", "Green Color", "Blue Color"};
        String[] speed = {"Average", "Fast", "Slow"};

        JComboBox player1Color = new JComboBox(colors);
        JComboBox player2Color = new JComboBox(colors);
        JComboBox gameSpeedCB = new JComboBox(speed);

        player1Color.setEditable(false);
        player2Color.setEditable(false);
        gameSpeedCB.setEditable(false);

        JTextField player1Name = new JTextField();
        JTextField player2Name = new JTextField();

        Object[] message = {"Name of Player 1:", player1Name, "Color of Player 1:", player1Color, "Name of Player 2:", player2Name, "Color of Player 2:", player2Color, "Choose the game speed", gameSpeedCB};

        while (true)
        {
            int option = JOptionPane.showConfirmDialog(null, message, "Enter the names and choose the colors!", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION)
            {
                if (player1Name.getText().length() > 0 && player2Name.getText().length() > 0 && player1Color.getSelectedItem() != player2Color.getSelectedItem())
                {
                    Color colorOfP1 = null;
                    Color colorOfP2 = null;

                    if (player1Color.getSelectedItem() == "Green Color") colorOfP1 = Color.green;
                    else if (player1Color.getSelectedItem() == "Blue Color") colorOfP1 = Color.CYAN;
                    else colorOfP1 = Color.red;

                    if (player2Color.getSelectedItem() == "Green Color") colorOfP2 = Color.green;
                    else if (player2Color.getSelectedItem() == "Blue Color") colorOfP2 = Color.CYAN;
                    else colorOfP2 = Color.red;

                    model.addPlayer(player1Name.getText(), colorOfP1);
                    model.addPlayer(player2Name.getText(), colorOfP2);

                    if (gameSpeedCB.getSelectedItem() == "Fast") GameWindow.gameSpeed = 3;
                    else if (gameSpeedCB.getSelectedItem() == "Slow") GameWindow.gameSpeed = 12;
                    else GameWindow.gameSpeed = 7;

                    break;
                }
            } else
            {
                System.exit(0);
            }
        }
    }

    public void showBestPlayers()
    {
        Database database = new Database();
        database.updateInfo();
        Map<String, Integer> playerInfo = new HashMap<String, Integer>();
        playerInfo=database.getMap();

        final Map<String, Integer> sortedPlayerInfo = playerInfo.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Set<Map.Entry<String, Integer>> sortedPlayerInfoSet = sortedPlayerInfo.entrySet();
        JFrame frame = new JFrame("Best Scores");
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        List<String> columns = new ArrayList<String>();
        List<String[]> rows = new ArrayList<String[]>();
        columns.add("Name of the Player");
        columns.add("Score of the Player");

        int i=0;
        for (Map.Entry<String, Integer> info : sortedPlayerInfoSet)
        {
            if(i!=10)
                rows.add(new String[]{info.getKey(), Integer.toString(info.getValue())});
            else
                break;
            ++i;
        }

        DefaultTableModel model = new DefaultTableModel(rows.toArray(new Object[][]{}), columns.toArray());
        JTable table = new JTable(model);
        JScrollPane tableContainer = new JScrollPane(table);
        panel.add(tableContainer, BorderLayout.CENTER);
        frame.add(panel);
        frame.pack();
        frame.setSize(400, 230);
        frame.setVisible(true);
    }
}