//Polymorphism, Override & Inheritance Example
public class Calculator extends Computer{
	String name;
	String batteryType;
	
	public Calculator(String name, String batteryType){
		super(name);
		this.batteryType = batteryType;
	}
	
	public String toString(){
		return super.toString() + " and has a "  + batteryType + " battery";
	}
	
	public int addition(int a, int b){
		return super.addition(a, b);
	}
	
	public int addition(int a, int b, int c){
		return super.addition(a, b, c);
	}
}