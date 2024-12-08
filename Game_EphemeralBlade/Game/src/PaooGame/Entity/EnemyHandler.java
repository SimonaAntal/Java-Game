package PaooGame.Entity;

import PaooGame.Objects.Chest;
import PaooGame.Objects.Object;
import PaooGame.Tiles.Map;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.util.ArrayList;

public class EnemyHandler {
    private ArrayList<Enemy> enemies;

    public EnemyHandler(Map map){
        enemies = new ArrayList<>();
        if (map.GetLevel() == 1){
            switch (map.room) {
                case 1:
                    enemies.add(new Viking(31*Tile.TILE_WIDTH, 7*Tile.TILE_HEIGHT));
                    break;
                case 2:
                    enemies.add(new Viking(21*Tile.TILE_WIDTH, 5*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(30*Tile.TILE_WIDTH, 13*Tile.TILE_HEIGHT));
                    break;
                case 3:
                    enemies.add(new Viking(19*Tile.TILE_WIDTH, 20*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(25*Tile.TILE_WIDTH, 15*Tile.TILE_HEIGHT));
                    break;
                case 4:
                    enemies.add(new Viking(1*Tile.TILE_WIDTH, 2*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(16*Tile.TILE_WIDTH, 17*Tile.TILE_HEIGHT));
                    break;
                case 5:
                    enemies.add(new Viking(20*Tile.TILE_WIDTH, 7*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(34*Tile.TILE_WIDTH, 9*Tile.TILE_HEIGHT));
                    break;
                case 6:
                    enemies.add(new Viking(20*Tile.TILE_WIDTH, 5*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(30*Tile.TILE_WIDTH, 3*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(36*Tile.TILE_WIDTH, 7*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(55*Tile.TILE_WIDTH, 6*Tile.TILE_HEIGHT));
                    break;
                case 7:
                    enemies.add(new Viking(20*Tile.TILE_WIDTH, 5*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(22*Tile.TILE_WIDTH, 16*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(16*Tile.TILE_WIDTH, 10*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(13*Tile.TILE_WIDTH, 2*Tile.TILE_HEIGHT));

                    break;
            }
        }else if (map.GetLevel() == 2){
            switch (map.room) {
                case 1:
                    enemies.add(new Toad(17*Tile.TILE_WIDTH, 8*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(21*Tile.TILE_WIDTH, 14*Tile.TILE_HEIGHT));
                    break;
                case 2:
                    enemies.add(new Toad(3*Tile.TILE_WIDTH, 20*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(20*Tile.TILE_WIDTH, 27*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(33*Tile.TILE_WIDTH, 10*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(38*Tile.TILE_WIDTH, 17*Tile.TILE_HEIGHT));
                    break;
                case 3:
                    enemies.add(new Toad(6*Tile.TILE_WIDTH, 10*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(16*Tile.TILE_WIDTH, 3*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(25*Tile.TILE_WIDTH, 12*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(26*Tile.TILE_WIDTH, 22*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(32 * Tile.TILE_WIDTH, 29 * Tile.TILE_HEIGHT));
                    break;
                case 4:
                    enemies.add(new Toad(35*Tile.TILE_WIDTH, 11*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(31*Tile.TILE_WIDTH, 22*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(17*Tile.TILE_WIDTH, 14*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(6*Tile.TILE_WIDTH, 8*Tile.TILE_HEIGHT));
                    break;
                case 5:
                    enemies.add(new Viking(10*Tile.TILE_WIDTH, 5*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(20*Tile.TILE_WIDTH, 3*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(15*Tile.TILE_WIDTH, 15*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(25*Tile.TILE_WIDTH, 10*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(9*Tile.TILE_WIDTH, 19*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(20*Tile.TILE_WIDTH, 17*Tile.TILE_HEIGHT));
                    break;
            }
        }else if (map.GetLevel() == 3){
            switch (map.room) {
                case 1:
                    enemies.add(new Wolf(17*Tile.TILE_WIDTH, 13*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(50*Tile.TILE_WIDTH, 9*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(42*Tile.TILE_WIDTH, 41*Tile.TILE_HEIGHT));
                    break;
                case 2:
                    enemies.add(new Wolf(19*Tile.TILE_WIDTH, 10*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(44*Tile.TILE_WIDTH, 5*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(32*Tile.TILE_WIDTH, 20*Tile.TILE_HEIGHT));
                    break;
                case 3:
                    enemies.add(new Wolf(5*Tile.TILE_WIDTH, 6*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(38*Tile.TILE_WIDTH, 5*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(20*Tile.TILE_WIDTH, 22*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(4*Tile.TILE_WIDTH, 36*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(35*Tile.TILE_WIDTH, 42*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(50*Tile.TILE_WIDTH, 34*Tile.TILE_HEIGHT));
                    break;
                case 4:
                    enemies.add(new Wolf(5*Tile.TILE_WIDTH, 6*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(20*Tile.TILE_WIDTH, 20*Tile.TILE_HEIGHT));
                    enemies.add(new Toad(23*Tile.TILE_WIDTH, 10*Tile.TILE_HEIGHT));
                    enemies.add(new Wolf(25*Tile.TILE_WIDTH, 28*Tile.TILE_HEIGHT));
                    enemies.add(new Viking(10*Tile.TILE_WIDTH, 25*Tile.TILE_HEIGHT));
                    break;
            }
        }
    }
    public void Update(Map map, Player player, int iStart, int iFinish, int jStart, int jFinish){
        for (Enemy enemy: enemies){
            int x = (enemy.x + enemy.TILE_WIDTH)/Tile.TILE_WIDTH;
            int y = (enemy.y + enemy.TILE_HEIGHT)/Tile.TILE_HEIGHT;

            if (y>=iStart && y<=iFinish && x>=jStart && x<=jFinish){
                enemy.Update(map, player);
            }
        }
    }

    public boolean Collision(Rectangle rect){
        int x, y, width, height;

        // Calculam coordonate hit box-ului in functie de directia inamicului
        for (Enemy enemy: enemies){
            x = enemy.x + enemy.solidArea.x;
            y = enemy.y + enemy.solidArea.y;
            width = enemy.solidArea.width;
            height = enemy.solidArea.height;

            Rectangle enemyRect = new Rectangle(x, y, width, height);
            if (enemy.alive == true && enemyRect.intersects(rect)){
                return true;
            }
        }
        return false;
    }

    public void AttackEnemy(Rectangle rect, int damage){
        int x, y, width, height;

        // Calculam coordonate hit box-ului in functie de directia inamicului
        for (Enemy enemy: enemies) {
            x = enemy.x + enemy.solidArea.x;
            y = enemy.y + 10;
            width = enemy.solidArea.width;
            height = enemy.TILE_HEIGHT-10;

            Rectangle enemyRect = new Rectangle(x, y, width, height);
            if (enemyRect.intersects(rect)) {
                enemy.isHit = true;
                enemy.life -= damage;
            }
        }
    }

    public void Draw(Graphics g, Player player, int iStart, int iFinish, int jStart, int jFinish){
        int screenX, screenY;

        for (Enemy enemy: enemies){
            int x = (enemy.x + enemy.TILE_WIDTH)/Tile.TILE_WIDTH;
            int y = (enemy.y + enemy.TILE_HEIGHT)/Tile.TILE_HEIGHT;

            if (y>=iStart && y<=iFinish && x>=jStart && x<=jFinish){
                screenX = enemy.y - player.y + Player.POSITION_Y;
                screenY = enemy.x - player.x + Player.POSITION_X;
                enemy.Draw(g, screenX, screenY);
                g.setColor(Color.red);
                g.drawRect(enemy.solidArea.x+screenY, enemy.solidArea.y+screenX, enemy.solidArea.width, enemy.solidArea.height);
            }
        }
    }
}
