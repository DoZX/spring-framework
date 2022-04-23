package dozx.study.spring.test;

import dozx.study.spring.annotation.DoZXComponent;
import dozx.study.spring.core.DoZXBeanPostProcessor;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;

import java.lang.reflect.Method;

@DoZXComponent(value = "beanPostProcessorAOP")
public class BeanPostProcessorAOP implements DoZXBeanPostProcessor {
	@Override
	public Object postProcessorBeforeInitialization(String beanName, Object bean) {
		System.out.println("BeanPostProcessorAOP: postProcessorBeforeInitialization");
		return bean;
	}

	@Override
	public Object postProcessorAfterInitialization(String beanName, Object bean) {
		System.out.println("BeanPostProcessorAOP: postProcessorAfterInitialization");
		if (bean instanceof BeanC) {
			Object proxyInstance = Proxy.newProxyInstance(BeanPostProcessorAOP.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					System.out.println("BeanPostProcessorAOP.postProcessorAfterInitialization():invoke");
					return method.invoke(bean, args);
				}
			});
			return proxyInstance;
		}
		return bean;
	}
}
