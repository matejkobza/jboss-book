package cz.muni.fi.jboss.book.web.core;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 * Created with IntelliJ IDEA.
 * User: matejkobza
 * Date: 6/18/13
 * Time: 10:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class WebApplication {


    static WebApplication ref;

    protected WebApplication() {
    }

    public static WebApplication getReference() {
        if (ref == null)
            ref = new WebApplication();
        return ref;
    }

    public Application getApplication() {
        return getFacesContext().getApplication();
    }

    public void setFacesBean(String beanScopeAndName, Object value) {
        Application app = getApplication();
        ValueExpression ve = app.getExpressionFactory().createValueExpression(
                getFacesContext().getELContext(), "#{" + beanScopeAndName + "}",
                value.getClass());
        ve.setValue(getFacesContext().getELContext(), value);
    }

    public void setFacesBeanNull(String beanScopeAndName, Object value) {
        Application app = getApplication();
        ValueExpression ve = app.getExpressionFactory().createValueExpression(
                getFacesContext().getELContext(), "#{" + beanScopeAndName + "}",
                value.getClass());
        ve.setValue(getFacesContext().getELContext(), null);
    }

    public Object getFacesBean(String beanScopeAndName, Class<?> cls) {
        Application app = getApplication();
        ValueExpression ve = app.getExpressionFactory().createValueExpression(
                getFacesContext().getELContext(), "#{" + beanScopeAndName + "}", cls);
        return ve.getValue(getFacesContext().getELContext());
    }

    public Object getFacesBean(String beanScopeAndName, Class<?> cls,
                               FacesContext facesContext) {
        Application app = facesContext.getApplication();
        ValueExpression ve = app.getExpressionFactory().createValueExpression(
                facesContext.getELContext(), "#{" + beanScopeAndName + "}", cls);
        return ve.getValue(facesContext.getELContext());
    }

    private FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

}
