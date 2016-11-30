import gui.ColorProfile;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Tom on 6/4/2015.
 */
public class MainWindow implements KeyListener {
    boolean smallView = false;
    protected static int teamCount = 0;
    protected static JLabel teamCountDisplay = new JLabel("Teams :--");
    static JPanel mainPanel = new JPanel();
    static JPanel winnerPanel = new JPanel();
    static JPanel loserPanel = new JPanel();
    static JScrollPane scrollPane = new JScrollPane(winnerPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    ColorProfile colors= new ColorProfile();
    static ArrayList<Game> gameList = new ArrayList<>();
    static ArrayList<String> teamList = new ArrayList<>();
    JFrame frame = new JFrame();
    static TeamListImportPopup tlimporter = new TeamListImportPopup();
    static JMenuBar mainMenuBar = new JMenuBar();
    static JMenu mnFile = new JMenu("File");
    static JMenu mnView = new JMenu("View");
    static JCheckBoxMenuItem mntmSmallView = new JCheckBoxMenuItem("Small Games");
    static JMenuItem mntmOpen = new JMenuItem("Open");
    static JMenuItem mntmSave = new JMenuItem("Save");
    static JMenuItem mntmSaveAs = new JMenuItem("Save As");
    static JMenuItem mntmPref = new JMenuItem("Preferences");
    static JMenuItem mntmExit = new JMenuItem("Exit");
    static JMenu mnEdit = new JMenu("Edit");
    static JMenu mnAbout = new JMenu("About");
    static JMenuItem mntmTeamList = new JMenuItem("Team List Editior");

    static int height = 0;

    static BinaryTree winnerBracket;
    static BinaryTree losersBracket;

    void onStart(boolean loaded) {
        makeMenuBar();
        frame = initFrame(frame);
        winnerPanel = initGamePanel("Winners");
        loserPanel = initGamePanel("Losers");

        mainPanel.setLayout(new GridLayout(2, 1));
        mainPanel.add(winnerPanel);
        mainPanel.add(loserPanel);
        scrollPane.getViewport().add(mainPanel);
        frame.add(scrollPane);
        frame.repaint();
        frame.revalidate();
        if(!loaded){
            winnerBracket = BinaryTree.createWinnerBracket(teamCount - 2);
            losersBracket = Bracket.createLoserBracket(teamCount - 2);
        }
        winnerBracket.labelWinnerBracket(winnerBracket.getRoot());
        losersBracket.labelWinnerBracket(losersBracket.getRoot());

        height = winnerPanel.getPreferredSize().height;
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(new KeyEventDispatcher() {
                    @Override
                    public boolean dispatchKeyEvent(KeyEvent e) {
                        if (e.getKeyCode() == KeyEvent.VK_TAB) {
                            WizardPopup.frame.setVisible(true);
                        }
                        return false;
                    }
                });
        winnerPanel.addKeyListener(this);
        frame.setJMenuBar(mainMenuBar);

        ArrayList<ArrayList<Game>> list = losersBracket.split();
        placeGames(list, loserPanel);
        list.clear();
        list = winnerBracket.split();
        placeGames(list, winnerPanel);

        winnerPanel= refreshGameButtonLinks(winnerPanel);
        loserPanel= refreshGameButtonLinks(loserPanel);

        frame.invalidate();
        winnerPanel.invalidate();
        winnerPanel.setVisible(true);
        frame.setVisible(true);
        //winnerBracket.getGameList().forEach(Game::printGame);
    }

    void placeGames(ArrayList<ArrayList<Game>> list, JPanel p) {

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                JPanel gamep = getSmallGameModule(list.get(i).get(j));

               // JPanel gamep = getLargeGameModule(list.get(i).get(j));
                Insets insets = p.getInsets();
                Dimension size = gamep.getPreferredSize();
                int yoff = (p.getPreferredSize().height / (list.get(i).size() + 1));
                gamep.setBounds(20 + (i * 200) + (insets.left), ((j+1) * (yoff)) + (insets.top)-size.height, size.width, size.height);
                p.add(gamep);
            }
        }
    }
    JFrame initFrame(JFrame frame) {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.setSize(new Dimension(800, 600));
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        centreWindow(frame);
        return frame;
    }

    JPanel initGamePanel(String title) {
        JPanel panel = new JPanel();
        Dimension size= new Dimension(700,700);
        panel.setSize(size);
        panel.setPreferredSize(size);
        panel.setBackground(colors.getMainColor());
        panel.setForeground(colors.getTextColor());
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder(null, title, TitledBorder.LEFT,
                TitledBorder.TOP, new Font("consolas",Font.PLAIN,14), colors.getTextColor()));
        panel.repaint();
        panel.revalidate();
        return panel;
    }
    void makeMenuBar() {
        mntmTeamList.setFont(Main.robotoThin);
        mainMenuBar.add(mnFile);
        mainMenuBar.add(mnView);
        mainMenuBar.add(mnEdit);
        mainMenuBar.add(mnAbout);
        mnView.add(mntmSmallView);
        mnFile.add(mntmOpen);
        mnFile.add(mntmSave);
        mnFile.add(mntmSaveAs);
        mnFile.add(mntmPref);
        mnFile.add(mntmExit);
        mnEdit.add(mntmTeamList);
        mntmSmallView.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

            }
        });
        mntmOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                Action details = fc.getActionMap().get("viewTypeDetails");            //set the default view of fc to detailed view
                details.actionPerformed(null);
                fc.showOpenDialog(frame);
                File file = fc.getSelectedFile();
                FileHandler.load(file);
            }
        });
        mntmSave.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                try {
                    FileHandler.save(winnerBracket,losersBracket);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        mntmTeamList.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {//new java.awt.FileDialog((java.awt.Frame) null).setVisible(true);
                tlimporter = new TeamListImportPopup();
                tlimporter.onStart();
                tlimporter.onReopen();
            }
        }));
    }
    static void centreWindow(Window frame) {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        System.out.println("Screen:" + dimension.getWidth() + " " + dimension.getHeight());

        frame.setLocation(x, y);
    }
    void setTeamCount(int tc) {
        teamCount = tc;
        teamCountDisplay.setText("Teams :" + teamCount);
    }
    public static void setTrees(BinaryTree w, BinaryTree l){
        winnerBracket=w;
        losersBracket=l;
    }
    /////////////////////////////////////////////////////////////////
    JPanel refreshGameButtonLinks(JPanel panel) {//send in only JPanels containing Game modules
        for (Component c : panel.getComponents()) {if (c instanceof JPanel) {for (Component a : ((JPanel) c).getComponents()) {if (a instanceof JButton) {a.addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e) {Main.out("!@!@");}});}}}}
        return panel;
        //loserPanel;

    }
    JPanel getSmallGameModule(Game g) {
        g=Game.getDummyGame();
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        JPanel gamePanel = new JPanel();
        JLabel team1 = new JLabel(g.getTeam1());
        JLabel team2 = new JLabel(g.getTeam2());
        JLabel score1 = new JLabel("" + g.getScore1());
        JLabel score2 = new JLabel("" + g.getScore2());
        JButton gameNumberButton = new JButton(g.getGameNumber());
        JPopupMenu gameMenuPopup = new JPopupMenu();
        JMenuItem mntmGameOptions = new JMenuItem("Game Options");
        Game finalG = g;

        mntmGameOptions.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                GameDetailsPopup gdp = new GameDetailsPopup(finalG);


            }
        });
        gameNumberButton.addMouseListener((new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                gameMenuPopup.show(e.getComponent(), gameNumberButton.getX(), gameNumberButton.getY() + gameNumberButton.getHeight());
            }
        }));

        gamePanel.setLayout(new MigLayout());
        gamePanel.setBorder(border);
        gameMenuPopup.add(mntmGameOptions);
        gamePanel.add(team1,"cell 1 0, w 100px");
        gamePanel.add(team2, "cell 1 1, w 100px");
        //gamePanel.add(new JLabel(),"cell 0 0");
        int buttonPosx= (int) gameNumberButton.getPreferredSize().getWidth();
        int buttonPosy= (int) (gamePanel.getPreferredSize().getHeight()+gameNumberButton.getPreferredSize().getHeight()+50);
        gamePanel.add(gameNumberButton,"dock west");//"pad 0 0 0 0, pos 0 0,"+"w "+buttonPosx+", h "+buttonPosy

        return gamePanel;
    }

    JPanel getLargeGameModule(Game g) {
        g=Game.getDummyGame();
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
        JLabel dateLabel = new JLabel(g.getDate());
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

        Game finalG = g;
        mntmGameOptions.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                GameDetailsPopup gdp = new GameDetailsPopup(finalG);


            }
        });
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

        gamePanel.setLayout(new MigLayout("debug"));
        gamePanel.setBorder(border);
        buttonGroup.add(winnerbutton1);
        buttonGroup.add(winnerbutton2);
        gameMenuPopup.add(mntmGameOptions);
        gamePanel.add(gameNumberButton);
        gamePanel.add(new JLabel("Date:"));
        gamePanel.add(dateLabel,"wrap");
        gamePanel.add(team1);
        gamePanel.add(new JLabel("Time:"));
        gamePanel.add(timeLabel,"wrap");
        gamePanel.add(team2);
        gamePanel.add(new JLabel("Loc:"));
        gamePanel.add(locLabel);
        return gamePanel;
    }
    public void keyTyped(KeyEvent e) {

    }
    public void keyPressed(KeyEvent e) {

    }
    public void keyReleased(KeyEvent e) {

    }
}