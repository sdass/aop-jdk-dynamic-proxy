1. On Reflection.java inside method callReflectively(...) the dynamic call to a method as below
is the  foundation for both Jdk Dynamic Proxy and Apache Cglib


		Object ret = -1;
		try {
			ret = method.invoke(target, args); //args is null for noargs
			
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			
----------------------------------------------------------------------
2.AOPjdk.java is the harness class for Jdk dynamic proxy call.
(a)
Initial setup consists of 5 items (including main() mehtod in class AOPjdk).
2 interfaces (IContract, Service) and class M and Y which implements one of the interface. 
(b)
Fundamental line to create dynamic proxy (one sample below):
System.out.println("-------------------2nd below---------------------------");
IContract z = new M();
IContract proxyServiceObj2nd = (IContract) Proxy.newProxyInstance( AOPjdk.class.getClassLoader(), new Class[] { IContract.class }, new DynamicInvocationAdvice(z));

(c) Last argument on the above call is the InvocationHandler object. Its no-arg constructor and overridden invoke method. <-- here interception takes place before|after method call. dynamic method call invoked on target object initialized during constructor call.
--------------------------------------------------