import static java.lang.Math.*;

public class MovementPhisics {
    public static int[] getPodState(int x1, int y1, int vx1, int vy1, int angle1, int xMov, int yMov, int thrust) {
        int angle2 = calcAngle2(x1,y1,angle1,xMov,yMov);
        int[] velocity = calcVelocity(vx1,vy1,angle2,thrust);
        int vx2 = velocity[0];
        int vy2 = velocity[1];
        int x2 = x1 + vx2;
        int y2 = y1 + vy2;
        vx2 = (int) round(0.85*vx2); // friction
        vy2 = (int) round(0.85*vy2); // friction
        
        int[] ret = {x2,y2,vx2,vy2,angle2};
        return ret;
    }
    
    private static int calcAngle2(int x1, int y1, int angle1, int xMov, int yMov) { // calculate angle2
        int angle2;
        int dxMov = xMov - x1;
        int dyMov = yMov - y1;
        
        int expAngle; // expected angle
        if(dyMov >= 0) {
            expAngle = vectorsAngle(1,0,dxMov,dyMov);
        } else {
            expAngle = 360 - vectorsAngle(1,0,dxMov,dyMov);
        }
        
        if(vectorsAngle((int) round(100*cos(angle1 / 57.2958)), (int) round(100*sin(angle1 / 57.2958)), dxMov, dyMov) <= 18 || expAngle == angle1) {
            angle2 = expAngle;
        } else if(abs(expAngle - angle1) <= 180 && expAngle != angle1) {
            angle2 = angle1 + 18*(expAngle - angle1)/abs(expAngle - angle1);
        } else {
            angle2 = angle1 - 18*(expAngle - angle1)/abs(expAngle - angle1);
            if(angle2 > 360) {
                angle2 = angle2 - 360;
            } else if(angle2 < 0) {
                angle2 = 360 + angle2;
            }
        }
        
        return angle2;
    }
    
    private static int[] calcVelocity(int vx1, int vy1, int angle2, int thrust) { // calculate velocity of pod
        int vx2 = vx1 + (int) round(thrust*cos(angle2/57.2958));
        int vy2 = vy1 + (int) round(thrust*sin(angle2/57.2958));
        int[] ret = {vx2, vy2};
        return ret;
    }
    
    public static int vectorsAngle(int x1, int y1, int x2, int y2) { // returns angle between two vectors
        int angle = (int) round(acos((x1*x2 + y1*y2)/(sqrt(pow(x1,2) + pow(y1,2))*sqrt(pow(x2,2) + pow(y2,2))))*57.2958);
        return angle;
    }
}