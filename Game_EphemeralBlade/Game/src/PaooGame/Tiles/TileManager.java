package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*! \class public class TileManager
    \brief Retine toate dalele intr-un vector si ofera posibilitatea regasirii dupa un levelId.
 */
public class TileManager{
    private static int noTiles;    /*!< Numarul de dale din harta curenta.*/
    private static int levelId;    /*!< Nivelul din care face parte harta.*/
    protected static Tile[] tiles; /*!< Vector de referinte de tipuri de dale.*/

    /*!< Initializeaza vectorul tiles cu tipurile de dale in functie de nivel.*/

    /*! \fn protected int GetLevel()
       \brief Returneaza ID-ul nivelului.
    */
    public int GetLevel(){
        return levelId;
    }
    public void SetLevel(int level) throws InvalidLevelId {
        if (level >= 1 || level <= 3) {
            levelId = level;
        }else{
            throw new InvalidLevelId();
        }
    }


    /*! \fn public boolean GetCollision(int x)
       \brief Returneaza coliziunea cu dala de tipul x
    */
    public boolean GetCollision(int x){
        return tiles[x].GetTileCollision();
    }

    /*! \fn protected TileManager(int level)
       \brief Constructorul aferent clasei.
    */
    protected TileManager(int level) {
        if (level > 3 || level < 1) {
            System.out.println("LevelIdNotFound");
            return;
        }
        Init(level);
        levelId = level;
    }

