
## Bean
> Bean 创建过程
1. 加载类
2. 调用无参数构造方法
   1. 首先找无参构造进行初始化
   2. 没有无参构造再找有参构造
   3. 如果有多个有参构造，需要增加注解（@Autowired），让Spring知道使用哪个构造方法
3. 得到对象
4. 依赖注入
5. 初始化前
6. 初始化
7. 初始化后(AOP)
8. 代理对象(父子类形式生成代理，一种直接调用super.method()，另一种调用父类对象.method())
9. 存到Map单例池，Map<BeanName, Instantiation>
10. Bean对象


> Bean的实例化：创建对象  
> Bean的初始化：初始化Bean实例属性，方法

> 三级缓存解决循环依赖  
> 场景：Service1, Service2循环依赖  
> 一级缓存：singletonObjects（完整的Bean对象）  
> 二级缓存：earlySingletonObjects（缓存临时Bean对象，这个不存在就去三级缓存中获取）  
> 三级缓存：singletonFactories（缓存类原始数据，普通对象(Lambdas)）  
Bean创建过程（三级缓存解决循环依赖）：  
1. 创建S1普通对象（并存放到三级缓存Map<beanName, Lambdas>）
2. 填充S2属性
   1. 先去一级缓存中去找S2的Bean对象
   2. 找不到就创建S2的Bean对象
      1. 创建S2普通对象（并存放到三级缓存Map<beanName, Lambdas>）
      2. 填充S1属性
         1. 先去一级缓存中去找S1的Bean对象
         2. 发现S1的Bean对象正在创建（出现循环依赖）
         3. 去二级缓存中找S1的Bean对象（这个对象可能是普通对象也可能是AOP代理对象）
         4. 二级缓存中没有，则从三级缓存中获取S1的Bean对象（不完整Bean对象）
         5. 执行三级缓存中的Lambdas获取到普通对象或者是代理对象，并存在到二级缓存中
         6. 将二级缓存中的Bean对象，赋值给S1
      3. 填充其它属性，及其它初始化操作（判断AOP等）
      4. 将S2的Bean对象放到单例池中
3. 填充其它属性
4. 其它初始化操作
5. 判断是否需要初始化后（AOP）操作（）
6. 将二级缓存中S1的Bean对象放到一级缓存中去
7. 放入单例池中

循环依赖需要使用@Async时，需要增加@Lazy
带有@Lazy的属性，并不会直接去创建普通对象，而是创建代理对象去赋值给属性
@Lazy在调用时才会去Spring容器中找Bean

- 初始化过程（只记录Bean对象创建的链路）
1. org.springframework.context.annotation.AnnotationConfigApplicationContext(Class<?>... componentClasses)
2. org.springframework.context.support.AbstractApplicationContext:refresh()
3. org.springframework.context.support.AbstractApplicationContext:finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory)
4. org.springframework.beans.factory.support.DefaultListableBeanFactory:preInstantiateSingletons()
5. org.springframework.beans.factory.support.AbstractBeanFactory:getBean(String name)
6. org.springframework.beans.factory.support.AbstractBeanFactory:doGetBean(String name, @Nullable Class<T> requiredType, @Nullable Object[] args, boolean typeCheckOnly)
7. org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory:createBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
8. org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory:doCreateBean(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
9. org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory:createBeanInstance(String beanName, RootBeanDefinition mbd, @Nullable Object[] args)
10. org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory:instantiateBean(String beanName, RootBeanDefinition mbd)
11. org.springframework.beans.factory.support.SimpleInstantiationStrategy:instantiate(RootBeanDefinition bd, @Nullable String beanName, BeanFactory owner)
12. org.springframework.beans.BeanUtils:instantiateClass(Constructor<T> ctor, Object... args)
13. java.lang.reflect.Constructor:newInstance(Object ... initargs)
14. sun.reflect.DelegatingConstructorAccessorImpl:newInstance(Object[] args)
15. sun.reflect.NativeConstructorAccessorImpl:newInstance(Object[] args)
16. sun.reflect.NativeConstructorAccessorImpl:private static native Object newInstance0(Constructor<?> c, Object[] args) throws InstantiationException, IllegalArgumentException, InvocationTargetException;
