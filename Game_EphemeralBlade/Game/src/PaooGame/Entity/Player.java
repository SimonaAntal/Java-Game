package PaooGame.Entity;

import PaooGame.Bars.*;
import PaooGame.GameState;
import PaooGame.Graphics.Assets;
import PaooGame.KeyHandler;
import PaooGame.Tiles.InvalidLevelId;
import PaooGame.Tiles.Map;
import PaooGame.Tiles.Tile;
import PaooGame.Objects.Object;
import PaooGame.Menu;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.*;

public class Player extends Entity{
    private static final int NO_SPRITES = 6;        /*!< Numarul de sprite-uri pt fiecare animatie.*/
    private static final int TILE_WIDTH = 128;      /*!< Latimea unei animatii.*/
    private static final int TILE_HEIGHT = 88;      /*!< Inaltimea unei animatii.*/
    private static final int SPRITE_SPEED = 8;      /*!< Timpul la care se schimba animatiile.*/
    private static final int DASH_SPEED = 10;       /*!< Viteza cu care se calculeaza formula dash-ului.*/
    private static final int STAMINA_SPEED = 6;       /*!< Viteza cu care se calculeaza formula dash-ului.*/
    private int staminaCounter = 0;
    public int shield;                              /*!< Protejeaza jucatorul la fiecare lovitura.*/
    private int stamina;                            /*!< Atunci cand este incarcata permite folosirea criticii.*/
    private boolean pause;                          /*!< Pause == true daca animatia opreste restul actiunilor.*/
    private boolean takeItem = false;                      /*!< */
    private boolean changeItem = false;                      /*!< */
    private boolean useItem = false;                      /*!< */
    private String interaction;
    public static final int POSITION_X = 1080/2-TILE_WIDTH/2; /*!< Pozitia playerului va fi mereu in centrul ecranului.*/
    public static final int POSITION_Y = 720/2-TILE_HEIGHT/2;
    private final KeyHandler kHandler;
    private final Bar[] bars = new Bar[2];
    private final Inventory inventory;
    private Menu menu;
    private boolean escPressed = false;
    private int escDelay;


    // animatii
    private static BufferedImage[] playerIdleLeft;
    private static BufferedImage[] playerIdleRight;
    private static BufferedImage[] playerRunLeft;
    private static BufferedImage[] playerRunRight;
    private static BufferedImage[] playerAttackRight;
    private static BufferedImage[] playerAttackLeft;
    private static BufferedImage[] playerCriticalRight;
    private static BufferedImage[] playerCriticalLeft;
    private static BufferedImage[] playerDashRight;
    private static BufferedImage[] playerDashLeft;
    private static BufferedImage[] playerDeathRight;
    private static BufferedImage[] playerDeathLeft;

    private static Player instance = null; /*!< La un moment dat exista doar un singur player.*/



    /*! \fn public Player(int x, int y, Collision collision, KeyHandler kh)
       \brief Constructorul aferent clasei (apelat cand se creeaza jucatorul).
    */
    private Player(int x, int y, KeyHandler kh){
        super(100, 15, 20,5, x, y, "Player", "idleRight", true, TILE_WIDTH, TILE_HEIGHT);
        stamina = 100;
        shield = 0;
        kHandler = kh;
        pause = false;
        interaction = "no";

        solidArea = new Rectangle(44, 50, 34, 36);
        
        BarFactory[] factory = new BarFactory[2];
        factory[0] = new HealthBarCreator();
        factory[1] = new StaminaBarCreator();

        bars[0] = factory[0].CreateBar();
        bars[1] = factory[1].CreateBar();
        inventory = Inventory.GetInstance();
        menu = new Menu();
    }

