package proxy1.impl;

public class AnimalWrapper implements Animal {
	private Animal animal;

	// 使用构造方法包装Animal的接口，这样所有的Animal实现类都可以被这个Wrapper包装。
	public AnimalWrapper(Animal animal) {
		this.animal = animal;
	}

	public void eat(String food) {
		System.out.println("+++Wrapped Before!+++");
		animal.eat(food);
		System.out.println("+++Wrapped After!+++");
	}


	public String type() {
		System.out.println("---Wrapped Before!---");
		String type = animal.type();
		System.out.println("---Wrapped After!---");
		return type;
	}
}
