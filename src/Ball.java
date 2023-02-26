import objectdraw.FilledOval;
import objectdraw.DrawingCanvas;
import objectdraw.*;
import static objectdraw.ActiveObject.pause;
import java.util.Random;

public class Ball extends ActiveObject {
    final private FilledOval ballOne;
    private double directionX, directionY;
    private Random rand = new Random();
    
    public Ball(DrawingCanvas canvas){
        ballOne = new FilledOval(canvas.getWidth()/3, canvas.getHeight()/2, BreakoutProgram.BALL_RADIUS, BreakoutProgram.BALL_RADIUS, canvas);
        
        double nt = BreakoutProgram.VELOCITY_X_MAX;
        int dX = (int)nt;
        int nxt = rand.nextInt(10)%2;
        if(nxt == 0){
            nxt = (rand.nextInt(dX) + (int)BreakoutProgram.VELOCITY_X_MIN) * -1;
        } else {
            nxt = (rand.nextInt(dX) + (int)BreakoutProgram.VELOCITY_X_MIN);
        }
        
        directionX = nxt;
        directionY = BreakoutProgram.VELOCITY_Y;
        
    }
    
    public void run(){
        while(true){
            ballOne.move(directionX, directionY);
            pause(BreakoutProgram.DELAY);
        }
    }

    
    public boolean overLaps(FilledRect obj){
        return ballOne.overlaps(obj);
       
    }
    public void setLocation(Location startPoint){
        ballOne.moveTo(startPoint);
    }
    public FilledOval getOval(){
        return ballOne;
    }
    
    public Location getLocation(){
        return ballOne.getLocation();
    }
    
    public void setDirectionX(){
        directionX *= -1;
    }
    
    public void setDirectionY(){
        directionY *= -1;
    }
    
    public double getDirectionX(){
        return directionX;
    }
    
    public double getDirectionY(){
        return directionY;
    }
}   
