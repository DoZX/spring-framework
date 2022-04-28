package dozx.study.plugin;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * 这个class会生成两个Bean对象
 * 	一个是：&doZXFactoryBean
 * 	另一个是：getObject() 方法返回的Bean
 * 	mybatis-spring就是利用这个机制，生成Mapper的代理对象
 */
@Component
public class DoZXFactoryBean implements FactoryBean {

	// 返回一个Bean对象
	@Override
	public Object getObject() throws Exception {
		return null;
	}

	// Bean对象的类型
	@Override
	public Class<?> getObjectType() {
		return null;
	}

	// 是否是单例
	@Override
	public boolean isSingleton() {
		return FactoryBean.super.isSingleton();
	}
}
