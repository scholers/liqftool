package thread;

public class TestSecrity {
	private static TestSecrity test = null;
	public static synchronized TestSecrity getInstance() {
		if(test == null) {
			System.out.println("111111111");
			test = new TestSecrity();
		}
		return test;
	}
	
	private TestSecrity() {
		
	}
	/*
	private long count = 0;

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
	
	
	public void printTest() {
		System.out.println("this.count======" + this.count);
	}
	*/
	

	public long printTest(long count) {
		count += 100L;
		System.out.println("this.count2======" + count);
		return count;
	}
	
}
