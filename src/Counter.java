abstract class Counter {

    Map map;
    int i;
    int podNum;
    int x;
    int [] xArray = {0, 0};
    int y;
    int [] yArray = {0, 0};
    int angle;
    int nextCheckpointId;
    int nextCheckpointX;
    int nextCheckpointY;
    int nextCheckpointNewX;
    int nextCheckpointNewY;
    int nextCheckpointDist;
    int nextCheckpointAngle;
    int nextCheckpointAbsAngle;
    int [] nextCheckpointDistArray = {0, 0};
    int [] nextCheckpointXArray = {0, 0};
    int velocity;
    int velocityAbsAngle;
    int lapNum = 0;
    int pos;
    
    public final void startCounter (Map mapPar, int iPar, int xPar, int yPar, int nextCheckpointIdPar, int anglePar) {
        
        map = mapPar;
        i = iPar;
        x = xPar;
        y = yPar;
        nextCheckpointId = nextCheckpointIdPar;
        angle = anglePar;
        nextCheckpointX = map.checkpointX.get(nextCheckpointId);
        nextCheckpointY = map.checkpointY.get(nextCheckpointId);
        nextCheckpointNewX = map.checkpointNewX.get(nextCheckpointId);
        nextCheckpointNewY = map.checkpointNewY.get(nextCheckpointId);
        
        nextCheckpointDistCounter();
        statementLog();
        velocityCounter();
        velocityAbsAngleCounter();
        nextCheckpointAbsAngleCounter();
        nextCheckpointAngleCounter();
        lapNumCounter();
        positionCounter();
    }
    
    private void nextCheckpointDistCounter () {
        
        nextCheckpointDist = (int) Math.round(Math.sqrt(Math.pow(x - nextCheckpointX, 2) + Math.pow(y - nextCheckpointY, 2)));
    }
    
    private void statementLog () {
        
        xArray [0] = xArray [1];
        yArray [0] = yArray [1];
        nextCheckpointDistArray [0] = nextCheckpointDistArray [1];
        nextCheckpointXArray [0] = nextCheckpointXArray [1];
        xArray [1] = x;
        yArray [1] = y;
        nextCheckpointDistArray [1] = nextCheckpointDist;
        nextCheckpointXArray [1] = nextCheckpointX;
    }
    
    private void velocityCounter () {
        
        velocity = (int) Math.round(Math.sqrt(Math.pow(xArray [1] - xArray [0], 2) + Math.pow(yArray [1] - yArray [0], 2)));
    }
    
    private void velocityAbsAngleCounter () {
        
        velocityAbsAngle = (int) Math.round(Math.acos(((double) xArray [1] - (double) xArray [0])/(double) velocity) * 57.2958);
        
        if (yArray [1] - yArray [0] > 0) {
            velocityAbsAngle = 360 - velocityAbsAngle;
        }
    }
    
    private void nextCheckpointAbsAngleCounter () {
        
        nextCheckpointAbsAngle = (int) Math.round(Math.acos(((double) nextCheckpointX - (double) xArray [1])/(double) nextCheckpointDist) * 57.2958);
        
        if (nextCheckpointY - yArray [1] > 0) {
            nextCheckpointAbsAngle = 360 - nextCheckpointAbsAngle;
        }
    }
    
    private void nextCheckpointAngleCounter () {
        
        nextCheckpointAngle = (int) Math.round(Math.abs(360 - angle - nextCheckpointAbsAngle));
        
        if (nextCheckpointAngle > 180) {
            nextCheckpointAngle = 360 - nextCheckpointAngle;
        }
    }

    private void lapNumCounter () {
        
        if (nextCheckpointId == 1 && nextCheckpointXArray [1] != nextCheckpointXArray [0]) {
            lapNum ++;
        }
    }
    
    private void positionCounter () {
        
        if (nextCheckpointId != 0) {
            pos = lapNum * (int) Math.pow(10, 6) + (nextCheckpointId - 1) * (int) Math.pow(10, 5) + (99000 - nextCheckpointDist);
        } else {
            pos = lapNum * (int) Math.pow(10, 6) + (map.checkpointX.size() - 1) * (int) Math.pow(10, 5) + (99000 - nextCheckpointDist);
        }
        //System.err.println(podNum + "pod pos = " + pos);
    }
}