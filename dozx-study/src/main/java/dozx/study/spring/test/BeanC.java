package dozx.study.spring.test;

import dozx.study.spring.annotation.DoZXComponent;

@DoZXComponent(value = "beanC")
public class BeanC implements BeanCInterface {

	@Override
	public void echo() {
		System.out.println("BeanCInterface BeanC.echo()");
	}
}
