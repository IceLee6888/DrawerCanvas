package businesslogic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class DrawMain extends JFrame{
    //声明颜色属性，并赋默认值
    public Color selectedColor = Color.BLACK;

    public String currentFunction="曲线";

    public JPanel panelcenter;

    public Graphics2D g;

    public boolean isLabelShow = true;
    //容器
    ArrayList<Shape> shapeList = new ArrayList<Shape>();
    ArrayList<LabelText> labelList = new ArrayList<LabelText>();

    public void InitUI()throws Exception{
        this.setSize(1000,780);
        this.setTitle("简单画板识别");
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        addMenu();

        //窗体添加主面板
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.add(panel);

        //实例化事件监听类
        ButtonListener bl = new ButtonListener(this);

        //实现中间面板
        panelcenter = new JPanel(){
            public void paint(Graphics g1) {
                g=(Graphics2D)g1;
                //画船体
                super.paint(g);
                for (int i = 0; i < shapeList.size(); i++) {
                    Shape shape =(Shape) shapeList.get(i);
                    shape.Draw(g);
                }
                if(isLabelShow){
                    for (int i = 0; i < labelList.size(); i++) {
                        LabelText label = (LabelText)labelList.get(i);
                        label.identify(g);
                    }
                }
            }
        };
        panelcenter.setBackground(Color.WHITE);
        panel.add(panelcenter);

        //实现性状面板
        JPanel FunctionPanel=new JPanel();
        FunctionPanel.setBackground(Color.WHITE);
        FunctionPanel.setPreferredSize(new Dimension(1000,30));
        FunctionPanel.setLayout(new FlowLayout(FlowLayout.CENTER,0,0));
        panel.add(FunctionPanel,BorderLayout.NORTH);

        String [] Func={"曲线","橡皮擦","标注",};
        for(int i=0;i<Func.length;i++){
            JButton button=new JButton(Func[i]);
            button.setBackground(Color.WHITE);
            button.addActionListener(bl);
            FunctionPanel.add(button);
        }


        //实现颜色面板
        JPanel ColorPanel=new JPanel();
        ColorPanel.setBackground(Color.black);
        ColorPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        ColorPanel.setBackground(Color.gray);;
        Color [] color={Color.BLACK,Color.blue,Color.white,Color.gray,Color.red,Color.CYAN,Color.green,Color.darkGray,Color.pink};
        for(int i=0;i<color.length;i++){
            JButton button=new JButton();
            button.setPreferredSize(new Dimension(30,30));
            button.setBackground(color[i]);
            button.addActionListener(bl);
            ColorPanel.add(button);
        }
        panel.add(ColorPanel,BorderLayout.SOUTH);


        this.setVisible(true);

        g=(Graphics2D)panelcenter.getGraphics();

        DrawListener dl =new DrawListener(g,this, shapeList, labelList);

        panelcenter.addMouseListener(dl);

        panelcenter.addMouseMotionListener(dl);
    }

    public void addMenu(){
        //菜单条对象创建
        JMenuBar bar = new JMenuBar();
        //菜单创建
        JMenu menu=new JMenu("文件");
        menu.setBackground(Color.white);
        //菜单项监听器创建
        MenuItemListener mil=new MenuItemListener(this);
        //创建三个菜单项
        JMenuItem newFileItem= new JMenuItem("新建");
        JMenuItem openFileItem= new JMenuItem("打开");
        JMenuItem saveFileItem= new JMenuItem("保存");
        //给每个菜单项添加监听器
        newFileItem.addActionListener(mil);
        openFileItem.addActionListener(mil);
        saveFileItem.addActionListener(mil);
        //将菜单条添加到窗体上
        this.setJMenuBar(bar);
        //将菜单添加到菜单条上
        bar.add(menu);
        //将菜单项添加到菜单上
        menu.add(newFileItem);
        menu.add(openFileItem);
        menu.add(saveFileItem);

        JMenu labelMenu = new JMenu("标注");
        labelMenu.setBackground(Color.white);
        JMenuItem showLabelItem = new JMenuItem("显示标注");
        JMenuItem hideLabelItem = new JMenuItem("隐藏标注");
        showLabelItem.addActionListener(mil);
        hideLabelItem.addActionListener(mil);
        bar.add(labelMenu);
        labelMenu.add(showLabelItem);
        labelMenu.add(hideLabelItem);
    }

}

