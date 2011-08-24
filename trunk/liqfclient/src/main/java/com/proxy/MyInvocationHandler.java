package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.proxy.impl.Monkey;


public class MyInvocationHandler implements InvocationHandler {
	private Object obj;

	public MyInvocationHandler(Object obj) {
		this.obj = obj;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		System.out.println("Invoke method Before!");
		Object returnObject = method.invoke(obj, args);
		System.out.println("Invoke method After!");
		return returnObject;
	}

	/**
	 * ≤‚ ‘∫Ø ˝
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 */
	public static void main(String[] args) throws InterruptedException,
			IllegalArgumentException, SecurityException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		Class<?> proxyClass = Proxy.getProxyClass(
				Monkey.class.getClassLoader(), Monkey.class.getInterfaces());
		Object proxy = proxyClass.getConstructor(
				new Class[] { InvocationHandler.class }).newInstance(
				new MyInvocationHandler(new Monkey()));
		
		Mammal mammal = (Mammal) proxy;
		mammal.eat("œ„Ω∂");
		mammal.type();
		
		
		Primate primate = (Primate) proxy;
		primate.think();
	}
}
