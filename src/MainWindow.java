import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
//Alex is high.
/**
 * Created by Tom on 6/4/2015.
 */
public class MainWindow {
    static int teamCount = 0;
    static Bracket2 bracket2 = new Bracket2();
    static JLabel teamCountDisplay = new JLabel("Teams :--");
    static MigLayout layout = new MigLayout();
    static JPanel panel = new JPanel();
    static JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    static ArrayList instructions = new ArrayList();
    static ArrayList<Game> gameList = new ArrayList<>();
    static ArrayList<String> teamList = new ArrayList();
    static JFrame frame = new JFrame();
    static int[] gamesPerRound;
    static boolean[] secRoundPlacementBools;
    static TeamListImportPopup tlimporter = new TeamListImportPopup();
    static JMenuBar mainMenuBar = new JMenuBar();
    static JMenu mnFile = new JMenu("File");
    static JMenuItem mntmOpen = new JMenuItem("Open");
    static JMenuItem mntmSave = new JMenuItem("Save");
    static JMenuItem mntmSaveAs = new JMenuItem("Save As");
    static JMenuItem mntmPref = new JMenuItem("Preferences");
    static JMenuItem mntmExit = new JMenuItem("Exit");
    static JMenu mnEdit = new JMenu("Edit");
    static JMenu mnAbout = new JMenu("About");
    static JMenuItem mntmTeamList = new JMenuItem("Team List Editior");
    static int gamePanelY= 0;

    static JPanel winnersBracketPanel = new JPanel();
    static JPanel losersBracketPanel = new JPanel();

    static JMenuItem mntm = new JMenuItem("");
    static int[] case1={1,3,2,4};
    static int[] case2={1,8,4,5,2,7,3,6};
    static int[] case3={1,16,8,9,3,13,5,12,2,15,7,10,4,14,6,11};


