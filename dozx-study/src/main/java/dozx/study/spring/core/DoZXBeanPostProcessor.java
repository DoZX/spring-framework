package dozx.study.spring.core;

// 实现类需要交给 Spring 管理
public interface DoZXBeanPostProcessor {

	Object postProcessorBeforeInitialization(String beanName, Object bean);
	Object postProcessorAfterInitialization(String beanName, Object bean);

}
