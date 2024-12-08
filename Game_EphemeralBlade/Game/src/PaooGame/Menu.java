package PaooGame;

import PaooGame.Buttons.MouseClick;
import PaooGame.Entity.Player;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Buttons.Button;
import PaooGame.Tiles.Map;

import javax.swing.*;
import java.awt.*;

public class Menu {
    private static final int POSITIONX = 476;
    private static final int POSITIONY = 360;
    private static final int SPACE = 15;
    private static Canvas canvas;
    private static MouseClick ml;
    private Button newGame;
    private Button loadGame;
    private Button saveGame;
    private Button controls;
    private boolean printControls = false;

    protected Menu(Canvas canvas, MouseClick ml){ // Meniu principal
        this.canvas = canvas;
        this.ml = ml;
        newGame = new Button("NEW GAME", POSITIONX, POSITIONY);
        loadGame = new Button("LOAD", POSITIONX, POSITIONY+Button.BUTTON_HEIGHT+SPACE);
    }

    public Menu(){ // Meniu ESC
        saveGame = new Button("SAVE", POSITIONX, 238);
        controls = new Button("CONTROLS", POSITIONX, 238+Button.BUTTON_HEIGHT+SPACE);
    }

    protected GameState Update(){
        // Extragem coordonatele mouse-ului din canvas (getContentPane nu doar frame deoarece ia toata fereastra)
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point location = pointerInfo.getLocation();
        SwingUtilities.convertPointFromScreen(location, canvas);

        // Verificam daca suntem cu mouse-ul pe buton si daca am dat click
        if (newGame.Hover(location.x, location.y) && ml.click){
            return GameState.NEWGAME;
        }
        if (loadGame.Hover(location.x, location.y) && ml.click){
            return GameState.LOAD;
        }

        return GameState.MENU;
    }
    public void Update2(Player player, Map map){
        // Extragem coordonatele mouse-ului din canvas (getContentPane nu doar frame deoarece ia toata fereastra)
        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point location = pointerInfo.getLocation();
        SwingUtilities.convertPointFromScreen(location, canvas);

        // Verificam daca suntem cu mouse-ul pe buton si daca am dat click
        if (saveGame.Hover(location.x, location.y) && ml.click){
            player.Save();
            map.Save();

        }
        if (controls.Hover(location.x, location.y) && ml.click){
            printControls = true;
        }
    }

    protected void Draw(Graphics g){
        g.drawImage(ImageLoader.LoadImage("/textures/Background.png"), 0, 0, 1080, 720, null);
        g.drawImage(ImageLoader.LoadImage("/textures/GameTitle.png"), 324, 150, 431, 381, null);
        newGame.Draw(g);
        loadGame.Draw(g);
    }

    public void Draw2(Graphics g){
        if (printControls == true){
            g.drawImage(ImageLoader.LoadImage("/textures/Controls.png"), 324, 150, 431, 381, null);
        }else {
            g.setColor(Color.WHITE);
            g.fillRect(POSITIONX - SPACE, 238 - SPACE, Button.BUTTON_WIDTH + SPACE*2, Button.BUTTON_HEIGHT*2 + SPACE*3);
            saveGame.Draw(g);
            controls.Draw(g);
        }
    }

    public void ResetMenu(){
        printControls = false;
    }
}
