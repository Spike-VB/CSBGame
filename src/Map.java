import java.util.*;

class Map {
    
    ArrayList<Integer> checkpointX = new ArrayList<Integer>();
    ArrayList<Integer> checkpointY = new ArrayList<Integer>();
    ArrayList<Integer> checkpointNewX = new ArrayList<Integer>();
    ArrayList<Integer> checkpointNewY = new ArrayList<Integer>();
    ArrayList<Integer> checkpointAngle = new ArrayList<Integer>();
    ArrayList<Integer> checkpointAbsAngleIn = new ArrayList<Integer>();
    ArrayList<Integer> checkpointAbsAngleOut = new ArrayList<Integer>();
    
    public void mapSetter (int chpX, int chpY) {
        
        checkpointX.add(chpX);
        checkpointY.add(chpY);
    }
    
    public void checkpointAngleCounter () {
        
        checkpointAngle.add(0, (int) Math.round(57.2958 * Math.acos((Math.pow(checkpointX.get(checkpointX.size() - 1) - checkpointX.get(0), 2) + 
        Math.pow(checkpointY.get(checkpointY.size() - 1) - checkpointY.get(0), 2) + Math.pow(checkpointX.get(0) - checkpointX.get(1), 2) + Math.pow(checkpointY.get(0) - checkpointY.get(1), 2) - 
        Math.pow(checkpointX.get(checkpointX.size() - 1) - checkpointX.get(1), 2) - Math.pow(checkpointY.get(checkpointY.size() - 1) - checkpointY.get(1), 2)) / (2 * Math.sqrt((
        Math.pow(checkpointX.get(checkpointX.size() - 1) - checkpointX.get(0), 2) + Math.pow(checkpointY.get(checkpointY.size() - 1) - checkpointY.get(0), 2)) * (
        Math.pow(checkpointX.get(0) - checkpointX.get(1), 2) + Math.pow(checkpointY.get(0) - checkpointY.get(1), 2)))))));
        
        for (int j = 1; j <= checkpointX.size() - 2; j++) {
                
            checkpointAngle.add(j, (int) Math.round(57.2958 * Math.acos((Math.pow(checkpointX.get(j - 1) - checkpointX.get(j), 2) + 
            Math.pow(checkpointY.get(j - 1) - checkpointY.get(j), 2) + Math.pow(checkpointX.get(j) - checkpointX.get(j + 1), 2) + Math.pow(checkpointY.get(j) - checkpointY.get(j + 1), 2) - 
            Math.pow(checkpointX.get(j - 1) - checkpointX.get(j + 1), 2) - Math.pow(checkpointY.get(j - 1) - checkpointY.get(j + 1), 2)) / (2 * Math.sqrt((
            Math.pow(checkpointX.get(j - 1) - checkpointX.get(j), 2) + Math.pow(checkpointY.get(j - 1) - checkpointY.get(j), 2)) * (
            Math.pow(checkpointX.get(j) - checkpointX.get(j + 1), 2) + Math.pow(checkpointY.get(j) - checkpointY.get(j + 1), 2)))))));
        }
        
        checkpointAngle.add(checkpointX.size() - 1, (int) Math.round(57.2958 * Math.acos((Math.pow(checkpointX.get(checkpointX.size() - 2) - checkpointX.get(checkpointX.size() - 1), 2) + 
        Math.pow(checkpointY.get(checkpointY.size() - 2) - checkpointY.get(checkpointY.size() - 1), 2) + Math.pow(checkpointX.get(checkpointX.size() - 1) - checkpointX.get(0), 2) + 
        Math.pow(checkpointY.get(checkpointY.size() - 1) - checkpointY.get(0), 2) - Math.pow(checkpointX.get(checkpointX.size() - 2) - checkpointX.get(0), 2) - 
        Math.pow(checkpointY.get(checkpointY.size() - 2) - checkpointY.get(0), 2)) / (2 * Math.sqrt((
        Math.pow(checkpointX.get(checkpointX.size() - 2) - checkpointX.get(checkpointX.size() - 1), 2) + Math.pow(checkpointY.get(checkpointY.size() - 2) - checkpointY.get(checkpointY.size() - 1), 2)) * (
        Math.pow(checkpointX.get(checkpointX.size() - 1) - checkpointX.get(0), 2) + Math.pow(checkpointY.get(checkpointY.size() - 1) - checkpointY.get(0), 2)))))));

        for (int j = 0; j <= checkpointX.size() - 2; j++) {
            
            if (checkpointX.get(j + 1) - checkpointX.get(j) != 0) {
            
                checkpointAbsAngleOut.add(j, (int) Math.round(Math.atan(Math.abs((double) (checkpointY.get(j + 1) - checkpointY.get(j))/(double) (checkpointX.get(j + 1) - checkpointX.get(j)))) * 57.2958));
            
                if (checkpointX.get(j + 1) - checkpointX.get(j) < 0 && checkpointY.get(j + 1) - checkpointY.get(j) <= 0) {
                    checkpointAbsAngleOut.add(j, 180 - checkpointAbsAngleOut.get(j));
                    checkpointAbsAngleOut.remove(j + 1);
                } else if (checkpointX.get(j + 1) - checkpointX.get(j) < 0 && checkpointY.get(j + 1) - checkpointY.get(j) > 0) {
                    checkpointAbsAngleOut.add(j, 180 + checkpointAbsAngleOut.get(j));
                    checkpointAbsAngleOut.remove(j + 1);
                } else if (checkpointX.get(j + 1) - checkpointX.get(j) > 0 && checkpointY.get(j + 1) - checkpointY.get(j) > 0) {
                    checkpointAbsAngleOut.add(j, 360 - checkpointAbsAngleOut.get(j));
                    checkpointAbsAngleOut.remove(j + 1);
                }
                
            } else if (checkpointX.get(j + 1) - checkpointX.get(j) == 0) {
                
                if (checkpointY.get(j + 1) - checkpointY.get(j) < 0) {
                    checkpointAbsAngleOut.add(j, 90);
                    checkpointAbsAngleOut.remove(j + 1);
                } else if (checkpointY.get(j + 1) - checkpointY.get(j) > 0) {
                    checkpointAbsAngleOut.add(j, 270);
                    checkpointAbsAngleOut.remove(j + 1);
                }
            }
        }
        
        if (checkpointX.get(0) - checkpointX.get(checkpointX.size() - 1) != 0) {
        
            checkpointAbsAngleOut.add(checkpointX.size() - 1, (int) Math.round(Math.abs(Math.atan((double) (checkpointY.get(0) - checkpointY.get(checkpointX.size() - 1))/(double) (checkpointX.get(0) - checkpointX.get(checkpointX.size() - 1)))) * 57.2958));
        
            if (checkpointX.get(0) - checkpointX.get(checkpointX.size() - 1) < 0 && checkpointY.get(0) - checkpointY.get(checkpointX.size() - 1) <= 0) {
                checkpointAbsAngleOut.add(checkpointX.size() - 1, 180 - checkpointAbsAngleOut.get(checkpointX.size() - 1));
                checkpointAbsAngleOut.remove(checkpointX.size());
            } else if (checkpointX.get(0) - checkpointX.get(checkpointX.size() - 1) < 0 && checkpointY.get(0) - checkpointY.get(checkpointX.size() - 1) > 0) {
                checkpointAbsAngleOut.add(checkpointX.size() - 1, 180 + checkpointAbsAngleOut.get(checkpointX.size() - 1));
                checkpointAbsAngleOut.remove(checkpointX.size());
            } else if (checkpointX.get(0) - checkpointX.get(checkpointX.size() - 1) > 0 && checkpointY.get(0) - checkpointY.get(checkpointX.size() - 1) > 0) {
                checkpointAbsAngleOut.add(checkpointX.size() - 1, 360 - checkpointAbsAngleOut.get(checkpointX.size() - 1));
                checkpointAbsAngleOut.remove(checkpointX.size());
            } 
            
        } else if (checkpointX.get(0) - checkpointX.get(checkpointX.size() - 1) == 0) {
            
            if (checkpointY.get(0) - checkpointY.get(checkpointX.size() - 1) < 0) {
                checkpointAbsAngleOut.add(checkpointX.size() - 1, 90);
                checkpointAbsAngleOut.remove(checkpointX.size());
            } else if (checkpointY.get(checkpointX.size() - 1) > 0) {
                checkpointAbsAngleOut.add(checkpointX.size() - 1, 270);
                checkpointAbsAngleOut.remove(checkpointX.size());
            }
        }
        
        for (int j = 0; j <= checkpointX.size() - 1; j++) {
            
            if (checkpointAbsAngleOut.get(j) == 360) {
                checkpointAbsAngleOut.add(j, 0);
                checkpointAbsAngleOut.remove(j + 1);
            }
        }
        
        if (checkpointAbsAngleOut.get(checkpointAbsAngleOut.size() - 1) <= 180) {
            checkpointAbsAngleIn.add(0, checkpointAbsAngleOut.get(checkpointAbsAngleOut.size() - 1) + 180);
        } else {
             checkpointAbsAngleIn.add(0, checkpointAbsAngleOut.get(checkpointAbsAngleOut.size() - 1) - 180);
        }
        
        for (int j = 0; j <= checkpointX.size() - 2; j++) {
            
            if (checkpointAbsAngleOut.get(j) <= 180) {
                checkpointAbsAngleIn.add(j + 1, checkpointAbsAngleOut.get(j) + 180);
            } else {
                checkpointAbsAngleIn.add(j + 1, checkpointAbsAngleOut.get(j) - 180);
            }
        }
    }
    
