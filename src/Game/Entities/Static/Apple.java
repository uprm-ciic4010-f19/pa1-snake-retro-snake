package Game.Entities.Static;

import Game.Entities.Dynamic.Player;
import Main.Handler;

/**
 * Created by AlexVR on 7/2/2018.
 */
public class Apple {

    private Handler handler;

    public int xCoord;
    public int yCoord;
    public static boolean goodApple;
    public static int turnsRotten;
    

    public Apple(Handler handler,int x, int y){
        this.handler=handler;
        this.xCoord=x;
        this.yCoord=y;
        turnsRotten = Player.steps + 300;
    }
    
    public static boolean isGood(){
    	if (Player.steps > turnsRotten) {
    		goodApple = false;
    		System.out.println("Apple rotten " + Player.steps);
    		return goodApple;
    	} else {
    		goodApple = true;
    		System.out.println("Apple good " + Player.steps);
    		return goodApple;
    	}
    	    }
}
