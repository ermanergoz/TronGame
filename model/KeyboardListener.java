// Yusuf Erman ERGÖZ
// Tron
// 2018/12/24 17:29:18

package model;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardListener extends KeyAdapter
{
    public void keyPressed(KeyEvent e)
    {
        int keyCode = e.getKeyCode();
        switch (keyCode)
        {   //For player 1
            case KeyEvent.VK_UP:
                if (!GameModel.keyStates.get(1))
                {
                    GameModel.players.get(0).createLine();
                    GameModel.keyStates.set(0, true);   //up arrow
                    GameModel.keyStates.set(1, false);  //down arrow
                    GameModel.keyStates.set(2, false);  //right arrow
                    GameModel.keyStates.set(3, false);  //left arrow
                }
                break;
            case KeyEvent.VK_DOWN:
                if (!GameModel.keyStates.get(0))
                {
                    GameModel.players.get(0).createLine();
                    GameModel.keyStates.set(1, true);
                    GameModel.keyStates.set(0, false);
                    GameModel.keyStates.set(2, false);
                    GameModel.keyStates.set(3, false);
                }
                break;
            case KeyEvent.VK_LEFT:
                if (!GameModel.keyStates.get(2))
                {
                    GameModel.players.get(0).createLine();
                    GameModel.keyStates.set(3, true);
                    GameModel.keyStates.set(0, false);
                    GameModel.keyStates.set(1, false);
                    GameModel.keyStates.set(2, false);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (!GameModel.keyStates.get(3))
                {
                    GameModel.players.get(0).createLine();
                    GameModel.keyStates.set(2, true);
                    GameModel.keyStates.set(3, false);
                    GameModel.keyStates.set(0, false);
                    GameModel.keyStates.set(1, false);
                }
                break;
            //For player 2
            case KeyEvent.VK_W:
                if (!GameModel.keyStates.get(5))
                {
                    GameModel.players.get(1).createLine();
                    GameModel.keyStates.set(4, true);   //W key
                    GameModel.keyStates.set(5, false);  //S key
                    GameModel.keyStates.set(6, false);  //D key
                    GameModel.keyStates.set(7, false);  //A key
                }
                break;
            case KeyEvent.VK_S:
                if (!GameModel.keyStates.get(4))
                {
                    GameModel.players.get(1).createLine();
                    GameModel.keyStates.set(5, true);
                    GameModel.keyStates.set(4, false);
                    GameModel.keyStates.set(7, false);
                    GameModel.keyStates.set(6, false);
                }
                break;
            case KeyEvent.VK_A:
                if (!GameModel.keyStates.get(6))
                {
                    GameModel.players.get(1).createLine();
                    GameModel.keyStates.set(7, true);
                    GameModel.keyStates.set(6, false);
                    GameModel.keyStates.set(4, false);
                    GameModel.keyStates.set(5, false);
                }
                break;
            case KeyEvent.VK_D:
                if (!GameModel.keyStates.get(7))
                {
                    GameModel.players.get(1).createLine();
                    GameModel.keyStates.set(6, true);
                    GameModel.keyStates.set(7, false);
                    GameModel.keyStates.set(4, false);
                    GameModel.keyStates.set(5, false);
                }
                break;
        }
    }
}