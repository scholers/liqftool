package proxy1.impl;

/**
 * 
 * @author jill
 *
 */
public class Test {
	
	public void test1() {
		AnimalWrapper aw = new AnimalWrapper(new Monkey());
		aw.eat("œ„Ω∂");
		aw.type();
	}
	
	public void test2() {
		Monkey aw = new Monkey();
		aw.eat("œ„Ω∂");
		aw.type();
	}
	
	public static void main(String[] args) throws Exception {
		Test test = new Test();
		//test.test2();
		
		test.test1();
	}
}
