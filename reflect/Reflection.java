package com.subra.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
class M {
	void m1(){
			System.out.println("curr-method: " + new Throwable().getStackTrace()[0].getMethodName());			
		
		Object o2 = new Object(){}; // anonymous inner class of type object
		System.out.println(o2.getClass().getName());
		System.out.println(o2.getClass().getEnclosingMethod().getName());
		 
		Method m = new Object(){}.getClass().getEnclosingMethod();
		System.out.println(m.getName());
	}
	
	void m2(){
		System.out.println(this.getClass().getName()); 
		
		System.out.println("in... m2()" );
	}
}
*/

class X {

	private int m0(String s){
		System.out.println(this.getClass().getName()); 
		
		System.out.println("in... method=" + new Throwable().getStackTrace()[0].getMethodName() + " of class=" + this.getClass()  + " arg=" + s);
		return -2;
	}

	public static int mStatic(String s){		
		
		Class classType = X.class;
		System.out.println("in... method=" + new Throwable().getStackTrace()[0].getMethodName() + " of class=" + classType.getName()  + " arg=" + s);
		return 3;
	}

	
	public void m1(){
		
		Object o2 = new Object(){}; // anonymous inner class of type NOT object (type is com.subra.X$1
		System.out.println(o2.getClass().getName());		
		 
		Method m = new Object(){}.getClass().getEnclosingMethod();
		System.out.println(m.getName());
	}
	
	public void m2(){
		System.out.println(this.getClass().getName()); 
		
		System.out.println("in... method=" + new Throwable().getStackTrace()[0].getMethodName() + " of class=" + this.getClass() );
	}
	
	public int m3(String s){
		System.out.println(this.getClass().getName()); 
		
		System.out.println("in... method=" + new Throwable().getStackTrace()[0].getMethodName() + " of class=" + this.getClass()  + " arg=" + s);
		return 17;
	}
}
public class Reflection {
	
	public static void main(String[] args){
		System.out.println("start");
		/*
		M a = new M();
		a.m1();
		System.out.println("--------")
		*/
		//publicMethodInvoke();
		//noArgsPublicMethodInvoke();
		//privateMethodInvoke();
		invokeStaticMehtod();
	}
	
	public static void invokeStaticMehtod(){
		X b = new X();
		//b.m0("aa");
		//System.exit(1);
		
		Method methodtoCall = null;
		try {			
			//methodtoCall = b.getClass().getMethod("m0", String.class); //works private access?
			//methodtoCall = b.getClass().getDeclaredMethod("mStatic", String.class); works for static method //getClass().getMethod("m0", String.class); //works private access?
			methodtoCall = X.class.getDeclaredMethod("mStatic", String.class);  //getClass().getMethod("m0", String.class); //works private access?
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---- called reflectively -----");
		callReflectively(null, methodtoCall, new String[]{"testStatic"}); //static method target is null
		
	}
	
	public static void privateMethodInvoke(){
		X b = new X();
		//b.m0("aa");
		//System.exit(1);
		
		Method methodtoCall = null;
		try {			
			methodtoCall = b.getClass().getDeclaredMethod("m0", String.class);  //getClass().getMethod("m0", String.class); //does not work for private.
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---- called reflectively -----");
		callReflectively(b, methodtoCall, new String[]{"testing"});
		
	}

	
	public static void noArgsPublicMethodInvoke(){
		X b = new X();		
		//b.m0("gg");
		//System.exit(1);
		Method methodtoCall = null;
		try {
			methodtoCall = b.getClass().getMethod("m1", null); //no arg so null in varargs classtype
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---- called reflectively -----");
		callReflectively(b, methodtoCall, null); //no argument
		
	}

	public static void publicMethodInvoke(){
		X b = new X();
		//b.m2();
		
		Method methodtoCall = null;
		try {
			// methodtoCall = X.class.getMethod("m3", String.class); works public access
			methodtoCall = b.getClass().getMethod("m3", String.class); //works public access
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("---- called reflectively -----");
		callReflectively(b, methodtoCall, new String[]{"testing"});
		
	}
	public static void callReflectively(Object target, Method method, Object[] args ){
		System.out.println("inside ... callReflectively() ");
		if(target != null) System.out.println(target.getClass().getName());
		System.out.println(method.getName());
		method.setAccessible(true); //CRITICAL LINE allows calling non-public method 
		Object ret = -1;
		try {
			ret = method.invoke(target, args); //args is null for noargs
			
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Integer yf = (Integer) ret;
		System.out.println("yf=" + yf);
	}
}
