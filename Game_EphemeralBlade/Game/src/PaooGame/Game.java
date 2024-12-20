package PaooGame;

import PaooGame.Buttons.MouseClick;
import PaooGame.Entity.Collision;
import PaooGame.Entity.Entity;
import PaooGame.Entity.Player;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Tiles.InvalidLevelId;
import PaooGame.Tiles.Map;
import PaooGame.Tiles.Tile;

import java.awt.*;
import java.awt.image.BufferStrategy;

/*! \class Game
    \brief Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)

                ------------
                |           |
                |     ------------
    60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
        =       |     ------------
     16.7 ms    |           |
                |     ------------
                |     |   Draw   |  -->{ deseneaza totul pe ecran
                |     ------------
                |           |
                -------------
    Implementeaza interfata Runnable:

        public interface Runnable {
            public void run();
        }

    Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
    Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
    in noul thread(fir de executie). Mai multe explicatii veti primi la curs.

    In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
        - public Game();            //constructor
        - private void init();      //metoda privata de initializare
        - private void update();    //metoda privata de actualizare a elementelor jocului
        - private void draw();      //metoda privata de desenare a tablei de joc
        - public run();             //metoda publica ce va fi apelata de noul fir de executie
        - public synchronized void start(); //metoda publica de pornire a jocului
        - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable
{
    private GameWindow      wnd;        /*!< Fereastra in care se va desena tabla jocului*/
    private GameState       runState;   /*!< Flag ce starea firului de executie.*/
    private Thread          gameThread; /*!< Referinta catre thread-ul de update si draw al ferestrei*/
    private KeyHandler      kh;         /*!< Preia inputul de la tastatura*/
    private MouseClick      ml;         /*!< Preia click-ul de la mouse*/
    private BufferStrategy  bs;         /*!< Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at

    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    private Graphics        g;          /*!< Referinta catre un context grafic.*/
    private Map map;
    private Player player;
    private Menu menu;
    private long frameCounter;


    private Tile tile; /*!< variabila membra temporara. Este folosita in aceasta etapa doar pentru a desena ceva pe ecran.*/

    /*! \fn public Game(String title, int width, int height)
        \brief Constructor de initializare al clasei Game.

        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.

        \param title Titlul ferestrei.
        \param width Latimea ferestrei in pixeli.
        \param height Inaltimea ferestrei in pixeli.
     */
    public Game(String title, int width, int height)
    {
            /// Obiectul GameWindow este creat insa fereastra nu este construita
            /// Acest lucru va fi realizat in metoda init() prin apelul
            /// functiei BuildGameWindow();
        kh = new KeyHandler();
        ml = new MouseClick();
        wnd = new GameWindow(title, width, height, kh, ml);
            /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        runState = GameState.TITLE;
    }

    /*! \fn private void init()
        \brief  Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.

        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.

     */
    private void InitGame()
    {
        wnd = new GameWindow("Schelet Proiect PAOO", 1080, 720, kh, ml);
            /// Este construita fereastra grafica.
        wnd.BuildGameWindow();
            /// Se incarca toate elementele grafice (dale)
        Assets.Init();
        Player.Sprites();
        menu = new Menu(wnd.GetCanvas(), ml);
    }

    /*! \fn public void run()
        \brief Functia ce va rula in thread-ul creat.

        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run()
    {
            /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime = 0;                    /*!< Retine timpul curent de executie.*/
        long deathTime = 0;                     /*!< Retine timpul mortii player-ului.*/

            /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
            /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond   = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame      = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/
        final double timedeathFrame = timeFrame * 3 * 60; /*!< Durata pana la Game Over screen.*/

        frameCounter = 0;
        while (runState != GameState.OVER){
            /// Se obtine timpul curent
            curentTime = System.nanoTime();
            if((curentTime - oldTime) > timeFrame)
            {
                try {
                    StateActions();
                } catch (InvalidLevelId e) {
                    throw new RuntimeException(e);
                }
                oldTime = curentTime;
            }
            /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
        }
        StopGame();
    }

    private void StateActions() throws InvalidLevelId {
        Collision collision;
        switch (runState){
            case TITLE:
                if (frameCounter != 60){
                    DrawGameTitle();
                    frameCounter++;
                }else{
                    frameCounter = 0;
                    runState = GameState.MENU;
                }
                break;

            case MENU:
                DrawMenu();
                runState = menu.Update();
                break;

            case NEWGAME:
                map = new Map(1, 1, "res/maps/level1/room1.txt");
                collision = new Collision(map);
                Entity.SetCollision(collision);
                player = Player.GetInstance(Tile.TILE_WIDTH +22, 5*Tile.TILE_HEIGHT, kh);
                runState = GameState.INGAME;
                break;

            case LOAD:
                map = Map.Load();
                collision = new Collision(map);
                Entity.SetCollision(collision);
                player = Player.Load(kh);
                runState = GameState.INGAME;
                break;

            case INGAME:
                if (map == null || player == null) {
                    System.out.println("Map or Player is null in INGAME state!");
                }
                /// Actualizeaza pozitiile elementelor
                runState = Update();
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                break;

            case DEATH:
                if (frameCounter == 60*3){
                    GameOver();
                    frameCounter++;
                }else if (frameCounter == 60*8) {
                    frameCounter = 0;
                    runState = GameState.OVER;
                }else{
                    frameCounter++;
                }
                break;
        }
    }

    private void DrawMenu(){
        bs = wnd.GetCanvas().getBufferStrategy();
        /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
            /// Se executa doar la primul apel al metodei Draw()
            try
            {
                /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }

        /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        menu.Draw(g);

        bs.show();
        g.dispose();
    }
    private void DrawGameTitle(){
        bs = wnd.GetCanvas().getBufferStrategy();
        /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
            /// Se executa doar la primul apel al metodei Draw()
            try
            {
                /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }

        /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());
        g.drawImage(ImageLoader.LoadImage("/textures/GameTitle.png"), 109, 120, 862, 762, null);

        bs.show();
        g.dispose();
    }
    private void GameOver(){
        /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();
        /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
            /// Se executa doar la primul apel al metodei Draw()
            try
            {
                /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }

        /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();

        g.drawImage(ImageLoader.LoadImage("/textures/GameOver.png"), 270, 300, 540, 200, null);

        bs.show();
        g.dispose();
    }

    /*! \fn public synchronized void start()
        \brief Creaza si starteaza firul separat de executie (thread).

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame()
    {
        if(runState == GameState.TITLE) {
            /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
            /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
            /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }else{
            /// Thread-ul este creat si pornit deja
            return;
        }
    }

    /*! \fn public synchronized void stop()
        \brief Opreste executie thread-ului.

        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame()
    {
            /// Inchidem fereastra
        wnd.DisposeGameWindow();
            /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
        try
        {
                /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
            gameThread.join();
        }
        catch(InterruptedException ex)
        {
                /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
            ex.printStackTrace();
        }

        System.exit(0);
    }

    /*! \fn private void Update()
        \brief Actualizeaza starea elementelor din joc.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private GameState Update() throws InvalidLevelId {
        return player.Update(map);
    }

    /*! \fn private void Draw()
        \brief Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.

        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw()
    {
        /// Returnez bufferStrategy pentru canvasul existent
        bs = wnd.GetCanvas().getBufferStrategy();
        /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
            /// Se executa doar la primul apel al metodei Draw()
            try
            {
                /// Se construieste tripul buffer
                wnd.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
            /// Se obtine contextul grafic curent in care se poate desena.
        g = bs.getDrawGraphics();
            /// Se sterge ce era
        g.clearRect(0, 0, wnd.GetWndWidth(), wnd.GetWndHeight());

            /// operatie de desenare
            // ...............
        map.Draw(g, player);
        player.Draw(g);

        //g.drawRect(1 * Tile.TILE_WIDTH, 1 * Tile.TILE_HEIGHT, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);


            // end operatie de desenare
            /// Se afiseaza pe ecran
        bs.show();

            /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
            /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
    }
}

