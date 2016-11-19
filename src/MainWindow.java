import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by Tom on 6/4/2015.
 */
public class MainWindow implements KeyListener {
    protected static int teamCount = 0;
    protected static JLabel teamCountDisplay = new JLabel("Teams :--");
    static JPanel mainPanel = new JPanel();
    static JPanel winnerPanel = new JPanel();
    static JPanel loserPanel = new JPanel();
    static JScrollPane scrollPane = new JScrollPane(winnerPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

    protected JFrame frame = new JFrame();
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

    static int height = 0;

    void onStart() {
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

        BinaryTree winnerBracket = BinaryTree.createWinnerBracket(teamCount - 2);
        BinaryTree losersBracket = Bracket.createLoserBracket(teamCount - 2);
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
        winnerBracket.getGameList().forEach(Game::printGame);
    }

    void placeGames(ArrayList<ArrayList<Game>> list, JPanel p) {

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).size(); j++) {
                JPanel gamep = getGameModule(list.get(i).get(j));
                Insets insets = p.getInsets();
                Dimension size = gamep.getPreferredSize();
                int yoff = p.getPreferredSize().height / (list.get(i).size() + 1);
                gamep.setBounds(20 + (i * 100) + (insets.left), ((j + 1) * (yoff)) + (insets.top), size.width, size.height);
                p.add(gamep);
            }
        }
    }

    JFrame initFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        panel.setPreferredSize(new Dimension(700, 300));
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createTitledBorder(title));
        panel.repaint();
        panel.revalidate();
        return panel;
    }

    public void makeMenuBar() {
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

    public void setTeamCount(int tc) {
        teamCount = tc;
        teamCountDisplay.setText("Teams :" + teamCount);
    }

    /////////////////////////////////////////////////////////////////
    JPanel refreshGameButtonLinks(JPanel panel) {//send in only JPanels containing Game modules
        for (Component c : panel.getComponents()) {if (c instanceof JPanel) {for (Component a : ((JPanel) c).getComponents()) {if (a instanceof JButton) {a.addMouseListener(new MouseAdapter() {public void mousePressed(MouseEvent e) {Main.out("!@!@");}});}}}}
        return panel;
        //loserPanel;

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

        mntmGameOptions.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                GameDetailsPopup gdp = new GameDetailsPopup();
                gdp.setGame(g);
                gdp.onStart();
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

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}