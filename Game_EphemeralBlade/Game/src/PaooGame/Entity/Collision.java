package PaooGame.Entity;

import PaooGame.Entity.Entity;
import PaooGame.Tiles.Map;
import PaooGame.Tiles.Tile;

import java.awt.*;

public class Collision {
    private final Map map;  /*!< Harta curenta (pentru coliziunile cu dalele). */
    protected String position;


    /*! \fn public Collision (Map map)
       \brief Constructorul aferent clasei.
    */
    public Collision (Map map){
        this.map = map;
    }


    /*! \fn public boolean CheckTile(Entity entity)
       \brief Verifica coliziunea entitatii cu dalele.
    */
    protected boolean CheckTile(Entity entity){

        // Calculam coordonatele ariei solide a entitatii
        int entityX0 = (entity.x + entity.solidArea.x) / Tile.TILE_WIDTH;
        int entityX1 = (entity.x + entity.solidArea.x + entity.solidArea.width) / Tile.TILE_WIDTH;
        int entityY0 = (entity.y + entity.solidArea.y) / Tile.TILE_HEIGHT;
        int entityY1 = (entity.y + entity.solidArea.y + entity.solidArea.height) / Tile.TILE_HEIGHT;

        int block0 = map.GetBlock(entityY0, entityX0);
        int block1 = map.GetBlock(entityY1, entityX1);
        if (block0 == -1){ block0 = 0;}
        if (block1 == -1){ block1 = 0;}

        // Coliziunea poate fi facuta cu un tile sau doua (in functie de pozitie, daca jucatorul e pe mijloc => 2)
        boolean tile1 = map.GetCollision(block0);
        boolean tile2 = map.GetCollision(block1);

        return tile1 || tile2; // Daca face coliziune cu macar un tile => returneaza true
    }

    //Verifica daca exista coliziune player-inamic
    protected boolean CheckEnemy(Entity entity){
        // Calculam coordonatele ariei solide a entitatii
        int x = entity.x+entity.solidArea.x;
        int y = entity.y+entity.solidArea.y;
        int width = entity.solidArea.width;
        int height = entity.solidArea.height;

        return map.EnemyCollision(new Rectangle(x, y, width, height));
    }

    protected void AttackEnemy(Entity entity, String type){
        int x, y, width, height;

        // Calculam coordonatele ariei de atac a entitatii
        if (entity.action.contains("Right")) {
            x = entity.x + entity.solidArea.x;
            y = entity.y;
            width = entity.TILE_WIDTH - entity.solidArea.width;
            height = entity.TILE_HEIGHT;
        }else{
            x = entity.x;
            y = entity.y;
            width = entity.TILE_WIDTH - entity.solidArea.width;
            height = entity.TILE_HEIGHT;
        }

        switch (type) {
            case "attack":
                map.AttackEnemy(new Rectangle(x, y, width, height), entity.damage);
                break;
            case "critical":
                map.AttackEnemy(new Rectangle(x, y, width, height), entity.critical);
                break;
        }
    }

    protected boolean TryAttackPlayer(Player player, Rectangle rect){
        int x = player.x+player.solidArea.x;
        int y = player.y+player.solidArea.y;
        int width = ((Entity)player).TILE_WIDTH - player.solidArea.width;
        int height = ((Entity)player).TILE_HEIGHT - player.solidArea.height;
        Rectangle playerRect = new Rectangle(x, y, width, height);

        return playerRect.intersects(rect);
    }

    //Verifica daca player se afla pe obiect
    protected boolean CheckObject(Entity entity){
        // Calculam coordonatele ariei solide a entitatii
        int x = entity.x+entity.solidArea.x;
        int y = entity.y+entity.solidArea.y;
        int width = entity.solidArea.width;
        int height = entity.solidArea.height;

        return map.ObjectCollision(new Rectangle(x, y, width, height));
    }

