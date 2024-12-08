package PaooGame.Entity;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity {
    protected final int TILE_WIDTH;      /*!< Latimea unei animatii.*/
    protected final int TILE_HEIGHT;      /*!< Inaltimea unei animatii.*/
    protected static int number;
    protected String name;
    protected int damage;
    protected int critical;
    protected int life;                 /*!< Viata caracterului.*/
    protected boolean alive;
    protected int speed;                /*!< Viteza de deplasare a caracterului.*/
    public int x;                    /*!< Pozitia pe Ox a caracterului.*/
    public int y;                    /*!< Pozitia pe Oy a caracterului.*/
    protected BufferedImage img;        /*!< Imaginea curenta a caracterului.*/
    public String action;            /*!< Actiunea executata curent de caracter.*/
    protected int spriteCounter;        /*!< Counter-ul imaginii curente din sprite.*/
    protected int spriteSpeedCounter;   /*!< Counter-ul derularii sprite-urilor.*/
    protected static Collision collision;
    public Rectangle solidArea;      /*!< Partea solida a imaginii caracterului.*/
    protected static boolean inMenu = false; /*!< Daca jucatorul deschide meniul, toate entitatile sunt in pauza.*/


    /*! \fn protected Entity(int life, int speed, int x, int y, BufferedImage img, String action, Collision collision)
       \brief Constructorul aferent clasei.
    */
    protected Entity(int life, int damage, int critical, int speed, int x, int y, String name, String action, boolean alive, int width, int height){
        this.life = life;
        this.damage = damage;
        this.critical = critical;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.name = name;
        this.action = action;
        this.alive = alive;
        spriteCounter = 0;
        spriteSpeedCounter = 0;
        TILE_WIDTH = width;
        TILE_HEIGHT = height;

        switch (name){
            case "Player":
                this.img = Assets.plIdleRight1;
                break;
            case "Viking":
                this.img = Assets.vikingIdleLeft1;
        }
    }

    public static void SetCollision(Collision collision){ Entity.collision = collision;}
}
