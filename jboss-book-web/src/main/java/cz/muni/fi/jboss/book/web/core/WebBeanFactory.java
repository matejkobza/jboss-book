package cz.muni.fi.jboss.book.web.core;

import cz.muni.fi.jboss.book.web.controller.IdentityBean;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean
@ApplicationScoped
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

    public static IdentityBean getIdentityBean() {
        return (IdentityBean)getBean("sessionScope.identityBean", IdentityBean.class);
    }



}
