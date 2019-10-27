package com.subra.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicInvocationAdvice implements InvocationHandler {

	private Object anyObject;
		
	public DynamicInvocationAdvice(Object anyObject) { //make it private
		super();
		this.anyObject = anyObject;
	}
	
	public static Object newProxyInstance(Object actual){
		
		return Proxy.newProxyInstance(actual.getClass().getClassLoader(), actual.getClass().getInterfaces(), new DynamicInvocationAdvice(actual));		
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		System.out.println("BEFORE: AOP applied DynamicInvocationAdvice: invoke() called.");
		Object result = method.invoke(anyObject, args);
		System.out.println("AFTER: AOP applied DynamicInvocationAdvice: invoke() called.");
		return result;
	}

}
