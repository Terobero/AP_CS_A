package application;

/**
 * AP CS A
 * 2017 - 2018 
 * Hisar School
 * 
 * @author	Terobero
 * Kaan Bozkurt
 */
import javafx.scene.shape.Rectangle;
//Created this class because the original was getting too big and confusing
public class TetrisHolder {
	//Getting the numbers and the grid from Tetris
	public static final int MOVE_AMOUNT = Tetris.MOVE_AMOUNT;
	public static final int SIZE = Tetris.SIZE;
	public static int XLIMIT = Tetris.XLIMIT;
	public static int YLIMIT = Tetris.YLIMIT;
	public static int[][] GRID = Tetris.GRID;
	/**
	 * Checks if it is possible to move the block right, 
	 * if it is possible, it moves right.
	 * @param shape shape which gets checked
	 */
	public static void CheckRight(Shape shape){
		if(shape.a.getX() + MOVE_AMOUNT <= XLIMIT - SIZE && shape.b.getX() + MOVE_AMOUNT <= XLIMIT - SIZE && shape.c.getX() + MOVE_AMOUNT <= XLIMIT - SIZE && shape.d.getX() + MOVE_AMOUNT <= XLIMIT - SIZE){
			int checka = GRID[((int)shape.a.getX()/SIZE) + 1][((int)shape.a.getY()/SIZE)];
			int checkb = GRID[((int)shape.b.getX()/SIZE) + 1][((int)shape.b.getY()/SIZE)];
			int checkc = GRID[((int)shape.c.getX()/SIZE) + 1][((int)shape.c.getY()/SIZE)];
			int checkd = GRID[((int)shape.d.getX()/SIZE) + 1][((int)shape.d.getY()/SIZE)];
			if(checka == 0 && checka == checkb && checkb == checkc && checkc == checkd){
				shape.a.setX(shape.a.getX() + MOVE_AMOUNT);
				shape.b.setX(shape.b.getX() + MOVE_AMOUNT);
				shape.c.setX(shape.c.getX() + MOVE_AMOUNT);
				shape.d.setX(shape.d.getX() + MOVE_AMOUNT);
			}
		}
	}
	/**
	 * Checks if it is possible to move the block left, 
	 * if it is possible, it moves left.
	 * @param shape shape which gets checked
	 */
	public static void CheckLeft(Shape shape){
		  if(shape.a.getX() - MOVE_AMOUNT >= 0 && shape.b.getX() - MOVE_AMOUNT >= 0 && shape.c.getX() - MOVE_AMOUNT >= 0 && shape.d.getX() - MOVE_AMOUNT >= 0){
			  int checka = GRID[((int)shape.a.getX()/SIZE) - 1][((int)shape.a.getY()/SIZE)];
			  int checkb = GRID[((int)shape.b.getX()/SIZE) - 1][((int)shape.b.getY()/SIZE)];
			  int checkc = GRID[((int)shape.c.getX()/SIZE) - 1][((int)shape.c.getY()/SIZE)];
			  int checkd = GRID[((int)shape.d.getX()/SIZE) - 1][((int)shape.d.getY()/SIZE)];
			  if(checka == 0 && checka == checkb && checkb == checkc && checkc == checkd){
				  shape.a.setX(shape.a.getX() - MOVE_AMOUNT);
				  shape.b.setX(shape.b.getX() - MOVE_AMOUNT);
				  shape.c.setX(shape.c.getX() - MOVE_AMOUNT);
				  shape.d.setX(shape.d.getX() - MOVE_AMOUNT);
				  }
			  }
		  }
	/**
	 * Creates a new block.
	 * 
	 * @return the block
	 */
	public static Shape createRect(){
		//Picks a random number, each block has a spawning chance of 3/20 except for I block
	      int block = (int)(Math.random() * 100);
	      String name;
	      Rectangle a = new Rectangle(SIZE, SIZE), b = new Rectangle(SIZE, SIZE), c = new Rectangle(SIZE, SIZE), d = new Rectangle(SIZE, SIZE);
	      if(block < 15){ //J
	    	  a.setX(XLIMIT / 2 - SIZE);
	    	  b.setX(XLIMIT / 2 - SIZE); b.setY(SIZE);
	    	  c.setX(XLIMIT / 2); c.setY(SIZE);
	    	  d.setX(XLIMIT / 2 + SIZE); d.setY(SIZE);
	    	  name = "j";
	      }
	      else if(block < 30){ //L
	    	  a.setX(XLIMIT / 2 + SIZE);
	    	  b.setX(XLIMIT / 2 - SIZE); b.setY(SIZE);
	    	  c.setX(XLIMIT / 2); c.setY(SIZE);
	    	  d.setX(XLIMIT / 2 + SIZE); d.setY(SIZE);
	    	  name = "l";
	      }
	      else if (block < 45){ //O
	    	  a.setX(XLIMIT / 2 - SIZE);
	    	  b.setX(XLIMIT / 2);
	    	  c.setX(XLIMIT / 2 - SIZE); c.setY(SIZE);
	    	  d.setX(XLIMIT / 2); d.setY(SIZE);
	    	  name = "o";
	      }
	      else if(block < 60){ //S
	    	  a.setX(XLIMIT / 2 + SIZE);
	    	  b.setX(XLIMIT / 2);
	    	  c.setX(XLIMIT / 2); c.setY(SIZE);
	    	  d.setX(XLIMIT / 2 - SIZE); d.setY(SIZE);
	    	  name = "s";
	      }
	      else if(block < 75){ //T
	    	  a.setX(XLIMIT / 2 - SIZE);
	    	  b.setX(XLIMIT / 2);
	    	  c.setX(XLIMIT / 2); c.setY(SIZE);
	    	  d.setX(XLIMIT / 2 + SIZE);
	    	  name = "t";
	      }
	      else if(block < 90){ //Z
	    	  a.setX(XLIMIT / 2 + SIZE);
	    	  b.setX(XLIMIT / 2);
	    	  c.setX(XLIMIT / 2  + SIZE); c.setY(SIZE);
	    	  d.setX(XLIMIT / 2 + SIZE + SIZE); d.setY(SIZE);
	    	  name = "z";
	      }
	      else{
	    	  a.setX(XLIMIT / 2 - SIZE - SIZE);
	    	  b.setX(XLIMIT / 2 - SIZE);
	    	  c.setX(XLIMIT / 2);
	    	  d.setX(XLIMIT / 2 + SIZE);
	    	  name = "i";
	      }
		  return new Shape(a, b, c, d, name);
	  }
}