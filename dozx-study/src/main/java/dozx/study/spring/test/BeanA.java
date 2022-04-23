package dozx.study.spring.test;

import dozx.study.spring.annotation.DoZXAutowired;
import dozx.study.spring.annotation.DoZXComponent;
import dozx.study.spring.annotation.DoZXScope;

@DoZXComponent(value = "beanA")
@DoZXScope()
public class BeanA {
	@DoZXAutowired
	private BeanB beanB;
}
