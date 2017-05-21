package points;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
  
public class DrawingPoints
{
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(new PointPanel());
        f.setSize(400,400);
        f.setLocation(200,200);
        f.setVisible(true);
        JRadioButton rbtn1=new JRadioButton();
        JRadioButton rbtn2=new JRadioButton();
        JRadioButton rbtn3=new JRadioButton();
        f.getContentPane().add(rbtn1);
        f.getContentPane().add(rbtn2);
        f.getContentPane().add(rbtn3);
        rbtn1.setLocation(0, 5);
        rbtn2.setLocation(25, 5);
        rbtn3.setLocation(50, 5);
        rbtn1.setVisible(true);
        rbtn2.setVisible(true);
        rbtn3.setVisible(true);
    }
}
  
class PointPanel extends JPanel
{
    List pointList;
    Color selectedColor;
    Ellipse2D selectedPoint;
  
    public PointPanel()
    {
        pointList = new ArrayList();
        selectedColor = Color.red;
        addMouseListener(new PointLocater(this));
        setBackground(Color.white);
    }
  
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        Ellipse2D e;
        Color color;
        for(int j = 0; j < pointList.size(); j++)
        {
            e = (Ellipse2D)pointList.get(j);
            if(e == selectedPoint)
                color = selectedColor;
            else
                color = Color.blue;
            g2.setPaint(color);
            g2.fill(e);
        }
    }
  
    public List getPointList()
    {
        return pointList;
    }
  
    public void setSelectedPoint(Ellipse2D e)
    {
        selectedPoint = e;
        repaint();
    }
  
    public void addPoint(Point p)
    {
        Ellipse2D e = new Ellipse2D.Double(p.x - 3, p.y - 3, 6, 6);
        pointList.add(e);
        selectedPoint = null;
        repaint();
    }
}
  
class PointLocater extends MouseAdapter
{
    PointPanel pointPanel;
  
    public PointLocater(PointPanel pp)
    {
        pointPanel = pp;
    }
  
    public void mousePressed(MouseEvent e)
    {
        Point p = e.getPoint();
        boolean haveSelection = false;
        List list = pointPanel.getPointList();
        Ellipse2D ellipse;
        for(int j = 0; j < list.size(); j++)
        {
            ellipse = (Ellipse2D)list.get(j);
            if(ellipse.contains(p))
            {
                pointPanel.setSelectedPoint(ellipse);
                haveSelection = true;
                break;
            }
        }
        if(!haveSelection)
            pointPanel.addPoint(p);
    }
}