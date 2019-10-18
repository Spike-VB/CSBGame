import static java.lang.Math.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Visualization {
    public static final int FIELD_DIVISOR = 20;
    public static final int ANIMATION_SPEED = 10;

    private final JFrame frame;
    private MyDrawPanel gameField;
    private boolean isStarted = false;
    private boolean isStopped = true;

    private int[] checkpointX;
    private int[] checkpointY;
    
    ArrayList<Player> playerList;
    
    javax.swing.Timer timer;
    
    int turn;
    
    int i = 0;
    int j = 1;

    public Visualization(JFrame f, ArrayList<Player> pList) {
        frame = f;
        playerList = pList;
    }

    public void isStarted(boolean b) {
        isStarted = b;
    }
    
    public void setPlayerList(ArrayList<Player> plList) {
        playerList = plList;
    }
    
    public void constructGameField(int[] chpX, int[] chpY) {
        checkpointX = chpX;
        checkpointY = chpY;
        gameField = new MyDrawPanel();
        Box backPanel = new Box(BoxLayout.X_AXIS);
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.getContentPane().add(BorderLayout.CENTER, backPanel);
        backPanel.add(gameField);
    }

    public void visualize(int t) {
        isStopped = false;
        turn = t;
        timer = new javax.swing.Timer(ANIMATION_SPEED, new VisualizationListener());
        timer.start();
    }
    
    public void stopVisualize() {
        if(isStarted == true) {
            timer.stop();
        }
        isStarted = false;
        isStopped = true;
        i = 0;
        j = 1;
        gameField.repaint();
    }
    
    private void visualizeGameField(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(0,0,gameField.getWidth(),gameField.getHeight());
        g.setColor(Color.white);
        g.fillRect((gameField.getWidth() - CSBGame.FIELD_WIDTH / Visualization.FIELD_DIVISOR) / 2, 
                (gameField.getHeight() - CSBGame.FIELD_HEIGHT / Visualization.FIELD_DIVISOR) / 2, 
                CSBGame.FIELD_WIDTH / Visualization.FIELD_DIVISOR,
                CSBGame.FIELD_HEIGHT / Visualization.FIELD_DIVISOR);
        g.setColor(Color.red);
        g.fillOval((gameField.getWidth() - CSBGame.FIELD_WIDTH / Visualization.FIELD_DIVISOR) / 2 + checkpointX[0] / Visualization.FIELD_DIVISOR - CSBGame.CHECKPOINT_DIAM / (2 * Visualization.FIELD_DIVISOR),
                (gameField.getHeight() - CSBGame.FIELD_HEIGHT / Visualization.FIELD_DIVISOR) / 2 + checkpointY[0] / Visualization.FIELD_DIVISOR - CSBGame.CHECKPOINT_DIAM / (2 * Visualization.FIELD_DIVISOR),
                CSBGame.CHECKPOINT_DIAM / Visualization.FIELD_DIVISOR, CSBGame.CHECKPOINT_DIAM / Visualization.FIELD_DIVISOR);
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont((float) 20));
        g.drawString("1", (gameField.getWidth() - CSBGame.FIELD_WIDTH / Visualization.FIELD_DIVISOR) / 2 + checkpointX[0] / Visualization.FIELD_DIVISOR, 
                (gameField.getHeight() - CSBGame.FIELD_HEIGHT / Visualization.FIELD_DIVISOR) / 2 + checkpointY[0] / Visualization.FIELD_DIVISOR);
        for (int i = 1; i < checkpointX.length; i++) {
            g.setColor(Color.gray);
            g.fillOval((gameField.getWidth() - CSBGame.FIELD_WIDTH / Visualization.FIELD_DIVISOR) / 2 + checkpointX[i] / Visualization.FIELD_DIVISOR - CSBGame.CHECKPOINT_DIAM / (2 * Visualization.FIELD_DIVISOR),
                    (gameField.getHeight() - CSBGame.FIELD_HEIGHT / Visualization.FIELD_DIVISOR) / 2 + checkpointY[i] / Visualization.FIELD_DIVISOR - CSBGame.CHECKPOINT_DIAM / (2 * Visualization.FIELD_DIVISOR),
                    CSBGame.CHECKPOINT_DIAM / Visualization.FIELD_DIVISOR, CSBGame.CHECKPOINT_DIAM / Visualization.FIELD_DIVISOR);
            g.setColor(Color.white);
            g.drawString("" + (i + 1), (gameField.getWidth() - CSBGame.FIELD_WIDTH / Visualization.FIELD_DIVISOR) / 2 + checkpointX[i] / Visualization.FIELD_DIVISOR, 
                    (gameField.getHeight() - CSBGame.FIELD_HEIGHT / Visualization.FIELD_DIVISOR) / 2 + checkpointY[i] / Visualization.FIELD_DIVISOR);
        }
    }
    
    private void visualizePodMovement(Graphics g) {
        visualizeGameField(g);
        for(int pl = 0; pl < playerList.size(); pl++) {
            Player player = playerList.get(pl);
            for(int p = 1; p <= 2; p++) {
                int x1 = (gameField.getWidth() - CSBGame.FIELD_WIDTH / Visualization.FIELD_DIVISOR) / 
                        2 + player.getPodXLog(p, i) / Visualization.FIELD_DIVISOR - CSBGame.POD_DIAM / (2 * Visualization.FIELD_DIVISOR);
                int x2 = (gameField.getWidth() - CSBGame.FIELD_WIDTH / Visualization.FIELD_DIVISOR) / 
                        2 + player.getPodXLog(p, i + 1) / Visualization.FIELD_DIVISOR - CSBGame.POD_DIAM / (2 * Visualization.FIELD_DIVISOR);
                int y1 = (gameField.getHeight() - CSBGame.FIELD_HEIGHT / Visualization.FIELD_DIVISOR) / 
                        2 + player.getPodYLog(p, i) / Visualization.FIELD_DIVISOR - CSBGame.POD_DIAM / (2 * Visualization.FIELD_DIVISOR);
                int y2 = (gameField.getHeight() - CSBGame.FIELD_HEIGHT / Visualization.FIELD_DIVISOR) / 
                        2 + player.getPodYLog(p, i + 1) / Visualization.FIELD_DIVISOR - CSBGame.POD_DIAM / (2 * Visualization.FIELD_DIVISOR);
                int x = x1 + (int) round(j*(x2 - x1)/10);
                int y = y1 + (int) round(j*(y2 - y1)/10);
                
                if(pl == 0) {
                    g.setColor(Color.yellow);
                } else if(pl == 1) {
                    g.setColor(Color.orange);
                }
                
                g.fillOval(x, y, CSBGame.POD_DIAM / Visualization.FIELD_DIVISOR, CSBGame.POD_DIAM / Visualization.FIELD_DIVISOR);
                
                int xAngle = (int) round(CSBGame.CHECKPOINT_DIAM/(2*Visualization.FIELD_DIVISOR)*0.5*cos(player.getPodAngleLog(p, i)/57.2958 + 0.4)) 
                        - 5 + x + CSBGame.POD_DIAM/(2*Visualization.FIELD_DIVISOR);
                int yAngle = (int) round(CSBGame.CHECKPOINT_DIAM/(2*Visualization.FIELD_DIVISOR)*0.5*sin(player.getPodAngleLog(p, i)/57.2958 + 0.4)) 
                        - 5 + y + CSBGame.POD_DIAM/(2*Visualization.FIELD_DIVISOR);
                
                g.setColor(Color.black);
                g.fillOval(xAngle,yAngle,10,10);
                
                xAngle = (int) round(CSBGame.CHECKPOINT_DIAM/(2*Visualization.FIELD_DIVISOR)*0.5*cos(player.getPodAngleLog(p, i)/57.2958 - 0.4)) 
                        - 5 + x + CSBGame.POD_DIAM/(2*Visualization.FIELD_DIVISOR);
                yAngle = (int) round(CSBGame.CHECKPOINT_DIAM/(2*Visualization.FIELD_DIVISOR)*0.5*sin(player.getPodAngleLog(p, i)/57.2958 - 0.4)) 
                        - 5 + y + CSBGame.POD_DIAM/(2*Visualization.FIELD_DIVISOR);
                
                g.fillOval(xAngle,yAngle,10,10);
            }
        }
        j++;
        if(j == 11) {
            i++;
            j=1;
        }
        if (i == turn) {
            timer.stop();
            isStopped = true;
        }
    }

    private class MyDrawPanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        public void paintComponent(Graphics g) {
            if (isStarted == false) {
                visualizeGameField(g);
            } else if (isStopped == false) {
                visualizePodMovement(g);
            }
        }
    }
    
    private class VisualizationListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            gameField.repaint();
        }
    }
}
