package PaooGame.Entity;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wolf extends Enemy{
    private static final int NO_SPRITES = 5;        /*!< Numarul de sprite-uri pt fiecare animatie.*/
    private static final int TILE_WIDTH = 100;      /*!< Latimea unei animatii.*/
    private static final int TILE_HEIGHT = 100;      /*!< Inaltimea unei animatii.*/
    private static final int SPRITE_SPEED = 6;      /*!< Timpul la care se schimba animatiile.*/
    private static final int DELAY_TIME = 30;      /*!< Timpul de asteptare intre atac si alte actiuni.*/

    private int delay;
    public Wolf(int x, int y) {
        super(40, 15, 0, 4, x, y, "Wolf", TILE_WIDTH, TILE_HEIGHT, SPRITE_SPEED, NO_SPRITES, 450);
        solidArea = new Rectangle(10, 65, 80, 35);
    }
    public Wolf(int life, int damage, int critical, int speed, int x, int y) {
        super(life, damage, critical, speed, x, y, "Wolf", TILE_WIDTH, TILE_HEIGHT, SPRITE_SPEED, NO_SPRITES, 450);
        solidArea = new Rectangle(10, 65, 80, 35);
    }

    @Override
    protected boolean TryAttack(Player player) {
        int x, y, width, height;

        // Daca actiunea anterioara necesita un delay
        if (delay<DELAY_TIME){
            delay++;
            return false;
        }

        delay = 0; // daca delay-ul s-a tratat, se reseteaza
        if (action.contains("Right")){
            x = this.x + solidArea.x + solidArea.width;
            y = this.y + solidArea.y+20;
            width = 20;
            height = solidArea.height-20;

        }else {
            x = this.x + solidArea.x+40;
            y = this.y + solidArea.y+20;
            width = 20;
            height = solidArea.height-20;
        }

        Rectangle rect = new Rectangle(x, y, width, height);
        if (collision.TryAttackPlayer(player, rect)){
            return true;
        }
        return false;
    }

    @Override
    protected void Sprites() {
        IdleRight = new BufferedImage[NO_SPRITES];
        IdleRight[0] = Assets.wolfIdleRight1;
        IdleRight[1] = Assets.wolfIdleRight2;
        IdleRight[2] = Assets.wolfIdleRight3;
        IdleRight[3] = Assets.wolfIdleRight4;
        IdleRight[4] = Assets.wolfIdleRight5;

        IdleLeft = new BufferedImage[NO_SPRITES];
        IdleLeft[0] = Assets.wolfIdleLeft1;
        IdleLeft[1] = Assets.wolfIdleLeft2;
        IdleLeft[2] = Assets.wolfIdleLeft3;
        IdleLeft[3] = Assets.wolfIdleLeft4;
        IdleLeft[4] = Assets.wolfIdleLeft5;

        WalkRight = new BufferedImage[NO_SPRITES];
        WalkRight[0] = Assets.wolfWalkRight1;
        WalkRight[1] = Assets.wolfWalkRight2;
        WalkRight[2] = Assets.wolfWalkRight3;
        WalkRight[3] = Assets.wolfWalkRight4;
        WalkRight[4] = Assets.wolfWalkRight5;

        WalkLeft = new BufferedImage[NO_SPRITES];
        WalkLeft[0] = Assets.wolfWalkLeft1;
        WalkLeft[1] = Assets.wolfWalkLeft2;
        WalkLeft[2] = Assets.wolfWalkLeft3;
        WalkLeft[3] = Assets.wolfWalkLeft4;
        WalkLeft[4] = Assets.wolfWalkLeft5;


        AttackRight = new BufferedImage[NO_SPRITES];
        AttackRight[0] = Assets.wolfAttackRight1;
        AttackRight[1] = Assets.wolfAttackRight2;
        AttackRight[2] = Assets.wolfAttackRight3;
        AttackRight[3] = Assets.wolfAttackRight4;
        AttackRight[4] = Assets.wolfAttackRight5;

        AttackLeft = new BufferedImage[NO_SPRITES];
        AttackLeft[0] = Assets.wolfAttackLeft1;
        AttackLeft[1] = Assets.wolfAttackLeft2;
        AttackLeft[2] = Assets.wolfAttackLeft3;
        AttackLeft[3] = Assets.wolfAttackLeft4;
        AttackLeft[4] = Assets.wolfAttackLeft5;

        DamageRight = new BufferedImage[NO_SPRITES];
        DamageRight[0] = Assets.wolfHitRight1;
        DamageRight[1] = Assets.wolfHitRight2;
        DamageRight[2] = Assets.wolfHitRight3;
        DamageRight[3] = Assets.wolfHitRight4;
        DamageRight[4] = Assets.wolfHitRight5;

        DamageLeft = new BufferedImage[NO_SPRITES];
        DamageLeft[0] = Assets.wolfHitLeft1;
        DamageLeft[1] = Assets.wolfHitLeft2;
        DamageLeft[2] = Assets.wolfHitLeft3;
        DamageLeft[3] = Assets.wolfHitLeft4;
        DamageLeft[4] = Assets.wolfHitLeft5;

        DeathRight = new BufferedImage[NO_SPRITES];
        DeathRight[0] = Assets.wolfDeathRight1;
        DeathRight[1] = Assets.wolfDeathRight2;
        DeathRight[2] = Assets.wolfDeathRight3;
        DeathRight[3] = Assets.wolfDeathRight4;
        DeathRight[4] = Assets.wolfDeathRight5;

        DeathLeft = new BufferedImage[NO_SPRITES];
        DeathLeft[0] = Assets.wolfDeathLeft1;
        DeathLeft[1] = Assets.wolfDeathLeft2;
        DeathLeft[2] = Assets.wolfDeathLeft3;
        DeathLeft[3] = Assets.wolfDeathLeft4;
        DeathLeft[4] = Assets.wolfDeathLeft5;
    }
}