    void onStart() {
        makeFrame();
        makeMenuBar();
        makePanels();
        frame.setJMenuBar(mainMenuBar);
        frame.invalidate();
        panel.invalidate();
        panel.setVisible(true);
        frame.setVisible(true);
    }
    void addLabel(JLabel l, int i, int col){
        Insets insets = panel.getInsets();
        Dimension size =l.getPreferredSize();
        l.setBounds(30+(col*30)+insets.left,100+(i*10)+insets.top,size.width,size.height);
        panel.add(l);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();

    }
    public static void makeFrame() {
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        //panel.setLayout(layout);
        frame.setSize(new Dimension(800, 600));
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        centreWindow(frame);

    }
    public static void makePanels()
    {
        panel=new JPanel();
        panel.setPreferredSize(new Dimension(1000,1000));
        panel.setLayout(null);
        scrollPane.getViewport().add(panel);
        frame.add(scrollPane);
        frame.getContentPane().setBackground(Color.black);
        panel.repaint();
        frame.repaint();
        panel.revalidate();
        frame.revalidate();
        /*
        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Winners" );
        winnersBracketPanel.setBorder(title);
        title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Losers" );
        losersBracketPanel.setBorder(title);
        winnersBracketPanel.setLayout(new MigLayout("debug"));
        losersBracketPanel.setLayout(new MigLayout("debug"));

        int winnerBracketPanelHeight = (frame.getHeight()/4);
        int losersBracketPanelHeight = (frame.getHeight()/4);

        winnersBracketPanel.setPreferredSize(new Dimension(frame.getWidth()-10, winnerBracketPanelHeight));
        losersBracketPanel.setPreferredSize(new Dimension(frame.getWidth() - 10, losersBracketPanelHeight));
        winnersBracketPanel.setSize(frame.getWidth() - 10, winnerBracketPanelHeight);
        losersBracketPanel.setSize(frame.getWidth() - 10, losersBracketPanelHeight);
    */
    }
    public static void makeMenuBar() {
        mntmTeamList.setFont(Main.robotoThin);
        mainMenuBar.add(mnFile);
        mainMenuBar.add(mnEdit);
        mainMenuBar.add(mnAbout);
        mnFile.add(mntmOpen);
        mnFile.add(mntmSave);
        mnFile.add(mntmSaveAs);
        mnFile.add(mntmPref);
        mnFile.add(mntmExit);
        mnEdit.add(mntmTeamList);
        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                Action details = fc.getActionMap().get("viewTypeDetails");            //set the default view of fc to detailed view
                details.actionPerformed(null);
                fc.showOpenDialog(frame);
                File file = fc.getSelectedFile();
            }
        });

        mntmTeamList.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                tlimporter = new TeamListImportPopup();
                tlimporter.onReopen();
            }
        }));
    }
    public static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        System.out.println("Screen:" + dimension.getWidth() + " " + dimension.getHeight());

        frame.setLocation(x, y);
    }
    public void setTeamCount(int tc) {
        teamCount = tc;
        teamCountDisplay.setText("Teams :" + teamCount);
    }
    public void generate(){
        int count=0;
        int pow2val=0;
        int overflow=0;
        char gameLetter='A';
        while(Math.pow(2,count)<= teamCount){//gets 2^i limit
            pow2val=(int) Math.pow(2, count);
            count++;
        }
        count--;
        overflow=teamCount-pow2val;
        Main.out(count+" "+pow2val+" "+ overflow);
        for(int i=0;i<teamCount;i++){//adds names to teamlist
            teamList.add("Team "+(i+1));
        }
        //Collections.reverse(teamList);

        ArrayList<JLabel> nameLabels = new ArrayList<>();
        ArrayList<JLabel> firstRound = new ArrayList<>();
        ArrayList<JLabel> secRound = new ArrayList<>();
        ArrayList<Game> gameList = new ArrayList<>();
        int col=0;
        for(int i=teamList.size()-(overflow*2)+1;i<=teamList.size();i++){//add overflow to screen
            String s=teamList.get(i-1);
            Main.out(s);
            JLabel l = new JLabel(s);
            //addLabel(l,i,col);
            nameLabels.add(l);
            firstRound.add(l);
        }
        col++;
        for(int i=0; i< teamList.size()-(overflow*2);i++){//adds labels to screen
            String s=teamList.get(i);
            Main.out(s);
            JLabel l = new JLabel(s);
            //addLabel(l,i,col);
            nameLabels.add(l);
            secRound.add(l);
        }

        if(overflow!=0){
            int maxGames=(int)Math.pow(2,count+1);
            int firstRoundTeams= overflow*2;
            int j=0;
            for(int i=0;i<overflow;i+=1){
                Game g = new Game(""+gameLetter,firstRound.get(j).getText(),firstRound.get(j+1).getText());
                gameLetter++;
                j+=2;
                Main.out("#"+firstRound.size());
                gameList.add(g);
                JPanel gamePanel = buildGameModule(g);
                Insets insets = panel.getInsets();
                Dimension size =gamePanel.getPreferredSize();
                gamePanel.setBounds(30+insets.left,30+(i*(60))+insets.top,size.width,size.height);
                panel.add(gamePanel);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();

            }

            secRoundPlacementBools = new boolean[pow2val];
            for(int i=0;i<pow2val;i++){
                secRoundPlacementBools[i]=false;
            }

            Main.out("overflow:"+overflow);
            int[] compArr=null;
            int x=teamList.size();
            if(x<8){
                compArr=case1;

            }
            if(x>8 && x<16) {
                compArr=case2;

            }
            if(x>16 && x<32) {
                compArr=case3;

            }
            Main.out(Arrays.toString(compArr));
            for(int i=0;i<teamList.size()- (overflow*2);i++){
                for(int k=0;k<compArr.length;k++){
                    Main.out("%"+k+" "+compArr.length);
                    if(compArr[k]==i+1){
                        secRoundPlacementBools[k]=true;
                    }
                }
            }
            j=0;
            char winLetter ='A';
            for(int i=0;i<pow2val;i++){
                String team1;
                if(secRoundPlacementBools[i]){
                    team1=secRound.get(j).getText();
                    if(j<secRound.size()-1)
                    j++;
                }else{
                    team1=""+winLetter;
                    winLetter++;
                }
                String team2;
                i++;
                if(secRoundPlacementBools[i]){
                    team2=secRound.get(j).getText();
                    if(j<secRound.size()-1)
                        j++;
                }else{
                    team2=""+winLetter;
                    winLetter++;
                }
                Game g = new Game(""+gameLetter,team1,team2);
                gameLetter++;
                Main.out("#"+secRound.size());
                gameList.add(g);
                JPanel gamePanel = buildGameModule(g);
                Insets insets = panel.getInsets();
                Dimension size =gamePanel.getPreferredSize();
                gamePanel.setBounds(250+insets.left,30+(i*(30))+insets.top,size.width,size.height);
                panel.add(gamePanel);
                frame.getContentPane().revalidate();
                frame.getContentPane().repaint();
            }
            Main.out(Arrays.toString(secRoundPlacementBools));
        }
        //calculate round 2 distribution
        for(int i=0;i<teamCount;i++){
            //if(Math.pow(2,i+1))
        }
    }
    public void secondRoundCheck( ){

    }
    public void loadBracket() {
        String fileName = "C:\\Users\\Tom\\Google Drive\\JAVA\\Tourna15\\data\\data" + teamCount + ".txt";
        try {
            String s;
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while ((s = bufferedReader.readLine()) != null) {
                instructions.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        printBracketInstructions();
        bracket2.setInstructions(instructions);
        bracket2.makeBracketGames();
        bracket2.printGameList();
        gameList = bracket2.getGamesList();

        gameListRoundAnalysis();
       // setTeamsFromList();
        placeGameModules();
    }
    public void printBracketInstructions() {
        for (int i = 0; i < instructions.size(); i++) {
            System.out.println(instructions.get(i));
        }
    }
    public void gameListRoundAnalysis() {
        String totalRounds = null;
        totalRounds = gameList.get(gameList.size() - 1).getRound();
        int[] rounds = new int[Integer.parseInt(totalRounds)];
        String previousRound = null;
        int count = 0;

        for (int i = 0; i < gameList.size(); i++) {
            String a = gameList.get(i).getRound();
            if (i == 0) {
                previousRound = a;
            }
            if (!previousRound.equals(a)) {
                rounds[Integer.parseInt(previousRound) - 1] = count;
                count = 0;
                previousRound = a;
            }
            count++;
        }
        rounds[rounds.length - 1] = 1;
        gamesPerRound = rounds;
        for (int i = 0; i < gamesPerRound.length; i++)
            System.out.println(rounds[i]);
    }
    static public void placeGameModules() {
        frame.setJMenuBar(mainMenuBar);

        int count = 0;
        String previousRound = null;
        int gamesWc = 0;
        int[] gamesW= new int[10];
        int gamesLc = 0;
        int[] gamesL= new int[10];

        int totalGames = 0;
        int f=0;

        for (int i = 0; i < gameList.size(); i++) {

            String a = gameList.get(i).getRound();
            String gameBracket = gameList.get(i).getBracket();
            if (i == 0) {
                previousRound = a;
            }
            if (!previousRound.equals(a)) {
                count = 0;
                gamesW[(Integer.parseInt(previousRound))-1]= gamesWc;
                gamesL[(Integer.parseInt(previousRound))-1]= gamesLc;
                gamesWc=0;
                gamesLc=0;
                previousRound = a;
            }
            if(gameBracket.equals("Winners"))
            {
                gamesWc++;
            }
            if(gameBracket.equals("Losers"))
            {
                gamesLc++;
            }
        }
        System.out.println("Games1W:"+gamesWc);
        System.out.println("Games1L:"+gamesLc);
        for (int i=0; i<gamesW.length; i++)
        {
            System.out.print(gamesW[i]+" ");
        }

        int lcount =0;

        for (int i = 0; i < gameList.size(); i++) {
            String a = gameList.get(i).getRound();
            String gameBracket = gameList.get(i).getBracket();
            JPanel gameModule = buildGameModule(gameList.get(i));


            if (i == 0) {
                previousRound = a;
            }
            if (!previousRound.equals(a)) {
                count = 0;
                previousRound = a;
            }

            int frameWidth = frame.getWidth();
            int frameHeight=frame.getHeight()/2;
            int gamesThisRound = Integer.parseInt(previousRound);
            int totalRounds = gamesPerRound.length;
            int xspacing = (frameWidth / totalRounds) / 2;
           // int yspacing = (winnersBracketPanel.getHeight()/gamesW[Integer.parseInt(previousRound)]);
            int posx = (((frameWidth / totalRounds) * gamesThisRound)) - (frameWidth / totalRounds);
            //int posx = 0;
           // int posy= (((frameHeight / gamesPerRound[gamesThisRound - 1]) * count) );
            int posy=0;

            if(!a.equals("1"))
            {
             // posy +=yspacing;
            }

            System.out.println("pr"+previousRound+ " "+gamesW[Integer.parseInt(previousRound)-1]+" "+gamesL[Integer.parseInt(previousRound)-1]);

            if(gameBracket.equals("Winners"))
            {
                if(gamesW[Integer.parseInt(previousRound)]==0)
                {
                    posy=0;
                }
                else{
                    posy=(winnersBracketPanel.getHeight()/gamesW[Integer.parseInt(previousRound)])*count;
                }
                String constraint = "pos " + posx + " " + posy+",wrap";
                winnersBracketPanel.add(gameModule, constraint);
                winnersBracketPanel.updateUI();
            }
            else if(gameBracket.equals("Losers"))
            {
                if(gamesL[Integer.parseInt(previousRound)]==0)
                {
                    posy=0;
                }
                else{
                    if(lcount ==gamesL[Integer.parseInt(previousRound)])
                    {
                        lcount=0;
                    }
                    posy=(losersBracketPanel.getHeight()/gamesL[Integer.parseInt(previousRound)])*lcount;
                    lcount++;
                }
                String constraint = "pos " + posx + " " + posy+",wrap";
                losersBracketPanel.add(gameModule, constraint);
                losersBracketPanel.updateUI();
            }
            /*
            else if(gameBracket.equals("Losers"))
            {
                //posy=(winnersBracketPanel.getHeight()/gamesPerRound[gamesThisRound -1]*count+(frameHeight/2));
                String constraint = "pos " + posx + " " + posy;
                losersBracketPanel.add(gameModule, constraint);
            }*/
            String constraints = "pos 0 "+(frame.getHeight()/2);
            //panel.add(winnersBracketPanel,"pos 0 0");
            //panel.add(losersBracketPanel,constraints);
            count++;
        }
    }
    public static JPanel buildWinnerBracketModule()
    {
        return winnersBracketPanel;
    }
    public static JPanel buildGameModule(Game g) {
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        JPanel gamePanel = new JPanel();
        GridBagConstraints gbc = new GridBagConstraints();
        JRadioButton winnerbutton1 = new JRadioButton();
        JRadioButton winnerbutton2 = new JRadioButton();
        JLabel team1 = new JLabel(g.getTeam1());
        JLabel team2 = new JLabel(g.getTeam2());
        JLabel score1 = new JLabel("" + g.getScore1());
        JLabel score2 = new JLabel("" + g.getScore2());
        JLabel timeLabel = new JLabel(g.getTime() + " " + g.getDate());
        JLabel locLabel = new JLabel(g.getLocation());
        JButton gameNumberButton = new JButton(g.getGameNumber());
        ButtonGroup buttonGroup = new ButtonGroup();
        JPopupMenu gameMenuPopup = new JPopupMenu();
        JMenuItem mntmGameOptions = new JMenuItem("Game Options");
        JPanel lineToNextGame = new JPanel();


        gameNumberButton.setFont(new Font("robotoThin", Font.PLAIN, 8));
        team2.setFont(Main.robotoThin);
        team1.setFont(Main.robotoThin);
        timeLabel.setFont(Main.robotoThin);
        locLabel.setFont(Main.robotoThin);
        score1.setFont(Main.robotoThin);
        score2.setFont(Main.robotoThin);

        mntmGameOptions.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                GameDetailsPopup gameDetailsPopup = new GameDetailsPopup();
                String s = gameNumberButton.getText();
                Game g = new Game();
                for (int i = 0; i < gameList.size(); i++) {
                    if (s.equals(gameList.get(i).getGameNumber())) {
                        g = gameList.get(i);
                    }
                }
                gameDetailsPopup.setGame(g);
                gameDetailsPopup.onStart();
            }
        }));
        winnerbutton1.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            }
        }));
        winnerbutton2.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
            }
        }));
        gameNumberButton.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                gameMenuPopup.show(e.getComponent(), gameNumberButton.getX(), gameNumberButton.getY() + gameNumberButton.getHeight());
            }
        }));

        gamePanel.setLayout(new GridBagLayout());
        gamePanel.setBorder(border);
        buttonGroup.add(winnerbutton1);
        buttonGroup.add(winnerbutton2);
        gameMenuPopup.add(mntmGameOptions);

        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridheight = 2;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.insets = new Insets(5, 5, 3, 3);
        gamePanel.add(gameNumberButton, gbc);
        gameNumberButton.setMargin(new Insets(0, 0, 0, 0));
        gameNumberButton.setPreferredSize(new Dimension(20, 20));
        gamePanel.updateUI();

        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(team1, gbc);


        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(score1, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(winnerbutton1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(team2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(score2, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(winnerbutton2, gbc);

        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 4, 0, 0);
        gamePanel.add(timeLabel, gbc);

        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(0, 4, 0, 0);
        gamePanel.add(locLabel, gbc);
        panel.add(gamePanel);
        panel.revalidate();
        panel.repaint();
        panel.updateUI();

        //scrollPane.updateUI();
        gamePanelY=gamePanel.getHeight();
        return gamePanel;
    }
/*
    public static void paint(Graphics g)
    {
        g.drawLine(0,0,200,200);
    }

    public static void recieveTeamListPopup(TeamListImportPopup tlimp) {
        tlimporter=tlimp;
        tlimporter.onStart();
        teamList=tlimporter.getTeamList();
    }

    public static void setTeamsFromList() {
        System.out.println("TeamList Size: " + teamList.size());
        System.out.println("GameList Size: "+gameList.size());

        for(int i=0; i<gameList.size(); i++)
        {
            String team1 = gameList.get(i).getTeam1();
            int pos = 0;
            if(team1.charAt(0)=='T')
            {
                team1=team1.replaceAll("\\s+","");
                pos=Integer.parseInt(team1.substring(4));
                if(Math.signum(pos)<0)
                {
                    pos*= -1;
                }
                gameList.get(i).setTeam1(teamList.get(pos-1));
            }
            String team2 = gameList.get(i).getTeam2();
            if(team2.charAt(0)=='T')
            {
                team2=team2.replaceAll("\\s+","");
                pos=Integer.parseInt(team2.substring(4));
                if(Math.signum(pos)<0)
                {
                    pos*= -1;
                }
                gameList.get(i).setTeam2(teamList.get(pos-1));
            }

        }
    }

    public static void setTeamList(ArrayList<String> arrayList)
    {
        teamList=arrayList;
    }
    */
}