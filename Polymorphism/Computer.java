public class Computer {
	String name;
	
	public Computer(String name){
		this.name = name;
	}
	
	public String toString(){
		return "This computer is called " + name;
	}
	
	public int addition(int a, int b){
		return a + b;
	}
	
	public int addition(int a, int b, int c){
		return a + b + c;
	}
}