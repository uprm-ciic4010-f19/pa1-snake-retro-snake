package Game.Entities.Dynamic;

import Main.Handler;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import Game.Entities.Static.Apple;
import Game.GameStates.State;

import java.util.List;


/**
 * Created by AlexVR on 7/2/2018.
 */
public class Player {

	
	public int lenght;
	public boolean justAte;
	private Handler handler;

	public int xCoord;
	public int yCoord;

	public double displayScore =  0;
	public double trackScore = 0;

	public int moveCounter;
	public int speed;
	public static int steps;
	public int index;
	public boolean checkApple;

	public String direction;//is your first name one?

	public String randomDirection() {
	    	List<String> list = new ArrayList<>();
	    	list.add("Right");
	    	list.add("Left");
	    	list.add("Down");
	    	list.add("Up");
	    	
	    	Random indexRandom = new Random();
	    	
	    	index = indexRandom.nextInt(list.size());
	    	
	    	String newDirection = list.get(index);
	 
//	    	System.out.println(index);
	    	
	    	return newDirection;
	    	
	    	
	    }

		

	public Player(Handler handler){
		this.handler = handler;
		xCoord = 0;
		yCoord = 0;
		moveCounter = 0;
		direction= randomDirection();
		justAte = false;
		lenght= 1;
		speed = 8;
		steps = 0;

	}

