public class Main {

	public static void main(String[] args) {
		Computer comp = new Computer("comp"); //Creating a computer object
		Computer calc = new Calculator("calc", "AAA"); //Creating a calculator object
		System.out.println(comp.toString() + ".\n" + calc.toString() + ".");
		System.out.println("Computer can do addition: 1 + 2 = " + comp.addition(1, 2) + " / 1 + 2 + 3 = " + comp.addition(1, 2, 3));
		System.out.println("Calculator can also do addition: 1 + 2 = " + calc.addition(1, 2) + " / 1 + 2 + 3 = " + calc.addition(1, 2, 3));
	}
}