    /*! \fn public Player(int x, int y, Collision collision, KeyHandler kh)
       \brief Constructorul aferent clasei (apelat cand se faca load game).
    */
    private Player(int life, int damage, int critical, int speed, int x, int y, int stamina, int shield, KeyHandler kh){
        super(life, damage, critical, speed, x, y, "Player", "idleRight", life > 0, TILE_WIDTH, TILE_HEIGHT);
        this.stamina = stamina;
        this.shield = shield;
        kHandler = kh;
        pause = false;
        interaction = "no";

        solidArea = new Rectangle(44, 50, 34, 36);

        BarFactory[] factory = new BarFactory[2];
        factory[0] = new HealthBarCreator();
        factory[1] = new StaminaBarCreator();

        bars[0] = factory[0].CreateBar();
        bars[1] = factory[1].CreateBar();
        inventory = Inventory.Load();
    }

    public static Player GetInstance(int x, int y, KeyHandler kh){
        if (instance == null){
            instance = new Player(x, y, kh);
        }
        return instance;
    }

    public static Player GetInstance(int life, int damage, int critical, int speed, int x, int y, int stamina, int shield, KeyHandler kh){
        if (instance == null){
            instance = new Player(life, damage, critical, speed, x, y, stamina, shield, kh);
        }
        return instance;
    }

    /*! \fn public void Update()
        \brief Actualizeaza actiunea curenta si invoca functia Actions care se
        ocupa cu tratarea acesteia
     */
    public GameState Update(Map map) throws InvalidLevelId {
        if (inMenu == true){
            if (kHandler.esc == false){
                escPressed = true;
            } else if (escPressed == true){
                escPressed = false;
                inMenu = false;
                menu.ResetMenu();
                escDelay = 60;
            }
            menu.Update2(this, map);
            return GameState.INGAME;
        }

        if (life <= 0){
            if (alive) {
                spriteCounter = 0; // resetam counter-ul pentru ca animatia sa inceapa de la 0
                alive = false;
            }
            if (action.contains("Right")) {
                action = "deathRight";
            } else {
                action = "deathLeft";
            }
        } else {
            if (kHandler.esc && escDelay == 0) {
                inMenu = true;
            }else{
                if (escDelay > 0){
                    escDelay--;
                }
            }

            if (pause == true) { // verificam cazul pt dash
                int xCopy = x; // Facem o copie pentru restaurare in caz de coliziune
                switch (action) {
                    case "dashRight": // ecuatie testata care functioneaza ok
                        x += DASH_SPEED * (1 - (spriteCounter + 1) / (double) (NO_SPRITES + 1));
                        if (collision.CheckTile(this) || collision.CheckObject(this) || collision.CheckEnemy(this)) {
                            x = xCopy;
                        }
                        break;
                    case "dashLeft":
                        x -= DASH_SPEED * (1 - (spriteCounter + 1) / (double) (NO_SPRITES + 1));
                        if (collision.CheckTile(this) || collision.CheckObject(this) || collision.CheckEnemy(this)) {
                            x = xCopy;
                        }
                        break;
                }
            }

            if (pause == false) {
                //verificam mai intai ce actiune a fost anterior si tratam cazul idle
                if (action.contains("Right")) {
                    action = "idleRight";
                } else {
                    action = "idleLeft";
                }


                //      miscaere WASD
                // pentru up/down in functie de sens, actiunea va fi tot run
                if (kHandler.up) {
                    y -= speed;
                    if (collision.CheckTile(this) || collision.CheckObject(this) || collision.CheckEnemy(this)) {
                        y += speed;
                    }
                    if (action.contains("Right")) {
                        action = "upRight";
                    } else {
                        action = "upLeft";
                    }
                }
                if (kHandler.down) {
                    y += speed;
                    if (collision.CheckTile(this) || collision.CheckObject(this) || collision.CheckEnemy(this)) {
                        y -= speed;
                    }
                    if (action.contains("Right")) {
                        action = "downRight";
                    } else {
                        action = "downLeft";
                    }
                }
                if (kHandler.left) {
                    x -= speed;
                    if (collision.CheckTile(this) || collision.CheckObject(this) || collision.CheckEnemy(this)) {
                        x += speed;
                    }
                    action = "runLeft";
                }
                if (kHandler.right) {
                    x += speed;
                    if (collision.CheckTile(this) || collision.CheckObject(this) || collision.CheckEnemy(this)) {
                        x -= speed;
                    }
                    action = "runRight";
                }


                //      atac K
                if (kHandler.attack) {
                    pause = true; // actiune cu prioritate, dam pauza la input
                    spriteCounter = 0; // resetam counter-ul
                    collision.AttackEnemy(this, "attack");
                    if (action.contains("Right")) {
                        action = "attackRight";
                    } else {
                        action = "attackLeft";
                    }
                }


                //      critica L
                if (kHandler.critical && stamina == 100) {
                    pause = true; // actiune cu prioritate, dam pauza la input
                    spriteCounter = 0; // resetam counter-ul
                    stamina = 50;
                    collision.AttackEnemy(this, "critical");
                    if (action.contains("Right")) {
                        action = "criticalRight";
                    } else {
                        action = "criticalLeft";
                    }
                }

                //      dash J
                if (kHandler.dash) {
                    pause = true; // actiune cu prioritate, dam pauza la input
                    spriteCounter = 0; // resetam counter-ul
                    if (action.contains("Right")) {
                        action = "dashRight";
                    } else {
                        action = "dashLeft";
                    }
                }
            }

            if (stamina != 100 && staminaCounter == STAMINA_SPEED){
                stamina++;
            }
            if (staminaCounter == STAMINA_SPEED){
                staminaCounter = 0;
            }else{
                staminaCounter++;
            }

            Interact(map); // tratarea interactiunilor cu obiectele
        }

        bars[0].Update(life);
        bars[1].Update(stamina);

        return Actions(); // tratarea actiunii
    }


