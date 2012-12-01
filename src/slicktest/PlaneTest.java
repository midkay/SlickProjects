package slicktest;

import java.io.IOException;
import java.net.*;
import java.text.DecimalFormat;

import javax.swing.*;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class PlaneTest extends BasicGame {

	Image land = null;
	Image plane = null;
	Image balloon = null;
	DecimalFormat df;

	float planeX;
	float planeY;
	int balloonX;
	int balloonY;
	int[][] balloonPositions = { { 435, 100 }, { 600, 350 }, { 120, 500 },
			{ 300, 100 }, { 500, 400 }, { 100, 150 }, { 700, 300 },
			{ 600, 250 }, { 150, 500 }, { -100, -100 } };
	float scale = 1.0f;

	int level;
	long startTime;
	double elapsed;
	double best;
	boolean won;
	boolean started;
	static String name;

	public PlaneTest() {
		super("Some Slick graphics testing");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		land = new Image("clouds.jpg");
		plane = new Image("plane.png");
		balloon = new Image("balloon2.png");
		level = 0;
		planeX = 400;
		planeY = 400;
		balloonX = balloonPositions[level][0];
		balloonY = balloonPositions[level][1];
		startTime = gc.getTime();
		elapsed = 0;
		won = false;
		started = false;
		df = new DecimalFormat("0.000");
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		Input input = gc.getInput();

		if (input.isKeyDown(Input.KEY_A)) {
			plane.rotate(-0.2f * delta);
			startGame(gc);
		}

		if (input.isKeyDown(Input.KEY_D)) {
			plane.rotate(0.2f * delta);
			startGame(gc);
		}

		if (input.isKeyDown(Input.KEY_W)) {
			float hip = 0.4f * delta;

			float rotation = plane.getRotation();

			planeX += hip * Math.sin(Math.toRadians(rotation));
			planeY -= hip * Math.cos(Math.toRadians(rotation));
			startGame(gc);
		}

		float planeCenterX = planeX + plane.getCenterOfRotationX();
		float planeCenterY = planeY + plane.getCenterOfRotationY();

		if (planeCenterX > balloonX && planeCenterX < balloonX + 51
				&& planeCenterY > balloonY && planeCenterY < balloonY + 69) {
			balloonX = balloonPositions[level][0];
			balloonY = balloonPositions[level][1];

			if (level < balloonPositions.length - 1) {
				level++;
			}
			else {
				won = true;
				if(best == 0 || elapsed < best) {
					best = elapsed;
					submitScore();
				}
			}
		}

		if (!won && started)
			elapsed = ((double) gc.getTime() - startTime) / 1000.0;

		if (won) {
			if (planeCenterX > 340 && planeCenterX < 560 && planeCenterY > 470
					&& planeCenterY < 510)
				init(gc);
		}
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		land.draw(0, 0);
		plane.draw(planeX, planeY, scale);
		balloon.draw(balloonX, balloonY);

		g.drawString("planeX: " + df.format(planeX) + " planeY: " + df.format(planeY), 10, 30);
		g.drawString("elapsed: " + df.format(elapsed) + " sec", 10, 70);
		g.drawString("best:    " + df.format(best) + " sec", 10, 90);

		if (won) {
			g.drawString("YOU FUCKING WIN. time = " + df.format(elapsed) + " sec", 325, 470);
			g.drawString("Fly over this to try again", 325, 490);
		} else if (!started) {
			g.drawString("Timer starts when you do! WSAD to move", 300, 480);
		}
	}

	public void startGame(GameContainer gc) {
		if (!started) {
			startTime = gc.getTime();
			elapsed = 0;
		}
		started = true;
	}
	
	public static void namePrompt() {
        JPanel connectionPanel;
        String[] ConnectOptionNames = { "Submit", "Cancel" };
        String   ConnectTitle = "Enter your name!";

	 	// Create the labels and text fields.
		JLabel     userNameLabel = new JLabel("Name:   ", JLabel.RIGHT);
	 	JTextField userNameField = new JTextField("");
		connectionPanel = new JPanel(false);
		connectionPanel.setLayout(new BoxLayout(connectionPanel,
							BoxLayout.X_AXIS));
		userNameLabel.setBounds(10, 10, 40, 10);
		userNameField.setBounds(40, 10, 40, 10);
		connectionPanel.add(userNameLabel);
		connectionPanel.add(userNameField);

	    // Connect or quit
		if(JOptionPane.showOptionDialog(null, connectionPanel, 
	                                        ConnectTitle,
	                                        JOptionPane.OK_CANCEL_OPTION, 
	                                        JOptionPane.INFORMATION_MESSAGE,
	                                        null, ConnectOptionNames, 
	                                        ConnectOptionNames[0]) == 0)
		    name = userNameField.getText();
		else
			System.exit(0);
	}
	
	public void submitScore() {
	    String request = "http://www.midkay.net/plane_scores.php?submit&score=" +elapsed+ "&name=" +name;
	    URL url = null;
		try {
			url = new URL(request);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
            url.openStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SlickException {
		namePrompt();
		
		AppGameContainer app = new AppGameContainer(new PlaneTest());

		app.setDisplayMode(800, 600, false);
		app.start();
	}
}