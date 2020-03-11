// Yusuf Erman ERGÖZ
// Tron
// 2018/12/24 17:29:18

package view;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GameMenu extends JMenuBar
{
    private JMenu gameMenu;

    private void createMenuItem(String name, Action action)
    {
        JMenuItem item = new JMenuItem(action);
        item.setText(name);
        gameMenu.add(item);
    }

    public GameMenu(Action restartAction, Action bestScoresAction, Action startGameAction)
    {
        gameMenu = new JMenu("Game Menu");

        createMenuItem("Start Game", startGameAction);
        createMenuItem("Restart Game", restartAction);
        createMenuItem("Best Scores", bestScoresAction);

        add(gameMenu);
    }
}