package dozx.study.spring.core;

public class DoZXBeanDefinition {

	private Class<?> type;
	private String scope;

	public DoZXBeanDefinition() {}

	public DoZXBeanDefinition(Class<?> type, String scope) {
		this.type = type;
		this.scope = scope;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}