    /*! \fn public Interact(Map map)
       \brief Realizeaza interactiunile jucatorului cu obiectele.
    */
    private void Interact(Map map) throws InvalidLevelId {
        Object item = null;

        if (map.openChest == true){ //daca animatia de deschis cufarul s-a pornit, se continua pana se termina
            //NU se poate interactiona cu alte obiecte intre timp
            map.UpdateObjects("chest");
        }else {
            item = (Object) collision.GetObject(this);
            if (item != null) {
                System.out.println("object");
                interaction = "object";
            } else interaction = "no";
            
            if (number == 0) { // daca nu mai exista inamici, se poate avansa
                if (collision.CheckDoor(this) == true) { // daca player-ul e langa usa afisam butonul
                    System.out.println("DOOR");

                    interaction = "door";
                } else if (item != null && item.GetName().equals("chest")) {
                    System.out.println("CHEST");
                    interaction = "chest";
                }
            }
        }

        //      interactionare F
        if (kHandler.interact){
            if (interaction.compareTo("chest")==0){ //daca e comoara o deschidem
                map.UpdateObjects("chest");
            }
            if (interaction.compareTo("door")==0) { //daca e usa facem teleport la camera urmatoare
                ChangeRoom(map);
            }
            if (item != null && takeItem == false){ //daca e obiect, il adaugam in inventar
                switch (item.GetName()) {
                    case "apple":
                    case "banana":
                    case "fish":
                        if (inventory.Add(item)) {
                            map.RemoveObject(item);
                        }
                        break;
                    case "coin1":
                    case "coin2":
                    case "coin3":
                    case "coin4":
                    case "coin5":
                        inventory.AddCoins(item.GetName());
                        map.RemoveObject(item);
                        break;
                    case "book":
                        map.RemoveObject(item);
                        shield = 5;
                        break;
                    case "sword":
                        map.RemoveObject(item);
                        damage += 5;
                        break;

                }
            }
            takeItem = true;
        }else{
            takeItem = false;
        }


        //      folosire E
        if (kHandler.use){
            if (useItem == false){
                inventory.UseElement(this);
            }
            useItem = true;
        }else{
            useItem = false;
        }


        //      schimbare obiect O
        if (kHandler.change){
            if (changeItem == false){
                inventory.ChangeElement();
            }
            changeItem = true;
        }
        else {
            changeItem = false;
        }
    }

