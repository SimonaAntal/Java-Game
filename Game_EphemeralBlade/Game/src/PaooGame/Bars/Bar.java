package PaooGame.Bars;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Bar {
    protected int width;
    protected int height;
    protected int noSprites;        /*!< Numarul de sprite-uri pt animatie.*/
    protected BufferedImage[] animations;
    protected int spriteCounter;


    public abstract void Update(int value);
    public abstract void Draw(Graphics g);
}
