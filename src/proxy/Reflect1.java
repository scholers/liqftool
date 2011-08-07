package proxy;

public class Reflect1 {

	protected Reflect1(String name) {
		System.out.println(name);
	}

	public void f() {
		System.out.println("invoke f");
	}

	String mf(int j, Object... args) {
		System.out.println("invoke mf");
		return String.valueOf(j + args.length);
	}

}
