package Game.GameStates;

import java.awt.Graphics;

import Main.Handler;
import Resources.Images;
import UI.UIImageButton;
import UI.UIManager;

public class GameOverState extends State {
	
	private int count = 0;
    private UIManager uiManager;

	public GameOverState(Handler handler) {
		super(handler);
		 uiManager = new UIManager(handler);
	        handler.getMouseManager().setUimanager(uiManager);

	        uiManager.addObjects(new UIImageButton(480, 550, 128, 64, Images.optionButton, () -> {
	            handler.getMouseManager().setUimanager(null);
	            State.setState(handler.getGame().menuState);
	        }));

	        uiManager.addObjects(new UIImageButton(200, 550, 128, 64, Images.titleButton, () -> {
	            handler.getMouseManager().setUimanager(null);
	            State.setState(handler.getGame().menuState);
	        }));

	}

	@Override
	public void tick() {
        handler.getMouseManager().setUimanager(uiManager);
        uiManager.tick();
        count++;
        if( count>=30){
            count=30;
        }
        if(handler.getKeyManager().pbutt && count>=30){
            count=0;

            State.setState(handler.getGame().menuState);
        }
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(Images.gameOver,0,0,800,600,null);
		uiManager.Render(g);
	}

}