    public void newCheckpointCounter () {
        
        for (int j = 0; j <= checkpointX.size() - 1; j++) {
            
            if ((checkpointAbsAngleIn.get(j) >= checkpointAbsAngleOut.get(j) && Math.abs(checkpointAbsAngleIn.get(j) - checkpointAbsAngleOut.get(j)) >= 180) || 
            (checkpointAbsAngleIn.get(j) < checkpointAbsAngleOut.get(j) && Math.abs(checkpointAbsAngleIn.get(j) - checkpointAbsAngleOut.get(j)) < 180)) {
                checkpointNewX.add(j, checkpointX.get(j) + (int) Math.round(300 * Math.cos(((double) checkpointAbsAngleIn.get(j) + Math.round(checkpointAngle.get(j) / 2)) / 57.2958)));
                checkpointNewY.add(j, checkpointY.get(j) - (int) Math.round(300 * Math.sin(((double) checkpointAbsAngleIn.get(j) + Math.round(checkpointAngle.get(j) / 2)) / 57.2958)));
            } else { 
                checkpointNewX.add(j, checkpointX.get(j) + (int) Math.round(300 * Math.cos(((double) checkpointAbsAngleOut.get(j) + Math.round(checkpointAngle.get(j) / 2)) / 57.2958)));
                checkpointNewY.add(j, checkpointY.get(j) - (int) Math.round(300 * Math.sin(((double) checkpointAbsAngleOut.get(j) + Math.round(checkpointAngle.get(j) /2)) / 57.2958)));
            }
        }
    }
    
    public void mapPrinter () {
        
        for(int j = 0; j <= checkpointX.size() - 1; j++) {
            //System.err.println("checkpointX N" + j + " = " + checkpointX.get(j));
            //System.err.println("checkpointY N" + j + " = " + checkpointY.get(j));
            //System.err.println("checkpointAngle N" + j + " = " + checkpointAngle.get(j));
            //System.err.println("checkpointAbsAngleIn N" + j + " = " + checkpointAbsAngleIn.get(j));
            //System.err.println("checkpointAbsAngleOut N" + j + " = " + checkpointAbsAngleOut.get(j));
            //System.err.println("difference N" + j + " = " + (int) Math.abs(checkpointAbsAngleIn.get(j) - checkpointAbsAngleOut.get(j)));
            //System.err.println("checkpointNewX N" + j + " = " + checkpointNewX.get(j));
            //System.err.println("checkpointNewY N" + j + " = " + checkpointNewY.get(j));
        }
    }
}