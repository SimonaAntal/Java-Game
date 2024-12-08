package PaooGame.Objects;

import PaooGame.Entity.Player;
import PaooGame.Tiles.Map;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ObjectManager {
    private final Random RAND= new Random();;
    private final List<String> FOOD = List.of("apple", "banana", "fish");
    private static ArrayList<Object> objects;


    private ObjectManager(){
        objects = new ArrayList<Object>();
    }
    public ObjectManager(int level, int room){
        objects = new ArrayList<Object>();
        if (level == 1){
            switch (room){
                case 1:
                    objects.add(new Chest(37*Tile.TILE_WIDTH, 5*Tile.TILE_HEIGHT));
                    //objects.add(new Chest(5*Tile.TILE_WIDTH, 5*Tile.TILE_HEIGHT));
                    break;
                case 2:
                    objects.add(new Chest(35*Tile.TILE_WIDTH, 13*Tile.TILE_HEIGHT));
                    break;
                case 3:
                    objects.add(new Chest(27*Tile.TILE_WIDTH, 20*Tile.TILE_HEIGHT));
                    break;
                case 4:
                    objects.add(new Chest(18*Tile.TILE_WIDTH, 8*Tile.TILE_HEIGHT));
                    break;
                case 5:
                    objects.add(new Chest(17*Tile.TILE_WIDTH, 2*Tile.TILE_HEIGHT));
                    break;
                case 6:
                    objects.add(new Chest(53*Tile.TILE_WIDTH, 5*Tile.TILE_HEIGHT));
                    break;
                case 7:
                    objects.add(new Chest(23*Tile.TILE_WIDTH, 10*Tile.TILE_HEIGHT));
                    break;
            }
        }

        if (level == 2){
            switch (room) {
                case 1:
                    objects.add(new Chest(26 * Tile.TILE_WIDTH+22, 9 * Tile.TILE_HEIGHT));
                    break;
                case 2:
                    objects.add(new Chest(31 * Tile.TILE_WIDTH+22, 5 * Tile.TILE_HEIGHT));
                    break;
                case 3:
                    objects.add(new Chest(5 * Tile.TILE_WIDTH+22, 3 * Tile.TILE_HEIGHT));
                    break;
                case 4:
                    objects.add(new Chest(38 * Tile.TILE_WIDTH, 19 * Tile.TILE_HEIGHT));
                    break;
                case 5:
                    objects.add(new Chest(3 * Tile.TILE_WIDTH, 17 * Tile.TILE_HEIGHT));
                    break;
            }
        }

        if (level == 3){
            switch (room) {
                case 1:
                    objects.add(new Chest(28*Tile.TILE_WIDTH + 22, 43*Tile.TILE_HEIGHT+22));
                    break;
                case 2:
                    objects.add(new Chest(45*Tile.TILE_WIDTH, 28*Tile.TILE_HEIGHT));
                    break;
                case 3:
                    objects.add(new Chest(38*Tile.TILE_WIDTH+22, 39*Tile.TILE_HEIGHT+22));
                    break;
            }
        }
    }

    public boolean UpdateChest(int level, int room){

        for (Object o: objects){
            if (o.name.compareTo("chest")==0){
                if (((Chest)o).UpdateChest() == false) //daca UpdateChest returneaza false
                {
                    int numCoins1 = 0, numCoins2 = 0, numFood = 0;

                    //se creeaza noi obiecte in functie de nivel si camera
                    if (level == 1){
                        switch (room){
                            case 7:
                                numCoins1 = RAND.nextInt(5) + 1;
                                numCoins2 = RAND.nextInt(4) + 1;
                                numFood = RAND.nextInt(6);
                                objects.add(new Object("book", false, o.x, o.y));
                                break;
                            default:
                                numCoins1 = RAND.nextInt(2) + 1;
                                numCoins2 = RAND.nextInt(3);
                                numFood = RAND.nextInt(3);
                        }
                    }else if (level == 2){
                        switch (room){
                            case 5: //boss room
                                numCoins1 = RAND.nextInt(3) + 2;
                                numCoins2 = RAND.nextInt(4) + 1;
                                numFood = RAND.nextInt(4)+2;
                                objects.add(new Object("sword", false, o.x, o.y));
                                break;
                            default:
                                numCoins1 = RAND.nextInt(2) + 2;
                                numCoins2 = RAND.nextInt(3);
                                numFood = RAND.nextInt(3)+1;
                        }
                    }else if (level == 3){
                            numCoins1 = RAND.nextInt(2) + 1;
                            numCoins2 = RAND.nextInt(4);
                            numFood = RAND.nextInt(2) + 3;
                    }

                    objects.add(new Object("coin" + numCoins1, false, o.x, o.y+30));
                    if (numCoins2!=0) {
                        objects.add(new Object("coin" + numCoins2, false, o.x + o.width, o.y + 40));
                    }
                    int deplX = 20, deplY = 0;
                    while (numFood > 0){
                        objects.add(new Object(FOOD.get(RAND.nextInt(3)), false, o.x+deplX, o.y+deplY));
                        numFood--;
                        deplX+=20;
                        deplY-=10;
                    }

                    objects.remove(o); //cufarul trebuie sters

                    return false; //s-au terminat animatiile de deschis cufarul
                }
            }
        }
        return true; // se deschide cufarul
    }

    public void EnemyDrop(String name, int x, int y){
        int numCoins = 0, numFood = 0;
        switch (name){
            case "Viking":
                numCoins = RAND.nextInt(2);
                if (numCoins == 0) {
                    numFood = RAND.nextInt(2);
                }
                break;
            case "Toad":
                numCoins = RAND.nextInt(2)+1;
                numFood = RAND.nextInt(3);
                break;
        }

        if (numCoins != 0){
            objects.add(new Object("coin" + numCoins, false, x, y+30));
        }
        if (numFood != 0) {
            objects.add(new Object(FOOD.get(RAND.nextInt(2)), false, x + 10, y + 30));
        }
    }
    public boolean ObjectCollision(Rectangle rect){
        for (Object o: objects){
            if (o.GetCollision()) {
                Rectangle rectObj = new Rectangle(o.x, o.y, o.width, o.height);
                if (rectObj.intersects(rect))
                    return true;
            }
        }
        return false;
    }
    public Object ObjectDetection(Rectangle rect){
        for (Object o: objects){
            Rectangle rectObj = new Rectangle(o.x, o.y, o.width, o.height);
            if (rectObj.intersects(rect))
                return o;
        }
        return null;
    }

    public void Remove (Object item){
        for (Object o: objects){
            if (o.x == item.x && o.y == item.y){
                objects.remove(o);
                return;
            }
        }
    }


    private void ObjectDataBase(){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS OBJECTS " +
                    "(NAME CHAR(10), " +
                    "COLLISION CHAR(6), " +
                    "X INT, " +
                    "Y INT)";

            stmt.execute(sql);
            stmt.close();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Objects: Opened database successfully");
    }

    public void Save(){
        // Incercam crearea unei baze de date
        ObjectDataBase();

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            // Curatam tabela inainte sa introducem datele
            String clearSql = "DELETE FROM OBJECTS";
            System.out.println("Executing SQL: " + clearSql);
            stmt.executeUpdate(clearSql);

            // Introducem datele
            for (Object o: objects){
                String sql = "INSERT INTO OBJECTS (NAME, COLLISION, X, Y) " +
                        "VALUES ( \""+ o.name + "\", " +
                        "\"" + o.collision + "\", " +
                        o.x + ", " +
                        o.y + ")";

                System.out.println("Executing SQL: " + sql);
                stmt.executeUpdate(sql);
            }

            stmt.close();
            c.commit();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Objects: Status saved succesfully");
    }

    public static ObjectManager Load(){
        ObjectManager obj = new ObjectManager();
        String objName;
        boolean objCollision;
        int x, y;
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery( "SELECT * FROM OBJECTS;" );
            while ( rs.next() ) {
                objName = rs.getString("NAME");
                objCollision = Boolean.parseBoolean(rs.getString("COLLISION"));
                x = rs.getInt("X");
                y = rs.getInt("Y");
                switch (objName){
                    case "chest":
                        objects.add(new Chest(x, y));
                        break;
                    default:
                        objects.add(new Object(objName, objCollision, x, y));
                        break;
                }
            }

            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Objects: Load done successfully");

        return obj;
    }

    public void Draw(Graphics g, Player player, int iStart, int iFinish, int jStart, int jFinish){
        int screenX, screenY;

        for (Object o: objects){
            int x = o.x/Tile.TILE_WIDTH;
            int y = o.y/Tile.TILE_HEIGHT;

            if (y>=iStart && y<=iFinish && x>=jStart && x<=jFinish){
                screenX = o.y - player.y + Player.POSITION_Y;
                screenY = o.x - player.x + Player.POSITION_X;
                o.Draw(g, screenX, screenY);
            }
        }
    }
}
