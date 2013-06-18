package cz.muni.fi.jboss.book.web.core;

import cz.muni.fi.jboss.book.web.LoginBean;
import cz.muni.fi.jboss.book.web.RegistrationBean;
import cz.muni.fi.jboss.book.web.ReservationBean;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: matejkobza
 * Date: 6/18/13
 * Time: 10:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebBeanFactory implements Serializable {

    /**
     * use this like WebBeanFactory.getBean("sessionScope.backingBean", BackingBean.class)
     * @param beanScope
     * @param cls
     * @return
     */
    public static Object getBean(String beanScope, Class<?> cls) {
        Object bean = WebApplication.getReference().getFacesBean(beanScope, cls);
        if (bean == null) {
            try {
                bean = cls.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
                return null;
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                return null;
            }
            WebApplication.getReference().setFacesBean(beanScope, bean);
        }
        return bean;
    }

    public static LoginBean getLoginBean() {
        return (LoginBean)getBean("sessionScope.loginBean", LoginBean.class);
    }

}
