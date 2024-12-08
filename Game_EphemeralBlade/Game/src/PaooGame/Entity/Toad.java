package PaooGame.Entity;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Toad extends Enemy{
    private static final int NO_SPRITES = 6;        /*!< Numarul de sprite-uri pt fiecare animatie.*/
    private static final int TILE_WIDTH = 160;      /*!< Latimea unei animatii.*/
    private static final int TILE_HEIGHT = 128;      /*!< Inaltimea unei animatii.*/
    private static final int SPRITE_SPEED = 15;      /*!< Timpul la care se schimba animatiile.*/
    private static final int DELAY_TIME = 20;      /*!< Timpul de asteptare intre atac si alte actiuni.*/

    private int delay;
    public Toad(int x, int y) {
        super(50, 10, 0, 3, x, y, "Toad", TILE_WIDTH, TILE_HEIGHT, SPRITE_SPEED, NO_SPRITES, 500);
        solidArea = new Rectangle(70, 60, 30, 30);
    }
    public Toad(int life, int damage, int critical, int speed, int x, int y) {
        super(life, damage, critical, speed, x, y, "Toad", TILE_WIDTH, TILE_HEIGHT, SPRITE_SPEED, NO_SPRITES, 500);
        solidArea = new Rectangle(70, 60, 30, 30);
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
            y = this.y + solidArea.y;
            width = TILE_WIDTH - solidArea.width + 100;
            height = solidArea.height;

        }else {
            x = this.x + solidArea.x+25;
            y = this.y + 60;
            width = TILE_WIDTH - solidArea.width - 125;
            height = TILE_HEIGHT - 90;
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
        IdleRight[0] = Assets.toadIdleRight1;
        IdleRight[1] = Assets.toadIdleRight2;
        IdleRight[2] = Assets.toadIdleRight3;
        IdleRight[3] = Assets.toadIdleRight4;
        IdleRight[4] = Assets.toadIdleRight5;
        IdleRight[5] = Assets.toadIdleRight6;

        IdleLeft = new BufferedImage[NO_SPRITES];
        IdleLeft[0] = Assets.toadIdleLeft1;
        IdleLeft[1] = Assets.toadIdleLeft2;
        IdleLeft[2] = Assets.toadIdleLeft3;
        IdleLeft[3] = Assets.toadIdleLeft4;
        IdleLeft[4] = Assets.toadIdleLeft5;
        IdleLeft[5] = Assets.toadIdleLeft6;

        WalkRight = new BufferedImage[NO_SPRITES];
        WalkRight[0] = Assets.toadWalkRight1;
        WalkRight[1] = Assets.toadWalkRight2;
        WalkRight[2] = Assets.toadWalkRight3;
        WalkRight[3] = Assets.toadWalkRight4;
        WalkRight[4] = Assets.toadWalkRight5;
        WalkRight[5] = Assets.toadWalkRight6;

        WalkLeft = new BufferedImage[NO_SPRITES];
        WalkLeft[0] = Assets.toadWalkLeft1;
        WalkLeft[1] = Assets.toadWalkLeft2;
        WalkLeft[2] = Assets.toadWalkLeft3;
        WalkLeft[3] = Assets.toadWalkLeft4;
        WalkLeft[4] = Assets.toadWalkLeft5;
        WalkLeft[5] = Assets.toadWalkLeft6;


        AttackRight = new BufferedImage[NO_SPRITES];
        AttackRight[0] = Assets.toadAttackRight1;
        AttackRight[1] = Assets.toadAttackRight2;
        AttackRight[2] = Assets.toadAttackRight3;
        AttackRight[3] = Assets.toadAttackRight4;
        AttackRight[4] = Assets.toadAttackRight5;
        AttackRight[5] = Assets.toadAttackRight6;

        AttackLeft = new BufferedImage[NO_SPRITES];
        AttackLeft[0] = Assets.toadAttackLeft1;
        AttackLeft[1] = Assets.toadAttackLeft2;
        AttackLeft[2] = Assets.toadAttackLeft3;
        AttackLeft[3] = Assets.toadAttackLeft4;
        AttackLeft[4] = Assets.toadAttackLeft5;
        AttackLeft[5] = Assets.toadAttackLeft6;

        DamageRight = new BufferedImage[NO_SPRITES];
        DamageRight[0] = Assets.toadHitRight1;
        DamageRight[1] = Assets.toadHitRight2;
        DamageRight[2] = Assets.toadHitRight3;
        DamageRight[3] = Assets.toadHitRight4;
        DamageRight[4] = Assets.toadHitRight5;
        DamageRight[5] = Assets.toadHitRight6;

        DamageLeft = new BufferedImage[NO_SPRITES];
        DamageLeft[0] = Assets.toadHitLeft1;
        DamageLeft[1] = Assets.toadHitLeft2;
        DamageLeft[2] = Assets.toadHitLeft3;
        DamageLeft[3] = Assets.toadHitLeft4;
        DamageLeft[4] = Assets.toadHitLeft5;
        DamageLeft[5] = Assets.toadHitLeft6;

        DeathRight = new BufferedImage[NO_SPRITES];
        DeathRight[0] = Assets.toadDeathRight1;
        DeathRight[1] = Assets.toadDeathRight2;
        DeathRight[2] = Assets.toadDeathRight3;
        DeathRight[3] = Assets.toadDeathRight4;
        DeathRight[4] = Assets.toadDeathRight5;
        DeathRight[5] = Assets.toadDeathRight6;

        DeathLeft = new BufferedImage[NO_SPRITES];
        DeathLeft[0] = Assets.toadDeathLeft1;
        DeathLeft[1] = Assets.toadDeathLeft2;
        DeathLeft[2] = Assets.toadDeathLeft3;
        DeathLeft[3] = Assets.toadDeathLeft4;
        DeathLeft[4] = Assets.toadDeathLeft5;
        DeathLeft[5] = Assets.toadDeathLeft6;
    }
}
