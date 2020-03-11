// Yusuf Erman ERGÖZ
// Tron
// 2018/12/24 17:29:18

package model;

import view.GameWindow;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

public class GameModel
{
    public enum Winner { NONE, PLAYER_1, PLAYER_2 }
    private int gameSpeed;
    static List<Boolean> keyStates;
    private int boardHeight;
    private int boardWidth;
    public static List<Player> players;
    Database database;

    public GameModel(int _gameSpeed)
    {
        gameSpeed = _gameSpeed; //lower number makes the game faster
        keyStates=new ArrayList<>();
        resetKeyStates();
        keyStates.set(0,true);
        keyStates.set(4,true);
        players = new ArrayList<>();
        database=new Database();
    }

    public void addPlayer(String name, Color color)
    {
        Player player = new Player(name,color);
        players.add(player);

        if(!database.isInDatabase(name))
        {
            database.addNewPlayer(name);
        }
    }

    public void setInitialPosition(int boardHeight, int boardWidth)
    {
        setBoardHeight(boardHeight);
        setBoardWidth(boardWidth);

        int pos = boardWidth/(players.size()+1);

        for(int i = 0; i < players.size(); ++i)
        {
            boardWidth=boardWidth-pos;
            players.get(i).setInitialPoint(boardWidth, boardHeight-20);
        }
    }

    public void resetKeyStates()
    {
        for(int i=0; i<8; ++i)
        {
            keyStates.add(false);
        }
    }

    public void resetGame(boolean keepPlayers)
    {
        setBoardHeight(0);
        setBoardWidth(0);
        keyStates=new ArrayList<>();
        resetKeyStates();
        keyStates.set(0,true);
        keyStates.set(4,true);

        if(!keepPlayers)
        {
            players = new ArrayList<>();
        }
        else
        {
            List<String> playerNames = new ArrayList<>();
            List<Color> playerColors = new ArrayList<>();

            int numberOfPlayers = players.size();
            for(int i = 0; i < numberOfPlayers; ++i)
            {
                playerNames.add(players.get(i).getName());
                playerColors.add(players.get(i).getColor());
            }

            players = new ArrayList<>();

            for(int i = 0; i < numberOfPlayers; ++i)
            {
                addPlayer(playerNames.get(i), playerColors.get(i));
            }
        }
    }

    public void setGameSpeed(int newGameSpeed)
    {
        gameSpeed = newGameSpeed;
    }

    public void setBoardHeight(int newHeight)
    {
        boardHeight = newHeight;
    }

    public void setBoardWidth(int newWidth)
    {
        boardWidth = newWidth;
    }

