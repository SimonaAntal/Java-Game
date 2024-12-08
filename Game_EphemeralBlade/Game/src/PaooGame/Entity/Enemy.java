package PaooGame.Entity;

import PaooGame.Tiles.Map;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Enemy extends Entity {
    private final int NO_SPRITES;        /*!< Numarul de sprite-uri pt fiecare animatie.*/
    private final int SPRITE_SPEED;      /*!< Timpul la care se schimba animatiile.*/
    private final int FOLLOW_RANGE;      /*!< Timpul la care se schimba animatiile.*/
    protected boolean pause;                          /*!< Pause == true daca animatia opreste restul actiunilor.*/
    protected boolean isHit = false;

    // animatii
    protected BufferedImage[] IdleRight;
    protected BufferedImage[] IdleLeft;
    protected BufferedImage[] WalkRight;
    protected BufferedImage[] WalkLeft;
    protected BufferedImage[] AttackRight;
    protected BufferedImage[] AttackLeft;
    protected BufferedImage[] DamageRight;
    protected BufferedImage[] DamageLeft;
    protected BufferedImage[] DeathRight;
    protected BufferedImage[] DeathLeft;


    public Enemy(int life, int damage, int critical, int speed, int x, int y, String name, int width, int height, int spriteSpeed, int noSprites, int followRange){
        super(life, damage, critical, speed, x, y, name, "idleLeft", true, width, height);
        SPRITE_SPEED = spriteSpeed;
        NO_SPRITES = noSprites;
        FOLLOW_RANGE = followRange;
        Sprites();
        pause = false;

        number++; // Creste numarul inamicilor
    }

    protected void Update(Map map, Player player){
        if (inMenu == false){ // Daca jucatorul e in meniu, nu se face update
            if (life <= 0){ // Daca viata a scazut sub 0, se seteaza animatia de death i
                if (alive) {
                    spriteCounter = 0; // resetam counter-ul pentru ca animatia sa inceapa de la 0
                    alive = false;
                    number--; // Decrementam numarul total al inamicilor
                    map.EnemyDrop(name, x, y);
                }
                if (action.contains("Right")) {
                    action = "deathRight";
                } else {
                    action = "deathLeft";
                }
            }
            else{
                if (isHit == true) { // Daca caracterul isi ia damage, oprim restul actiunilor
                    if (pause == false) {
                        pause = true;
                        spriteCounter = 0;
                    }
                        if (action.contains("Right")) {
                            action = "damageRight";
                        } else {
                            action = "damageLeft";
                        }
                }

                if (pause == false){ // Daca pause este true, caracterul este deja intr-o actiune
                    if (action.contains("Right")) {
                        action = "idleRight";
                    } else {
                        action = "idleLeft";
                    }
                    if (TryAttack(player)){ // Daca caracterul poate ataca, dam pauza la restul actiunilor si realizam atacul
                        player.life = player.life - damage + player.shield;
                        pause = true;
                        spriteCounter = 0;
                        if (action.contains("Right")) {
                            action = "attackRight";
                        } else {
                            action = "attackLeft";
                        }
                    }else { // Daca nu poate ataca, incercam urmarirea jucatorului
                        FollowPlayer(player);
                    }
                }
            }
            Actions();
        }
    }

    protected void FollowPlayer(Player player){
        // FOLLOW_RANGE == 0 pt inamicii care stau pe loc
        if (Math.abs(x - player.x) < FOLLOW_RANGE && FOLLOW_RANGE != 0){ // Daca jucatorul se afla la o distanta mai mica de 500 pixeli, il urmarim
            if (player.x + player.solidArea.x + player.solidArea.width < x){ // jucatorul e in stanga
                x -= speed;
                if (collision.CheckTile(this) || collision.CheckObject(this)){
                    x += speed;
                }

                    action = "walkLeft";

            }else if (player.x > x + solidArea.x + solidArea.width){ // jucatorul e la dreapta
                x += speed;
                if (collision.CheckTile(this) || collision.CheckObject(this)){
                    x -= speed;
                }

                    action = "walkRight";

            }

            if (player.y + player.solidArea.y + player.solidArea.height < y){ // jucatorul e in sus
                y -= speed;
                if (collision.CheckTile(this) || collision.CheckObject(this)){
                    y += speed;
                }
                    if (action.contains("Right")) {
                        action = "upRight";
                    }else{
                        action = "upLeft";
                    }

            }else if (player.y > y + solidArea.y + solidArea.height){ // jucatorul e in jos
                y += speed;
                if (collision.CheckTile(this) || collision.CheckObject(this)){
                    y -= speed;
                }

                    if (action.contains("Right")) {
                        action = "downRight";
                    }else{
                        action = "downLeft";
                    }

            }
        }
    }
    protected abstract boolean TryAttack(Player player);
    protected void Actions(){
        if (spriteCounter>NO_SPRITES-1){ // s-a terminat animatia
            if (alive == false){ //daca player-ul a murit, ramane ultima animatie
                pause = true;
                spriteCounter = NO_SPRITES-1;
            }
            else {
                spriteCounter = 0; // se reseteaza counter-ul
                pause = false;
                isHit = false;
            }
        }

        if (spriteSpeedCounter == SPRITE_SPEED) {
            spriteSpeedCounter = 0;

            switch (action) {
                case "idleRight":
                    img = IdleRight[spriteCounter++];
                    break;
                case "idleLeft":
                    img = IdleLeft[spriteCounter++];
                    break;
                case "upRight":
                case "downRight":
                case "walkRight":
                    img = WalkRight[spriteCounter++];
                    break;
                case "upLeft":
                case "downLeft":
                case "walkLeft":
                    img = WalkLeft[spriteCounter++];
                    break;
                case "attackRight":
                    img = AttackRight[spriteCounter++];
                    break;
                case "attackLeft":
                    img = AttackLeft[spriteCounter++];
                    break;
                case "damageRight":
                    img = DamageRight[spriteCounter++];
                    break;
                case "damageLeft":
                    img = DamageLeft[spriteCounter++];
                    break;
                case "deathRight":
                    img = DeathRight[spriteCounter++];
                    break;
                case "deathLeft":
                    img = DeathLeft[spriteCounter++];
                    break;
                default:
                    break;
            }
        }
        else {
            spriteSpeedCounter++;
        }
    }

    protected void Draw(Graphics g, int x, int y){
        g.drawImage(img, y, x, TILE_WIDTH, TILE_HEIGHT, null);
    }

    protected abstract void Sprites();
}
