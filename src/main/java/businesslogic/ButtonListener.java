package businesslogic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonListener implements ActionListener {
    public DrawMain dm;

    //构造函数
    public ButtonListener(DrawMain drawMain) {
        dm = drawMain;
    }

    //监听具体实现
    public void actionPerformed(ActionEvent e) {

        //拿到被选中按钮的对象
        JButton bt =(JButton)e.getSource();
        if(e.getActionCommand().equals("")){
            //拿到被选中按钮的背景颜色
            Color c= bt.getBackground();
            dm.selectedColor = c;
        }else{
            String command = bt.getActionCommand();
            //把背景颜色复制给DrawBorder中的颜色属性
            dm.currentFunction = command;
        }

    }
}
