package cz.muni.fi.jboss.book.web.core;

import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.ResourceBundle;

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

    public Object getFacesBean(String beanScopeAndName, Class<?> cls) {
        Application app = getApplication();
        ValueExpression ve = app.getExpressionFactory().createValueExpression(
                getFacesContext().getELContext(), "#{" + beanScopeAndName + "}", cls);
        return ve.getValue(getFacesContext().getELContext());
    }

    private FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public void addInfoMessage(String title, String message) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, title, message));
    }

    public void addWarnMessage(String title, String message) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, title, message));
    }

    public void addErrorMessage(String title, String message) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, title, message));
    }

    public void addFatalMessage(String title, String message) {
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, title, message));
    }

    public void addInfoMessage(String message) {
        addInfoMessage(null, message);
    }

    public void addWarnMessage(String message) {
        addWarnMessage(null, message);
    }

    public void addErrorMessage(String message) {
        addErrorMessage(null, message);
    }

    public void addFatalMessage(String message) {
        addFatalMessage(null, message);
    }

    /**
     *
     * @return return current resource bundle
     */
    public ResourceBundle getResourceBundle() {
        return WebApplication.getReference().getResourceBundle();
    }

}
