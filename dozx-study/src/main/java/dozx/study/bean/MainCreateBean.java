package dozx.study.bean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

public class MainCreateBean {
	public static void main(String[] args) {
		System.out.println("==========  START  ==========");
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
		Map<String, BeanA> beansOfType = applicationContext.getBeansOfType(BeanA.class);
		BeanA beanA = (BeanA) applicationContext.getBean("beanA");
		beanA.getBeanBObject();
		System.out.println("========== SUCCESS ==========");
	}
}

@ComponentScan("dozx.study.bean")
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
	public void BeanABeforeInstantiation() {
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

// 切面Bean
class AspectBeanC {

}

