package demos;

import processing.core.*;

public class MyOwnPApplet extends PApplet{

	public String URL="palmTrees.jpg";
	public PImage backgroundImg=loadImage(URL,"jpg");
	
	public void setup() {
		size(400,400);
		stroke(0);
		backgroundImg.resize(0, height);
		image(backgroundImg,0,0);
	}
	
	public void draw() {
		int[] color=sunColorSec(second());
		fill(color[0],color[1],color[2]);
		ellipse(width/4,height/5,width/5,height/5);
	}
	
	public int[] sunColorSec(float seconds) {
		int[] rgb=new int[3];
		float diffFrom30=Math.abs(30-seconds);
		float ratio=diffFrom30/30;
		rgb[0]=(int)(255*ratio);
		rgb[1]=(int)(255*ratio);
		rgb[2]=0;
		return rgb;
	}
}
