// Yusuf Erman ERGÖZ
// Tron
// 2018/12/24 17:29:18

package view;

import model.GameModel;
import model.KeyboardListener;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class GameWindow extends JFrame
{
    public static JFrame frame;
    public static CustomPanel customPanel;
    private GameMenu gameMenu;
    private static JLabel timeLabel;
    static GameModel model;
    static long startTime;
    public static int gameSpeed;
    static Dialog dialog;

    public GameWindow()
    {
        customPanel = new CustomPanel();
        frame = new JFrame("Tron Game");
        frame.setSize(1300, 800);
        frame.add(customPanel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameMenu = new GameMenu(restartAction, bestScoresAction, startGameAction);
        frame.setJMenuBar(gameMenu);
        restartAction.setEnabled(true);
        bestScoresAction.setEnabled(true);
        timeLabel = new JLabel();
        frame.add(timeLabel, BorderLayout.SOUTH);
        frame.setVisible(true);
        frame.addKeyListener((KeyListener) new KeyboardListener());
        model = new GameModel(0);
        dialog = new Dialog(model);
    }

    private final Action startGameAction = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)

        {
            customPanel.removeAll();
            model.resetGame(false);
            dialog.showStartGameWindow();
            model.setGameSpeed(gameSpeed);
            model.setInitialPosition(frame.getHeight(), frame.getWidth());
            startTime = System.currentTimeMillis();
            updateTimeLabel();
            model.moveToDirection();
        }
    };

    private final Action bestScoresAction = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dialog.showBestPlayers();
        }
    };

    private final Action restartAction = new AbstractAction()

    {

        @Override

        public void actionPerformed(ActionEvent e)

        {
            customPanel.removeAll();
            model.resetGame(true);
            model.setInitialPosition(frame.getHeight(), frame.getWidth());
            startTime = System.currentTimeMillis();
            updateTimeLabel();
            model.moveToDirection();
        }

    };

    public static void updateTimeLabel()
    {
        timeLabel.setText(" Elapsed Time: " + ( System.currentTimeMillis() - startTime ) / 1000 + " seconds");
    }

    static public void drawLine(List<Integer> linesOfP1, List<Integer> linesOfP2, Color lineColorP1, Color lineColorP2)
    {
        customPanel.setLineCoordinates(linesOfP1, linesOfP2, lineColorP1, lineColorP2);
        frame.repaint();
    }

    public static void showEndGameWindow()
    {
        int n = JOptionPane.showConfirmDialog(null,
                "Game Over!\nClick Yes to play again\nClick No to quit",
                "Approve", JOptionPane.YES_NO_OPTION);

        if (n == JOptionPane.YES_OPTION)
        {
            customPanel.removeAll();
            model.resetGame(true);
            model.setInitialPosition(frame.getHeight(), frame.getWidth());
            model.moveToDirection();
        }
        if (n == JOptionPane.NO_OPTION)
        {
            System.exit(0);
        }
    }
}