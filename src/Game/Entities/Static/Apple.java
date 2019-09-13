package Game.Entities.Static;

import Game.Entities.Dynamic.Player;
import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;

    public int xCoord;
    public int yCoord, alive;
    public static boolean goodApple;
    public static int rotten;
    

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
        this.alive = Player.steps;
        Apple.rotten = alive + 250;
    }
    
    public static boolean isGood(){
    	if (Player.steps > rotten) {
    		goodApple = false;
    		System.out.println("Apple is bad " + Player.steps);
    		return goodApple;
    	} else {
    		goodApple = true;
    		System.out.println("Apple is good " + Player.steps);
    		return goodApple;
    	}
    	    }
}
