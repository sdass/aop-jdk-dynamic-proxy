package com.subra.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface IContract{
	void m1();
}

interface Service {
	public void m1();
	public int m3(String s);
}


class M implements IContract{
	public void m1() {
		System.out.println("curr-method: "
				+ new Throwable().getStackTrace()[0].getMethodName()
				+ " classname=" + this.getClass().getName());
	}

	void m2() {
		System.out.println(this.getClass().getName());

		System.out.println("in... m2()");
	}
}


class Y implements Service {

	public void m1() {

		Object o2 = new Object() {
		}; // anonymous inner class of type NOT object (type is com.subra.X$1
		System.out.println(o2.getClass().getName());

		Method m = new Object() {
		}.getClass().getEnclosingMethod();
		System.out.println(m.getName());
	}

	public int m3(String s) {
		System.out.println(this.getClass().getName());

		System.out.println("in... method="
				+ new Throwable().getStackTrace()[0].getMethodName()
				+ " of class=" + this.getClass() + " arg=" + s);
		return 17;
	}
}

public class AOPjdk {

	public static void main(String[] args) {
		System.out.println("AOPjdk");
		//externallyCreatingProxy();
		getProxyPreparedForYou();
		

	}
	public static void getProxyPreparedForYou(){
		Service y = new Y();
		IContract z = new M();
		//https://docs.oracle.com/javase/8/docs/technotes/guides/reflection/proxy.html
		Service servObj = (Service) DynamicInvocationAdvice.newProxyInstance(y);
		Integer ret = servObj.m3("hi");
		System.out.println("ret=" + ret);
		IContract contractObj = (IContract) DynamicInvocationAdvice.newProxyInstance(z);
		contractObj.m1();
		
		
	}
	public static void externallyCreatingProxy(){
		Service y = new Y();
	
	InvocationHandler handler =  new DynamicInvocationAdvice(y); //critical line

	Service proxyServiceObj = (Service) Proxy.newProxyInstance( AOPjdk.class.getClassLoader(), new Class[] { Service.class }, handler);

	Integer i =  proxyServiceObj.m3("first-testing");
	System.out.println("returned=" + i);
	System.out.println("=-=-=-=-==-=-=-");
	proxyServiceObj.m1();
	System.out.println("-------------------2nd below---------------------------");
	IContract z = new M();
	
	IContract proxyServiceObj2nd = (IContract) Proxy.newProxyInstance( AOPjdk.class.getClassLoader(), new Class[] { IContract.class }, new DynamicInvocationAdvice(z));
	proxyServiceObj2nd.m1();
	System.out.println("-------------------3rd below---------------------------");
	//Service proxyServiceObj3rd = (Service) Proxy.newProxyInstance( AOPjdk.class.getClassLoader(), new Class[] { Service.class }, new DynamicInvocationAdvice(y));
	//proxyServiceObj3rd.m1();
	}
}
