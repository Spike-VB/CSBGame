//Алгоритм многоуровнего случайного поиска

import java.util.*;
import static java.lang.Math.*;

public class RandomPlayerVer3 extends Player {
    
    public final int D_XY = 500;
    public final int GUESS_NUM = 50;

    int podNum;
    int bestGuess;
    
    @Override
    public void initializePlayer() {
    }
    
    @Override
    public int[] getOrder(int pN){
        podNum = pN;
        
        int xMov = checkpointX[nextCheckpointId[podNum - 1]];
        int yMov = checkpointY[nextCheckpointId[podNum - 1]];
        bestGuess = simulate(xMov, yMov);
        int depth  = 0;
        
        for(int i = 0; i < GUESS_NUM; i++) {
            int[] guess = makeGuess(xMov, yMov, D_XY);
            int tSim = simulate(guess[0], guess[1]);
            if(tSim < bestGuess) {
                depth++;
                bestGuess = tSim;
                xMov = guess[0];
                yMov = guess[1];
            }
            if(depth == 4) {
                break;
            }
        }

        int[] ret = {xMov, yMov, (int) 100};
        return ret;
    }
    
    private int[] makeGuess(int x, int y, int delta) {
        int dx = (int) round(random()*delta);
        int dy = (int) round(random()*delta);
        if(random() > (double) 0.5) {
            dx = -1*dx;
        }
        if(random() > (double) 0.5) {
            dy = -1*dy;
        }
        int xMov = x + dx;
        int yMov = y + dy;
        int[] ret = {xMov, yMov};
        return ret;
    }
    
    private int simulate(int xMov, int yMov){
        System.out.println("Start sim");
        int xSim = x[podNum - 1];
        int ySim = y[podNum - 1];
        int vxSim = vx[podNum - 1];
        int vySim = vy[podNum - 1];
        int angleSim = angle[podNum - 1];
        int nextCheckpointIdSim = nextCheckpointId[podNum - 1];
        int t = 0;
        
        int countPart = 1;
        
        boolean simulate = true;
        int[] podState;
        int nextCheckpointDist;
        
        while(simulate) {
            t++;
            if (countPart == 1) {
                System.out.println("Part 1");
                podState = MovementPhisics.getPodState(xSim, ySim, vxSim, vySim, angleSim, xMov, yMov, (int) 100);
                xSim = podState[0];
                ySim = podState[1];
                vxSim = podState[2];
                vySim = podState[3];
                angleSim = podState[4];

                nextCheckpointDist = (int) round(sqrt(pow(checkpointX[nextCheckpointIdSim] - xSim, 2) + 
                            pow(checkpointY[nextCheckpointIdSim] - ySim, 2)));
                
                if(nextCheckpointDist <= CSBGame.CHECKPOINT_DIAM/2) {
                    nextCheckpointIdSim++;
                    countPart = 2;
                    nextCheckpointDist = CSBGame.CHECKPOINT_DIAM;
                    if(nextCheckpointIdSim == checkpointX.length) {
                        nextCheckpointIdSim = 0;
                    }
                }
            } else {
                System.out.println("Part 2");
                podState = MovementPhisics.getPodState(xSim, ySim, vxSim, vySim, angleSim, 
                        checkpointX[nextCheckpointIdSim], checkpointY[nextCheckpointIdSim], (int) 100);
                
                xSim = podState[0];
                ySim = podState[1];
                vxSim = podState[2];
                vySim = podState[3];
                angleSim = podState[4];
                
                nextCheckpointDist = (int) round(sqrt(pow(checkpointX[nextCheckpointIdSim] - xSim, 2) + 
                            pow(checkpointY[nextCheckpointIdSim] - ySim, 2)));
            }
            if ((countPart == 2 && nextCheckpointDist <= CSBGame.CHECKPOINT_DIAM / 2) || t == bestGuess || t == 100) {
                simulate = false;
            }
        }
        System.out.println("Stop sim");
        return t;
    }
}