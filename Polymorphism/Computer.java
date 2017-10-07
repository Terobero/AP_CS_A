public class Computer {
	String name;
	
	public Computer(String name){
		this.name = name;
	}
	
	public String toString(){
		return "This computer is called " + name; //Returning the name of the computer
	}
	
	public int addition(int a, int b){
		return a + b; //Returning the sum of a, b
	}
	
	public int addition(int a, int b, int c){
		return a + b + c; //Returning the sum of a, b, c
	}
}
