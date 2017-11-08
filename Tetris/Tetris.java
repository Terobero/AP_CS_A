package application;

/**
 * AP CS A
 * 2017 - 2018 
 * Hisar School
 * 
 * @author	Terobero
 * Kaan Bozkurt
 */

//Importing JavaFX Libraries
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Tetris Class
public class Tetris extends Application {
  //The variables that are being used between methods
  public static final int MOVE_AMOUNT = 25;
  public static final int SIZE = 25;
  public static int XLIMIT = SIZE * 10;
  public static int YLIMIT = SIZE * 24;
  public static int[][] GRID = new int[XLIMIT/SIZE][YLIMIT/SIZE];
  private static Pane group = new Pane();
  private static Pane gameoverGroup = new Pane();
  private static Shape object;
  private static Scene scene = new Scene(group, XLIMIT + 200, YLIMIT);
  private static Scene gameoverScene = new Scene(gameoverGroup, 500, YLIMIT);
  public static int score = 0;
  public static int mins = 0;
  private static int top = 0;
  private static int finalscore = 0;
  private static boolean time = true;
  private static Shape nextShape = TetrisHolder.createRect();
  
  //Creating the scene and starting the game
  public static void main(String[] args) { launch(args); }
  @Override public void start(Stage stage) throws Exception {
    for(int[] a: GRID){
    	Arrays.fill(a, 0);
    }
    //Game over screen preparation
    Text gameovertext = new Text("GAME\nOVER");
    gameovertext.setFill(Color.RED);
    gameovertext.setStyle("-fx-font: 100 arial;");
    gameovertext.setY(250);
    gameovertext.setX(100);
    Text quittingtext = new Text("");
    quittingtext.setFill(Color.MEDIUMPURPLE);
    quittingtext.setStyle("-fx-font: 25 arial;");
    quittingtext.setY(450);
    quittingtext.setX(180);
    Text finalscoretext = new Text("");
    finalscoretext.setFill(Color.MEDIUMPURPLE);
    finalscoretext.setStyle("-fx-font: 25 arial;");
    finalscoretext.setY(150);
    finalscoretext.setX(160);
    gameoverGroup.getChildren().addAll(gameovertext, quittingtext, finalscoretext);
    
    //Creating score and time texts
    Line line = new Line(XLIMIT, 0, XLIMIT, YLIMIT);
    Line line2 = new Line(XLIMIT, 300, XLIMIT + 200, 300);
    Text scoretext = new Text("Score: 0");
    Text timetext = new Text("Time: 0 s");
    scoretext.setY(350);
    scoretext.setX(XLIMIT + 25);
    timetext.setY(400);
    timetext.setX(XLIMIT + 25);
    group.getChildren().addAll(scoretext, timetext, line, line2);
    //Creating the first block and the stage
    Shape a = nextShape;
    group.getChildren().addAll(a.a, a.b, a.c, a.d);
	moveOnKeyPress(a);
	object = a;
	nextShape = TetrisHolder.createRect();
    stage.setScene(scene);
    stage.show();
    //Timer for falling blocks
    Timer fall = new Timer();
    TimerTask task =new TimerTask() {
  	  @Override
  	  public void run() {
  		  Platform.runLater(new Runnable(){
  			  public void run(){
  				  if(object.a.getY() == 0 || object.b.getY() == 0 || object.c.getY() == 0 || object.d.getY() == 0)
  					  top++;
  				  else
  					  top = 0;
  				  //Checking if a block has stayed on the top row too much, if so, brings up the game over screen
  				  if(top == 2){
  					  finalscore = score;
  					  stage.hide();
  					  stage.setScene(gameoverScene);
  					  stage.show();
  				  }
  				  //Shows the final score
  				  if(top > 2){
  					  finalscoretext.setText("Final Score: " + Integer.toString(finalscore));
  					  quittingtext.setText("Quitting in " + Integer.toString(10-top/3));
  				  }
  				  //Quits the game
  				  if(top == 30){
					  System.exit(0);
				  }
  				  if(time)
  					  CheckDown(object);
  				  score++;
  				  scoretext.setText("Score: " + Integer.toString(score));
  			  }
  		  });
  	  	}
  	  };
  	  fall.schedule(task,0,300);
  	//Timer for time showing in game  
  	Timer time = new Timer();
    TimerTask task2 =new TimerTask() {
  	  @Override
  	  public void run() {
  		  Platform.runLater(new Runnable(){
  			  public void run(){
  				  	mins++;
  				  	timetext.setText("Time: " + Integer.toString(mins) + " s");
  				  	if(mins > 60){
  				  	timetext.setText("Time: " + Integer.toString(mins/60) + " min " + Integer.toString(mins%60) + " s");
  				  	}
  			  }
  		  });
  	  	}
  	  };
  	  time.schedule(task2,0,1000);    
    }
/**
 * Moves the pieces with the keys user pressed.
 * 
 * @param rect	first part of a block
 * @param rect2	second part of a block
 * @param rect3	third part of a block
 * @param rect4	fourth part of a block
 */
  private void moveOnKeyPress(Shape shape) {
      scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override public void handle(KeyEvent event) {
    	//Combining the blocks to a shape
    	//Deciding what to do on user input
        switch (event.getCode()) {
          case RIGHT: 
        	  TetrisHolder.CheckRight(shape);
        	  break;
          case DOWN:  
        	  CheckDown(shape);
          	  break;
          case LEFT:  
        	  TetrisHolder.CheckLeft(shape);
        	  break;
          case UP:
        	  CheckTurn(shape);
        	  break;
          case SPACE:
        	  time = !time;
        }
      }
    });
  }
  /**
   * Checks if it is possible to turn the, 
   * if it is possible, it turns.
   * 
   * @param shape shape which gets checked
   */
  private void CheckTurn(Shape shape){
	  int f = shape.form;
	  Rectangle a = shape.a;
	  Rectangle b = shape.b;
	  Rectangle c = shape.c;
	  Rectangle d = shape.d;
	  switch(shape.getName()){
	    case "j":
	    	if(f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)){
	    		CheckRight(shape.a);
	    		CheckDown(shape.a);
	    		CheckDown(shape.c);
	    		CheckLeft(shape.c);
	    		CheckDown(shape.d);
	    		CheckDown(shape.d);
	    		CheckLeft(shape.d);
	    		CheckLeft(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)){
	    		CheckDown(shape.a);
	    		CheckLeft(shape.a);
	    		CheckLeft(shape.c);
	    		CheckUp(shape.c);
	    		CheckLeft(shape.d);
	    		CheckLeft(shape.d);
	    		CheckUp(shape.d);
	    		CheckUp(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)){
	    		CheckLeft(shape.a);
	    		CheckUp(shape.a);
	    		CheckUp(shape.c);
	    		CheckRight(shape.c);
	    		CheckUp(shape.d);
	    		CheckUp(shape.d);
	    		CheckRight(shape.d);
	    		CheckRight(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)){
	    		CheckUp(shape.a);
	    		CheckRight(shape.a);
	    		CheckRight(shape.c);
	    		CheckDown(shape.c);
	    		CheckRight(shape.d);
	    		CheckRight(shape.d);
	    		CheckDown(shape.d);
	    		CheckDown(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	break;
		case "l":
	    	if(f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)){
	    		CheckRight(shape.a);
	    		CheckDown(shape.a);
	    		CheckUp(shape.c);
	    		CheckRight(shape.c);
	    		CheckUp(shape.b);
	    		CheckUp(shape.b);
	    		CheckRight(shape.b);
	    		CheckRight(shape.b);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)){
	    		CheckDown(shape.a);
	    		CheckLeft(shape.a);
	    		CheckRight(shape.b);
	    		CheckRight(shape.b);
	    		CheckDown(shape.b);
	    		CheckDown(shape.b);
	    		CheckRight(shape.c);
	    		CheckDown(shape.c);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)){
	    		CheckLeft(shape.a);
	    		CheckUp(shape.a);
	    		CheckDown(shape.c);
	    		CheckLeft(shape.c);
	    		CheckDown(shape.b);
	    		CheckDown(shape.b);
	    		CheckLeft(shape.b);
	    		CheckLeft(shape.b);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)){
	    		CheckUp(shape.a);
	    		CheckRight(shape.a);
	    		CheckLeft(shape.b);
	    		CheckLeft(shape.b);
	    		CheckUp(shape.b);
	    		CheckUp(shape.b);
	    		CheckLeft(shape.c);
	    		CheckUp(shape.c);
	    		shape.changeForm();
				break;
	    	}
	    	break;
		case "o":
			break;
		case "s":
	    	if(f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)){
	    		CheckDown(shape.a);
	    		CheckLeft(shape.a);
	    		CheckLeft(shape.c);
	    		CheckUp(shape.c);
	    		CheckUp(shape.d);
	    		CheckUp(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)){
	    		CheckUp(shape.a);
	    		CheckRight(shape.a);
	    		CheckRight(shape.c);
	    		CheckDown(shape.c);
	    		CheckDown(shape.d);
	    		CheckDown(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)){
	    		CheckDown(shape.a);
	    		CheckLeft(shape.a);
	    		CheckLeft(shape.c);
	    		CheckUp(shape.c);
	    		CheckUp(shape.d);
	    		CheckUp(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)){
	    		CheckUp(shape.a);
	    		CheckRight(shape.a);
	    		CheckRight(shape.c);
	    		CheckDown(shape.c);
	    		CheckDown(shape.d);
	    		CheckDown(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	break;
		case "t":
	    	if(f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)){
	    		CheckUp(shape.a);
	    		CheckRight(shape.a);
	    		CheckDown(shape.d);
	    		CheckLeft(shape.d);
	    		CheckLeft(shape.c);
	    		CheckUp(shape.c);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)){
	    		CheckRight(shape.a);
	    		CheckDown(shape.a);
	    		CheckLeft(shape.d);
	    		CheckUp(shape.d);
	    		CheckUp(shape.c);
	    		CheckRight(shape.c);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)){
	    		CheckDown(shape.a);
	    		CheckLeft(shape.a);
	    		CheckUp(shape.d);
	    		CheckRight(shape.d);
	    		CheckRight(shape.c);
	    		CheckDown(shape.c);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)){
	    		CheckLeft(shape.a);
	    		CheckUp(shape.a);
	    		CheckRight(shape.d);
	    		CheckDown(shape.d);
	    		CheckDown(shape.c);
	    		CheckLeft(shape.c);
	    		shape.changeForm();
				break;
	    	}
	    	break;
		case "z":
	    	if(f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)){
	    		CheckUp(shape.b);
	    		CheckRight(shape.b);
	    		CheckLeft(shape.c);
	    		CheckUp(shape.c);
	    		CheckLeft(shape.d);
	    		CheckLeft(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)){
	    		CheckDown(shape.b);
	    		CheckLeft(shape.b);
	    		CheckRight(shape.c);
	    		CheckDown(shape.c);
	    		CheckRight(shape.d);
	    		CheckRight(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)){
	    		CheckUp(shape.b);
	    		CheckRight(shape.b);
	    		CheckLeft(shape.c);
	    		CheckUp(shape.c);
	    		CheckLeft(shape.d);
	    		CheckLeft(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)){
	    		CheckDown(shape.b);
	    		CheckLeft(shape.b);
	    		CheckRight(shape.c);
	    		CheckDown(shape.c);
	    		CheckRight(shape.d);
	    		CheckRight(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	break;
		case "i":
	    	if(f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)){
	    		CheckUp(shape.a);
	    		CheckUp(shape.a);
	    		CheckRight(shape.a);
	    		CheckRight(shape.a);
	    		CheckUp(shape.b);
	    		CheckRight(shape.b);
	    		CheckDown(shape.d);
	    		CheckLeft(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)){
	    		CheckDown(shape.a);
	    		CheckDown(shape.a);
	    		CheckLeft(shape.a);
	    		CheckLeft(shape.a);
	    		CheckDown(shape.b);
	    		CheckLeft(shape.b);
	    		CheckUp(shape.d);
	    		CheckRight(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)){
	    		CheckUp(shape.a);
	    		CheckUp(shape.a);
	    		CheckRight(shape.a);
	    		CheckRight(shape.a);
	    		CheckUp(shape.b);
	    		CheckRight(shape.b);
	    		CheckDown(shape.d);
	    		CheckLeft(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	if(f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)){
	    		CheckDown(shape.a);
	    		CheckDown(shape.a);
	    		CheckLeft(shape.a);
	    		CheckLeft(shape.a);
	    		CheckDown(shape.b);
	    		CheckLeft(shape.b);
	    		CheckUp(shape.d);
	    		CheckRight(shape.d);
	    		shape.changeForm();
				break;
	    	}
	    	break;
	  }
  }
  /**
   * Deletes rows if the row is full.
   * 
   * @param pane pane of the scene
   */
  private void DeleteRows(Pane pane){
	  ArrayList<Node> rects = new ArrayList<Node>();
	  ArrayList<Integer> lines = new ArrayList<Integer>();
	  ArrayList<Node> newrects = new ArrayList<Node>();
	  int full = 0;
	  //Finding which line is full
	  for(int i = 0; i < GRID[0].length; i++){
		  for(int j = 0; j < GRID.length; j++){
			  if(GRID[j][i] == 1)
				  full++;
		  }
		  if(full == GRID.length)
			  lines.add(i+lines.size());
		  full = 0;
	  }
	  //Deleting rows if any is full
	  if(lines.size() > 0)
		  do{
			  for(Node node: pane.getChildren()) {
				   if(node instanceof Rectangle)
				        rects.add(node);
				   }
			  score+= 100;
			  //Deleting the blocks on the full row
			  for(Node node: rects){
				  Rectangle a = (Rectangle)node;
				  if(a.getY() == lines.get(0)*SIZE){
					  GRID[(int)a.getX()/SIZE][(int)a.getY()/SIZE] = 0;
					  pane.getChildren().remove(node);
				  }
				  else
					  newrects.add(node);
			  }
			  //Added because it was causing problems when it was inside the iteration above.
			  for(Node node: newrects){
				  Rectangle a = (Rectangle)node;
				  if(a.getY() < lines.get(0)*SIZE){
					  GRID[(int)a.getX()/SIZE][(int)a.getY()/SIZE] = 0;
					  a.setY(a.getY() + SIZE);
					  GRID[(int)a.getX()/SIZE][(int)a.getY()/SIZE] = 1;
					  }
			  }
			  lines.remove(0);
			  rects.clear();
			  newrects.clear();
			  
		  } while(lines.size() > 0);
	  rects.clear();
	  for(Node node: pane.getChildren()) {
		   if(node instanceof Rectangle)
		        rects.add(node);
		   }
	  for(Node node: rects){
		  Rectangle a = (Rectangle)node;
		  try {
			  GRID[(int)a.getX()/SIZE][(int)a.getY()/SIZE] = 1;
		  } catch (ArrayIndexOutOfBoundsException e) {
		  }
	  }
	  rects.clear();
  }
  
  /**
   * Checks a single rectangle if it is possible to move down.
   * @param rect rectangle
   */
  private void CheckDown(Rectangle rect){
	  if(rect.getY() + MOVE_AMOUNT < YLIMIT)
		  rect.setY(rect.getY() + MOVE_AMOUNT);
  }
  
  /**
   * Checks a single rectangle if it is possible to move right.
   * @param rect rectangle
   */
  private void CheckRight(Rectangle rect){
	  if(rect.getX() + MOVE_AMOUNT <= XLIMIT - SIZE)
		  rect.setX(rect.getX() + MOVE_AMOUNT);
  }
 
  /**
   * Checks a single rectangle if it is possible to move left.
   * @param rect rectangle
   */
  private void CheckLeft(Rectangle rect){
	  if(rect.getX() - MOVE_AMOUNT >= 0)
		  rect.setX(rect.getX() - MOVE_AMOUNT);
  }
  
  /**
   * Checks a single rectangle if it is possible to move up.
   * @param rect rectangle
   */
  private void CheckUp(Rectangle rect){
	  if(rect.getY() - MOVE_AMOUNT > 0)
		  rect.setY(rect.getY() - MOVE_AMOUNT);
  }
  
  
  /**
   * Checks if it is possible to move the block down, 
   * if it is possible, it moves down, 
   * else, it sticks to the block or to the base and creates a new block.
   * 
   * @param shape shape which gets checked
   */
  private void CheckDown(Shape shape){
	  //Checking if down is full
	  if(shape.a.getY() == YLIMIT - SIZE || shape.b.getY() == YLIMIT - SIZE || shape.c.getY() == YLIMIT - SIZE || shape.d.getY() == YLIMIT - SIZE || checkA(shape) || checkB(shape) || checkC(shape) || checkD(shape)){
		  GRID[(int)shape.a.getX()/SIZE][(int)shape.a.getY()/SIZE] = 1;
		  GRID[(int)shape.b.getX()/SIZE][(int)shape.b.getY()/SIZE] = 1;
		  GRID[(int)shape.c.getX()/SIZE][(int)shape.c.getY()/SIZE] = 1;
		  GRID[(int)shape.d.getX()/SIZE][(int)shape.d.getY()/SIZE] = 1;
		  DeleteRows(group);
		  //Creating a new block and adding it to the scene
		  Shape a = nextShape;
		  nextShape = TetrisHolder.createRect();
		  object = a;
		  group.getChildren().addAll(a.a, a.b, a.c, a.d);
		  moveOnKeyPress(a);
		  }
	  //Moving one block down if down is not full
	  if(shape.a.getY() + MOVE_AMOUNT < YLIMIT && shape.b.getY() + MOVE_AMOUNT < YLIMIT && shape.c.getY() + MOVE_AMOUNT < YLIMIT && shape.d.getY() + MOVE_AMOUNT < YLIMIT){
		  int checka = GRID[(int)shape.a.getX()/SIZE][((int)shape.a.getY()/SIZE) + 1];
		  int checkb = GRID[(int)shape.b.getX()/SIZE][((int)shape.b.getY()/SIZE) + 1];
		  int checkc = GRID[(int)shape.c.getX()/SIZE][((int)shape.c.getY()/SIZE) + 1];
		  int checkd = GRID[(int)shape.d.getX()/SIZE][((int)shape.d.getY()/SIZE) + 1];
		  if(checka == 0 && checka == checkb && checkb == checkc && checkc == checkd){
			  shape.a.setY(shape.a.getY() + MOVE_AMOUNT);
			  shape.b.setY(shape.b.getY() + MOVE_AMOUNT);
			  shape.c.setY(shape.c.getY() + MOVE_AMOUNT);
			  shape.d.setY(shape.d.getY() + MOVE_AMOUNT);
			  }
		  }
	  }
  /**
   * Checks if it is possible to move the first block down.
   * 
   * @param shape shape which gets checked
   * @return true if it is possible to move the first block down
   */
  private boolean checkA(Shape shape){
	  return (GRID[(int)shape.a.getX()/SIZE][((int)shape.a.getY()/SIZE) + 1] == 1);
  }
  /**
   * Checks if it is possible to move the second block down.
   * 
   * @param shape shape which gets checked
   * @return true if it is possible to move the second block down
   */
  private boolean checkB(Shape shape){
	  return (GRID[(int)shape.b.getX()/SIZE][((int)shape.b.getY()/SIZE) + 1] == 1);
  }
  /**
   * Checks if it is possible to move the third block down.
   * 
   * @param shape shape which gets checked
   * @return true if it is possible to move the third block down
   */
  private boolean checkC(Shape shape){
	  return (GRID[(int)shape.c.getX()/SIZE][((int)shape.c.getY()/SIZE) + 1] == 1);
  }
  /**
   * Checks if it is possible to move the fourth block down.
   * 
   * @param shape shape which gets checked
   * @return true if it is possible to move the fourth block down
   */
  private boolean checkD(Shape shape){
	  return (GRID[(int)shape.d.getX()/SIZE][((int)shape.d.getY()/SIZE) + 1] == 1);
  }
  
  
  /**
   * cB is short for checkBlock, it checks if it is possible to turn the block
   * @param rect rectangle
   * @param x amount in x that is going to be changed
   * @param y amount in y that is going to be changed
   * @return if it is possible to turn the block
   */
  private boolean cB(Rectangle rect, int x, int y){
	  boolean xb = false;
	  boolean yb = false;
	  if(x >= 0)
		  xb = rect.getX() + x*MOVE_AMOUNT <= XLIMIT - SIZE;
	  if(x < 0)
		  xb = rect.getX() + x*MOVE_AMOUNT >= 0;
	  if(y >= 0)
		  yb = rect.getY() - y*MOVE_AMOUNT > 0;
	  if(y < 0)
		  yb = rect.getY() + y*MOVE_AMOUNT < YLIMIT;
	  return xb && yb && GRID[((int)rect.getX()/SIZE) + x][((int)rect.getY()/SIZE) - y] == 0;
  }
  
}