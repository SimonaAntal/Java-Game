package PaooGame.Entity;

import PaooGame.GameState;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Viking extends Enemy{
    private static final int NO_SPRITES = 6;        /*!< Numarul de sprite-uri pt fiecare animatie.*/
    private static final int TILE_WIDTH = 160;      /*!< Latimea unei animatii.*/
    private static final int TILE_HEIGHT = 80;      /*!< Inaltimea unei animatii.*/
    private static final int SPRITE_SPEED = 10;      /*!< Timpul la care se schimba animatiile.*/
    private static final int DELAY_TIME = 40;      /*!< Timpul de asteptare intre atac si alte actiuni.*/

    private int delay;
    public Viking(int x, int y) {
        super(60, 10, 0, 2, x, y, "Viking", TILE_WIDTH, TILE_HEIGHT, SPRITE_SPEED, NO_SPRITES, 500);
        solidArea = new Rectangle(70, 50, 30, 30);
    }
    public Viking(int life, int damage, int critical, int speed, int x, int y) {
        super(life, damage, critical, speed, x, y, "Viking", TILE_WIDTH, TILE_HEIGHT, SPRITE_SPEED, NO_SPRITES, 500);
        solidArea = new Rectangle(70, 50, 30, 30);
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
            x = this.x + solidArea.x + solidArea.width+30;
            y = this.y + 40;
            width = TILE_WIDTH - solidArea.x - solidArea.width-20;
            height = TILE_HEIGHT - 40;

        }else {
            x = this.x + solidArea.x;
            y = this.y + 40;
            width = TILE_WIDTH - solidArea.width - 40;
            height = TILE_HEIGHT - 40;
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
        IdleRight[0] = Assets.vikingIdleRight1;
        IdleRight[1] = Assets.vikingIdleRight2;
        IdleRight[2] = Assets.vikingIdleRight3;
        IdleRight[3] = Assets.vikingIdleRight4;
        IdleRight[4] = Assets.vikingIdleRight5;
        IdleRight[5] = Assets.vikingIdleRight6;

        IdleLeft = new BufferedImage[NO_SPRITES];
        IdleLeft[0] = Assets.vikingIdleLeft1;
        IdleLeft[1] = Assets.vikingIdleLeft2;
        IdleLeft[2] = Assets.vikingIdleLeft3;
        IdleLeft[3] = Assets.vikingIdleLeft4;
        IdleLeft[4] = Assets.vikingIdleLeft5;
        IdleLeft[5] = Assets.vikingIdleLeft6;

        WalkRight = new BufferedImage[NO_SPRITES];
        WalkRight[0] = Assets.vikingWalkRight1;
        WalkRight[1] = Assets.vikingWalkRight2;
        WalkRight[2] = Assets.vikingWalkRight3;
        WalkRight[3] = Assets.vikingWalkRight4;
        WalkRight[4] = Assets.vikingWalkRight5;
        WalkRight[5] = Assets.vikingWalkRight6;

        WalkLeft = new BufferedImage[NO_SPRITES];
        WalkLeft[0] = Assets.vikingWalkLeft1;
        WalkLeft[1] = Assets.vikingWalkLeft2;
        WalkLeft[2] = Assets.vikingWalkLeft3;
        WalkLeft[3] = Assets.vikingWalkLeft4;
        WalkLeft[4] = Assets.vikingWalkLeft5;
        WalkLeft[5] = Assets.vikingWalkLeft6;


        AttackRight = new BufferedImage[NO_SPRITES];
        AttackRight[0] = Assets.vikingAttackRight1;
        AttackRight[1] = Assets.vikingAttackRight2;
        AttackRight[2] = Assets.vikingAttackRight3;
        AttackRight[3] = Assets.vikingAttackRight4;
        AttackRight[4] = Assets.vikingAttackRight5;
        AttackRight[5] = Assets.vikingAttackRight6;

        AttackLeft = new BufferedImage[NO_SPRITES];
        AttackLeft[0] = Assets.vikingAttackLeft1;
        AttackLeft[1] = Assets.vikingAttackLeft2;
        AttackLeft[2] = Assets.vikingAttackLeft3;
        AttackLeft[3] = Assets.vikingAttackLeft4;
        AttackLeft[4] = Assets.vikingAttackLeft5;
        AttackLeft[5] = Assets.vikingAttackLeft6;

        DamageRight = new BufferedImage[NO_SPRITES];
        DamageRight[0] = Assets.vikingHitRight1;
        DamageRight[1] = Assets.vikingHitRight2;
        DamageRight[2] = Assets.vikingHitRight3;
        DamageRight[3] = Assets.vikingHitRight4;
        DamageRight[4] = Assets.vikingHitRight5;
        DamageRight[5] = Assets.vikingHitRight6;

        DamageLeft = new BufferedImage[NO_SPRITES];
        DamageLeft[0] = Assets.vikingHitLeft1;
        DamageLeft[1] = Assets.vikingHitLeft2;
        DamageLeft[2] = Assets.vikingHitLeft3;
        DamageLeft[3] = Assets.vikingHitLeft4;
        DamageLeft[4] = Assets.vikingHitLeft5;
        DamageLeft[5] = Assets.vikingHitLeft6;

        DeathRight = new BufferedImage[NO_SPRITES];
        DeathRight[0] = Assets.vikingDeathRight1;
        DeathRight[1] = Assets.vikingDeathRight2;
        DeathRight[2] = Assets.vikingDeathRight3;
        DeathRight[3] = Assets.vikingDeathRight4;
        DeathRight[4] = Assets.vikingDeathRight5;
        DeathRight[5] = Assets.vikingDeathRight6;

        DeathLeft = new BufferedImage[NO_SPRITES];
        DeathLeft[0] = Assets.vikingDeathLeft1;
        DeathLeft[1] = Assets.vikingDeathLeft2;
        DeathLeft[2] = Assets.vikingDeathLeft3;
        DeathLeft[3] = Assets.vikingDeathLeft4;
        DeathLeft[4] = Assets.vikingDeathLeft5;
        DeathLeft[5] = Assets.vikingDeathLeft6;
    }
}
