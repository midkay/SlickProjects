package zeldaCraft;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class DriverZeldaCraft extends ZeldaCraft {
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new ZeldaCraft());

		app.setDisplayMode(SCREEN_WIDTH, SCREEN_HEIGHT, false);
		app.start();

	}

}
