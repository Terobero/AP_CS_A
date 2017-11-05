package application;

/**
 * AP CS A
 * 2017 - 2018 
 * Hisar School
 * 
 * @author	Terobero
 * Kaan Bozkurt
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Shape {
	Rectangle a;
	Rectangle b;
	Rectangle c;
	Rectangle d;
	private String name;
	public int form = 1;
	/**
	 * Combines the four parts of the block.
	 * 
	 * @param a the first part of the block
	 * @param b the second part of the block
	 * @param c the third part of the block
	 * @param d the fourth part of the block
	 */
	public Shape(Rectangle a, Rectangle b, Rectangle c, Rectangle d){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	/**
	 * 
	 * @param a the first part of the block
	 * @param b the second part of the block
	 * @param c the third part of the block
	 * @param d the fourth part of the block
	 * @param name name of the block
	 */
	public Shape(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String name){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.name = name;
		Color color = Color.WHITE;
		//Sets color depending on the name of the shape
		switch(name){
			case "j":
				color = Color.SILVER;
				break;
			case "l":
				color = Color.PURPLE;
				break;
			case "o":
				color = Color.NAVY;
				break;
			case "s":
				color = Color.DARKGREEN;
				break;
			case "t":
				color = Color.BROWN;
				break;
			case "z":
				color = Color.TEAL;
				break;
			case "i":
				color = Color.MAROON;
				break;
				
		}
		this.a.setFill(color);
		this.b.setFill(color);
		this.c.setFill(color);
		this.d.setFill(color);
	}
	/**
	 * @return name of the block
	 */
	public String getName(){
		return name;
	}
	/**
	 * Changes form of the shape.
	 */
	public void changeForm(){
		if(form != 4){
			form++;
		} else{
			form = 1;
		}
	}
}
