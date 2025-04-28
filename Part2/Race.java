import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.*;
import java.lang.Math;
import javax.swing.*;
import java.awt.*;
import java.io.*;


/**
 * A three-horse race, each horse running in its own lane
 * for a given distance
 * 
 * @author McRaceface
 * @version 1.0
 */
public class Race
{
    private int raceLength;
    private int NumberOfRounds = 5;
    private HashMap<String, ImageIcon> ImgResources = new HashMap<String, ImageIcon>();
    private JFrame ScreenGUI = new JFrame("HorseRacing");
    private JPanel MainPanel = new JPanel(null);
    private String RaceTrack = "Oval";
    private ArrayList<Horse> RaceLanes = new ArrayList<Horse>();
    private ArrayList<Player> Players = new ArrayList<Player>();
    private Horse lane1Horse;
    private Horse lane2Horse;
    private Horse lane3Horse;

    /**
     * Constructor for objects of class Race
     * Initially there are no horses in the lanes
     * ERROR: this was not used when assigning
     * @param distance the length of the racetrack (in metres/yards...)
     */
    public Race(int distance)
    {
        // initialise instance variables
        ScreenGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ScreenGUI.setLayout(null);
        
        this.raceLength = distance;
        this.lane1Horse = null;
        this.lane2Horse = null;
        this.lane3Horse = null;

        //Load image resources
        ImgResources.put("BrownHorse", new ImageIcon("Images/Horse/Brown/Brown.png"));
        ImgResources.put("BrownHorseWin", new ImageIcon("Images/Horse/Brown/Brown_win.png"));
        ImgResources.put("BrownHorseBow", new ImageIcon("Images/Horse/Brown/Brown_Bow.png"));
        ImgResources.put("BrownHorseReins", new ImageIcon("Images/Horse/Brown/Brown_Reins.png"));
        ImgResources.put("BrownHorseLose", new ImageIcon("Images/Horse/Brown/Brown_Lose.png"));

        ImgResources.put("PurpleHorse", new ImageIcon("Images/Horse/Purple/Purple.png"));
        ImgResources.put("PurpleHorseWin", new ImageIcon("Images/Horse/Purple/Purple_Win.png"));
        ImgResources.put("PurpleHorseSunglass", new ImageIcon("Images/Horse/Purple/PurpleSunglass.png"));
        ImgResources.put("PurpleHorseReins", new ImageIcon("Images/Horse/Purple/Purple_Reins.png"));
        ImgResources.put("PurpleHorseLose", new ImageIcon("Images/Horse/Purple/Purple_Lose.png"));

        ImgResources.put("WhiteHorse", new ImageIcon("Images/Horse/White/White.png"));
        ImgResources.put("WhiteHorseWin", new ImageIcon("Images/Horse/White/White_Win.png"));
        ImgResources.put("WhiteHorseHat", new ImageIcon("Images/Horse/White/White_Hat.png"));
        ImgResources.put("WhiteHorseReins", new ImageIcon("Images/Horse/White/White_Reins.png"));
        ImgResources.put("WhiteHorseLose", new ImageIcon("Images/Horse/White/White_Lose.png"));

        ImgResources.put("SquareRace", new ImageIcon("Images/Racetrack/SquareRace.png"));
    }
    
    /**
     * ERROR:this was not use so: lane1Horse = theHorse instead of this.....
     * Adds a horse to the race in a given lane
     * 
     * @param theHorse the horse to be added to the race
     * @param laneNumber the lane that the horse will be added to
     */
    public void customiseHorse(int Breed, int Accessory)
    {
        final String [] Breeds = {"BrownHorse","PurpleHorse","WhiteHorse"};
        final String [] Accessorys = {"","Bow","Reins","","Sunglass","Reins","","Hat","Reins"};
        final String DisplayImg = Breeds[Breed] + Accessorys[Accessory + (Breed * 3)];
        System.out.println(DisplayImg);

        int NextBreed = 0;
        int NextAccessory = 0;

        if (Accessory + 1 < 3)
        {
            NextAccessory = Accessory + 1;
        }

        if (Breed + 1 < 3)
        {
            NextBreed = Breed + 1;
        }

        final int fNextBreed = NextBreed;
        final int fNextAccessory = NextAccessory;

        refreshMainPanel();
        MainPanel.setSize(300,300);

        JLabel HorseImg = new JLabel(ImgResources.get(DisplayImg));
        HorseImg.setBounds(100,10, 50,50);

        JButton ChangeBreedButton = new JButton("Change Breed");
        ChangeBreedButton.setSize(ChangeBreedButton.getPreferredSize());
        ChangeBreedButton.setLocation(100, 50);
        ChangeBreedButton.addActionListener(e -> customiseHorse(fNextBreed, 0));

        JButton ChangeAccessoryButton = new JButton("Change Accessory");
        ChangeAccessoryButton.setSize(ChangeBreedButton.getPreferredSize());
        ChangeAccessoryButton.setLocation(100, 100);
        ChangeAccessoryButton.addActionListener(e -> customiseHorse(Breed, fNextAccessory));

        JTextField HorseName = new JTextField("Horse Name", 10);
        HorseName.setSize(HorseName.getPreferredSize());
        HorseName.setLocation(20, 20);

        JButton FinishButton = new JButton("Finish");
        FinishButton.setSize(ChangeBreedButton.getPreferredSize());
        FinishButton.setLocation(100, 100);
        FinishButton.addActionListener(e -> addHorse(HorseName.getText(), Breeds[Breed],Accessorys[Accessory + (Breed * 3)] ));

        MainPanel.add(ChangeBreedButton);
        MainPanel.add(FinishButton);
        MainPanel.add(HorseImg);
        MainPanel.add(HorseName);
        MainPanel.add(ChangeAccessoryButton);

        PackGUI(300, 300);
    }

