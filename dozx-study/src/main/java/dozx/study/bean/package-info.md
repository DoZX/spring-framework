
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


## CASE
- BEAN-CASE1: 初始化前调用方法进行对象初始化赋值
- BEAN-CASE2: 初始化后调用方法