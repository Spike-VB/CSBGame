//Алгоритм одноуровнего случайного поиска с случайным выбором тяги (лучший)

import java.util.*;
import static java.lang.Math.*;

public class RandomPlayerVer2 extends Player {
    
    int xGuess;
    int yGuess;
    int vxGuess;
    int vyGuess;
    int angleGuess;
    int nextCheckpointIdGuess;
    int xMov;
    int yMov;
    int thrust;
    int t;
    int podNum;
    ArrayList<int[]> guessList;
    
    @Override
    public void initializePlayer() {
    }
    
    @Override
    public int[] getOrder(int pN){
        podNum = pN;
        guessList = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            makeGuess();
            simulate();
            int[] g = {xMov,yMov,thrust,t};
            guessList.add(g);
        }
        
        int bestGuess = guessList.get(0)[3];
        int bestGuessIndex = 0;
        
        for(int[] guess: guessList) {
            if(guess[3] < bestGuess) {
                bestGuess = guess[3];
                bestGuessIndex = guessList.indexOf(guess);
            }
        }
        int[] ret = {guessList.get(bestGuessIndex)[0], guessList.get(bestGuessIndex)[1], guessList.get(bestGuessIndex)[2]};
        return ret;
    }
    
    private void makeGuess() {
        int dx = (int) round(random()*1000);
        int dy = (int) round(random()*1000);
        if(random() > (double) 0.5) {
            dx = -1*dx;
        }
        if(random() > (double) 0.5) {
            dy = -1*dy;
        }
        xMov = checkpointX[nextCheckpointId[podNum - 1]] + dx;
        yMov = checkpointY[nextCheckpointId[podNum - 1]] + dy;
        
        int nextCheckpointDist = (int) round(sqrt(pow(checkpointX[nextCheckpointIdGuess] - xGuess, 2) + 
                            pow(checkpointY[nextCheckpointIdGuess] - yGuess, 2)));
        if(nextCheckpointDist < 1000) {
            thrust = (int) round(random()*100);
            if(thrust < 100) {
                thrust = 100;
            }
        } else {
            thrust = 100;
        }
    }
    
    private void simulate(){
        xGuess = x[podNum - 1];
        yGuess = y[podNum - 1];
        vxGuess = vx[podNum - 1];
        vyGuess = vy[podNum - 1];
        angleGuess = angle[podNum - 1];
        nextCheckpointIdGuess = nextCheckpointId[podNum - 1];
        t = 0;
        
        int countPart = 1;
        
        boolean simulate = true;
        int[] podState;
        int nextCheckpointDist;
        
        while(simulate) {
            t++;
            if (countPart == 1) {
                podState = MovementPhisics.getPodState(xGuess, yGuess, vxGuess, vyGuess, angleGuess, xMov, yMov, thrust);
                xGuess = podState[0];
                yGuess = podState[1];
                vxGuess = podState[2];
                vyGuess = podState[3];
                angleGuess = podState[4];

                nextCheckpointDist = (int) round(sqrt(pow(checkpointX[nextCheckpointIdGuess] - xGuess, 2) + 
                            pow(checkpointY[nextCheckpointIdGuess] - yGuess, 2)));
                
                if(nextCheckpointDist <= CSBGame.CHECKPOINT_DIAM/2) {
                    nextCheckpointIdGuess++;
                    countPart = 2;
                    if(nextCheckpointIdGuess == checkpointX.length) {
                        nextCheckpointIdGuess = 0;
                    }
                }
            } else {
                podState = MovementPhisics.getPodState(xGuess, yGuess, vxGuess, vyGuess, angleGuess, 
                        checkpointX[nextCheckpointIdGuess], checkpointY[nextCheckpointIdGuess], (int) 100);
                
                xGuess = podState[0];
                yGuess = podState[1];
                vxGuess = podState[2];
                vyGuess = podState[3];
                angleGuess = podState[4];
                
                nextCheckpointDist = (int) round(sqrt(pow(checkpointX[nextCheckpointIdGuess] - xGuess, 2) + 
                            pow(checkpointY[nextCheckpointIdGuess] - yGuess, 2)));
                
                if(nextCheckpointDist <= CSBGame.CHECKPOINT_DIAM/2 || t == 100) {
                    simulate = false;
                }
            }
        }
    }
}