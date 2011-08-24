package proxy1.impl;

public class AnimalWrapper implements Animal {
	private Animal animal;

	// ʹ�ù��췽����װAnimal�Ľӿڣ��������е�Animalʵ���඼���Ա����Wrapper��װ��
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
