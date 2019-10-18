import java.util.*;
import static java.lang.Math.*;

public class GameController {
    private final Visualization visualization;
    private ArrayList<Player> playerList;
    
    private final int laps = 3;
    private  int[] checkpointX;
    private  int[] checkpointY;
    
    private int turn = 0;
    
    public GameController(Visualization vis, ArrayList<Player> plList) {
        visualization = vis;
        playerList = plList;
    }
    
    public void refresh(ArrayList<Player> plList) {
        playerList = plList;
        turn = 0;
    }
    
    public void gameStart(int[] chpX, int[] chpY) {
        checkpointX = chpX;
        checkpointY = chpY;
        this.gameSetup();
        this.gameRun();
    }
    
    private void gameSetup() {
        for(Player player : playerList) {
            player.setInitialConditions(laps, checkpointX, checkpointY);
            player.initializePlayer();
        }
    }
    
    private void gameRun() {
        boolean gameRun = true;
        while(gameRun) {
            turn++;
            for(Player player : playerList) {
                for(int i = 1; i <= 2; i++) {
                    int[] order = player.getOrder(i);
                    int xMov = order[0];
                    int yMov = order[1];
                    int thrust = order[2];
                    
                    int[] velocity = player.getVelocity(i);
                    int vx = velocity[0];
                    int vy = velocity[1];
                    
                    int angle1 = player.getAngle(i);
                    
                    int[] position = player.getPosition(i);
                    int x1 = position[0];
                    int y1 = position[1];
                    
                    int[] podState = MovementPhisics.getPodState(x1, y1, vx, vy, angle1, xMov, yMov, thrust);
                    int x2 = podState[0];
                    int y2 = podState[1];
                    vx = podState[2];
                    vy = podState[3];
                    int angle2 = podState[4];
                    
                    int nextCheckpointId1 = player.getNextCheckpointId(i);
                    
                    int nextCheckpointDist = (int) round(sqrt(pow(checkpointX[nextCheckpointId1] - x2, 2) + 
                            pow(checkpointY[nextCheckpointId1] - y2, 2)));
                    
                    if(nextCheckpointDist <= CSBGame.CHECKPOINT_DIAM/2) {
                        player.increaseNextCheckpointId(i);
                    }
                    
                    int nextCheckpointId2 = player.getNextCheckpointId(i);
                    
                    if(nextCheckpointId1 == 0 && nextCheckpointId2 == 1) {
                        player.increaseLap(i);
                    }
                    
                    player.revalidatePodState(i, x2, y2, vx, vy, angle2);
                    
                    if(player.getLap(i) > laps) {
                        gameRun = false;
                    }
                }
            }
        }
        visualization.visualize(turn);
    }
}