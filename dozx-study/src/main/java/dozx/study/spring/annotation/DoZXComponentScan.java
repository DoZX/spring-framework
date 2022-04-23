package dozx.study.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)  // 在Class上使用
@Retention(RetentionPolicy.RUNTIME)  // 运行时存在
public @interface DoZXComponentScan {
	String value() default "";  // 扫描路径
}