	public void tick(){
		moveCounter++;
		steps ++;
		if(moveCounter>=speed) { // this changes the speed
			checkCollisionAndMove();
			moveCounter=0;
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_UP) && direction != "Down"){
			direction="Up";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_DOWN) && direction != "Up"){
			direction="Down";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_LEFT) && direction != "Right"){
			direction="Left";
		}if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_RIGHT) && direction != "Left"){
			direction="Right";
		} if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_N)) {
			handler.getWorld().body.addFirst(new Tail(xCoord, yCoord, handler));
		} if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_M) && lenght > 0) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y]=false;
			handler.getWorld().body.removeLast();
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y]=false;
			handler.getWorld().body.removeLast();
		}
		if(handler.getKeyManager().keyJustPressed(KeyEvent.VK_MINUS)) {
			speed++;
		}
		if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_EQUALS)) {
			speed--;
		} if (handler.getKeyManager().keyJustPressed(KeyEvent.VK_ESCAPE)) {
        	State.setState(handler.getGame().pauseState);
        }



	}

	public void checkCollisionAndMove(){
		
		handler.getWorld().playerLocation[xCoord][yCoord]=false;
		int x = xCoord;
		int y = yCoord;
		switch (direction){
		case "Left":
			if(xCoord==0){
				xCoord= handler.getWorld().GridWidthHeightPixelCount-1;
			}else{
				xCoord--;
			}
			break;
		case "Right":
			if(xCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				xCoord=0;
			}else{
				xCoord++;
			}
			break;
		case "Up":
			if(yCoord==0){
				yCoord=handler.getWorld().GridWidthHeightPixelCount-1;
			}else{
				yCoord--;
			}
			break;
		case "Down":
			if(yCoord==handler.getWorld().GridWidthHeightPixelCount-1){
				yCoord=0;
			}else{
				yCoord++;
			}
			break;
		}
		if (handler.getWorld().playerLocation[xCoord][yCoord]) {
			kill();
		}else {
			handler.getWorld().playerLocation[xCoord][yCoord]=true;
		}


		if(handler.getWorld().appleLocation[xCoord][yCoord]){
			Eat();
			if (checkApple == false) {
				if( handler.getWorld().body.isEmpty() == false) {
					if (handler.getWorld().body.size() > 1) {
					speed ++;
					handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y]=false;
					handler.getWorld().body.removeLast();
					handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y]=false;
					handler.getWorld().body.removeLast();
					trackScore -= Math.sqrt(2*trackScore+1);
					trackScore = Math.round(trackScore*100.0) / 100.0;
					if(displayScore != trackScore) {
						if (trackScore <= -0.7) {
							trackScore = 0.0;
						}
						displayScore= trackScore;
					} 
				} else {
					kill();
				}
			} else {
				kill();
			}
				} else if(checkApple == true) {
					speed --;
					trackScore += Math.sqrt(2*trackScore+1);
					trackScore = Math.round(trackScore*100.0) / 100.0;
					if(displayScore != trackScore) {
						displayScore= trackScore;
				}
			}
		}

		if(!handler.getWorld().body.isEmpty()) {
			handler.getWorld().playerLocation[handler.getWorld().body.getLast().x][handler.getWorld().body.getLast().y] = false;
			handler.getWorld().body.removeLast();
			handler.getWorld().body.addFirst(new Tail(x, y,handler));
		}
		}




	public void render(Graphics g,Boolean[][] playeLocation){
		checkApple = Apple.isGood();
		//        Random r = new Random();
		Color newGreen = new Color(172, 225, 175);
		Color brown = new Color(131, 105, 83);
		Color newRed = new Color(201, 20, 20);
		g.setColor(newGreen);;
		g.setFont(new Font("Monospaced",1,40));
		g.drawString("Score: "+displayScore, 4, 35);
		
		
		
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {
				 

				if(playeLocation[i][j]) { // sets the color to the snake
					g.setColor(newGreen);
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}
				if (handler.getWorld().appleLocation[i][j]){ // sets color to the apple
					if (checkApple == true) {
						g.setColor(newRed);
					} else if(checkApple == false) {
						g.setColor(brown);
					}
					g.fillRect((i*handler.getWorld().GridPixelsize),
							(j*handler.getWorld().GridPixelsize),
							handler.getWorld().GridPixelsize,
							handler.getWorld().GridPixelsize);
				}
					

			}
		}


	}

	public void Eat(){
		lenght++;
		Tail tail= null;
		handler.getWorld().appleLocation[xCoord][yCoord]=false;
		handler.getWorld().appleOnBoard=false;
		switch (direction){
		case "Left":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail = new Tail(this.xCoord+1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail = new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail =new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler);
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler);
					}else{
						tail=new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler);

					}
				}

			}
			break;
		case "Right":
			if( handler.getWorld().body.isEmpty()){
				if(this.xCoord!=0){
					tail=new Tail(this.xCoord-1,this.yCoord,handler);
				}else{
					if(this.yCoord!=0){
						tail=new Tail(this.xCoord,this.yCoord-1,handler);
					}else{
						tail=new Tail(this.xCoord,this.yCoord+1,handler);
					}
				}
			}else{
				if(handler.getWorld().body.getLast().x!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
				}else{
					if(handler.getWorld().body.getLast().y!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
					}
				}

			}
			break;
		case "Up":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(this.xCoord,this.yCoord+1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					}
				}
			}else{
				if(handler.getWorld().body.getLast().y!=handler.getWorld().GridWidthHeightPixelCount-1){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord+1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		case "Down":
			if( handler.getWorld().body.isEmpty()){
				if(this.yCoord!=0){
					tail=(new Tail(this.xCoord,this.yCoord-1,handler));
				}else{
					if(this.xCoord!=0){
						tail=(new Tail(this.xCoord-1,this.yCoord,handler));
					}else{
						tail=(new Tail(this.xCoord+1,this.yCoord,handler));
					} System.out.println("Tu biscochito");
				}
			}else{
				if(handler.getWorld().body.getLast().y!=0){
					tail=(new Tail(handler.getWorld().body.getLast().x,this.yCoord-1,handler));
				}else{
					if(handler.getWorld().body.getLast().x!=0){
						tail=(new Tail(handler.getWorld().body.getLast().x-1,this.yCoord,handler));
					}else{
						tail=(new Tail(handler.getWorld().body.getLast().x+1,this.yCoord,handler));
					}
				}

			}
			break;
		}
		handler.getWorld().body.addLast(tail);
		handler.getWorld().playerLocation[tail.x][tail.y] = true;

	}

	public void kill(){
		lenght = 0;
		for (int i = 0; i < handler.getWorld().GridWidthHeightPixelCount; i++) {
			for (int j = 0; j < handler.getWorld().GridWidthHeightPixelCount; j++) {

				handler.getWorld().playerLocation[i][j]=false;
				State.setState(handler.getGame().gameOverState);

			}
		}
	}

	public boolean isJustAte() {
		return justAte;
	}

	public void setJustAte(boolean justAte) {
		this.justAte = justAte;
	}
}
