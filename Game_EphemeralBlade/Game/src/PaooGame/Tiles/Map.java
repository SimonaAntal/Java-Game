package PaooGame.Tiles;

import PaooGame.Entity.Enemy;
import PaooGame.Entity.EnemyHandler;
import PaooGame.Entity.Inventory;
import PaooGame.Entity.Player;
import PaooGame.Objects.Object;
import PaooGame.Objects.ObjectManager;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Map extends TileManager{
    private int noLines;   /*!< Numarul de linii ale hartii. */
    private int noColumns; /*!< Numarul de coloane ale hartii. */
    protected int[][] map; /*!< Matricea cu numerele dalelor din mapa */
    public int room;
    private static ObjectManager obj;
    private EnemyHandler enemyH;
    public boolean openChest = false; /*!< Flag daca se deschide un chest */


    public int GetCol(){return noColumns;}
    public int GetRow(){return noLines;}

    /*! \fn public Map(int level, String path)
       \brief Constructorul aferent clasei.

       \param level ID-ul nivelului aferent hartii.
       \param path Calea catre fisierul .txt cu harta.
    */
    public Map(int level, int room, String path){
        super(level);

        int i, j;

        this.room = room; //initial player-ul se afla in prima camera

        File fisier = new File(path);
        Scanner scanner = null;
        try {
            scanner = new Scanner(fisier);
            String line = scanner.nextLine();
            String[] numbers = line.split("\t");

            noLines = Integer.parseInt(numbers[0]);
            noColumns = Integer.parseInt(numbers[1]);
            map = new int[noLines][noColumns];

            for (i = 0; i<noLines; ++i) {
                line = scanner.nextLine();
                numbers = line.split("\t");
                for (j = 0; j<noColumns; ++j)
                {
                    map[i][j] = Integer.parseInt(numbers[j]);
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        obj = new ObjectManager(level, room);

        // TEST PT LOAD
        enemyH = new EnemyHandler(this);
    }

    public void ChangeMap(int level, int room, String path) throws InvalidLevelId {
        int i, j;

        if (level != GetLevel()){
            SetLevel(level);
            Init(level);
        }
        this.room = room;

        File fisier = new File(path);
        Scanner scanner = null;
        try {
            scanner = new Scanner(fisier);
            String line = scanner.nextLine();
            String[] numbers = line.split("\t");

            noLines = Integer.parseInt(numbers[0]);
            noColumns = Integer.parseInt(numbers[1]);
            map = new int[noLines][noColumns];

            for (i = 0; i<noLines; ++i) {
                line = scanner.nextLine();
                numbers = line.split("\t");
                for (j = 0; j<noColumns; ++j)
                {
                    map[i][j] = Integer.parseInt(numbers[j]);
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        obj = new ObjectManager(level, room);
        enemyH = new EnemyHandler(this);
    }

    public void SetRoom(int room){
        this.room = room;
    }

    /*! \fn public int GetBlock(int x, int y)
       \brief Returneaza id-ul dalei de pe pozitia indicata de x si y
    */
    public int GetBlock(int x, int y){
        return map[x][y];
    }


    /*! \fn private void Color(Graphics g)
       \brief Coloreaza fundalul cu o culoare specifica nivelului
    */
    private void Color(Graphics g){
        Color background = Color.BLACK;
        switch (GetLevel()){
            case 1:
                background = new Color(24, 20, 37);
                break;
            case 2:
                background = new Color(74, 124, 78);
                break;
            case 3:
                background = new Color(7, 7, 7);
        }
        g.setColor(background);
        g.fillRect(0, 0, 1080, 720);
    }


    public Object ObjectDetection(Rectangle rect){
        return obj.ObjectDetection(rect);
    }
    public boolean ObjectCollision(Rectangle rect){
        return obj.ObjectCollision(rect);
    }
    public boolean EnemyCollision(Rectangle rect){ return enemyH.Collision(rect); }
    public void RemoveObject(Object item){obj.Remove(item);}

    public void EnemyDrop(String name, int x, int y){obj.EnemyDrop(name, x, y);}

    public void UpdateObjects(String action){
        switch (action){
            case "chest":
                if(obj.UpdateChest(GetLevel(), room) == true){ // se deschide cufarul
                    openChest = true;
                }else{
                    openChest = false; //s-au terminat animatiile de deschis cufarul
                }
                break;
        }
    }

    public void AttackEnemy(Rectangle rect, int damage){enemyH.AttackEnemy(rect, damage);}


    private void MapDataBase(){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS MAP " +
                    "(LEVEL INT NOT NULL, " +
                    " ROOM INT NOT NULL)";

            stmt.execute(sql);
            stmt.close();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Map: Opened database successfully");
    }

    public void Save(){
        // Incercam crearea unei baze de date
        MapDataBase();

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            // Curatam tabela inainte sa introducem datele
            String clearSql = "DELETE FROM MAP";
            System.out.println("Executing SQL: " + clearSql);
            stmt.executeUpdate(clearSql);

            // Introducem datele
            String sql = "INSERT INTO MAP (LEVEL, ROOM) " +
                    "VALUES ("+ GetLevel() + ", " +
                    room + ")";

            System.out.println("Executing SQL: " + sql);

            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Map: Status saved succesfully");

        // Salvam si obiectele de pe mapa
        obj.Save();
    }

    public static Map Load(){
        Map map = null;
        int level, room;
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery( "SELECT * FROM MAP;" );
            level = rs.getInt("LEVEL");
            if (level<1 || level >3){
                throw new InvalidLevelId();
            }
            room = rs.getInt("ROOM");
            map = new Map(level, room, "res/maps/level" + level + "/room" + room + ".txt");
            obj = ObjectManager.Load();

            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Map: Load done successfully");

        return map;
    }

    /*! \fn public void Draw(Graphics g, Player player)
       \brief Afiseaza harta. (avem nevoie de coordonatele jucatorului deoarece pozitia lui e
       fixata pe ecran si harta se misca in jurul lui.
    */
    public void Draw(Graphics g, Player player){
        int i, j, x, screenX, screenY;

        Color(g); // Adaugam culoarea de fundal in functie de nivel

        // Calculam indicii partii de harta care trebuie afisata
        // inceput = pozPlayer - mijEcran
        // sfarsit = pozPlayer + mijEcran
        int iStart = Math.max ((int)(player.y - Player.POSITION_Y)/Tile.TILE_HEIGHT, 0);
        int iFinish = Math.min ((int)(player.y + Player.POSITION_Y)/Tile.TILE_HEIGHT + 3, noLines);
        int jStart = Math.max ((int)(player.x - Player.POSITION_X)/Tile.TILE_WIDTH, 0);
        int jFinish = Math.min ((int)(player.x + Player.POSITION_X)/Tile.TILE_WIDTH + 4, noColumns);

        for (i = iStart; i < iFinish; ++i) {
            for (j = jStart; j < jFinish; ++j) {
                // Calculam unde afisam blocul pe ecran relativ fata de mijEcran (pozitia playerului)
                screenX = i*Tile.TILE_HEIGHT - player.y + Player.POSITION_Y;
                screenY = j*Tile.TILE_WIDTH - player.x + Player.POSITION_X;
                x = map[i][j];
                if (x == -1)
                {
                    x = 0;
                }

                tiles[x].Draw(g, screenY, screenX);
            }
        }

        obj.Draw(g, player, iStart, iFinish, jStart, jFinish);

        //Se face Update doar la inamicii DIN CADRU
        enemyH.Update(this, player, iStart, iFinish, jStart, jFinish);
        enemyH.Draw(g, player, iStart, iFinish, jStart, jFinish);
    }
}
