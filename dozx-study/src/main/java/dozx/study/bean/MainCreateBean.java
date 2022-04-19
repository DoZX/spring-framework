package dozx.study.bean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainCreateBean {
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainCreateBean.class);
		Object bean = applicationContext.getBean("mainCreateBean");
		System.out.println("SUCCESS");
	}
}