    public void moveToDirection()
    {
        Thread thread = new Thread("Runs the game")
        {
            public void run()
            {
                Winner whoWon = Winner.NONE;
                while (whoWon == Winner.NONE)
                {
                    try
                    {   directions(players.get(1).getLineCoords(), players.get(1).getx2(), players.get(1).gety2(),
                                keyStates.get(4), keyStates.get(5), keyStates.get(6), keyStates.get(7));

                        directions(players.get(0).getLineCoords(), players.get(0).getx2(), players.get(0).gety2(),
                                keyStates.get(0), keyStates.get(1), keyStates.get(2), keyStates.get(3));

                        GameWindow.drawLine(players.get(1).getLineCoords(), players.get(0).getLineCoords(),
                                players.get(0).getColor(), players.get(1).getColor());

                        GameWindow.updateTimeLabel();

                        sleep(gameSpeed);

                    } catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }

                    whoWon = isThereAWinner();
                    keepScore();
/*
                    if(!(whoWon == Winner.NONE))
                        GameWindow.showEndGameWindow();*/
                }
           }
        };
        thread.start();
    }

    private void directions(List<Integer> lineList, int x2, int y2, boolean isUp, boolean isDown, boolean isRight, boolean isLeft)
    {

        if (isUp)  // go upwards
        {
            lineList.set(y2, lineList.get(y2) - 1);
        } else if (isDown)    //go downwards
        {
            lineList.set(y2, lineList.get(y2) + 1);
        } else if (isRight) //go right
        {
            lineList.set(x2, lineList.get(x2) + 1);
        } else if (isLeft) //go right
        {
            lineList.set(x2, lineList.get(x2) - 1);
        }
    }

    public Winner isThereAWinner()
    {

        for(int p1i=0; p1i<=players.get(0).getLineCoords().size()-1; p1i=p1i+4)
        {   //if a player touches another players trace

            for(int p2i=0; p2i<players.get(1).getLineCoords().size(); p2i=p2i+4)
            {
                double p1x1 = players.get(0).getLineCoords().get(0+p1i);
                double p1y1 = players.get(0).getLineCoords().get(1+p1i);
                double p1x2 = players.get(0).getLineCoords().get(2+p1i);
                double p1y2 = players.get(0).getLineCoords().get(3+p1i);

                double p2x1 = players.get(1).getLineCoords().get(0+p2i);
                double p2y1 = players.get(1).getLineCoords().get(1+p2i);
                double p2x2 = players.get(1).getLineCoords().get(2+p2i);
                double p2y2 = players.get(1).getLineCoords().get(3+p2i);

                if(p1x1 == p1x2 && p1y1 == p1y2 || p2x1 == p2x2 && p2y1 == p2y2)
                {
                    continue;   //so the game won't finish when the line is just a point
                }

                if(Line2D.linesIntersect(p1x1, p1y1,
                        p1x2, p1y2,
                        p2x1, p2y1,
                        p2x2, p2y2))
                {
                    double distanceFromP1 = Line2D.ptSegDist(p2x1, p2y1, p2x2, p2y2, p1x2, p1y2);
                    double distanceFromP2 = Line2D.ptSegDist(p1x1, p1y1, p1x2, p1y2, p2x2, p2y2);

                    if(distanceFromP1 < distanceFromP2) //to determine who is the winner
                    {
                        return Winner.PLAYER_1;
                    }
                    else
                    {
                        return Winner.PLAYER_2;
                    }
                }
            }
        }

        for(int p1i=0; p1i<=players.get(0).getLineCoords().size()-1; p1i=p1i+4)
        {   //player 2 will lose if he touches its own trace
            for(int p2i=0; p2i<players.get(0).getLineCoords().size(); p2i=p2i+4)
            {
                double p1x1 = players.get(0).getLineCoords().get(0+p1i);
                double p1y1 = players.get(0).getLineCoords().get(1+p1i);
                double p1x2 = players.get(0).getLineCoords().get(2+p1i);
                double p1y2 = players.get(0).getLineCoords().get(3+p1i);

                double p2x1 = players.get(0).getLineCoords().get(0+p2i);
                double p2y1 = players.get(0).getLineCoords().get(1+p2i);
                double p2x2 = players.get(0).getLineCoords().get(2+p2i);
                double p2y2 = players.get(0).getLineCoords().get(3+p2i);

                if(p2i == p1i || p1i == p2i + 4 || p1i + 4 == p2i || p1x1 == p1x2 && p1y1 == p1y2 || p2x1 == p2x2 && p2y1 == p2y2)
                {
                    continue;   //so the game won't finish when the line is just a point
                }

                if(Line2D.linesIntersect(p1x1, p1y1,
                        p1x2, p1y2,
                        p2x1, p2y1,
                        p2x2, p2y2))
                {
                    return Winner.PLAYER_1;
                }
            }
        }

        for(int p1i=0; p1i<=players.get(1).getLineCoords().size()-1; p1i=p1i+4)
        {   //player 1 will lose if he touches its own trace
            for(int p2i=0; p2i<players.get(1).getLineCoords().size(); p2i=p2i+4)
            {
                double p1x1 = players.get(1).getLineCoords().get(0+p1i);
                double p1y1 = players.get(1).getLineCoords().get(1+p1i);
                double p1x2 = players.get(1).getLineCoords().get(2+p1i);
                double p1y2 = players.get(1).getLineCoords().get(3+p1i);

                double p2x1 = players.get(1).getLineCoords().get(0+p2i);
                double p2y1 = players.get(1).getLineCoords().get(1+p2i);
                double p2x2 = players.get(1).getLineCoords().get(2+p2i);
                double p2y2 = players.get(1).getLineCoords().get(3+p2i);

                if(p2i == p1i || p1i == p2i + 4 || p1i + 4 == p2i || p1x1 == p1x2 && p1y1 == p1y2 || p2x1 == p2x2 && p2y1 == p2y2)
                {
                    continue;
                }

                if(Line2D.linesIntersect(p1x1, p1y1,
                        p1x2, p1y2,
                        p2x1, p2y1,
                        p2x2, p2y2))
                {
                    return Winner.PLAYER_2;
                }
            }
        }

        double p1PosX = players.get(0).getLineCoords().get(players.get(0).getx2());
        double p1PosY = players.get(0).getLineCoords().get(players.get(0).gety2());
        if (p1PosX > boardWidth || p1PosX < 0 || p1PosY > boardHeight || p1PosY < 0)
        {
            return Winner.PLAYER_1;
        }

        double p2PosX = players.get(1).getLineCoords().get(players.get(1).getx2());
        double p2PosY = players.get(1).getLineCoords().get(players.get(1).gety2());
        if (p2PosX > boardWidth || p2PosX < 0 || p2PosY > boardHeight || p2PosY < 0)
        {
            return Winner.PLAYER_2;
        }
        return Winner.NONE;
    }

    public void keepScore()
    {
        if(isThereAWinner()==Winner.PLAYER_1)
        {
            database.increaseScore(players.get(0).getName());
        }
        if(isThereAWinner()==Winner.PLAYER_2)
        {
            database.increaseScore(players.get(1).getName());
        }
    }
}