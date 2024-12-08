package PaooGame.Objects;

import PaooGame.Entity.Player;

public class Chest extends Object{
    private static final int OBJECT_WIDTH = 48;      /*!< Latimea unei animatii.*/
    private static final int OBJECT_HEIGHT = 48;
    private static final int NO_SPRITES = 18;
    private int spriteCounter;

    protected Chest(int x, int y) {
        super("chest", true, x, y, OBJECT_WIDTH, OBJECT_HEIGHT);
        spriteCounter = 0;
    }

    protected boolean UpdateChest(){
        spriteCounter++;
        if (spriteCounter == NO_SPRITES){
            spriteCounter = 0;
            if (ChangeChestImage() == false){
                return false; //s-au terminat animatiile de deschis cufarul
            }
        }
        return true; //se deschide cufarul
    }

}
