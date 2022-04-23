package dozx.study.spring.test;

import dozx.study.spring.annotation.DoZXComponentScan;
import dozx.study.spring.core.DoZXApplicationContext;

public class Main {
	public static void main(String[] args) throws Exception {
		System.out.println("==========  START  ==========");
		DoZXApplicationContext doZXApplicationContext = new DoZXApplicationContext(Config.class);

//		System.out.println("beanA");
//		System.out.println(doZXApplicationContext.getBean("beanA"));
//		System.out.println(doZXApplicationContext.getBean("beanA"));
//		System.out.println(doZXApplicationContext.getBean("beanA"));
//
//		System.out.println("beanB");
//		System.out.println(doZXApplicationContext.getBean("beanB"));
//		System.out.println(doZXApplicationContext.getBean("beanB"));
//		System.out.println(doZXApplicationContext.getBean("beanB"));
//
//		System.out.println("beanC");
//		System.out.println(doZXApplicationContext.getBean("beanC"));

		BeanA beanA = (BeanA) doZXApplicationContext.getBean("beanA");

		// 代理
		BeanCInterface beanC = (BeanCInterface) doZXApplicationContext.getBean("beanC");

		System.out.println("========== SUCCESS ==========");
	}
}

@DoZXComponentScan("dozx.study.spring.test")
class Config {
}