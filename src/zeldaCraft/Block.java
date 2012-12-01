package zeldaCraft;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Polygon;
 
public class Block  {
	public Polygon poly;
	
	public Block(int x, int y, int tileType[],String type) {   
		
		poly = new Polygon(new float[]{
			x+tileType[0], y+tileType[1],
			x+tileType[2], y+tileType[3],
			x+tileType[4], y+tileType[5],
			x+tileType[6], y+tileType[7],
			}); 
		}
 
	public void update (int delta) {
	}
 
	public void draw (Graphics g) {
		g.draw(poly);
	}

}
