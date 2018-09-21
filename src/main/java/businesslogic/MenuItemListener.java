package businesslogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MenuItemListener implements ActionListener {

    public DrawMain paint;

    //构造方法
    public MenuItemListener(DrawMain paint) {
        this.paint = paint;
    }


    public void actionPerformed(ActionEvent e){
        //判断是哪个菜单项被按下
        String command =e.getActionCommand();

        /*
         * 新建逻辑实现：
         * 当新建的时候，只需要把容器里面所有的对象清空，然后将中间面板重绘就可以了
         */
        if("新建".equals(command)){

            int value=JOptionPane.showConfirmDialog(null, "是否需要保存当前文件？", "提示信息", 0);
            if(value==0){

                saveFile();
            }
            if(value==1){
                paint.shapeList.removeAll(paint.shapeList);
                paint.panelcenter.repaint();
            }
        }
        /*
         * 打开逻辑实现：
         * 当点击打开菜单项时，首先应该清空容器里面的东西，然后面板重绘
         * 然后再把打开的文件利用对象输入流读入
         * 将读入的信息取出来，转换成相应的图形对象
         * 将取出来的图形对象添加到容器里面
         * 调用中间画板，将取出来的图形进行绘制
         */
        else if("打开".equals(command)){

            int value=JOptionPane.showConfirmDialog(null, "是否需要保存当前文件？", "提示信息", 0);
            if(value==0){

                saveFile();
            }
            if(value==1){
                //清空容器里面的东西
                paint.shapeList.removeAll(paint.shapeList);
                paint.labelList.removeAll(paint.labelList);
                paint.panelcenter.repaint();

                try {
                    //弹出选择对话框，选择需要读入的文件
                    JFileChooser chooser = new JFileChooser();
                    chooser.showOpenDialog(null);
                    File file = chooser.getSelectedFile();
                    //如果为选中文件
                    if(file == null){
                        JOptionPane.showMessageDialog(null, "没有选择文件");
                    }
                    else {
                        //选中了相应的文件，则柑橘选中的文件创建对象输入流
                        FileInputStream fis = new FileInputStream(file);
                        ObjectInputStream ois = new ObjectInputStream(fis);
                        //将读出来的对象转换成父类对象的容器进行接收
                        ArrayList<Shape> shapeList =(ArrayList<Shape>)ois.readObject();
                        //遍历容器里面的具体对象，将取出来的对象保存到容器里面
                        for (int i = 0; i <shapeList.size(); i++) {
                            Shape shape = (Shape)shapeList.get(i);
                            paint.shapeList.add(shape);
                        }
                        ArrayList<LabelText> labelList = (ArrayList<LabelText>)ois.readObject();
                        for (int i = 0; i <labelList.size(); i++) {
                            LabelText label = (LabelText) labelList.get(i);
                            paint.labelList.add(label);
                        }
                        paint.panelcenter.repaint();
                        ois.close();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }else if("保存".equals(command)){
            saveFile();
        }
        else if("显示标注".equals(command)){
            paint.isLabelShow = true;
            paint.panelcenter.repaint();
        }
        else if ("隐藏标注".equals(command)){
            paint.isLabelShow = false;
            paint.panelcenter.repaint();
        }

    }

    public void saveFile(){
        //选择要保存的位置以及文件名字和信息
        JFileChooser chooser = new JFileChooser();
        chooser.showSaveDialog(null);
        File file =chooser.getSelectedFile();

        if(file==null){
            JOptionPane.showMessageDialog(null, "没有选择文件");
        }else {

            try {
                //根据要保存的文件创建对象输出流
                FileOutputStream fis = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fis);
                //将容器里面所绘制的图形利用对象流全部写入选中的文件中
                oos.writeObject(paint.shapeList);
                oos.writeObject(paint.labelList);
                JOptionPane.showMessageDialog(null, "保存成功！");
                oos.close();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

}
