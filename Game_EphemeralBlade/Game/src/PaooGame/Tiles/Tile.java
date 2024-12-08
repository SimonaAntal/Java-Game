package PaooGame.Tiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile
{
    public static final int TILE_WIDTH  = 48;       /*!< Latimea unei dale.*/
    public static final int TILE_HEIGHT = 48;       /*!< Inaltimea unei dale.*/
    private final BufferedImage img;                /*!< Imaginea aferenta tipului de dala.*/
    private final boolean collision;                /*!< Exista sau nu coliziune cu dala.*/


    /*! \fn public Tile(BufferedImage texture, int id)
        \brief Constructorul aferent clasei.

        \param image Imaginea corespunzatoare dalei.
        \param id Id-ul dalei.
     */
    protected Tile(BufferedImage image, boolean col)
    {
        img = image;
        collision = col;
    }

    /*! \fn protected boolean GetTileCollision()
        \brief Returneaza daca exista sau nu coliziune cu dala.
     */
    protected boolean GetTileCollision(){
        return collision;
    }


    /*! \fn public void Draw(Graphics g, int x, int y)
        \brief Deseneaza in fereastra dala.

        \param g Contextul grafic in care sa se realizeze desenarea
        \param x Coordonata x in cadrul ferestrei unde sa fie desenata dala
        \param y Coordonata y in cadrul ferestrei unde sa fie desenata dala
     */
    protected void Draw(Graphics g, int x, int y)
    {
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }
}
