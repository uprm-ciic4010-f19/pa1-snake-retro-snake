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
    

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
    }
    
    public static boolean isGood(){
    	if (Player.steps % 50 != 0) {
    		goodApple = true;
    		return goodApple;
    	} else {
    		goodApple = false;
    		return goodApple;
    	}
    	    }
}
