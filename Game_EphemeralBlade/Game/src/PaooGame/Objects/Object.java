package PaooGame.Objects;

import PaooGame.Entity.Entity;
import PaooGame.Entity.Player;
import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Object {
    protected int width, height;
    protected BufferedImage img;
    protected String name;
    protected boolean collision;
    protected int x, y;

    protected Object(BufferedImage img){
        this.img = img;
    }

    protected Object(String name, boolean collision, int x, int y){
        this.name = name;
        this.collision = collision;
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;

        SetImage(name);
    }
    protected Object(String name, boolean collision, int x, int y, int width, int height){
        this.name = name;
        this.collision = collision;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        SetImage(name);
    }

    public String GetName(){
        return name;
    }

    private void SetImage(String name){
        switch (name){
            case "chest":
                img = Assets.chest1;
                break;
            case "coin1":
                img = Assets.coin1;
                break;
            case "coin2":
                img = Assets.coin2;
                break;
            case "coin3":
                img = Assets.coin3;
                break;
            case "coin4":
                img = Assets.coin4;
                break;
            case "coin5":
                img = Assets.coin5;
                break;
            case "apple":
                img = Assets.apple;
                break;
            case "banana":
                img = Assets.banana;
                break;
            case "fish":
                img = Assets.fish;
                break;
            case "book":
                img = Assets.book;
                break;
            case "sword":
                img = Assets.sword;
                break;
        }
    }

    public static BufferedImage GetObjImage(String name){
        switch (name){
            case "apple":
                return Assets.apple;
            case "banana":
                return Assets.banana;
            case "fish":
                return Assets.fish;
            case "coin":
                return Assets.coin1;
        }
        return null;
    }
    protected boolean GetCollision(){
        return collision;
    }
    public BufferedImage GetImage(){return img;}

    protected boolean ChangeChestImage(){
        if (img == Assets.chest1){
            img = Assets.chest2;
            return true;
        }
        if (img == Assets.chest2){
            img = Assets.chest3;
            return true;
        }
        if (img == Assets.chest3){
            img = Assets.chest4;
            return true;
        }
        return false; //s-au terminat animatiile de deschis cufarul
    }

    protected void Draw(Graphics g, int x, int y){
        g.drawImage(img, y, x, width, height, null);
        g.setColor(Color.cyan);
        g.drawRect(y, x, width, height);
    }
}