    //Intoarce un obiect daca exista langa player
    protected Object GetObject(Entity player){
        // Calculam coordonatele ariei de interactiune a player-ului
        int x = player.x - Tile.TILE_WIDTH + player.solidArea.x;
        int y = player.y - Tile.TILE_HEIGHT + player.solidArea.y;
        int width = 2*Tile.TILE_WIDTH + player.TILE_WIDTH;
        int height = 2*Tile.TILE_HEIGHT + player.TILE_HEIGHT;

        return map.ObjectDetection(new Rectangle(x, y, width, height));
    }

    protected boolean CheckDoor(Player player){
        // Calculam coordonatele blocurilor din jurul player-ului
        int block1X, block1Y, block2X, block2Y, block1, block2;
        int level = map.GetLevel();

        //          blocuri SUS
        block1X = (player.x + player.solidArea.x) / Tile.TILE_WIDTH;
        block1Y = (player.y + player.solidArea.y) / Tile.TILE_HEIGHT - 1;
        block2X = (player.x + player.solidArea.x + player.solidArea.width) / Tile.TILE_WIDTH;
        block2Y = block1Y;

        block1 = map.GetBlock(block1Y, block1X);
        block2 = map.GetBlock(block2Y, block2X);


        switch (level) {
            case 1:
                if (block1 == 13 || block1 == 14 || block2 == 13 || block2 == 14) {
                    position = "up";
                    return true;
                }
                break;
            case 2:
                if (block1 == -1 || block2 == -1){
                    position = "up";
                    return true;
                }
                break;
            case 3:
                if (block1 == 0 || block2 == 0) {
                    position = "up";
                    return true;
                }
                break;
        }

        //          blocuri JOS
        block1X = (player.x + player.solidArea.x) / Tile.TILE_WIDTH;
        block1Y = (player.y + player.solidArea.y) / Tile.TILE_HEIGHT + 1;
        block2X = (player.x + player.solidArea.x + player.solidArea.width) / Tile.TILE_WIDTH;
        block2Y = block1Y;

        block1 = map.GetBlock(block1Y, block1X);
        block2 = map.GetBlock(block2Y, block2X);

        //13, 14 - usa SUS, JOS
        switch (level) {
            case 1:
                if (block1 == 13 || block1 == 14 || block2 == 13 || block2 == 14) {
                    position = "down";
                    return true;
                }
                break;
            case 2:
                if (block1 == -1 || block2 == -1){
                    position = "down";
                    return true;
                }
                break;
            case 3:
                if (block1 == 57 || block2 == 57) {
                    position = "down";
                    return true;
                }
                break;
        }

        //          blocuri DREAPTA
        block1X = (player.x + player.solidArea.x) / Tile.TILE_WIDTH + 1;
        block1Y = (player.y + player.solidArea.y) / Tile.TILE_WIDTH;
        block2X = block1X;
        block2Y = (player.y + player.solidArea.y + player.solidArea.height) / Tile.TILE_WIDTH;

        block1 = map.GetBlock(block1Y, block1X);
        block2 = map.GetBlock(block2Y, block2X);

        switch (level) {
            case 1:
                if (block1 == -1 || block1 == 7 || block1 == 8 || block2 == -1 || block2 == 7 || block2 == 8) {
                    position = "right";
                    return true;
                }
                break;
            case 2:
                if (block1 == -1 || block2 == -1){
                    position = "right";
                    return true;
                }
            case 3:
                if (block1 == 0 || block2 == 0) {
                    position = "right";
                    return true;
                }
                break;
        }

        //          blocuri STANGA
        block1X = (player.x + player.solidArea.x) / Tile.TILE_WIDTH - 1;
        block1Y = (player.y + player.solidArea.y) / Tile.TILE_WIDTH;
        block2X = block1X;
        block2Y = (player.y + player.solidArea.y + player.solidArea.height) / Tile.TILE_WIDTH;

        block1 = map.GetBlock(block1Y, block1X);
        block2 = map.GetBlock(block2Y, block2X);

        switch (level) {
            case 1:
                if (block1 == -1 || block1 == 7 || block1 == 8 || block2 == -1 || block2 == 7 || block2 == 8) {
                    position = "left";
                    return true;
                }
                break;
            case 3:
                if (block1 == 0 || block2 == 0) {
                    position = "left";
                    return true;
                }
                break;
        }

        return false;
    }
}