    /**
     *
     */
    public void addHorse(String Name,String Breed, String Accessory)
    {
        Horse NewHorse = new Horse(Name, Breed, Accessory, 0.9);
        RaceLanes.add(NewHorse);
        raceSetup();
    }

    /**
     * Start the race
     * The horse are brought to the start and
     * then repeatedly moved forward until the 
     * race is finished
     */
    private void startRace()
    {
        //declared a local variable to store the horse that won so that the winner message can be printed
        Horse WinnerHorse = null;
        //declare a local variable to tell us when the race is finished
        boolean finished = false;
        
        //reset all the lanes (all horses not fallen and back to 0).
        for (int i = 0; i < RaceLanes.size(); i++){
            RaceLanes.get(i).goBackToStart();
        }


        while (!finished)
        {
            //move each horse
            for (int i = 0; i < RaceLanes.size(); i++){
                moveHorse(RaceLanes.get(i));
            }

            //print the race positions
            
            //if any of the three horses has won the race is finished
            for (int i = 0; i < RaceLanes.size(); i++)
            {
                if (raceWonBy(RaceLanes.get(i)))
                {
                    finished = true;
                }
            }

            boolean AllFell = true;

            for (int i = 0; i < RaceLanes.size(); i++)
            {
                if (!(RaceLanes.get(i).hasFallen()))
                {
                    AllFell = false;
                }
            }

            if (AllFell)
            {
                System.out.println("Draw. All horses have fallen!");
                finished = true;
            }
            displayRace();

            //wait for 100 milliseconds
            //try{
              //  TimeUnit.MILLISECONDS.sleep(500);
            //}catch(Exception e){}
            displayRace();
            Timer timer = new Timer(100);
            timer.setRepeats(false);
            timer.start();
        }

        //printWinnerMessage(WinnerHorse);
    }
    /**
     *
     */

    public void displayRace()
    {
        refreshMainPanel();
        MainPanel.setSize(500,500);

        Image img = ImgResources.get("SquareRace").getImage();
        Image newimg = img.getScaledInstance(400, 400,  java.awt.Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newimg);

        JLabel HorseImg = new JLabel(newIcon);
        HorseImg.setBounds(0,0, 400,400);

        MainPanel.add(HorseImg);
        PackGUI(500, 500);
    }

    /**
     *
     */

    public void startRaceGUI()
    {
        menuGUI();
    }

    /**
     * Randomly make a horse move forward or fall depending
     * on its confidence rating
     * A fallen horse cannot move
     * 
     * @param theHorse the horse to be moved
     */
    private void moveHorse(Horse theHorse)
    {
        //if the horse has fallen it cannot move, 
        //so only run if it has not fallen
        if  (!theHorse.hasFallen())
        {
            
            //the probability that the horse will move forward depends on the confidence;
            if (Math.random() < theHorse.getConfidence())
            {
                theHorse.moveForward();

            }
            
            //the probability that the horse will fall is very small (max is 0.1)
            //but will also will depends exponentially on confidence 
            //so if you double the confidence, the probability that it will fall is *2
            if (Math.random() < (0.1*theHorse.getConfidence()*theHorse.getConfidence()))
            {
                theHorse.fall();
            }
        }
    }

    /** 
     * Determines if a horse has won the race
     *
     * @param theHorse The horse we are testing
     * @return true if the horse has won, false otherwise.
     */
    private boolean raceWonBy(Horse theHorse)
    {
        if (theHorse.getDistanceTravelled() == raceLength)
        {
            printWinnerMessage(theHorse);
            return true;
        }
        else
        {
            return false;
        }
    }


    /***
     * prints out a given string.
     *
     * @param Message The message we want to print
     */

