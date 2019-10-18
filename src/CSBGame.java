import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EtchedBorder;

public class CSBGame {
    public static final int FIELD_WIDTH = 16000;
    public static final int FIELD_HEIGHT = 9000;
    public static final int CHECKPOINT_DIAM = 1200;
    public static final int POD_DIAM = 800;
    
    private static CSBGame game;
    
    private JFrame frame;
    private JPanel checkpointPanel;
    JTextField checkpointsNumField;
    
    private GameController gameController;
    private Visualization visualization;
    private ArrayList<Player> playerList;
    private Player player1;
    private Player player2;
    
    int checkpointsNum = 4;
    ArrayList<JTextField> checkpointValue;
    private int[] checkpointX = {1400,9000,14000,6000};
    private int[] checkpointY = {2000,1000,3000,7400};
    
    //private int[] checkpointX = {1400,12000,14000,3000};
    //private int[] checkpointY = {1000,7400,3000,6000};
    
    public static void main(String[] args) {
        game = new CSBGame();
        game.gameInitialize();
        game.buildGui();
    }
    
    private void createPlayers() {
        playerList = new ArrayList<>();
        player1 = new RandomPlayerVer2(); // yellow
        playerList.add(player1);
        
        //player2 = new RandomPlayerVer4();
        //playerList.add(player2);
        
        //player2 = new SimpleConditionPlayer();
        //playerList.add(player2);
        
        player2 = new ConditionPlayer();
        playerList .add(player2);
    }
    
    private void gameInitialize () {
        frame = new JFrame("Coders Strike Back");
        createPlayers();
        visualization = new Visualization(frame, playerList);
        gameController = new GameController(visualization, playerList);
    }
    
    private void buildGui() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(200,70,1400,800);
        frame.setVisible(true);
        
        Box controlPanel = new Box(BoxLayout.Y_AXIS);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(BorderLayout.EAST, controlPanel);
        
        checkpointPanel = new JPanel();
        checkpointPanel.setLayout(new BoxLayout(checkpointPanel, BoxLayout.X_AXIS));
        checkpointPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder
        (BorderFactory.createEtchedBorder(EtchedBorder.LOWERED),"Checkpoints"), BorderFactory.createEmptyBorder(30, 30, 30, 30)));
        controlPanel.add(checkpointPanel);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1));
        controlPanel.add(buttonPanel);
        
        JPanel startButtonBackPanel = new JPanel();
        startButtonBackPanel.setLayout(new GridLayout(0, 1));
        startButtonBackPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        setCheckpointPanel();
        
        JButton setCheckpointsNumButton = new JButton("Set checkpoints number");
        setCheckpointsNumButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        setCheckpointsNumButton.addActionListener(new SetCheckpointsNumListener());
        buttonPanel.add(BorderLayout.CENTER, setCheckpointsNumButton);
        
        JButton buildGameFieldButton = new JButton("Build game field");
        buildGameFieldButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buildGameFieldButton.addActionListener(new BuildGameFieldListener());
        buttonPanel.add(BorderLayout.CENTER, buildGameFieldButton);
        
        JButton startGameButton = new JButton("START GAME");
        startGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startGameButton.addActionListener(new StartGameListener());
        startButtonBackPanel.add(startGameButton);
        frame.getContentPane().add(BorderLayout.SOUTH, startButtonBackPanel);
        
        JButton resetGameButton = new JButton("Reset game");
        resetGameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetGameButton.addActionListener(new ResetGameListener());
        buttonPanel.add(BorderLayout.CENTER, resetGameButton);
        
        visualization.constructGameField(checkpointX, checkpointY);
        frame.revalidate();
    }
    
    private void setCheckpointPanel() {
        JPanel checkpointLabelPanel = new JPanel();
        checkpointLabelPanel.setLayout(new BoxLayout(checkpointLabelPanel, BoxLayout.Y_AXIS));
        checkpointPanel.add(checkpointLabelPanel);
        
        JPanel checkpointDataPanel = new JPanel();
        checkpointDataPanel.setLayout(new BoxLayout(checkpointDataPanel, BoxLayout.Y_AXIS));
        checkpointPanel.add(checkpointDataPanel);
        
        Label checkpointsNumLabel = new Label("Ch.p. num - ");
        checkpointLabelPanel.add(checkpointsNumLabel);
        
        checkpointsNumField = new JTextField("" + checkpointsNum);
        checkpointsNumField.setHorizontalAlignment(JTextField.CENTER);
        checkpointDataPanel.add(checkpointsNumField);
        
        checkpointValue = new ArrayList<>();
        
        for(int i = 0; i < checkpointsNum; i++) {
            Label label = new Label("Ch.p. X" + (i + 1) + " - ");
            checkpointLabelPanel.add(label);
            label = new Label("Ch.p. Y" + (i + 1) + " - ");
            checkpointLabelPanel.add(label);
            
            JTextField value = new JTextField("" + checkpointX[i], 10);
            value.setHorizontalAlignment(JTextField.CENTER);
            checkpointDataPanel.add(value);
            checkpointValue.add(value);
            value = new JTextField("" + checkpointY[i], 10);
            value.setHorizontalAlignment(JTextField.CENTER);
            checkpointDataPanel.add(value);
            checkpointValue.add(value);
        }
    }
    
    private void resetGame() {
        visualization.stopVisualize();
        createPlayers();
        visualization.setPlayerList(playerList);
        gameController.refresh(playerList);
    }
    
    public class StartGameListener implements ActionListener  {
        @Override
        public void actionPerformed(ActionEvent e) {
            visualization.isStarted(true);
            gameController.gameStart(checkpointX, checkpointY);
        }
    }
    
    public class ResetGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }
    
    public class SetCheckpointsNumListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
            checkpointsNum = Integer.parseInt(checkpointsNumField.getText());
            checkpointX = new int[checkpointsNum];
            checkpointY = new int[checkpointsNum];
            checkpointPanel.removeAll();
            setCheckpointPanel();
            frame.revalidate();
        }
    }
    
    public class BuildGameFieldListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
            for(int i = 1; i <= checkpointsNum; i++) {
                checkpointX[i - 1] = Integer.parseInt(checkpointValue.get(i*2 - 2).getText());
                checkpointY[i - 1] = Integer.parseInt(checkpointValue.get(i*2 - 1).getText());
            }
            visualization.constructGameField(checkpointX, checkpointY);
            frame.revalidate();
        }
    }
}