import java.util.List;
import java.awt.Color;
import objectdraw.*;
import java.util.ArrayList;

public class BreakoutGame extends ActiveObject {
    final private List< List<FilledRect >> bricks;
    final private FilledRect northWall, eastWall, southWall, westWall;
    final private FilledRect playerPaddle;
    final private Ball ball;
    final private Text text, endGame, winGame, startGame;
    private int destroyedBrickCount;
    private int tries = BreakoutProgram.NTURNS;
    private double brickX = BreakoutProgram.BRICK_SEP;
    private double brickY = BreakoutProgram.BRICK_Y_OFFSET;
    
    public BreakoutGame(DrawingCanvas canvas) {
        bricks = new ArrayList<>();
        setBricks(canvas);
        
        westWall = new FilledRect(0, 0, 4, canvas.getHeight(), canvas);
        westWall.setColor(Color.WHITE);
        
        eastWall = new FilledRect(canvas.getWidth()-4, 0, 4, canvas.getHeight(), canvas);
        eastWall.setColor(Color.WHITE);
        
        northWall = new FilledRect(0, 0, canvas.getWidth(), 4, canvas);
        northWall.setColor(Color.WHITE);
        
        southWall = new FilledRect(0, canvas.getHeight()-4, canvas.getWidth(), 4, canvas);
        southWall.setColor(Color.WHITE);
        
        playerPaddle = new FilledRect((canvas.getWidth()/2), canvas.getHeight() - BreakoutProgram.PADDLE_Y_OFFSET, BreakoutProgram.PADDLE_WIDTH, BreakoutProgram.PADDLE_HEIGHT, canvas);
        
        ball = new Ball(canvas);
        
        text = new Text(tries, canvas.getWidth()-30, canvas.getHeight()-56, canvas);
        text.setFont(BreakoutProgram.SCREEN_FONT);
        text.setFontSize(56);
        text.setBold();
        
        endGame = new Text("GAME-OVER", BreakoutProgram.BRICK_SEP, canvas.getHeight()/2, canvas);
        endGame.hide();
        endGame.setFont(BreakoutProgram.SCREEN_FONT);
        endGame.setFontSize(69);
        endGame.setBold();
        
        winGame = new Text("YOU WON!", BreakoutProgram.BRICK_SEP, canvas.getHeight()/2, canvas);
        winGame.hide();
        winGame.setFont(BreakoutProgram.SCREEN_FONT);
        winGame.setFontSize(69);
        winGame.setBold();
        destroyedBrickCount = bricks.size() * bricks.size();
        
        ball.getOval().hide();
        text.hide();
        startGame = new Text("Click to start the game", BreakoutProgram.BRICK_SEP, canvas.getHeight()/2, canvas);
        startGame.setFontSize(31);
        startGame.setBold();
        
        start();
    }
    
    public void run(){
        while(true){

            if(ball.overLaps(southWall)){
                tries--;
                text.setText(String.valueOf(tries));
                if(tries == 0){
                    text.hide();
                    endGame.show();
                    playerPaddle.hide();
                    ball.getOval().hide();
                    stop();
                } else {
                    ball.setLocation(new Location(210, 300));
                }
            }
            else if(ball.getOval().overlaps(northWall)){
                ball.setDirectionY();
                ball.getOval().moveTo(ball.getLocation().getX(), northWall.getY()+5);
            }
            else if(ball.getOval().overlaps(westWall)){
                ball.setDirectionX();
                ball.getOval().moveTo(ball.getLocation().getX()+5, ball.getLocation().getY());
            }
            else if (ball.getOval().overlaps(eastWall)){
                ball.setDirectionX();
                ball.getOval().moveTo(ball.getLocation().getX()-5, ball.getLocation().getY());
            }
            else if(ball.getOval().overlaps(playerPaddle)){
                ball.setDirectionY();
                ball.getOval().moveTo(ball.getLocation().getX(), ball.getLocation().getY()-5);
            } 
            
            int index = bricks.size();
            for(int i = 0; i < index; i++){
                for(int j = 0; j < index; j++){
                    if(ball.overLaps(bricks.get(i).get(j))){
                        bricks.get(i).get(j).removeFromCanvas();
                        index = j;
                        destroyedBrickCount--;
                        if(destroyedBrickCount == 0){
                            winGame.show();
                            playerPaddle.hide();
                            ball.getOval().hide();
                            stop();
                        }
                        ball.setDirectionY();
                        ball.getOval().moveTo(ball.getLocation().getX(), ball.getLocation().getY()+5);
                        break;
                    }
                }
            }
 
        }
    }
      
    public void setBricks(DrawingCanvas canvas){
        
        for(int i = 0; i < BreakoutProgram.NBRICK_ROWS; i++){
            List<FilledRect> row = new ArrayList<>();
            brickX = BreakoutProgram.BRICK_SEP;
            for(int j = 0; j < BreakoutProgram.NBRICK_COLUMNS; j++){
                row.add(new FilledRect(brickX, brickY, BreakoutProgram.BRICK_WIDTH, BreakoutProgram.BRICK_HEIGHT, canvas));
                brickX += BreakoutProgram.BRICK_WIDTH + BreakoutProgram.BRICK_SEP;
            }
            bricks.add(row);
            brickY += BreakoutProgram.BRICK_HEIGHT + BreakoutProgram.BRICK_SEP;
        }
    }
    
    public void movePlayerPaddle(Location mousePoint){
        if (mousePoint.getX()+playerPaddle.getWidth() < eastWall.getX()
                || mousePoint.getX() < westWall.getX() + westWall.getWidth()) {
            playerPaddle.moveTo(mousePoint.getX(), playerPaddle.getY());
        } else {playerPaddle.moveTo(playerPaddle.getLocation());}
    }
    
    public void startBall(){
        if(!ball.isAlive()){
            startGame.hide();
            ball.getOval().show();
            text.show();
            ball.start();
        }
        
    }
    
}
