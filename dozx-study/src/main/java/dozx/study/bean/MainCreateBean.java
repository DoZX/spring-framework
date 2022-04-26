package dozx.study.bean;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

public class MainCreateBean {
	public static void main(String[] args) {
		System.out.println("==========  START  ==========");
		// [Spring-Read] 创建Bean_1
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);

		// getBean
		Map<String, BeanA> beansOfType = applicationContext.getBeansOfType(BeanA.class);
		BeanA beanA = (BeanA) applicationContext.getBean("beanA");
		beanA.getBeanBObject();

		// AOP
		BeanC beanC = (BeanC) applicationContext.getBean("beanC");
		beanC.aspectMethod();

		System.out.println("========== SUCCESS ==========");
	}
}

@Configuration  // Configuration注解生产代理对象（super.Method()去调用方法），最终是Spring创建Bean，而不是Java创建对象
@ComponentScan("dozx.study.bean")
@EnableAspectJAutoProxy
class Config {

}

// 普通Bean
@Component
class BeanA {
	@Autowired
	private BeanB beanB;
	@Autowired
	private BeanABeforeInstantiation beanABeforeInstantiation;

	public void getBeanBObject() {
		System.out.println(beanB);
	}

	@PostConstruct  // BEAN-CASE1: Spring初始化前会调用
	public void beforeInstantiation() {
		System.out.println("BeanA 初始化前, before()");
	}
}

// 初始化时，Spring对Bean的操作
@Component
class BeanABeforeInstantiation implements InitializingBean {
	@PostConstruct  // BEAN-CASE1: Spring初始化前会调用
	public void beanABeforeInstantiation() {
		System.out.println("BeanABeforeInstantiation 初始化前, BeanABeforeInstantiation()");
	}

	// BEAN-CASE1: 实现InitializingBean接口后, Spring初始化时，会先调用afterPropertiesSet方法
	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("实现InitializingBean接口后，Spring初始化时，会先调用afterPropertiesSet方法");
	}
}

// 普通Bean
@Service
class BeanB {

}

@Service
class BeanC {
	@Autowired
	private BeanB beanB;

	public void aspectMethod() {
		System.out.println(beanB);
	}
}

// 对BeanC做切面
@Aspect
@Component
class AspectBeanC {
	@Before("execution(public void dozx.study.bean.BeanC.aspectMethod())")
	public void aspectBeanCBeforeMethod() {
		System.out.println("AspectBeanC aspectBeanCBeforeMethod()");
	}
}

