import java.util.*;

public abstract class Player {
    protected int laps;
    protected int checkpointCount;
    protected int[] checkpointX;
    protected int[] checkpointY;

    protected int[] x = new int[2];
    protected int[] y = new int[2];
    protected int[] vx = new int[2];
    protected int[] vy = new int[2];
    protected int[] angle = new int[2];
    protected int[] nextCheckpointId = new int[2];
    protected int[] lap = new int[2];
    
    protected int[] x2 = new int[2];
    protected int[] y2 = new int[2];
    protected int[] vx2 = new int[2];
    protected int[] vy2 = new int[2];
    protected int[] angle2 = new int[2];
    protected int[] nextCheckpointId2 = new int[2];
    
    
    private ArrayList<Integer> pod1XLog = new ArrayList<>();
    private ArrayList<Integer> pod1YLog = new ArrayList<>();
    private ArrayList<Integer> pod1AngleLog = new ArrayList<>();
    
    private ArrayList<Integer> pod2XLog = new ArrayList<>();
    private ArrayList<Integer> pod2YLog = new ArrayList<>();
    private ArrayList<Integer> pod2AngleLog = new ArrayList<>();
    
    public Player() {
        x[0] = 0;
        x[1] = 0;
        y[0] = 0;
        y[1] = 0;
        vx[0] = 0;
        vx[1] = 0;
        vy[0] = 0;
        vy[1] = 0;
        angle[0] = 0;
        angle[1] = 0;
        nextCheckpointId[0] = 1;
        nextCheckpointId[1] = 1;
        lap[0] = 1;
        lap[1] = 1;
        
        x2[0] = 0;
        x2[1] = 0;
        y2[0] = 0;
        y2[1] = 0;
        vx2[0] = 0;
        vx2[1] = 0;
        vy2[0] = 0;
        vy2[1] = 0;
        angle2[0] = 0;
        angle2[1] = 0;
        nextCheckpointId2[0] = 1;
        nextCheckpointId2[1] = 1;
    }
    
    public abstract void initializePlayer();
    
    public abstract int[] getOrder(int podNum);
    
    public void setInitialConditions(int la, int[] chX, int[] chY) {
        laps = la;
        checkpointX = chX;
        checkpointY = chY;
        
        x[0] = checkpointX[0];
        x[1] = checkpointX[0];
        y[0] = checkpointY[0];
        y[1] = checkpointY[0];
        x2[0] = checkpointX[0];
        x2[1] = checkpointX[0];
        y2[0] = checkpointY[0];
        y2[1] = checkpointY[0];
        
        pod1XLog.add(x[0]);
        pod1YLog.add(y[0]);
        pod1AngleLog.add(angle[0]);
        
        pod2XLog.add(x[1]);
        pod2YLog.add(y[1]);
        pod2AngleLog.add(angle[1]);
        
        checkpointCount = checkpointX.length;
    }
    
    public int[] getVelocity(int podNum) {
        int[] ret = new int[2];
        ret[0] = vx[podNum - 1];
        ret[1] = vy[podNum - 1];
        return ret;
    }
    
    public int getAngle(int podNum) {
        int ret = angle[podNum - 1];
        return ret;
    }
    
    public int[] getPosition(int podNum) {
        int[] ret = new int[2];
        ret[0] = x[podNum - 1];
        ret[1] = y[podNum - 1];
        return ret;
    }
    
    public int getNextCheckpointId(int podNum) {
        int ret = nextCheckpointId[podNum - 1];
        return ret;
    }
    
    public int getLap(int podNum) {
        int ret = lap[podNum - 1];
        return ret;
    }
    
    public int getPodXLog(int podNum, int i) {
        int ret = 0;
        if(podNum == 1) {
            ret = pod1XLog.get(i);
        } else if(podNum == 2) {
            ret = pod2XLog.get(i);
        }
        return ret;
    }
    
    public int getPodYLog(int podNum, int i) {
        int ret = 0;
        if(podNum == 1) {
            ret = pod1YLog.get(i);
        } else if(podNum == 2) {
            ret = pod2YLog.get(i);
        }
        return ret;
    }
    
    public int getPodAngleLog(int podNum, int i) {
        int ret = 0;
        if(podNum == 1) {
            ret = pod1AngleLog.get(i);
        } else if(podNum == 2) {
            ret = pod2AngleLog.get(i);
        }
        return ret;
    }
    
    public void revalidatePodState(int podNum, int xPar, int yPar, int vxPar, int vyPar, int anglePar) {
        x[podNum - 1] = xPar;
        y[podNum - 1] = yPar;
        vx[podNum - 1] = vxPar;
        vy[podNum - 1] = vyPar;
        angle[podNum - 1] = anglePar;
        
        if(podNum == 1) {
            pod1XLog.add(xPar);
            pod1YLog.add(yPar);
            pod1AngleLog.add(anglePar);
        } else if(podNum == 2) {
            pod2XLog.add(xPar);
            pod2YLog.add(yPar);
            pod2AngleLog.add(anglePar);
        }
    }
    
    public void increaseNextCheckpointId(int podNum) {
        nextCheckpointId[podNum - 1]++;
        if(nextCheckpointId[podNum - 1] == checkpointX.length) {
            nextCheckpointId[podNum - 1] = 0;
        }
    }
    
    public void increaseLap(int podNum) {
        lap[podNum - 1]++;
    }
}