    private void println(String Message)
    {
        System.out.println(Message);
    }

    /***
     * prints out the winner message when a horse wins.
     *
     * @param WinnerHorse The horse that won that we want to print out its name in the winner message.
     */

    private void printWinnerMessage(Horse WinnerHorse)
    {
        System.out.println(WinnerHorse.getName() + " has won!");
    }

    /***
     * Initialise the menu panel
     *
     */

    private void menuGUI()
    {
        refreshMainPanel();
        MainPanel.setSize(200,300);
        JButton StartButton = new JButton("Start Race");
        StartButton.setSize(StartButton.getPreferredSize());
        StartButton.setLocation(40, 90);
        StartButton.addActionListener(e -> raceSetup());

        JButton CusTrackButton = new JButton("Customise Racetrack");
        CusTrackButton.setSize(CusTrackButton.getPreferredSize());
        CusTrackButton.setLocation(40, 200);
        CusTrackButton.addActionListener(e -> customiseTrack());

        MainPanel.add(StartButton);
        MainPanel.add(CusTrackButton);
        ScreenGUI.add(MainPanel);

        PackGUI(300, 300);

    }


    /***
     * Initialise the menu panel
     *
     */

    private void raceSetup()
    {
        refreshMainPanel();
        MainPanel.setSize(500,300);

        final JList<String> PlayerList = new JList<String>(getPlayerNames());
        JScrollPane Scroller = new JScrollPane();
        Scroller.setViewportView(PlayerList);
        PlayerList.setLayoutOrientation(JList.VERTICAL);
        Scroller.setLocation(10,40);
        Scroller.setSize(new Dimension(100,100));

        final JList<String> HorseList = new JList<String>(getHorseNames());
        JScrollPane HScroller = new JScrollPane();
        HScroller.setViewportView(HorseList);
        HorseList.setLayoutOrientation(JList.VERTICAL);
        HScroller.setLocation(300,40);
        HScroller.setSize(new Dimension(100,100));

        JButton NewHorseButton = new JButton("Add Horse");
        NewHorseButton.setSize(NewHorseButton.getPreferredSize());
        NewHorseButton.setLocation(300, 10);
        NewHorseButton.addActionListener(e -> customiseHorse(0,0));

        JTextField NewPlayerName = new JTextField("Player Name", 10);
        NewPlayerName.setSize(NewPlayerName.getPreferredSize());
        NewPlayerName.setLocation(10, 10);
        //JTextField.setLocation(10,10);

        JButton AddPlayerButton = new JButton("Add player");
        AddPlayerButton.setSize(AddPlayerButton.getPreferredSize());
        AddPlayerButton.setLocation(120, 10);
        AddPlayerButton.addActionListener(e -> addPlayer(NewPlayerName.getText()));

        JButton ContinueButton = new JButton("Continue");
        ContinueButton.setSize(AddPlayerButton.getPreferredSize());
        ContinueButton.setLocation(100, 200);
        ContinueButton.addActionListener(e -> playerBetting());

        MainPanel.add(NewPlayerName);
        MainPanel.add(AddPlayerButton);
        MainPanel.add(NewHorseButton);
        MainPanel.add(Scroller);
        MainPanel.add(HScroller);
        MainPanel.add(ContinueButton);
        ScreenGUI.add(MainPanel);

        PackGUI(500, 300);

    }
    /***
     * Initialise the menu panel
     *
     */

    private void playerBetting()
    {
        String [] HorseChoice = new String [RaceLanes.size()];
        String [] PlayerChoice = new String [Players.size()];
        String [] PossibleBets = {"0","5","10","15","20","25","30"};

        for (int i =0; i < RaceLanes.size(); i++)
        {
            HorseChoice[i] = RaceLanes.get(i).getName();
        }

        for (int i =0; i < Players.size(); i++)
        {
            PlayerChoice[i] = Players.get(i).getName();
        }

        refreshMainPanel();
        MainPanel.setSize(500,300);

        final JComboBox<String> Horses = new JComboBox<String>(HorseChoice);
        Horses.setSize(Horses.getPreferredSize());
        Horses.setLocation(200,10);

        final JComboBox<String> PlayerDropdown = new JComboBox<String>(PlayerChoice);
        PlayerDropdown.setSize(PlayerDropdown.getPreferredSize());
        PlayerDropdown.setLocation(20,10);

        final JComboBox<String> PBDropdown = new JComboBox<String>(PossibleBets);
        PBDropdown.setSize(PBDropdown.getPreferredSize());
        PBDropdown.setLocation(300,10);

        final JList<String> PlayerList = new JList<String>(getPlayerNamesBettings());
        JScrollPane Scroller = new JScrollPane();
        Scroller.setViewportView(PlayerList);
        PlayerList.setLayoutOrientation(JList.VERTICAL);
        Scroller.setLocation(10,200);
        Scroller.setSize(new Dimension(200,100));

        JButton PlaceBetButton = new JButton("Place bet");
        PlaceBetButton.setSize(PlaceBetButton.getPreferredSize());
        PlaceBetButton.setLocation(300, 10);
        PlaceBetButton.addActionListener(e -> placePlayerBet(PlayerDropdown.getSelectedIndex(),PBDropdown.getSelectedItem().toString(),Horses.getSelectedIndex()) );

        JButton RaceButton = new JButton("Race!");
        RaceButton.setSize(RaceButton.getPreferredSize());
        RaceButton.setLocation(300, 200);
        RaceButton.addActionListener(e -> startRace());

        MainPanel.add(Horses);
        MainPanel.add(PBDropdown);
        MainPanel.add(RaceButton);
        MainPanel.add(PlaceBetButton);
        MainPanel.add(Scroller);
        MainPanel.add(PlayerDropdown);

        PackGUI(500, 300);
    }

