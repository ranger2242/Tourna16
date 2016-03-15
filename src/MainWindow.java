import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tom on 6/4/2015.
 */
public class MainWindow implements  KeyListener{
    static String sep="-----------------";
    protected static int teamCount = 0;
    protected static JLabel teamCountDisplay = new JLabel("Teams :--");
    protected static JPanel panel = new JPanel();
    protected static JPanel loserPanel= new JPanel();
    protected static JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    protected static ArrayList<Game> gameList = new ArrayList<>();
    protected static ArrayList<String> teamList = new ArrayList();
    protected static JFrame frame = new JFrame();
    protected static boolean[] secRoundPlacementBools;
    protected static TeamListImportPopup tlimporter = new TeamListImportPopup();
    protected static JMenuBar mainMenuBar = new JMenuBar();
    protected static JMenu mnFile = new JMenu("File");
    protected static JMenuItem mntmOpen = new JMenuItem("Open");
    protected static JMenuItem mntmSave = new JMenuItem("Save");
    protected static JMenuItem mntmSaveAs = new JMenuItem("Save As");
    protected static JMenuItem mntmPref = new JMenuItem("Preferences");
    protected static JMenuItem mntmExit = new JMenuItem("Exit");
    protected static JMenu mnEdit = new JMenu("Edit");
    protected static JMenu mnAbout = new JMenu("About");
    protected static JMenuItem mntmTeamList = new JMenuItem("Team List Editior");
    protected static int gamePanelY= 0;
    static int[] case1={1,3,2,4};
    static int[] case2={1,8,4,5,2,7,3,6};
    static int[] case3={1,16,8,9,3,13,5,12,2,15,7,10,4,14,6,11};
    int power;//highest power of 2^i <= n teams

