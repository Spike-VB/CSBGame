class Pod {
    
    int moveToX;
    int moveToY;
    int podMethod;
    
    public int nearCheckpointBraking (MyCounter counter, int thrust, int breakingStartDist, int breakingStartVelocity, int breakingThrust) {
        
        if (counter.nextCheckpointDist < breakingStartDist && counter.velocity > breakingStartVelocity) {
            thrust = breakingThrust;
            podMethod = 1;
        }
        return thrust;
    }

    public int nextCheckpointTurnThrust (MyCounter counter, int thrust, int nextCheckpointTurnStartDist, int nextCheckpointTurnStopAngle, double logCoeff) {
        
        if (counter.nextCheckpointDist > nextCheckpointTurnStartDist && Math.abs(counter.nextCheckpointAngle) > nextCheckpointTurnStopAngle) {
            thrust = (int) Math.round((100 - (int) Math.round((Math.abs(counter.nextCheckpointAngle) - nextCheckpointTurnStopAngle) / ((double) (180 - nextCheckpointTurnStopAngle) / 100))) *
            (double) Math.pow(logCoeff, (Math.abs(counter.nextCheckpointAngle) - nextCheckpointTurnStopAngle)/10));
            podMethod = 3;
        }
        return thrust;
    }
    
    public boolean boost (MyCounter counter, Map map, int lapNum, int boostNextCheckpointDist, int boostNextCheckpointAngle) { // not used
        
        boolean boostIsDone = false;
        
        if (counter.lapNum == lapNum && counter.nextCheckpointId == 0 && 
        counter.nextCheckpointDist > boostNextCheckpointDist && Math.abs(counter.nextCheckpointAngle) < boostNextCheckpointAngle) {
            System.out.println(counter.nextCheckpointX + " " + counter.nextCheckpointY + " " + "BOOST");
            boostIsDone = true;
        }
        return boostIsDone;
    }
    
    public void block (MyCounter counter, int x, int y) { //not used
        
        System.out.println(x + " " + y + " " + 100);
    }

    public int[] movDirection (MyCounter counter, Map map, int thrust, int correctionStartNextCheckpointDist, int correctionStartDelVelocityAbsAngle) {
        
        moveToX = counter.nextCheckpointNewX;
        moveToY = counter.nextCheckpointNewY;
        int delX = 0;
        int delY = 0;
        
        int delVelocityAbsAngle = counter.velocityAbsAngle - counter.nextCheckpointAbsAngle;
        if (delVelocityAbsAngle > 180) {
            delVelocityAbsAngle = 360 - delVelocityAbsAngle;
        } else if (delVelocityAbsAngle < -180) {
            delVelocityAbsAngle = -360 - delVelocityAbsAngle;
        }

        if (counter.nextCheckpointDist >= 2000) { // Correction of pod movement direction
        
            if (counter.i > 2 && Math.abs(delVelocityAbsAngle) > correctionStartDelVelocityAbsAngle && 
            Math.abs(delVelocityAbsAngle) < 90 && counter.nextCheckpointDist < correctionStartNextCheckpointDist) {
            
                delX = (int) Math.round(counter.velocity * Math.sin(((double) counter.velocityAbsAngle)/57.2958)) * delVelocityAbsAngle/Math.abs(delVelocityAbsAngle);
                delY = (int) Math.round(counter.velocity * Math.cos(((double) counter.velocityAbsAngle)/57.2958)) * delVelocityAbsAngle/Math.abs(delVelocityAbsAngle);
            
                if ((counter.velocityAbsAngle < 90 && counter.nextCheckpointAbsAngle > 270) || (counter.velocityAbsAngle > 270 && counter.nextCheckpointAbsAngle < 90)) {
                    delX = -1 * delX;
                    delY = -1 * delY;
                }
            
                moveToX = moveToX + 3 * delX;
                moveToY = moveToY + 3 * delY;
                podMethod = 4;
            }
           
        } else if (counter.nextCheckpointDist < 2000 && counter.nextCheckpointDist >= 550 && counter.velocity > 100 && Math.abs(delVelocityAbsAngle) < (int) Math.round(Math.atan((double) 500 / counter.nextCheckpointDist) * 57.2958)) { // <= 400 ===> < 600 Previous pod's nose turn to next checkpoint
            
            if (counter.nextCheckpointId < map.checkpointX.size() - 1) {
                moveToX = map.checkpointNewX.get(counter.nextCheckpointId + 1);
                moveToY  = map.checkpointNewY.get(counter.nextCheckpointId + 1);
            } else {
                moveToX = map.checkpointNewX.get(0);
                moveToY = map.checkpointNewY.get(0);
            }
            podMethod = 5;
            
        } else if (counter.nextCheckpointDist < 550) {
            
            if (counter.nextCheckpointId < map.checkpointX.size() - 1) {
                
                moveToX = map.checkpointNewX.get(counter.nextCheckpointId + 1);
                moveToY  = map.checkpointNewY.get(counter.nextCheckpointId + 1);
                
            } else {
                
                moveToX = map.checkpointNewX.get(0);
                moveToY = map.checkpointNewY.get(0);
            }
            podMethod = 5;
        }
        
        int[] ret = new int[3];
        
        if (counter.i > 10 && (counter.oppDist[0] <= 850 || counter.oppDist[1] <= 850)) {
            //System.out.println(moveToX + " " + moveToY + " " + thrust);
            ret[0] = moveToX;
            ret[1] = moveToY;
            ret[2] = thrust;
        } else {
        //System.out.println(moveToX + " " + moveToY + " " + thrust);
        ret[0] = moveToX;
        ret[1] = moveToY;
        ret[2] = thrust;
        }
        return ret;
    }
    
    public boolean shield (MyCounter counter, int shieldDist, int shieldVelocity) { //not used
        
        if (counter.i > 10 && ((counter.oppDist[0] <= shieldDist && counter.oppConvergenceVelocity[0] > shieldVelocity) || (counter.oppDist[1] <= shieldDist && counter.oppConvergenceVelocity[1] > shieldVelocity))) { // 400 => 300
            System.out.println(moveToX + " " + moveToY + " " + "SHIELD");
            return true;
        } else {
            return false;
        }     
    }
    
    public void podMethodPrinter (MyCounter counter) {
        
        if (podMethod == 0) {
            //System.err.println(counter.podNum + " podMethod - Normal velocity");
        } else if (podMethod == 1) {
            //System.err.println(counter.podNum + " podMethod - Near checkpoint braking");
        } else if (podMethod == 2) {
            //System.err.println(counter.podNum + " podMethod - Near checkpoint max thrust");
        } else if (podMethod == 3) {
            //System.err.println(counter.podNum + " podMethod - Next checkpoint turnaround");
        } else if (podMethod ==  4) {
            //System.err.println(counter.podNum + " podMethod - movDirection correction");
        } else if (podMethod == 5) {
            //System.err.println(counter.podNum + " podMethod - Advance next checkpoint turn");
        }
        podMethod = 0;
    }
}