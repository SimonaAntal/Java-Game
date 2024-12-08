package PaooGame.Buttons;

import PaooGame.Graphics.Assets;
import PaooGame.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Button extends JButton {
    public static final int BUTTON_WIDTH = 128;
    public static final int BUTTON_HEIGHT = 64;
    private static final int TEXT_SIZE = 15;
    private int x;
    private int y;
    private BufferedImage img;
    private String text;
    private Font font;

    public Button(String text, int x, int y){
        this.x = x;
        this.y = y;
        this.img = Assets.button;
        this.text = text;
        this.font = new Font("Helvetica", Font.BOLD, TEXT_SIZE);
    }

    public boolean Hover(int mouseX, int mouseY){
        return mouseX >= x && mouseX <= x + BUTTON_WIDTH && mouseY >= y && mouseY <= y + BUTTON_HEIGHT;
    }

    public void Draw(Graphics g) {
        g.drawImage(img, x, y, BUTTON_WIDTH, BUTTON_HEIGHT, null);

        g.setFont(font);
        g.setColor(Color.WHITE);;
        g.drawString(text, x+20, y+(BUTTON_HEIGHT+TEXT_SIZE)/2);
    }

}
