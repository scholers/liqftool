import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class CopyBean {
	public static Object wrap(Object actionObj, Class OperatorAction)
			throws Exception, InstantiationException {
		Object o = OperatorAction.newInstance();
		BeanInfo beanInfo = Introspector.getBeanInfo(actionObj.getClass());
		PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
		Method get = null;
		Method set = null;
		for (int i = 0; i < properties.length; i++) {
			try {
				get = properties[i].getReadMethod();
				set = o.getClass().getMethod(
						properties[i].getWriteMethod().getName(),
						new Class[] { properties[i].getPropertyType() });
			} catch (Throwable a) {
				continue;
			}
			if (set != null && get != null) {
				set.invoke(o, new Object[] { get.invoke(actionObj,
						new Object[] {}) });
				set = null;
				get = null;
			}
		}
		return o;
	}
}
