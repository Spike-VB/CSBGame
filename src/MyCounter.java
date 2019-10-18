class MyCounter extends Counter implements OppCountModule {
    
    public int [] oppX = {0, 0};
    public int [] oppY = {0, 0};
    public int [] oppDist = {0, 0};
    public int [] oppDistLog1 = {0, 0};
    public int [] oppDistLog2 = {0, 0};
    public int [] oppConvergenceVelocity = {0, 0};
    public int [] oppAbsAngle = {0, 0};
    public int [] oppAngle = {0, 0};
    
    public void startOppCounter (int j, int oppXPar, int oppYPar) {
        
        oppStatementLog (j, oppXPar, oppYPar);
        oppDistCounter(j);
        oppDistLog(j);
        oppConvergenceVelocityCounter(j);
        oppAbsAngleCounter();
        oppAngleCounter();
    }
    
    public void oppStatementLog (int j, int oppXPar, int oppYPar) {
        
        if (j == 0) {
            oppX[0] = oppXPar;
            oppY[0] = oppYPar;
        } else {
            oppX[1] = oppXPar;
            oppY[1] = oppYPar;
        }    
    }
        
    public void oppDistCounter (int j) {
        
        if (j == 0) {
            oppDist[0] = (int) Math.round(Math.sqrt(Math.pow(oppX[0] - xArray [1], 2) + Math.pow(oppY[0] - yArray [1], 2)));
        } else {
            oppDist[1] = (int) Math.round(Math.sqrt(Math.pow(oppX[1] - xArray [1], 2) + Math.pow(oppY[1] - yArray [1], 2)));
        }
    }
    
    public void oppDistLog (int j) {
        
        if (j == 0) {
            oppDistLog1[0] = oppDistLog1[1];
            oppDistLog1[1] = oppDist[0];
        } else {
            oppDistLog2[0] = oppDistLog2[1];
            oppDistLog2[1] = oppDist[1];
        }
    }
    
    public void oppConvergenceVelocityCounter (int j) {
        
        if (j == 0) {
            oppConvergenceVelocity[0] = oppDistLog1[0] - oppDistLog1[1];
        } else {
            oppConvergenceVelocity[1] = oppDistLog2[0] - oppDistLog2[1];
        }
    }
    
    public void oppAbsAngleCounter () {
        
        oppAbsAngle[0] = (int) Math.round(Math.acos(((double) oppX[0] - (double) xArray [1])/oppDist[0]) * 57.2958);
        
        if (oppY[0] - yArray [1] > 0) {
            oppAbsAngle[0] = 360 - oppAbsAngle[0];
        }
        
        oppAbsAngle[1] = (int) Math.round(Math.acos(((double) oppX[1] - (double) xArray [1])/oppDist[1]) * 57.2958);
        
        if (oppY[1] - yArray [1] > 0) {
            oppAbsAngle[1] = 360 - oppAbsAngle[1];
        }
    }
    
    public void oppAngleCounter () {
        
        for(int j=0; j<2; j++) {
            oppAngle[j] = (int) Math.abs(360 - angle - oppAbsAngle[j]);
            if (oppAngle[j] >180) {
                oppAngle[j] = 360 - oppAngle[j];
            }
        }
    }
    
}