    /*! \fn private GameState Actions()
        \brief Actualizeaza imaginea actiunii curente si returneaza starea jocului.
     */
    private GameState Actions(){
        if (spriteCounter>NO_SPRITES-1){ // s-a terminat animatia
            if (alive == false){ //daca player-ul a murit, ramane ultima animatie
                return GameState.DEATH;
            }
            else {
                spriteCounter = 0; // se reseteaza counter-ul
                pause = false;
            }
        }

        if (spriteSpeedCounter == SPRITE_SPEED) {
            spriteSpeedCounter = 0;

            switch (action) {
                case "idleRight":
                    img = playerIdleRight[spriteCounter++];
                    break;
                case "idleLeft":
                    img = playerIdleLeft[spriteCounter++];
                    break;
                case "upRight":
                case "downRight":
                case "runRight":
                    img = playerRunRight[spriteCounter++];
                    break;
                case "upLeft":
                case "downLeft":
                case "runLeft":
                    img = playerRunLeft[spriteCounter++];
                    break;
                case "attackRight":
                    img = playerAttackRight[spriteCounter++];
                    break;
                case "attackLeft":
                    img = playerAttackLeft[spriteCounter++];
                    break;
                case "criticalRight":
                    img = playerCriticalRight[spriteCounter++];
                    break;
                case "criticalLeft":
                    img = playerCriticalLeft[spriteCounter++];
                    break;
                case "dashRight":
                    img = playerDashRight[spriteCounter++];
                    break;
                case "dashLeft":
                    img = playerDashLeft[spriteCounter++];
                    break;
                case "deathRight":
                    img = playerDeathRight[spriteCounter++];
                    break;
                case "deathLeft":
                    img = playerDeathLeft[spriteCounter++];
                    break;
                default:
                    break;
            }
        }
        else {
            spriteSpeedCounter++;
        }

        return GameState.INGAME;
    }


