package PaooGame.Entity;

import PaooGame.Graphics.Assets;
import PaooGame.KeyHandler;
import PaooGame.Objects.Object;

import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Inventory {
    private static final int MAX_ELEMENTS = 3;
    private static final int STACK = 9;
    private static final int SLOT_DIMENSIONS = 48;
    private static final int MARGIN = 27;
    private static int noElements;
    private static ArrayList<String> elements;
    private static ArrayList<Integer> counter;
    private static int coinNumber;
    private static int currentIndex;
    private static Inventory instance = null;

    private Inventory(){
        elements = new ArrayList<>(MAX_ELEMENTS);
        counter = new ArrayList<>(MAX_ELEMENTS);
        noElements = 0;
        coinNumber = 0;
        currentIndex = 0;
    }
    public static Inventory GetInstance(){
        if (instance == null){
            instance = new Inventory();
        }
        return instance;
    }

    public boolean Add(Object obj){
        boolean rez = true;
        String item = obj.GetName();

        if (elements.contains(item) == false){ // nu exista elementul
            elements.add(item); // adaugam prima sa aparitie
            counter.add(1);
            noElements++;
        }
        else{
            Integer num = counter.get(elements.indexOf(item));
            if (num < STACK){ // daca exista verificam sa nu depaseasca maximul de aparitii
                counter.set(elements.indexOf(item), ++num); // incrementam numarul aparitiilor
            }
            else{
                rez = false; // nu am adaugat elementul
            }
        }

        return rez;
    }

    public void ChangeElement(){
        if (currentIndex < noElements - 1){
            currentIndex++;
        }else {
            currentIndex = 0;
        }
    }

    public void UseElement(Player player){
        int number;

        if (noElements == 0){ // Daca nu sunt elemente, nu facem nimic
            return;
        }

        number = counter.get(currentIndex);
        switch (elements.get(currentIndex)){
            case "apple":
                player.life += 10;
                break;
            case "banana":
                player.life += 15;
                break;
            case "fish":
                player.life += 20;
                break;
        }

        if (number > 1){
            number--;
            counter.set(currentIndex, number);
        } else{
            elements.remove(currentIndex);
            counter.remove(currentIndex);
            noElements--;
            if (currentIndex > noElements - 1 && currentIndex != 0){
                currentIndex--;
            }
        }
        if (player.life > 100){
            player.life = 100;
        }
    }
    public void AddCoins(String name){
        String number = name.replace("coin","");
        coinNumber += Integer.parseInt(number);
    }

    private void InventoryDataBase(){
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            stmt = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS INVENTORY " +
                    "(NUMBER INT, " +
                    "COINS INT, " +
                    "CURRENTINDEX INT)";
            stmt.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS INVENTORYITEMS " +
                    "(NAME CHAR(10), " +
                    "NUMBER INT)";

            stmt.execute(sql);

            stmt.close();
            c.close();


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Inventory: Opened database successfully");
    }

    public void Save(){
        // Incercam crearea unei baze de date
        InventoryDataBase();

        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();

            // Curatam tabela inainte sa introducem datele
            String clearSql;
            clearSql = "DELETE FROM INVENTORY";
            System.out.println("Executing SQL: " + clearSql);
            stmt.executeUpdate(clearSql);

            clearSql = "DELETE FROM INVENTORYITEMS";
            System.out.println("Executing SQL: " + clearSql);
            stmt.executeUpdate(clearSql);


            String sql;
            // Introducem campurile inventarului
            sql = "INSERT INTO INVENTORY (NUMBER, COINS, CURRENTINDEX) " +
                    "VALUES ("+ noElements + ", " +
                    coinNumber + ", " +
                    currentIndex + ")";
            System.out.println("Executing SQL: " + sql);
            stmt.executeUpdate(sql);

            // Introducem obiectele din inventar
            for (int i =0; i<noElements; ++i){
                sql = "INSERT INTO INVENTORYITEMS (NAME, NUMBER) " +
                        "VALUES ( \""+ elements.get(i) + "\", " +
                        counter.get(i) + ")";

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
        System.out.println("Inventory: Status saved succesfully");
    }

    public static Inventory Load(){
        Inventory inventory = Inventory.GetInstance();
        String itemName;
        int itemCounter;
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:game.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet rs;

            rs = stmt.executeQuery( "SELECT * FROM INVENTORY;" );
            noElements = rs.getInt("NUMBER");
            coinNumber = rs.getInt("COINS");
            currentIndex = rs.getInt("CURRENTINDEX");

            rs = stmt.executeQuery( "SELECT * FROM INVENTORYITEMS;" );
            while ( rs.next() ) {
                itemName = rs.getString("NAME");
                itemCounter = rs.getInt("NUMBER");
                elements.add(itemName);
                counter.add(itemCounter);
            }

            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Inventory: Load done successfully");

        return inventory;
    }

    public void Draw (Graphics g){
        int i, x, y;

        // Coins
        x = 120; y = 42;
        g.drawImage(Object.GetObjImage("coin"), x, y, SLOT_DIMENSIONS, SLOT_DIMENSIONS, null);
        x += 40;
        y += 28;
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(coinNumber), x, y);

        // Afisam slot-urile pe ecran
        x = 10; y = 80;
        for (i = 0; i<MAX_ELEMENTS; ++i){
            g.drawImage(Assets.inventorySlot, x, y, SLOT_DIMENSIONS, SLOT_DIMENSIONS, null);
            x += SLOT_DIMENSIONS;
        }

        // Afisam slot-ul selectat intr-un dreptunghi
        g.setColor(Color.WHITE);
        g.drawRect(10 + currentIndex*(SLOT_DIMENSIONS), y, SLOT_DIMENSIONS, SLOT_DIMENSIONS);


        // Afisam itemele
        x = 24; y += 14;
        for (String item: elements){
            g.drawImage(Object.GetObjImage(item), x, y, SLOT_DIMENSIONS-MARGIN, SLOT_DIMENSIONS-MARGIN, null);
            x += SLOT_DIMENSIONS;
        }

        // Afisam numarul de iteme
        x = SLOT_DIMENSIONS; y = 80 + SLOT_DIMENSIONS;
        g.setFont(new Font("Helvetica", Font.BOLD, 15));
        for (Integer number: counter){
            if (number == STACK){
                g.setColor(Color.RED);
            }else{
                g.setColor(Color.WHITE);
            }
            g.drawString(number.toString(), x, y);
            x += SLOT_DIMENSIONS;
        }
    }
}
