// Yusuf Erman ERGÖZ
// Tron
// 2018/12/24 17:29:18

package model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Player
{
    private Color playersColor;
    private List<Integer> playersLine;
    private String name;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public Player(String _name, Color _playersColor)
    {
        this.name = _name;
        this.playersColor = _playersColor;
        this.playersLine = new ArrayList<>();
        this.x1 = 0;
        this.y1 = 1;
        this.x2 = 2;
        this.y2 = 3;
    }

    public void setInitialPoint(int initialX, int initialY)
    {
        this.playersLine.add(initialX);
        this.playersLine.add(initialY);
        this.playersLine.add(initialX);
        this.playersLine.add(initialY);
    }

    public String getName()
    {
        return this.name;
    }

    public Color getColor()
    {
        return this.playersColor;
    }

    public List<Integer> getLineCoords()
    {
        return this.playersLine;
    }

    public int getx2()
    {
        return this.x2;
    }

    public int gety2()
    {
        return this.y2;
    }

    public void createLine()
    {
        this.playersLine.add(playersLine.get(x2)); //x1
        this.playersLine.add(playersLine.get(y2)); //y1
        this.playersLine.add(playersLine.get(x2)); //x2
        this.playersLine.add(playersLine.get(y2)); //y2
        this.x1 = x1 + 4;
        this.y1 = y1 + 4;
        this.x2 = x2 + 4;
        this.y2 = y2 + 4;
    }
}