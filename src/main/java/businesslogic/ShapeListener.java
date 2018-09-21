package businesslogic;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.*;

public class ShapeListener extends MouseAdapter implements ActionListener{
    private int x1, y1, x2, y2;
    private Graphics2D g;
    private DrawMain df;
    public ArrayList list;
    String shape="曲线";
    private int count=0;
    Color color;

    ShapeListener(DrawMain d){
        df=d;
    }


    //获取形状和颜色
    public void actionPerformed(ActionEvent e){
        JButton button = (JButton) e.getSource();
        if(e.getActionCommand().equals("")){
            color = button.getBackground();
        }else{
            shape = button.getActionCommand();
        }
    }

    //实现画笔
    public void mousePressed(MouseEvent e) {
        g=(Graphics2D) df.getGraphics();
        g.setColor(color);
        x1=e.getX();
        y1=e.getY();
    }


    public void mouseDragged(MouseEvent e) {
        x2 = e.getX();
        y2 = e.getY();
        if (shape.equals("曲线")) {
				g.setStroke(new BasicStroke(3));
//				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        }else if(shape.equals("橡皮擦")){
            g.setStroke(new BasicStroke(80));
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setColor(Color.WHITE);
            g.drawLine(x1, y1, x2, y2);
            x1 = x2;
            y1 = y2;
        }
    }

    public void mouseReleased(MouseEvent e){
        if (shape.equals("曲线")) {
            count++;
        }
    }

    public void mouseClicked(MouseEvent e){
        x2 = e.getX();
        y2 = e.getY();
        if(shape.equals("标注")){
            String text = "未知图形";
            switch (count){
                case 0:text="未识别到图形";break;
                case 1:text="圆";count=0;break;
                case 2:text="三角形";count=0;break;
                case 3:text="长方形";count=0;break;
                default:break;
            }

            Font font = new Font("黑体", Font.PLAIN, 20);//字体，粗体，斜体，大小
            g.setFont(font);
            g.drawString(text, x2, y2); // 绘制文本
        }
    }
}