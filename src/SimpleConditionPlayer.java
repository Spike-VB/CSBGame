
public class SimpleConditionPlayer extends Player  {
    
    @Override
    public void initializePlayer() {
        
    }
    
    @Override
    public int[] getOrder(int podNum) {
        int[] ret1 = new int[3];
        int[] ret2 = new int[3];
        
        ret1[0] = checkpointX[nextCheckpointId[0]];
        ret1[1] = checkpointY[nextCheckpointId[0]];
        ret1[2] = (int) 100;
        
        ret2[0] = checkpointX[nextCheckpointId[1]];
        ret2[1] = checkpointY[nextCheckpointId[1]];
        ret2[2] = (int) 100;
        
        int[] ret = ret1;
        if(podNum == 2) {
            ret = ret2;
        }
        return ret;
    }
}