    static int nthPower2;
    int overflow;
    char gameLetter;
    char winLetter;
    int teamsRound1Count;
    int teamsRound2Count;
    int gamesRound1Count;
    int gamesRound2Count;
    ArrayList<JLabel> nameLabels = new ArrayList<>();
    ArrayList<JLabel> firstRound = new ArrayList<>();
    ArrayList<String> firstRoundTeams=new ArrayList<>();
    ArrayList<String> secRoundTeams=new ArrayList<>();
    ArrayList<JLabel> secRound = new ArrayList<>();
    static int height =0;
    static int gameBlockHeight=0;
    void onStart() {
        makeFrame();
        makeMenuBar();
        makePanels();
        height=panel.getPreferredSize().height;
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if(e.getKeyCode()==KeyEvent.VK_TAB){
                            //Main.wizard.frame.getContentPane().revalidate();

                            //Main.wizard.frame.getContentPane().repaint();
                            Main.wizard.frame.setVisible(true);
                            //Main.wizard.onStart();


                        }
                        return false;
                    }
                });
        panel.addKeyListener(this);
        generate();
        frame.setJMenuBar(mainMenuBar);
        frame.invalidate();
        panel.invalidate();
        panel.setVisible(true);
        frame.setVisible(true);
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
    public static void makePanels() {
        Border lineBorder= BorderFactory.createTitledBorder("Winners");
        panel=new JPanel();
        panel.setPreferredSize(new Dimension(2000,700));
        panel.setLayout(null);
        panel.setBorder(lineBorder);
        scrollPane.getViewport().add(panel);
        frame.add(scrollPane);
        //frame.getContentPane().setBackground(Color.blue);
        panel.repaint();
        frame.repaint();
        panel.revalidate();
        frame.revalidate();
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
        initVariableSet();
        Main.out("POW:"+power+" RND2:"+nthPower2+" OVFL:"+ overflow);
        createTeamList();
        printTeams();
        createSeedRounds();
        if(overflow!=0){
            int j=0;
            createOverflowGames();
            calculatePositioningArray();
            createOverflowRound2Games();
            //resize panel
            if(gamesRound1Count>gamesRound2Count){
                //init gameBlockHeight
                Game g= new Game("-");
                buildGameModule(g,0,0);
                panel.removeAll();
                Main.out(gameBlockHeight+"");
                int height=(gameBlockHeight*gamesRound1Count)+(20*(gamesRound1Count));
                panel.setPreferredSize(new Dimension(1500,height));
            }
            j=0;
            for(int i=0; i<nthPower2;i++){//build first round games
                if(!secRoundPlacementBools[i]){
                    Game g = gameList.get(j);
                    double weight = (double)panel.getPreferredSize().height/ (double)(Math.pow(2,power));
                    Main.out("weight"+weight+" height"+height+" nth"+nthPower2);
                    buildGameModule(g,30,(int)(i*weight));

                    j++;
                }
            }
            j=overflow;
            for(int i=0;i<nthPower2/2;i++){//build second round games
                Game g = gameList.get(j);
                double weight = panel.getPreferredSize().getHeight()/(nthPower2/2);
                buildGameModule(g,250,(gameBlockHeight)/2+(i*((int)weight)));
                j++;
            }

        }else{  //no overflow
            int j=0;
            for(int i=0;i<teamCount/2;i++){
                Game g=new Game(""+gameLetter, teamList.get(j),teamList.get(j+1));
                gameList.add(g);
                j+=2;
                gameLetter++;
            }
            j=0;
            for(int i=0; i<nthPower2/2;i++){//place first round games
                    Game g = gameList.get(j);
                double weight =  panel.getPreferredSize().getHeight()/(nthPower2);
                buildGameModule(g,30,30+(i*((int)weight)));
                    j++;
            }
        }
        int j=power-1;
        int index=0;
        index=gameList.size();

        while(j>=0) {//create remaining games.
            for(int i=0;i<Math.pow(2,j)/2;i++){
                Game g= new Game(""+gameLetter,""+(winLetter),""+(char)(winLetter+1));
                gameList.add(g);
                gameLetter++;
                winLetter+=2;
            }
            j--;
        }
        j=power-1;
        while(j>=0) {//fill remaining games.
            for (int i = 0; i < Math.pow(2, j) / 2; i++) {
                try {
                Game g = gameList.get(index);
                    int coloffset = 0;
                    if (overflow != 0) coloffset = 1;
                    double weight =  panel.getPreferredSize().getHeight() / ((Math.pow(2, j)/2));
                    Main.out("weight"+weight+" height"+height+" nth"+Math.pow(2, j));

                    buildGameModule(g,250 + 250 * (power - 1 + coloffset - j),((gameBlockHeight)*(power-j+1))+ (i * ((int) weight)));
                index++;}
                catch (IndexOutOfBoundsException e){
                    Main.out("!!!!!_____INDEXOUTOFBOUNDSEXCEPTION");
                }
            }
            j--;
        }

    }
    //////////////////////////////////////////////////////////////////
    //Generator functions
    void initVariableSet(){
        teamList.clear();
        secRoundPlacementBools=null;
        power=power2();//highest power of 2^i <= n teams
        nthPower2=(int)Math.pow(2,power);
        overflow=teamCount-nthPower2;
        gameLetter='A';
        winLetter ='A';
        teamsRound1Count=overflow*2;
        teamsRound2Count=teamCount-teamsRound1Count;
        gamesRound1Count=overflow;
        gamesRound2Count=nthPower2/2;
        nameLabels = new ArrayList<>();
        firstRound = new ArrayList<>();
        firstRoundTeams=new ArrayList<>();
        secRound = new ArrayList<>();
        secRoundTeams=new ArrayList<>();
        gameList = new ArrayList<>();
    }
    int power2(){
        int power=0;
        while(Math.pow(2,power)<= teamCount){//gets 2^i limit
           // pow2val=(int) Math.pow(2, power);
            power++;
        }
        power--;
        return power;
    }
    void createTeamList(){
        teamList.clear();
        for(int i=0;i<teamCount;i++){//adds names to teamlist
            teamList.add("Team "+(i+1));
        }
    }
    void createSeedRounds(){
        Main.out("----Placing Teams----");
        for(int i=teamsRound2Count;i<teamCount;i++){//add round 1 teams to  list
            String s=teamList.get(i);
            Main.out(s+" selected for Round 1");
            firstRoundTeams.add(s);
        }
        for(int i=0;i<teamsRound2Count;i++){
            String s=teamList.get(i);
            Main.out(s+" selected for Round 2");
            secRoundTeams.add(s);
        }
        Main.out(sep);
    }
    void createOverflowGames(){
        int j=0;
        Main.out("----Placing Games----");
        for(int i=0;i<overflow;i+=1){
            Game g = new Game(""+gameLetter,firstRoundTeams.get(j),firstRoundTeams.get(j+1));
            gameLetter++;
            j+=2;
            gameList.add(g);
            Main.out("Game "+g.getGameNumber()+" added to round 1");
        }
        Main.out(sep);

    }
    void calculatePositioningArray(){
        int[] compArr=null;
        int x=teamList.size();
        if(x<8){compArr=case1;}
        else if(x>8 && x<16) {compArr=case2;}
        else if(x>16 && x<32) {compArr=case3;}

        secRoundPlacementBools = new boolean[compArr.length];
        for(int i=0;i<compArr.length;i++){
            secRoundPlacementBools[i]=false;
        }
        Main.out(Arrays.toString(compArr));
        for(int i=0;i<teamsRound2Count;i++){
            for(int k=0;k<compArr.length;k++){
                //Main.out("%"+k+" "+compArr.length);
                if(compArr[k]==i+1){
                    //Main.out("** "+secRoundPlacementBools.length);
                    secRoundPlacementBools[k]=true;
                }
            }
        }
        Main.out(Arrays.toString(secRoundPlacementBools));
    }
    void createOverflowRound2Games(){
        int j=0;
        for(int i=0;i<nthPower2;i++){
            String team1;
            if(secRoundPlacementBools[i]){
                team1=secRoundTeams.get(j);
                if(j<secRoundTeams.size()-1)
                    j++;
            }else{
                team1=""+winLetter;
                winLetter++;
            }
            String team2;
            i++;
            if(secRoundPlacementBools[i]){
                team2=secRoundTeams.get(j);
                if(j<secRoundTeams.size()-1)
                    j++;
            }else{
                team2=""+winLetter;
                winLetter++;
            }
            Game g = new Game(""+gameLetter,team1,team2);
            gameLetter++;
            gameList.add(g);
            Main.out("Game "+g.gameNumber+" added to round 2");

        }
    }
    void calculateRound1OverflowGamePosition(){

    }
    /////////////////////////////////////////////////////////////////
    void printTeams(){
        Main.out("----Team List----");
        for(String s:teamList){
            Main.out(s);
        }
        Main.out(sep);
    }
    public static void buildGameModule(Game g, int xoffset, int yoffset) {
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

        gamePanelY=gamePanel.getHeight();
        Insets insets = panel.getInsets();
        Dimension size =gamePanel.getPreferredSize();
        gameBlockHeight=size.height;
        gamePanel.setBounds(xoffset+insets.left,yoffset+insets.top,size.width,size.height);
        panel.add(gamePanel);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
    public void refresh(){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        generate();
    }
    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {

    }
}