    /*! \fn private void ChangeRoom(Map map)
        \brief Realizeaza teleportarea de pe o harta pe alta.
     */
    private void ChangeRoom(Map map) throws InvalidLevelId {
        if (map.GetLevel() == 1) {
            switch (map.room) {
                case 1:
                    if (collision.position.compareTo("up") == 0) {
                        map.ChangeMap(1, 2, "res/maps/level1/room2.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 9 * Tile.TILE_WIDTH;
                        y = 6 * Tile.TILE_HEIGHT;
                    }
                    if (collision.position.compareTo("down") == 0 && y > 0) {
                        map.ChangeMap(1, 5, "res/maps/level1/room5.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 32 * Tile.TILE_WIDTH;
                        y = 4 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 2:
                    if (collision.position.compareTo("up") == 0) {
                        map.ChangeMap(1, 3, "res/maps/level1/room3.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 10 * Tile.TILE_WIDTH;
                        y = 5 * Tile.TILE_HEIGHT;
                    }
                    if (collision.position.compareTo("down") == 0 && y > 10 * Tile.TILE_HEIGHT) {
                        map.ChangeMap(1, 4, "res/maps/level1/room4.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 11 * Tile.TILE_WIDTH;
                        y = 9 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 3:
                    if (collision.position.compareTo("down") == 0 && y > 10 * Tile.TILE_HEIGHT) {
                        map.ChangeMap(1, 7, "res/maps/level1/room7.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 2 * Tile.TILE_WIDTH;
                        y = 0 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 4:
                    if (collision.position.compareTo("right") == 0) {
                        map.ChangeMap(1, 7, "res/maps/level1/room7.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 22;
                        y = 6 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 5:
                    if (collision.position.compareTo("down") == 0) {
                        map.ChangeMap(1, 6, "res/maps/level1/room6.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 6 * Tile.TILE_WIDTH;
                        y = 0 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 6:
                    if (collision.position.compareTo("right") == 0) {
                        map.ChangeMap(1, 7, "res/maps/level1/room7.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 22;
                        y = 15 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 7:
                    if (collision.position.compareTo("right") == 0) {
                        map.ChangeMap(2, 1, "res/maps/level2/room1.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 4 * Tile.TILE_WIDTH;
                        y = 3 * Tile.TILE_HEIGHT;
                    }
                    break;

            }
        }else if (map.GetLevel() == 2) {
            switch (map.room) {
                case 1:
                    if (collision.position.compareTo("down") == 0) {
                        map.ChangeMap(2, 2, "res/maps/level2/room2.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 21 * Tile.TILE_WIDTH;
                        y = 16 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 2:
                    if (collision.position.compareTo("right") == 0) {
                        map.ChangeMap(2, 3, "res/maps/level2/room3.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 9 * Tile.TILE_WIDTH;
                        y += 6 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 3:
                    if (collision.position.compareTo("right") == 0) {
                        map.ChangeMap(2, 4, "res/maps/level2/room4.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 0;
                        y -= 10 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 4:
                    if (collision.position.compareTo("up") == 0 && y<14*Tile.TILE_HEIGHT) {
                        map.ChangeMap(2, 5, "res/maps/level2/room5.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 25 * Tile.TILE_WIDTH;
                        y = 25 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 5:
                    if (collision.position.compareTo("right") == 0) {
                        map.ChangeMap(3, 1, "res/maps/level3/room1.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 3 * Tile.TILE_WIDTH;
                        y = 7 * Tile.TILE_HEIGHT;
                    }
                    break;
            }
        }else if (map.GetLevel() == 3) {
            switch (map.room) {
                case 1:
                    if (collision.position.compareTo("up") == 0 && y < 20 * Tile.TILE_HEIGHT) {
                        map.ChangeMap(3, 2, "res/maps/level3/room2.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 2 * Tile.TILE_WIDTH + 22;
                        y = 11 * Tile.TILE_HEIGHT;
                    }
                    if (collision.position.compareTo("up") == 0 && y > 20 * Tile.TILE_HEIGHT) {
                        map.ChangeMap(3, 3, "res/maps/level3/room3.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 32 * Tile.TILE_WIDTH + 22;
                        y = 18 * Tile.TILE_HEIGHT;
                    }
                    if (collision.position.compareTo("right") == 0) {
                        map.ChangeMap(3, 4, "res/maps/level3/room4.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 22;
                        y = 15 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 2:
                    if (collision.position.compareTo("down") == 0 && y > 20 * Tile.TILE_HEIGHT) {
                        map.ChangeMap(3, 4, "res/maps/level3/room4.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 9 * Tile.TILE_WIDTH + 22;
                        y = 3 * Tile.TILE_HEIGHT;
                    }
                    break;
                case 3:
                    if (collision.position.compareTo("right") == 0) {
                        map.ChangeMap(3, 4, "res/maps/level3/room4.txt"); //generam noua camera

                        //schimbam pozitiile player-ului
                        x = 22;
                        y = 27 * Tile.TILE_HEIGHT;
                    }
                    break;
            }
        }
    }


    /*! \fn private void PlayerDataBase()
        \brief Creeaza tabela aferenta jucatorului.
     */
    private void PlayerDataBase(){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS PLAYER " +
                    "(LIFE INT NOT NULL, " +
                    " DAMAGE INT NOT NULL, " +
                    " CRITICAL INT NOT NULL, " +
                    " SPEED INT NOT NULL, " +
                    " X INT, " +
                    " Y INT, " +
                    " STAMINA INT, " +
                    " SHIELD INT)";
            stmt.execute(sql);
            stmt.close();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Player: Opened database successfully");
    }


    /*! \fn private void PlayerDataBase()
        \brief Realizeaza salvarea datelor jucatorului.
     */
    public void Save(){
        // Incercam crearea unei baze de date
        PlayerDataBase();

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            // Curatam tabela inainte sa introducem datele
            String clearSql = "DELETE FROM PLAYER";
            System.out.println("Executing SQL: " + clearSql);
            stmt.executeUpdate(clearSql);


            // Introducem datele
            String sql = "INSERT INTO PLAYER (LIFE, DAMAGE, CRITICAL, SPEED, X, Y, STAMINA, SHIELD) " +
                    "VALUES ("+ life + ", " +
                    damage + ", " +
                    critical + ", " +
                    speed + ", " +
                    x + ", " +
                    y + ", " +
                    stamina + ", " +
                    shield + ")";

            System.out.println("Executing SQL: " + sql);

            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Player: Status saved succesfully");

        // Salvam si inventarul jucatorului
        inventory.Save();
    }


    /*! \fn private void PlayerDataBase()
        \brief Realizeaza incarcarea datelor jucatorului cu cea mai recenta salvare.
     */
    public static Player Load(KeyHandler kh){
        Player pl = null;
        int life, damage, critical, speed, x, y, stamina, shield;
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM PLAYER;" );
            life = rs.getInt("LIFE");
            damage = rs.getInt("DAMAGE");
            critical = rs.getInt("CRITICAL");
            speed = rs.getInt("SPEED");
            x = rs.getInt("X");
            y = rs.getInt("Y");
            stamina = rs.getInt("STAMINA");
            shield = rs.getInt("SHIELD");

            pl = Player.GetInstance(life, damage, critical, speed, x, y, stamina, shield, kh);

            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Player: Load done successfully");

        return pl;
    }


    /*! \fn private void PlayerDataBase()
        \brief Realizeaza afisarea jucatorului si a status-urilor aferente acestuia.
     */
    public void Draw(Graphics g) {
        bars[0].Draw(g);
        bars[1].Draw(g);
        inventory.Draw(g);
        g.drawImage(img, POSITION_X, POSITION_Y, TILE_WIDTH, TILE_HEIGHT, null);
        g.setColor(Color.red);
        g.drawRect(solidArea.x+POSITION_X, solidArea.y+POSITION_Y, solidArea.width,solidArea.height);

        if (inMenu == true) { // Afisam si meniul
            menu.Draw2(g);
        }
    }


    /*! \fn public static void Sprites()
        \brief Functia initializaza vectorii cu animatii.
     */
    public static void Sprites (){
        playerIdleRight = new BufferedImage[NO_SPRITES];
        playerIdleRight[0] = Assets.plIdleRight1;
        playerIdleRight[1] = Assets.plIdleRight2;
        playerIdleRight[2] = Assets.plIdleRight3;
        playerIdleRight[3] = Assets.plIdleRight4;
        playerIdleRight[4] = Assets.plIdleRight5;
        playerIdleRight[5] = Assets.plIdleRight6;

        playerIdleLeft = new BufferedImage[NO_SPRITES];
        playerIdleLeft[0] = Assets.plIdleLeft1;
        playerIdleLeft[1] = Assets.plIdleLeft2;
        playerIdleLeft[2] = Assets.plIdleLeft3;
        playerIdleLeft[3] = Assets.plIdleLeft4;
        playerIdleLeft[4] = Assets.plIdleLeft5;
        playerIdleLeft[5] = Assets.plIdleLeft6;

        playerRunRight = new BufferedImage[NO_SPRITES];
        playerRunRight[0] = Assets.plRunRight1;
        playerRunRight[1] = Assets.plRunRight2;
        playerRunRight[2] = Assets.plRunRight3;
        playerRunRight[3] = Assets.plRunRight4;
        playerRunRight[4] = Assets.plRunRight5;
        playerRunRight[5] = Assets.plRunRight6;

        playerRunLeft = new BufferedImage[NO_SPRITES];
        playerRunLeft[0] = Assets.plRunLeft1;
        playerRunLeft[1] = Assets.plRunLeft2;
        playerRunLeft[2] = Assets.plRunLeft3;
        playerRunLeft[3] = Assets.plRunLeft4;
        playerRunLeft[4] = Assets.plRunLeft5;
        playerRunLeft[5] = Assets.plRunLeft6;

        playerAttackRight = new BufferedImage[NO_SPRITES];
        playerAttackRight[0] = Assets.plAttackRight1;
        playerAttackRight[1] = Assets.plAttackRight2;
        playerAttackRight[2] = Assets.plAttackRight3;
        playerAttackRight[3] = Assets.plAttackRight4;
        playerAttackRight[4] = Assets.plAttackRight5;
        playerAttackRight[5] = Assets.plAttackRight6;

        playerAttackLeft = new BufferedImage[NO_SPRITES];
        playerAttackLeft[0] = Assets.plAttackLeft1;
        playerAttackLeft[1] = Assets.plAttackLeft2;
        playerAttackLeft[2] = Assets.plAttackLeft3;
        playerAttackLeft[3] = Assets.plAttackLeft4;
        playerAttackLeft[4] = Assets.plAttackLeft5;
        playerAttackLeft[5] = Assets.plAttackLeft6;

        playerCriticalRight = new BufferedImage[NO_SPRITES];
        playerCriticalRight[0] = Assets.plCriticalRight1;
        playerCriticalRight[1] = Assets.plCriticalRight2;
        playerCriticalRight[2] = Assets.plCriticalRight3;
        playerCriticalRight[3] = Assets.plCriticalRight4;
        playerCriticalRight[4] = Assets.plCriticalRight5;
        playerCriticalRight[5] = Assets.plCriticalRight6;

        playerCriticalLeft = new BufferedImage[NO_SPRITES];
        playerCriticalLeft[0] = Assets.plCriticalLeft1;
        playerCriticalLeft[1] = Assets.plCriticalLeft2;
        playerCriticalLeft[2] = Assets.plCriticalLeft3;
        playerCriticalLeft[3] = Assets.plCriticalLeft4;
        playerCriticalLeft[4] = Assets.plCriticalLeft5;
        playerCriticalLeft[5] = Assets.plCriticalLeft6;

        playerDashRight = new BufferedImage[NO_SPRITES];
        playerDashRight[0] = Assets.plDashRight1;
        playerDashRight[1] = Assets.plDashRight2;
        playerDashRight[2] = Assets.plDashRight3;
        playerDashRight[3] = Assets.plDashRight4;
        playerDashRight[4] = Assets.plDashRight5;
        playerDashRight[5] = Assets.plDashRight6;

        playerDashLeft = new BufferedImage[NO_SPRITES];
        playerDashLeft[0] = Assets.plDashLeft1;
        playerDashLeft[1] = Assets.plDashLeft2;
        playerDashLeft[2] = Assets.plDashLeft3;
        playerDashLeft[3] = Assets.plDashLeft4;
        playerDashLeft[4] = Assets.plDashLeft5;
        playerDashLeft[5] = Assets.plDashLeft6;

        playerDeathRight = new BufferedImage[NO_SPRITES];
        playerDeathRight[0] = Assets.plDeathRight1;
        playerDeathRight[1] = Assets.plDeathRight2;
        playerDeathRight[2] = Assets.plDeathRight3;
        playerDeathRight[3] = Assets.plDeathRight4;
        playerDeathRight[4] = Assets.plDeathRight5;
        playerDeathRight[5] = Assets.plDeathRight6;

        playerDeathLeft = new BufferedImage[NO_SPRITES];
        playerDeathLeft[0] = Assets.plDeathLeft1;
        playerDeathLeft[1] = Assets.plDeathLeft2;
        playerDeathLeft[2] = Assets.plDeathLeft3;
        playerDeathLeft[3] = Assets.plDeathLeft4;
        playerDeathLeft[4] = Assets.plDeathLeft5;
        playerDeathLeft[5] = Assets.plDeathLeft6;
    }
}
