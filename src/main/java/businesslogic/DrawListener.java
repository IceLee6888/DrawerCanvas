package businesslogic;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawListener implements MouseListener,MouseMotionListener {
    public Graphics2D g;
    public int x1,y1,x2,y2;
    public Color color;
    public DrawMain dm;
    public ArrayList shapeList;
    public ArrayList labelList;
    private int count=0;

    //构造函数3
    public DrawListener(Graphics g2, DrawMain dm1,ArrayList shapeList,ArrayList labelList) {
        g = (Graphics2D)g2;
        dm = dm1;
        this.shapeList = shapeList;
        this.labelList = labelList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        String command = dm.currentFunction;
        if(command.equals("标注")){
            String text = "未知图形";
            switch (count){
                case 0:text="未识别到图形";break;
                case 1:text="圆";count=0;break;
                case 2:text="三角形";count=0;break;
                case 3:text="长方形";count=0;break;
                default:break;
            }
            Font font = new Font("黑体", Font.PLAIN, 20);
            LabelText label = new LabelText(x2,y2,font,g.getColor(),text);
            label.identify(g);
            labelList.add(label);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        g.setColor(color);
        x1=e.getX();
        y1=e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        String command = dm.currentFunction;
        if (command.equals("曲线")) {
            count++;
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        color = dm.selectedColor;
        g.setColor(color);
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        String command = dm.currentFunction;
        if (command.equals("曲线")) {
            Shape line = new Line(x1, y1, x2, y2,g.getColor(),3);
            line.Draw(g);
            shapeList.add(line);
            x1 = x2;
            y1 = y2;
        }else if(command.equals("橡皮擦")){
            dm.selectedColor = Color.WHITE;
            g.setStroke(new BasicStroke(10));
            g.setColor(dm.selectedColor);

            Shape line = new Line(x1, y1, x2, y2,g.getColor(),80);
            line.Draw(g);
            shapeList.add(line);
            x1 = x2;
            y1 = y2;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
