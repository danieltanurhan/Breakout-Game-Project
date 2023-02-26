import objectdraw.*;
public class Breakout extends WindowController {
    BreakoutGame game;
    
    public void begin(){
        game = new BreakoutGame(canvas);
    }
    
    public void onMouseMove(Location point){
        game.movePlayerPaddle(point);
    }
    
    public void onMouseClick(Location point){
        game.startBall();
    }
    
    public static void main(String[] args) {
        new Breakout().startController(430, 600, "Breakout-Game");
    }
    
}
