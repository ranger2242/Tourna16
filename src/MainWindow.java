import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Tom on 6/4/2015.
 */
public class MainWindow implements  KeyListener{
    static String sep="-----------------";
    protected static int teamCount = 0;
    protected static JLabel teamCountDisplay = new JLabel("Teams :--");
    static JPanel rootPanel = new JPanel();
    protected static JPanel panel = new JPanel();
    protected static JPanel loserPanel= new JPanel();
    protected static JScrollPane scrollPane = new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    static ArrayList<Game> gameList = new ArrayList<>();
    static ArrayList<Game> loserGameList=new ArrayList<>();
    static ArrayList<JPanel> roundPanelListW=new ArrayList<>();
    static ArrayList<String> teamList = new ArrayList<>();
    static ArrayList<String> loserInstList= new ArrayList<>();
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
    int loserPosArr[]={1,1,2,2,4,8};
    int overflow;
    char gameLetterPre='A';
    char gameLetter;
    char winLetter;
    int teamsRound1Count;
    int teamsRound2Count;
    int gamesRound1Count;
    int gamesRound2Count;
    boolean gameNumPrefix=false;
    ArrayList<JLabel> nameLabels = new ArrayList<>();
    ArrayList<JLabel> firstRound = new ArrayList<>();
    ArrayList<String> firstRoundTeams=new ArrayList<>();
    ArrayList<String> secRoundTeams=new ArrayList<>();
    ArrayList<JLabel> secRound = new ArrayList<>();
    static int height =0;
    static int gameBlockHeight=0;
    static int gameBlockWidth=0;
    void onStart() {
        BinaryTree winnerBracket = BinaryTree.makeBracket(teamCount-2);
        winnerBracket.insertRootLeft(new BinaryNode());
        winnerBracket.insertRootLeft(new BinaryNode());
        winnerBracket.labelWinnerBracket(winnerBracket.getRoot());
        BinaryTree.printBT(winnerBracket);

        ArrayList<ArrayList<Game>> list=BinaryTree.split();

        initLoserList();
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
                            WizardPopup.frame.setVisible(true);
                            //Main.wizard.onStart();


                        }
                        return false;
                    }
                });
        panel.addKeyListener(this);
        //generate();
        frame.setJMenuBar(mainMenuBar);
        placeGames(list);
       // panel.;
        frame.invalidate();
        panel.invalidate();
        panel.setVisible(true);
        frame.setVisible(true);
    }
    public void placeGames(ArrayList<ArrayList<Game>> list){

        for(int i=0;i<list.size();i++){
            for(int j=0;j<list.get(i).size();j++){
                JPanel gamep=getGameModule(list.get(i).get(j));
                Insets insets = panel.getInsets();
                Dimension size= gamep.getPreferredSize();
                int yoff=panel.getPreferredSize().height/(list.get(i).size()+1);
                gamep.setBounds(20+(i*100)+(insets.left),((j+1)*(yoff))+(insets.top),size.width,size.height);
                panel.add(gamep);
            }
        }
    }
    public static void makeFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        rootPanel.setLayout(new GridLayout(2,1));

        Border lineBorder= BorderFactory.createTitledBorder("Winners");
        panel=new JPanel();
        panel.setPreferredSize(new Dimension(700,300));
        loserPanel.setPreferredSize(new Dimension(700,300));

        panel.setLayout(null);
        loserPanel.setLayout(null);
        panel.setBorder(lineBorder);
        lineBorder= BorderFactory.createTitledBorder("Losers");
        loserPanel.setBorder(lineBorder);
        rootPanel.add(panel);

        rootPanel.add(loserPanel);
        scrollPane.getViewport().add(rootPanel);
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
            //    File file = fc.getSelectedFile();
            }
        });

        mntmTeamList.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                tlimporter = new TeamListImportPopup();
                tlimporter.onStart();
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
    void initLoserList(){
        loserInstList.add("DA-CB-HI-EJ");
        loserInstList.add("BC-EA-DI-JK-FL");
        loserInstList.add("AB-CD-FJ-EF-LM-GN");
        loserInstList.add("EA-DK-CB-FL-GM-NO-HP");
        loserInstList.add("FA-DB-EL-CM-GN-HO-PQ-IR");
        loserInstList.add("GA-EB-DC-FM-NO-HP-IQ-RS-JT");
        loserInstList.add("HA-GB-FG-ED-NO-PQ-IR-JS-TU-KV");
        loserInstList.add("BC-IA-HO-GD-FE-PQ-RS-JT-KU-VW-LX");
        loserInstList.add("BC-EF-JA-IP-HD-GQ-RS-TU-KV-LW-XY-MZ");
        loserInstList.add("BC-DE-FG-KA-JQ-IR-HS-XU-VW-LX-MY-Z*AA-N*AB");
        loserInstList.add("AB-CD-EF-GH-LR-KS-JT-IU-VW-XY-MZ-N*AA-*AB*AC-O*AD");
        loserInstList.add("");//17
        loserInstList.add("JA=FB=IT-HG-EU-DC-LV-KW-NX-MY-Z*AA-*AB*AC-P*AD-O*AE-*AF*AG-Q*AH");
        loserInstList.add("KA-GB-EC-JA-IH-FV-DW-MX-LY-OZ-N*AA-*AB*AC-*AD*AE-Q*AF-P*AG-*AH*AI-R*AJ");
        loserInstList.add("LA-JB-HC-FD-KV-IW-GX-EY-NZ-M*AA-P*AB-O*AC-*AD*AE-*AF*AG-R*AH-Q*AI-*AJ*AK-S*AL");
        loserInstList.add("MA-KB-JC-ID-GE-LW-XY-MZ-P*AA-O*AB-N*AC-O*AD-P*AE-*AF*AG-*AH*AI-S*AJ-R*AK-*AL*AM-T*AN");
        loserInstList.add("NA-LB-KC-JD-HE-GF-MX-YZ-I*AA-*AB*AC-P*AD-O*AE-R*AF-Q*AG-*AH*AI-*AJ*AK-T*AL-S*AM-*AN*AO-U*AP");//22
        loserInstList.add("OA-MB-LC-KD-JE-IF-HG-NY-Z*AA-*AB*AC-*AD*AE-Q*AF-P*AG-S*AH-R*AI-*AJ*AK-*AL*AM-U*AN-T*AO-*AP*AQ-V*AR");
        loserInstList.add("PA-OB-NC-MD-LE-KF-JG-IH-Z*AA-*AB*AC-*AD*AE-*AF*AG-R*AH-Q*AI-T*AJ-S*AK-*AL*AM-*AN*AO-V*AP-U*AQ-*AR*AS-W*AT");
        loserInstList.add("BC-QA-P*AA-OD-ND-MF-LG-KH-JI-*AB*AC-*AD*AE-*AF*AG-*AH*AI-S*AJ-R*AK-U*AL-T*AM-*AN*AO-*AP*AQ-W*AR-V*AS-*AT*AU-X*AV");
        loserInstList.add("BC-GH-RA-Q*AB-PD-OE-NF-M*AC-LI-KJ-*AD*AE-*AF*AG-*AH*AI-*AJ*AK-T*AL-S*AM-V*AN-U*AO-*AP*AQ-*AR*AS-X*AT-W*AU-*AV*AW-Y*AX");
        loserInstList.add("BC-GH-JK-SA-R*AC-QD-PC-OF-N*AD-MI-L*AR-*AF*AG-*AH*AI-*AJ*AK-*AL*AM-U*AN-T*AO-W*AP-V*AQ-*AR*AS-*AT*AU-Y*AV-X*AW-*AX*AY-Z*AZ");
        loserInstList.add("BC-EF-HI-KL-TA-S*AD-RD-Q*AE-PG-O*AF-NJ-M*AG-*AH*AI-*AJ*AK-*AL*AM-*AN*AO-V*AP-U*AQ-X*AR-W*AS-*AT*AU-*AV*AW-Z*AX-Y*AY-*AZ*BA-*AA*BB");
        loserInstList.add("BC-DE-FG-IJ-LM-UA-T*AE-S*AF-R*AG-QH-P*AH-QK-N*AI-*AJ*AK-*AL*AM-*AN*AO-*AP*AQ-W-*AM-V*AS-Y*AT-X*AU-*AV*AW-*AX*AY-*AA*AZ-Z*BA-*BB*BC-*AB*BE");
        loserInstList.add("");
        loserInstList.add("");
        loserInstList.add("");
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
                Game g= new Game();
                buildGameModule(panel,g,0,0);
                panel.removeAll();
                Main.out(gameBlockHeight+"");
                int height=(gameBlockHeight*gamesRound1Count);
                panel.setPreferredSize(new Dimension(1500,height));
            }
            j=0;
            for(int i=0; i<nthPower2;i++){//build first round games
                if(!secRoundPlacementBools[i]){
                    Game g = gameList.get(j);
                    double weight = (double)panel.getPreferredSize().height/ Math.pow(2,power);
                    Main.out("weight"+weight+" height"+height+" nth"+nthPower2);
                    buildGameModule(panel,g,30,(int)(i*weight));

                    j++;
                }
            }
            j=overflow;
            for(int i=0;i<nthPower2/2;i++){//build second round games
                Game g = gameList.get(j);
                double weight = panel.getPreferredSize().getHeight()/(nthPower2/2);
                buildGameModule(panel,g,0,(gameBlockHeight)/2+(i*((int)weight)));
                j++;
            }

        }else{  //no overflow
            int j=0;
            for(int i=0;i<teamCount/2;i++){
                Game g=new Game(checkGameNumber(gameLetter), teamList.get(j),teamList.get(j+1));
                gameList.add(g);
                j+=2;
                gameLetter++;
            }
            j=0;
            for(int i=0; i<nthPower2/2;i++){//place first round games
                    Game g = gameList.get(j);
                double weight =  panel.getPreferredSize().getHeight()/(nthPower2);
                buildGameModule(panel, g,30,30+(i*((int)weight)));
                    j++;
            }
        }
        int j=power-1;
        int index=0;
        index=gameList.size();

        while(j>=0) {//create remaining games.
            for(int i=0;i<Math.pow(2,j)/2;i++){
                Game g= new Game(checkGameNumber(gameLetter),("Winner "+checkGameNumber(winLetter)),("Winner "+checkGameNumber((char)(winLetter+1))));
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

                    buildGameModule(panel, g,250 + 250 * (power - 1 + coloffset - j),((gameBlockHeight)*(power-j+1))+ (i * ((int) weight))+50);
                index++;}
                catch (IndexOutOfBoundsException e){
                    Main.out("!!!!!_____INDEXOUTOFBOUNDSEXCEPTION");
                }
            }
            j--;
        }
        //////////////////////////////////////////////////////
        //Losers Bracket
        createLosersBracket();
        frame.pack();
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
            Game g = new Game(checkGameNumber(gameLetter),firstRoundTeams.get(j),firstRoundTeams.get(j+1));
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
                team1=""+checkGameNumber(winLetter);
                winLetter++;
            }
            String team2;
            i++;
            if(secRoundPlacementBools[i]){
                team2=secRoundTeams.get(j);
                if(j<secRoundTeams.size()-1)
                    j++;
            }else{
                team2=""+checkGameNumber(winLetter);
                winLetter++;
            }
            Game g = new Game(checkGameNumber(gameLetter),team1,team2);
            gameLetter++;
            gameList.add(g);
            Main.out("Game "+g.gameNumber+" added to round 2");

        }
    }
    void createLoserOverflowGames(){

    }
    void createLosersBracket(){
        loserGameList.clear();

        String s = loserInstList.get(teamCount-6);
        int pos=0;
        gameLetter++;
        char base=gameLetter;
        for(int i=0;i<=gameList.size()-3;i++){
            String team1="";
            try{
            char temp=s.charAt(pos);
            if(temp !='*' && temp !='-' ) {
                if (temp < base){
                    team1="Loser "+temp;
                }
                else{
                    team1="Winner "+temp;
                }
                pos++;
            }
            else if(temp =='*'){
                pos++;
                team1="Winner "+s.charAt(pos)+""+s.charAt(pos+1);
                pos++;
            }
            String team2="";
            temp=s.charAt(pos);
            if(temp !='*' && temp !='-' ) {
                if (temp < base){
                    team2="Loser "+temp;
                }
                else{
                    team2="Winner "+temp;
                }
                pos++;
                }
                else if(temp =='*'){
                    pos++;
                    team2="Winner "+s.charAt(pos)+""+s.charAt(pos+1);
                    pos++;
                }
                temp = s.charAt(pos);
                if (temp == '-') {
                    pos++;
                }
                Game g= new Game(checkGameNumber(gameLetter),team1,team2);
                loserGameList.add(g);
                Main.out("Game "+g.gameNumber+" added to losers list");
                Main.out("Len:"+s.length()+" pos:"+pos);
                gameLetter++;
            }
            catch (StringIndexOutOfBoundsException e){

            }

        }
        int total=0;
        int arrPos=0;
        int ovf=0;
        boolean flag=false;
        int tempTotal=0;
        for(int i=0;i<loserPosArr.length;i++){
            total+=loserPosArr[i];
            if(total>=loserGameList.size() && !flag){
                arrPos=i;
                flag=true;
                ovf=loserGameList.size()-tempTotal;
            }
            else if(total<loserGameList.size()){
                tempTotal+=loserPosArr[i];
            }
        }
        Main.out(" arrPos"+arrPos+" loserList size"+loserGameList.size());
        Main.out("ovf"+ovf);

        //calc loser positions and add to panel
        int count=0;
        if(ovf>0)
        for(int i=0;i<ovf;i++){
            Main.out("x"+loserPosArr[arrPos]+" count"+count+" ovf"+ovf);
            int y = (gameBlockHeight+30) * (i);
            int x = ((gameBlockWidth+30) * 0);
            //if(count<loserGameList.size()) {
                buildGameModule(loserPanel, loserGameList.get(count), x, y);
                Main.out("Module added to losers panel at x:"+x+" y:"+y);
            //}
            count++;
        }

        if(ovf!=0){
            arrPos--;
        }
            for (int j = 0; j < loserGameList.size()-ovf &&arrPos>=0; j++) {
                for (int i = 0; i < loserPosArr[arrPos]; i++) {
                    Main.out("x"+loserPosArr[arrPos]+" count"+count+" j"+j);
                    int xmod=j;
                    if(ovf !=0){
                        xmod=j+1;
                    }
                    int y = (gameBlockHeight+30) * (i);
                    int x = ((gameBlockWidth+30) * xmod);
                    if(count<loserGameList.size()) {
                        buildGameModule(loserPanel, loserGameList.get(count), x, y);
                        Main.out("Module added to losers panel at x:" + x + " y:" + y);

                        count++;
                    }
                }
                arrPos--;
            }

    }
    String  checkGameNumber(char letter){
        int ovf=0;
        String num;
        if(letter>'Z' || gameNumPrefix){
            ovf=letter-'Z';
            letter=(char)('A'+(ovf%26));
            if(gameList.size()>52){
                gameLetterPre++;
            }
            num=gameLetterPre+""+letter;
            gameNumPrefix=true;

        }
        else{
            num=""+letter;
        }
        return num;
    }
    /////////////////////////////////////////////////////////////////
    void printTeams(){
        Main.out("----Team List----");
        for(String s:teamList){
            Main.out(s);
        }
        Main.out(sep);
    }
    public JPanel getGameModule(Game g) {
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
        // JPanel lineToNextGame = new JPanel();


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

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(team2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(score2, gbc);

        return gamePanel;
    }
    public static void buildGameModule(JPanel pnl, Game g, int xoffset, int yoffset) {
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
       // JPanel lineToNextGame = new JPanel();


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
/*
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(winnerbutton1, gbc);
*/
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(team2, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 3, 0, 0);
        gamePanel.add(score2, gbc);
/*
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
        */
        pnl.add(gamePanel);
        pnl.revalidate();
        pnl.repaint();
        pnl.updateUI();

        gamePanelY=gamePanel.getHeight();
        Insets insets = pnl.getInsets();
        Dimension size =gamePanel.getPreferredSize();
        gameBlockHeight=size.height;
        gameBlockWidth=size.width;
        gamePanel.setBounds(xoffset+insets.left,yoffset+insets.top,size.width,size.height);
        //pnl.add(gamePanel);
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