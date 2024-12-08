package PaooGame.Bars;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlayerStaminaBar extends Bar {
    protected PlayerStaminaBar() {
        width = 120;
        height = 32;
        noSprites = 5;
        spriteCounter = noSprites - 1;
        Sprites();
    }

    @Override
    public void Update(int stamina) {
        if (stamina < 60){
            spriteCounter = 0;
        }
        else if (stamina < 70){
            spriteCounter = 1;
        }
        else if (stamina < 80){
            spriteCounter = 2;
        }
        else if (stamina < 90){
            spriteCounter = 3;
        }
        else{
            spriteCounter = 4;
        }
    }

    @Override
    public void Draw(Graphics g){
        g.drawImage(animations[spriteCounter], 5, 50, width, height, null);
    }
    private void Sprites (){
        animations = new BufferedImage[noSprites];
        animations[0] = Assets.plStaminaBar1;
        animations[1] = Assets.plStaminaBar2;
        animations[2] = Assets.plStaminaBar3;
        animations[3] = Assets.plStaminaBar4;
        animations[4] = Assets.plStaminaBar5;
    }
}
