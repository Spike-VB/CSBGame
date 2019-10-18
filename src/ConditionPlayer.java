
public class ConditionPlayer extends Player {
    
    private int i;
    private boolean thereIsBoost1;
    private boolean thereIsBoost2;
    private MyCounter myCounter1;
    private MyCounter myCounter2;
    private Pod pod1;
    private Pod pod2;
    private Map map;
    private final OppCounter[] oppCounter = new OppCounter[2];
    
    @Override
    public void initializePlayer() {
        i = 0;
        thereIsBoost1 = true;
        thereIsBoost2 = true;
        
        myCounter1 = new MyCounter();
        myCounter2 = new MyCounter();
        pod1 = new Pod();
        pod2 = new Pod();
        map = new Map();
        oppCounter[0] = new OppCounter();
        oppCounter[1] = new OppCounter();
        
        for (int j = 0; j < checkpointCount; j++) {
            map.mapSetter(checkpointX[j], checkpointY[j]);
        }
        
        map.checkpointAngleCounter();
        map.newCheckpointCounter();
        map.mapPrinter();
    }
    
    @Override
    public int[] getOrder(int podNum) {
        int[] ret1 = new int[3];
        int[] ret2 = new int[3];
        
        myCounter1.podNum = 11;
        myCounter1.startCounter(map, i, x[0], y[0], nextCheckpointId[0], angle[0]);

        myCounter2.podNum = 12;
        myCounter2.startCounter(map, i, x[1], y[1], nextCheckpointId[1], angle[1]);
            
        oppCounter[0].podNum = 21;
        oppCounter[0].startCounter(map, i, x2[0], y2[0], nextCheckpointId2[0], angle2[0]);

        oppCounter[1].podNum = 22;
        oppCounter[1].startCounter(map, i, x2[1], y2[1], nextCheckpointId2[1], angle2[1]);
            
        for (int j = 0; j < 2; j++) {
            myCounter1.startOppCounter(j, x2[j], y2[j]);
            myCounter2.startOppCounter(j, x2[j], y2[j]);
        }

        i++;
        int thrust1 = 100;
        int thrust2 = 100;
        boolean boostIsDone1 = false;
        boolean boostIsDone2 = false;
        boolean shieldIsDone1 = false;
        boolean shieldIsDone2 = false;

        // Near checkpoint breaking 1
        if (map.checkpointAngle.get(myCounter1.nextCheckpointId) < 135) {
            thrust1 = pod1.nearCheckpointBraking(myCounter1, thrust1, 2000, 200, (int) Math.round((double) map.checkpointAngle.get(myCounter1.nextCheckpointId) / 1.35 * Math.pow(0.99, ((double) 135 - map.checkpointAngle.get(myCounter1.nextCheckpointId)) / 135)));
        }

        //if (map.checkpointAngle.get(myCounter2.nextCheckpointId) < 135) {
        //    thrust2 = pod2.nearCheckpointBraking(myCounter2, thrust2, 2000, 200, (int) Math.round((double) map.checkpointAngle.get(myCounter2.nextCheckpointId) / 1.35 * Math.pow(0.99, ((double) 135 - map.checkpointAngle.get(myCounter2.nextCheckpointId)) / 135)));
        //}
        // Next checkpoint turn
        thrust1 = pod1.nextCheckpointTurnThrust(myCounter1, thrust1, 2500, 60, 0.6);
        //thrust2 = pod2.nextCheckpointTurnThrust(myCounter2, thrust2, 2500, 60, 0.6);

        // Shield 1
        /* отключено во време написания симуляции
        if (shieldIsDone1 == false) {
            if (myCounter1.oppConvergenceVelocity[0] >= 500 || myCounter1.oppConvergenceVelocity[1] >= 500) {
                shieldIsDone1 = pod1.shield(myCounter1, 1400, 600);
            } else {
                shieldIsDone1 = pod1.shield(myCounter1, 1200, 250);
            }
        }
        */
    
        // Boost 1
        if (thereIsBoost1 == true && boostIsDone1 == false && shieldIsDone1 == false) {
    
            //if (pod1.boost(myCounter1, map, laps, 1000, (int) Math.round(Math.atan((double) 600 / myCounter1.nextCheckpointDist) * 57.2958)) == true) {
            //    thereIsBoost1 = false;
            //    boostIsDone1 = true;
            //}
            /*
                if (i == 3) {
                    System.out.println(myCounter1.nextCheckpointNewX + " " + myCounter1.nextCheckpointNewY + " " + "BOOST");
                    thereIsBoost1 = false;
                    boostIsDone1 = true;
                }
             */
    
            if (myCounter1.lapNum == laps && myCounter1.nextCheckpointDist >= 3000 && myCounter1.nextCheckpointAngle < (int) Math.round(Math.atan((double) 600 / myCounter1.nextCheckpointDist) * 57.2958)) {
                //System.out.println(myCounter1.nextCheckpointNewX + " " + myCounter1.nextCheckpointNewY + " " + "BOOST");
                ret1[0] = myCounter1.nextCheckpointNewX;
                ret1[1] = myCounter1.nextCheckpointNewY;
                ret1[2] = 650;
                thereIsBoost1 = false;
                boostIsDone1 = true;
            }

        }
    
        // Pod's moove direction 1
        if (boostIsDone1 == false && shieldIsDone1 == false) {
            int[] movDirRes;
            movDirRes = pod1.movDirection(myCounter1, map, thrust1, 5000, (int) Math.round(Math.atan((double) 600 / myCounter1.nextCheckpointDist) * 57.2958));
            ret1[0] = movDirRes[0];
            ret1[1] = movDirRes[1];
            ret1[2] = movDirRes[2];
        }

//======================================================================================================================================================================================================================
        int targetOppAngle = 60;
        int delX = 0;
        int delY = 0;
        int j = 0;

        if (oppCounter[0].pos < oppCounter[1].pos) {
            j = 1;
        }

        if (myCounter2.oppDist[j] > 1200) {
            delX = (int) Math.round(Math.cos((double) (360 - oppCounter[j].angle) / 57.2958) * oppCounter[j].velocity * 7);
            delY = (int) Math.round(Math.sin((double) (360 - oppCounter[j].angle) / 57.2958) * oppCounter[j].velocity * 7);
        }

        if (Math.abs(myCounter2.oppAngle[j]) > targetOppAngle) {
            thrust2 = (int) Math.round((100 - (int) Math.round((Math.abs(myCounter2.oppAngle[j]) - targetOppAngle) / ((double) (180 - targetOppAngle) / 100)))
                    * (double) Math.pow(0.6, (Math.abs(myCounter2.oppAngle[j]) - targetOppAngle) / 10));
        }

        /* отключено во время написания симуляции
        if (shieldIsDone2 == false) {
            pod2.moveToX = oppCounter[j].x + delX;
            pod2.moveToY = oppCounter[j].y - delY;
            if (myCounter2.oppConvergenceVelocity[j] >= 600) {
                shieldIsDone2 = pod2.shield(myCounter2, 1400, 600);
            } else {
                shieldIsDone2 = pod2.shield(myCounter2, 1200, 250);
            }
            //System.err.println("oppConvergenceVelocity = " + myCounter2.oppConvergenceVelocity[j]);
            //System.err.println("oppDist = " + myCounter2.oppDist[j]);
        }
        */
        /*
            if (thereIsBoost2 == true && boostIsDone2 == false && shieldIsDone2 == false) {
                if (oppCounter[j].lapNum == laps && myCounter2.oppDist[j] > 1500 && myCounter2.oppAngle[j] < 1) {
                    System.out.println((oppCounter[j].x + delX) + " " + (oppCounter[j].y - delY) + " " + "BOOST");
                    thereIsBoost2 = false;
                    boostIsDone2 = true;
                }
            }
         */
    
        if (boostIsDone2 == false && shieldIsDone2 == false) {
            //System.out.println((oppCounter[j].x + delX / 2) + " " + (oppCounter[j].y - delY / 2) + " " + thrust2);
            ret2[0] = oppCounter[j].x + delX / 2;
            ret2[1] = oppCounter[j].y - delY / 2;
            ret2[2] = thrust2;
        }

        //======================================================================================================================================================================================================================
        /*
            // Shield 2
            
            if (shieldIsDone2 == false) {
                shieldIsDone2 = pod2.shield(myCounter2, 350);
            }

            // Boost 2
            
            if (thereIsBoost2 == true && boostIsDone2 == false && shieldIsDone2 == false) {
                
                //if (pod2.boost(myCounter2, map, laps, 1000, (int) Math.round(Math.atan((double) 600 / myCounter2.nextCheckpointDist) * 57.2958)) == true) {
                //    thereIsBoost2 = false;
                //    boostIsDone2 = true;
                //}
                
                if (myCounter2.lapNum == laps && myCounter2.nextCheckpointDist >= 3000 && myCounter2.nextCheckpointAngle < (int) Math.round(Math.atan((double) 600 / myCounter2.nextCheckpointDist) * 57.2958)) {
                    System.out.println(myCounter2.nextCheckpointNewX + " " + myCounter2.nextCheckpointNewY + " " + "BOOST");
                    thereIsBoost2 = false;
                    boostIsDone2 = true;
                }
            }
            
            // Pod's moove direction 2
            
            if (boostIsDone2 == false && shieldIsDone2 == false) {
                pod2.movDirection(myCounter2, map, thrust2, 5000, (int) Math.round(Math.atan((double) 600 / myCounter2.nextCheckpointDist) * 57.2958));
                //System.out.println(0 + " " + 0 + " " + 100);
            }
         */
        // Pod's method printer
        //pod1.podMethodPrinter(myCounter1);
        //pod2.podMethodPrinter(myCounter2);
        //System.err.println("i = " + i);
    
        int[] ret = ret1;
        if(podNum == 2) {
            ret = ret2;
        }
        return ret;
    }
}
