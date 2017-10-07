//Polymorphism, Override & Inheritance Example
public class Calculator extends Computer{ //Inheritance example
	String name;
	String batteryType;
	
	public Calculator(String name, String batteryType){
		super(name); //More inheritance
		this.batteryType = batteryType;
	}
	
	public String toString(){
		return super.toString() + " and has a "  + batteryType + " battery"; //Returning toString() method of Computer + the battery type
	}
	
	public int addition(int a, int b){
		return super.addition(a, b); //Returning addition(int a, int b) method of Computer
	}
	
	public int addition(int a, int b, int c){
		return super.addition(a, b, c); //Returning addition(int a, int b, int c) method of Computer
	}
}