    /***
     * Initialise the menu panel
     *
     */

    private String [] getPlayerNamesBettings()
    {
        String [] PlayerNames = new String [Players.size()];

        for (int i = 0; i < Players.size(); i++)
        {
            PlayerNames[i] = Players.get(i).getName() + " " + Players.get(i).getCash() + " " + Players.get(i).getCashWagered() + " " + Players.get(i).getLaneBettedOn();
        }

        return PlayerNames;
    }

    /***
     * Initialise the menu panel
     *
     */

    private void placePlayerBet(int PlaBeting, String BettingAmount, int LaneIndex)
    {
        Players.get(PlaBeting).wagerCash(Integer.parseInt(BettingAmount), LaneIndex);
        playerBetting();
    }

    /***
     * Initialise the menu panel
     *
     */
    private String [] getPlayerNames()
    {
        String [] PlayerNames = new String [Players.size()];

        for (int i = 0; i < Players.size(); i++)
        {
            PlayerNames[i] = Players.get(i).getName();
        }

        return PlayerNames;
    }

    /***
     * Initialise the menu panel
     *
     */
    private String [] getHorseNames()
    {
        String [] HorseNames = new String [RaceLanes.size()];

        for (int i = 0; i < RaceLanes.size(); i++)
        {
            HorseNames[i] = RaceLanes.get(i).getName();
        }

        return HorseNames;
    }

    /***
     *
     */

    private void addPlayer(String PlaName)
    {
        Players.add(new Player(PlaName,100));
        raceSetup();
    }

    /***
     * Initialise the menu panel
     *
     */

    private void customiseTrack()
    {
        refreshMainPanel();
        JButton SqButton = new JButton("Track 1");
        SqButton.setSize(SqButton.getPreferredSize());
        SqButton.setLocation(0, 100);
        SqButton.addActionListener(e -> changeTrack("SquareTrack"));

        JButton ReturnButton = new JButton("Return to menu");
        ReturnButton.setSize(new Dimension(200,20));
        ReturnButton.setLocation(0, 0);
        ReturnButton.addActionListener(e -> menuGUI());

        JTextField TrackDistance = new JTextField("" + raceLength, 10);

        JButton ApplyDistance = new JButton("Apply");
        ApplyDistance.setSize(ApplyDistance.getPreferredSize());
        ApplyDistance.setLocation(100, 50);
        ApplyDistance.addActionListener(e -> setRaceLength(TrackDistance.getText()));

        TrackDistance.setSize(TrackDistance.getPreferredSize());
        TrackDistance.setLocation(0, 50);

        MainPanel.add(SqButton);
        MainPanel.add(TrackDistance);
        MainPanel.add(ReturnButton);
        MainPanel.add(ApplyDistance);

        PackGUI(300, 300);

    }

    /***
     *
     */
    private void changeTrack(String RaceTrackChange)
    {
        RaceTrack = RaceTrackChange;
    }

    /***
     *
     */
    private void setRaceLength(String Length)
    {
        boolean IsNum = true;
        for (int i = 0; i < Length.length(); i++)
        {
            if (!(Length.charAt(i) >= '0' && Length.charAt(i) <= '9'))
            {
                IsNum = false;
            }
        }
        if (IsNum)
        {
            raceLength = Integer.parseInt(Length);
        }
    }

    /***
     * Initialise the menu panel
     *
     */

    private void PackGUI(int SizeX, int SizeY)
    {
        ScreenGUI.pack();
        ScreenGUI.setSize(SizeX,SizeY);
        ScreenGUI.setVisible(true);
    }

    /***
     *
     */
    private void refreshMainPanel()
    {
        MainPanel.removeAll();
        MainPanel.repaint();
        MainPanel.revalidate();
    }


}

