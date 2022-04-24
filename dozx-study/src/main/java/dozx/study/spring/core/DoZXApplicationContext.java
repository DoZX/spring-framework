package dozx.study.spring.core;

import dozx.study.spring.annotation.DoZXAutowired;
import dozx.study.spring.annotation.DoZXComponent;
import dozx.study.spring.annotation.DoZXComponentScan;
import dozx.study.spring.annotation.DoZXScope;

import java.beans.Introspector;
import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring 容器
 */
public class DoZXApplicationContext {
	// Bean的元信息
	private Map<String, DoZXBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
	// Bean 一级缓存
	private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();

	// BeanPostProcessor
	private List<DoZXBeanPostProcessor> beanPostProcessorList = new ArrayList<>();

	private Class<?> configClass;

	public DoZXApplicationContext(Class<?> configClass) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		this.configClass = configClass;

		// 扫描class元数据
		if (configClass.isAnnotationPresent(DoZXComponentScan.class)) {
			DoZXComponentScan componentScan = configClass.getAnnotation(DoZXComponentScan.class);
			String scanPath = componentScan.value();  // 配置的扫描路径
			scanPath = scanPath.replace(".", "/");

			ClassLoader classLoader = DoZXApplicationContext.class.getClassLoader();
			URL classResourcePath = classLoader.getResource(scanPath);  // build后class文件所在的路径

			assert classResourcePath != null;
			File classResourceFile = new File(classResourcePath.getFile());
			if (classResourceFile.isDirectory()) {
				File[] rootFiles = classResourceFile.listFiles();  // 代码根路径下所有文件对象
				for (File rootFile : rootFiles) {
					String rootFileAbsolutePath = rootFile.getAbsolutePath();  // 文件绝对路径
					if (rootFileAbsolutePath.endsWith(".class")) {
						// 获取className
						String className = rootFileAbsolutePath.substring(rootFileAbsolutePath.indexOf((System.getProperty("os.name").startsWith("Windows")) ? "dozx\\study" : "dozx/study"), rootFileAbsolutePath.indexOf(".class"));
						className = className.replace((System.getProperty("os.name").startsWith("Windows")) ? "\\" : "/", ".");

						// 加载类，获取class元信息
						Class<?> clazz = classLoader.loadClass(className);
						if (clazz.isAnnotationPresent(DoZXComponent.class)) {
							// class是否实现了DoZXBeanPostProcessor
							if (DoZXBeanPostProcessor.class.isAssignableFrom(clazz)) {
								DoZXBeanPostProcessor postProcessor = (DoZXBeanPostProcessor) clazz.newInstance();
								this.beanPostProcessorList.add(postProcessor);
							}

							// 存Bean信息
							String beanName = clazz.getAnnotation(DoZXComponent.class).value();
							// beanName = Introspector.decapitalize(className);
							String scope = "singleton";
							if (clazz.isAnnotationPresent(DoZXScope.class)) {
								scope = clazz.getAnnotation(DoZXScope.class).value();
							}
							// 存Bean对应的BeanDefinition对象
							this.beanDefinitionMap.put(beanName, new DoZXBeanDefinition(clazz, scope));
						}
					}  // 非class结尾的File对象
				}
			}
		}

		// 初始化单例Bean
		this.beanDefinitionMap.forEach((beanName, beanDefinition) -> {
			if ("singleton".equals(beanDefinition.getScope())) {
				Object bean = this.createBean(beanName, beanDefinition);
				this.singletonObjects.put(beanName, bean);
			}
		});

	}

	private Object createBean(String beanName, DoZXBeanDefinition beanDefinition) {
		Object beanInstance = null;
		try {
			Class<?> clazz = beanDefinition.getType();
			// 实例化
			beanInstance = clazz.getConstructor().newInstance();

			// IOC (注解)
			for (Field field : clazz.getDeclaredFields()) {
				if (field.isAnnotationPresent(DoZXAutowired.class)) {
					field.setAccessible(Boolean.TRUE);
					field.set(beanInstance, this.getBean(field.getName()));
				}
			}

			// Aware回调 (Bean初始化也是类似的写法)
			if (beanInstance instanceof DoZXBeanNameAware) {
				((DoZXBeanNameAware) beanInstance).setBeanName(beanName);
			}

			// 初始化前
			for (DoZXBeanPostProcessor beanPostProcessor : this.beanPostProcessorList) {
				beanInstance = beanPostProcessor.postProcessorBeforeInitialization(beanName, beanInstance);
			}

			// 初始化 TODO

			// 初始化后
			for (DoZXBeanPostProcessor beanPostProcessor : this.beanPostProcessorList) {
				// AOP
				beanInstance = beanPostProcessor.postProcessorAfterInitialization(beanName, beanInstance);
			}




		} catch (Exception e) {
			e.printStackTrace();
		}
		return beanInstance;
	}

	public Object getBean(String beanName) throws Exception {
		DoZXBeanDefinition beanDefinition = this.beanDefinitionMap.get(beanName);
		if (beanDefinition == null)
			throw new NullPointerException("beanName DoZXBeanDefinition is Null");

		String scope = beanDefinition.getScope();
		if ("singleton".equals(scope)) {
			// 单例
			Object bean = this.singletonObjects.get(beanName);
			if (bean == null) {
				bean = this.createBean(beanName, beanDefinition);
				this.singletonObjects.put(beanName, bean);
			}
			return bean;
		} else if ("prototype".equals(scope)) {
			// 多例
			return this.createBean(beanName, beanDefinition);
		} else {
			throw new Exception("prototype is error");
		}
	}
}
