package PaooGame;

import PaooGame.GameWindow.GameWindow;

public class Main
{
    public static void main(String[] args)
    {
        Game paooGame = new Game("PaooGame", 1080, 720);
        paooGame.StartGame();
    }
}
