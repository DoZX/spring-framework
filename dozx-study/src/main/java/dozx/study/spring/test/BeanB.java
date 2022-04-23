package dozx.study.spring.test;

import dozx.study.spring.annotation.DoZXComponent;
import dozx.study.spring.annotation.DoZXScope;

@DoZXComponent(value = "beanB")
@DoZXScope(value = "prototype")
public class BeanB {
}
