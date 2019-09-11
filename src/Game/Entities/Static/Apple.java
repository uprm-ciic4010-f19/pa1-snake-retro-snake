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
    public static boolean goodApple = true;
    public int rotten;
    

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
        this.alive = Player.steps;
        rotten = alive + 50;
    }
    
    public boolean isGood() {
    	if (Player.steps > rotten) {
    		goodApple = false;
    		return goodApple;
    	} else {
    		return goodApple;
    	}
    	    }
}
