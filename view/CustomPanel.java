// Yusuf Erman ERGÖZ
// Tron
// 2018/12/24 17:29:18

package view;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CustomPanel extends JPanel
{
    private Color lineColorP1;
    private Color lineColorP2;
    public static List<Integer> linesOfPlayer1= new ArrayList<>();
    public static List<Integer> linesOfPlayer2= new ArrayList<>();

    public CustomPanel()
    {
        setBackground(Color.DARK_GRAY);
    }

    public void setLineCoordinates(List<Integer> _linesOfPlayer1, List<Integer> _linesOfPlayer2, Color _lineColorP1, Color _lineColorP2)
    {
        this.linesOfPlayer1 = _linesOfPlayer1;
        this.linesOfPlayer2 = _linesOfPlayer2;
        this.lineColorP1 = _lineColorP1;
        this.lineColorP2 = _lineColorP2;
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(7));   //for thicker line

        for (int i = 0; i < linesOfPlayer1.size(); i=i+4)   //for player 1
        {
            g.setColor(lineColorP1);
            g.drawLine(linesOfPlayer1.get(0+i), linesOfPlayer1.get(1+i), linesOfPlayer1.get(2+i), linesOfPlayer1.get(3+i));
        }
        for (int i = 0; i < linesOfPlayer2.size(); i=i+4)   //for player 2
        {
            g.setColor(lineColorP2);
            g.drawLine(linesOfPlayer2.get(0+i), linesOfPlayer2.get(1+i), linesOfPlayer2.get(2+i), linesOfPlayer2.get(3+i));
        }
    }
}