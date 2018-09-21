package businesslogic;

import java.awt.*;
import java.io.Serializable;

public class LabelText implements Serializable {
    String text;
    Font font;
    int x1,y1;
    Color color;
    public LabelText(int x1, int y1,Font font, Color color,String text){
        this.x1=x1;
        this.y1=y1;
        this.font=font;
        this.color=color;
        this.text=text;
    }
    public void identify(Graphics2D g) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(text, x1, y1);
    }
}
