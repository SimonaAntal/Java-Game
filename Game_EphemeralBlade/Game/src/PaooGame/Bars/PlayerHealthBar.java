package PaooGame.Bars;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerHealthBar extends Bar {
    protected PlayerHealthBar() {
        width = 192;
        height = 64;
        noSprites = 12;
        spriteCounter = 0;
        Sprites();
    }

    @Override
    public void Update(int life) {
        if (life == 0){
            spriteCounter = 11;
        }
        else if (life < 5){
            spriteCounter = 10;
        }
        else if (life < 10){
            spriteCounter = 9;
        }
        else if (life < 20){
            spriteCounter = 8;
        }
        else if (life < 30){
            spriteCounter = 7;
        }
        else if (life < 40){
            spriteCounter = 6;
        }
        else if (life < 50){
            spriteCounter = 5;
        }
        else if (life < 60){
            spriteCounter = 4;
        }
        else if (life < 70){
            spriteCounter = 3;
        }
        else if (life < 80){
            spriteCounter = 2;
        }
        else if (life < 90){
            spriteCounter = 1;
        }
        else{
            spriteCounter = 0;
        }
    }
    @Override
    public void Draw(Graphics g){
        g.drawImage(animations[spriteCounter], 0, 0, width, height, null);
    }
    private void Sprites (){
        animations = new BufferedImage[noSprites];
        animations[0] = Assets.plHealthBar1;
        animations[1] = Assets.plHealthBar2;
        animations[2] = Assets.plHealthBar3;
        animations[3] = Assets.plHealthBar4;
        animations[4] = Assets.plHealthBar5;
        animations[5] = Assets.plHealthBar6;
        animations[6] = Assets.plHealthBar7;
        animations[7] = Assets.plHealthBar8;
        animations[8] = Assets.plHealthBar9;
        animations[9] = Assets.plHealthBar10;
        animations[10] = Assets.plHealthBar11;
        animations[11] = Assets.plHealthBar12;
    }
}