    protected static void Init(int levelId){
        switch (levelId){
            case 1: // level 1
                noTiles = 44;
                tiles = new Tile[noTiles];
                tiles[0] = new Tile(Assets.background, true);
                tiles[1] = new Tile(Assets.corner1, true);
                tiles[2] = new Tile(Assets.corner2, true);
                tiles[3] = new Tile(Assets.corner3, true);
                tiles[4] = new Tile(Assets.corner4, true);
                tiles[5] = new Tile(Assets.corner5, true);
                tiles[6] = new Tile(Assets.corner6, true);
                tiles[7] = new Tile(Assets.corner7, true);
                tiles[8] = new Tile(Assets.corner8, true);
                tiles[9] = new Tile(Assets.crackedFloor1, false);
                tiles[10] = new Tile(Assets.crackedFloor2, false);
                tiles[11] = new Tile(Assets.crackedFloor3, false);
                tiles[12] = new Tile(Assets.crackedFloor4, false);
                tiles[13] = new Tile(Assets.door1, true);
                tiles[14] = new Tile(Assets.door2, true);
                tiles[15] = new Tile(Assets.edge1, true);
                tiles[16] = new Tile(Assets.edge2, true);
                tiles[17] = new Tile(Assets.floor, false);
                tiles[18] = new Tile(Assets.lineCorner1, false);
                tiles[19] = new Tile(Assets.lineCorner2, false);
                tiles[20] = new Tile(Assets.lineCorner3, false);
                tiles[21] = new Tile(Assets.lineCorner4, false);
                tiles[22] = new Tile(Assets.line1, false);
                tiles[23] = new Tile(Assets.line2, false);
                tiles[24] = new Tile(Assets.line3, false);
                tiles[25] = new Tile(Assets.line4, false);
                tiles[26] = new Tile(Assets.shadowCorner1, false);
                tiles[27] = new Tile(Assets.shadowCorner2, false);
                tiles[28] = new Tile(Assets.shadowCorner3, false);
                tiles[29] = new Tile(Assets.shadowCorner4, false);
                tiles[30] = new Tile(Assets.shadow1, false);
                tiles[31] = new Tile(Assets.shadow2, false);
                tiles[32] = new Tile(Assets.shadow3, false);
                tiles[33] = new Tile(Assets.shadow4, false);
                tiles[34] = new Tile(Assets.shadow5, false);
                tiles[35] = new Tile(Assets.shadow6, false);
                tiles[36] = new Tile(Assets.shadow7, false);
                tiles[37] = new Tile(Assets.stairShadow1, false);
                tiles[38] = new Tile(Assets.stairShadow2, false);
                tiles[39] = new Tile(Assets.stair1, false);
                tiles[40] = new Tile(Assets.stair2, false);
                tiles[41] = new Tile(Assets.wall1, true);
                tiles[42] = new Tile(Assets.wall2, true);
                tiles[43] = new Tile(Assets.wall3, true);
                break;

            case 2: // level 2
                noTiles = 62;
                tiles = new Tile[noTiles];
                tiles[0] = new Tile(Assets.lv2background, true);
                tiles[1] = new Tile(Assets.lv2floor, false);
                tiles[2] = new Tile(Assets.lv2wall1, false);
                tiles[3] = new Tile(Assets.lv2wall2, true);
                tiles[4] = new Tile(Assets.lv2wall3, false);
                tiles[5] = new Tile(Assets.lv2wall4, true);
                tiles[6] = new Tile(Assets.lv2wall5, true);
                tiles[7] = new Tile(Assets.lv2wall6, false);
                tiles[8] = new Tile(Assets.lv2wall7, true);
                tiles[9] = new Tile(Assets.lv2wall8, true);
                tiles[10] = new Tile(Assets.lv2wall9, false);
                tiles[11] = new Tile(Assets.lv2wall10, true);
                tiles[12] = new Tile(Assets.lv2wall11, true);
                tiles[13] = new Tile(Assets.lv2wall12, true);
                tiles[14] = new Tile(Assets.lv2wall13, true);
                tiles[15] = new Tile(Assets.lv2wall14, true);
                tiles[16] = new Tile(Assets.lv2wall15, true);
                tiles[17] = new Tile(Assets.lv2wall16, true);
                tiles[18] = new Tile(Assets.lv2wall17, true);
                tiles[19] = new Tile(Assets.lv2wall18, false);
                tiles[20] = new Tile(Assets.lv2wall19, true);
                tiles[21] = new Tile(Assets.lv2wall20, true);
                tiles[22] = new Tile(Assets.lv2wall21, false);
                tiles[23] = new Tile(Assets.lv2wall22, false);
                tiles[24] = new Tile(Assets.lv2wall23, true);
                tiles[25] = new Tile(Assets.lv2wall24, true);
                tiles[26] = new Tile(Assets.lv2wall25, true);
                tiles[27] = new Tile(Assets.lv2wall26, true);
                tiles[28] = new Tile(Assets.lv2wall27, true);
                tiles[29] = new Tile(Assets.lv2wall28, false);
                tiles[30] = new Tile(Assets.lv2wall29, true);
                tiles[31] = new Tile(Assets.lv2wall30, false);
                tiles[32] = new Tile(Assets.lv2stairs1, false);
                tiles[33] = new Tile(Assets.lv2stairs2, false);
                tiles[34] = new Tile(Assets.lv2wall33, false);
                tiles[35] = new Tile(Assets.lv2wall34, false);
                tiles[36] = new Tile(Assets.lv2wall35, false);
                tiles[37] = new Tile(Assets.lv2wall36, false);
                tiles[38] = new Tile(Assets.lv2wall37, false);
                tiles[39] = new Tile(Assets.lv2wall38, true);
                tiles[40] = new Tile(Assets.lv2wall39, false);
                tiles[41] = new Tile(Assets.lv2wall40, true);
                tiles[42] = new Tile(Assets.lv2wall41, false);
                tiles[43] = new Tile(Assets.lv2wall42, true);
                tiles[44] = new Tile(Assets.lv2wall43, false);
                tiles[45] = new Tile(Assets.lv2wall44, false);
                tiles[46] = new Tile(Assets.lv2wall45, false);
                tiles[47] = new Tile(Assets.lv2wall46, false);
                tiles[48] = new Tile(Assets.lv2wall47, false);
                tiles[49] = new Tile(Assets.lv2wall48, false);
                tiles[50] = new Tile(Assets.lv2wall49, true);
                tiles[51] = new Tile(Assets.lv2wall50, true);
                tiles[52] = new Tile(Assets.lv2wall51, true);
                tiles[53] = new Tile(Assets.lv2wall52, true);
                tiles[54] = new Tile(Assets.lv2wall53, true);
                tiles[55] = new Tile(Assets.lv2wall54, true);
                tiles[56] = new Tile(Assets.lv2wall55, false);
                tiles[57] = new Tile(Assets.lv2wall56, false);
                tiles[58] = new Tile(Assets.lv2wall57, false);
                tiles[59] = new Tile(Assets.lv2wall58, true);
                tiles[60] = new Tile(Assets.lv2wall59, true);
                tiles[61] = new Tile(Assets.lv2wall60, false);
                break;

            case 3: // level 3
                noTiles = 61;
                tiles = new Tile[noTiles];
                tiles[0] = new Tile(Assets.lv3background, true);
                tiles[1] = new Tile(Assets.lv3floor, false);
                tiles[2] = new Tile(Assets.lv3cracked_floor, false);
                tiles[3] = new Tile(Assets.lv3cracked_floor2, false);
                tiles[4] = new Tile(Assets.lv3grate1, false);
                tiles[5] = new Tile(Assets.lv3grate2, false);
                tiles[6] = new Tile(Assets.lv3grate3, false);
                tiles[7] = new Tile(Assets.lv3grate4, false);
                tiles[8] = new Tile(Assets.lv3wallcorner1, true);
                tiles[9] = new Tile(Assets.lv3wallmargin1, true);
                tiles[10]= new Tile(Assets.lv3wallcorner2, true);
                tiles[11] = new Tile(Assets.lv3wallframe1, true);
                tiles[12] = new Tile(Assets.lv3wallframe2, true);
                tiles[13] = new Tile(Assets.lv3wallcorner3, true);
                tiles[14] = new Tile(Assets.lv3wallcorner4, true);
                tiles[15] = new Tile(Assets.lv3wallmargin2, false);
                tiles[16] = new Tile(Assets.lv3wallbrick1, true);
                tiles[17] = new Tile(Assets.lv3wallbrick2, true);
                tiles[18] = new Tile(Assets.lv3wallbrick3, true);
                tiles[19] = new Tile(Assets.lv3wallbrick4, true);
                tiles[20] = new Tile(Assets.lv3wallbrick5, true);
                tiles[21] = new Tile(Assets.lv3wallbrick6, true);
                tiles[22] = new Tile(Assets.lv3wallbrick7, true);
                tiles[23] = new Tile(Assets.lv3wallbrick8, true);
                tiles[24] = new Tile(Assets.lv3wallbrick9, true);
                tiles[25] = new Tile(Assets.lv3wallbrick10, true);
                tiles[26] = new Tile(Assets.lv3wallbrick11, true);
                tiles[27] = new Tile(Assets.lv3wallcorner5, true);
                tiles[28] = new Tile(Assets.lv3wallcorner6, true);
                tiles[29] = new Tile(Assets.lv3wallcorner7, true);
                tiles[30] = new Tile(Assets.lv3wallcorner8, true);
                tiles[31] = new Tile(Assets.lv3wallframe3, true);
                tiles[32] = new Tile(Assets.lv3wallframe4, true);
                tiles[33] = new Tile(Assets.lv3wallframe5, true);
                tiles[34] = new Tile(Assets.lv3wallframe6, true);
                tiles[35] = new Tile(Assets.lv3wallframe7, true);
                tiles[36] = new Tile(Assets.lv3skeleton1, true);
                tiles[37] = new Tile(Assets.lv3skeleton2, true);
                tiles[38] = new Tile(Assets.lv3skeleton3, true);
                tiles[39] = new Tile(Assets.lv3skeleton4, true);
                tiles[40] = new Tile(Assets.lv3grate5, true);
                tiles[41] = new Tile(Assets.lv3grate6, true);
                tiles[42] = new Tile(Assets.lv3grate7, true);
                tiles[43] = new Tile(Assets.lv3doorframe1, true);
                tiles[44] = new Tile(Assets.lv3doorframe2, true);
                tiles[45] = new Tile(Assets.lv3doorframe3, true);
                tiles[46] = new Tile(Assets.lv3doorframe4, true);
                tiles[47] = new Tile(Assets.lv3doorframe5, true);
                tiles[48] = new Tile(Assets.lv3doorframe6, true);
                tiles[49] = new Tile(Assets.lv3floor2, false);
                tiles[50] = new Tile(Assets.lv3doorframe7, true);
                tiles[51] = new Tile(Assets.lv3doorframe8, true);
                tiles[52] = new Tile(Assets.lv3doorframe9, true);
                tiles[53] = new Tile(Assets.lv3doorframe10, true);
                tiles[54] = new Tile(Assets.lv3doorframe11, true);
                tiles[55] = new Tile(Assets.lv3doorframe12, true);
                tiles[56] = new Tile(Assets.lv3doorframe13, true);
                tiles[57] = new Tile(Assets.lv3doorframe14, true);
                tiles[58] = new Tile(Assets.lv3doorframe15, true);
                tiles[59] = new Tile(Assets.lv3wallframe8, true);
                tiles[60] = new Tile(Assets.lv3wallframe9, true);

                break;
        }

    }

}
