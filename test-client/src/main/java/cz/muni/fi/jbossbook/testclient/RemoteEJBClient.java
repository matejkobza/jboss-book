package cz.muni.fi.jbossbook.testclient;

import cz.muni.fi.jboss.book.ejb.manager.BookManager;
import cz.muni.fi.jboss.book.ejb.manager.BookManagerImpl;
import cz.muni.fi.jboss.book.persistence.entity.Author;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import cz.muni.fi.jboss.book.persistence.entity.Book;
 
/**
 * A sample program which acts a remote client for a EJB deployed on AS7 server.
 * This program shows how to lookup stateful and stateless beans via JNDI and then invoke on them
 *
 * @author Jaikiran Pai
 */
public class RemoteEJBClient {
 
    public static void main(String[] args) throws Exception {
        // Invoke a stateless bean
        invokeStatelessBean();
 
        // Invoke a stateful bean
        //invokeStatefulBean();
    }
 
    /**
     * Looks up a stateless bean and invokes on it
     *
     * @throws NamingException
     */
    private static void invokeStatelessBean() throws NamingException {
        // Let's lookup the remote book manager
        final BookManager bookManager = lookupRemoteStatelessBookManager();
        System.out.println("Obtained a remote stateless calculator for invocation");
        
        Book book = new Book();
        book.setTitle("foo");
        book.setPages(200);
        book.setAuthor(new Author());
        book.setISBN(12345L);
        book.setPublisher("publisher");
        
        System.out.println("just before add book");
        bookManager.addBook(book);
        System.out.println("just after add book");
        
        @SuppressWarnings("unused")
		int foo = 10;
        
        // invoke on the remote calculator
        /*-int a = 204;
        int b = 340;
        System.out.println("Adding " + a + " and " + b + " via the remote stateless calculator deployed on the server");
        int sum = bookManager.add(a, b);
        System.out.println("Remote calculator returned sum = " + sum);
        if (sum != a + b) {
            throw new RuntimeException("Remote stateless calculator returned an incorrect sum " + sum + " ,expected sum was " + (a + b));
        }
        // try one more invocation, this time for subtraction
        int num1 = 3434;
        int num2 = 2332;
        System.out.println("Subtracting " + num2 + " from " + num1 + " via the remote stateless calculator deployed on the server");
        int difference = bookManager.subtract(num1, num2);
        System.out.println("Remote calculator returned difference = " + difference);
        if (difference != num1 - num2) {
            throw new RuntimeException("Remote stateless calculator returned an incorrect difference " + difference + " ,expected difference was " + (num1 - num2));
        }*/
    }
 
    /**
     * Looks up a stateful bean and invokes on it
     *
     * @throws NamingException
     */
    /*-private static void invokeStatefulBean() throws NamingException {
        // Let's lookup the remote stateful counter
        final RemoteCounter statefulRemoteCounter = lookupRemoteStatefulCounter();
        System.out.println("Obtained a remote stateful counter for invocation");
        // invoke on the remote counter bean
        final int NUM_TIMES = 20;
        System.out.println("Counter will now be incremented " + NUM_TIMES + " times");
        for (int i = 0; i < NUM_TIMES; i++) {
            System.out.println("Incrementing counter");
            statefulRemoteCounter.increment();
            System.out.println("Count after increment is " + statefulRemoteCounter.getCount());
        }
        // now decrementing
        System.out.println("Counter will now be decremented " + NUM_TIMES + " times");
        for (int i = NUM_TIMES; i > 0; i--) {
            System.out.println("Decrementing counter");
            statefulRemoteCounter.decrement();
            System.out.println("Count after decrement is " + statefulRemoteCounter.getCount());
        }
    }*/
 
    /**
     * Looks up and returns the proxy to remote stateless calculator bean
     *
     * @return
     * @throws NamingException
     */
    private static BookManager lookupRemoteStatelessBookManager() throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put("jboss.naming.client.ejb.context", true);
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        //jndiProperties.put(Context.PROVIDER_URL, "jnp://localhost:1099/");
        //jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        //jndiProperties.put("java.naming.factory.url", "org.jboss.naming:org.jnp.interfaces");
        //jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        
        //jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        //jndiProperties.put(Context.PROVIDER_URL, "remote://localhost:4447");
        //jndiProperties.put(Context.SECURITY_PRINCIPAL, "admin");
        //jndiProperties.put(Context.SECURITY_CREDENTIALS, "adminadmin");
        final Context context = new InitialContext(jndiProperties);
        // The app name is the application name of the deployed EJBs. This is typically the ear name
        // without the .ear suffix. However, the application name could be overridden in the application.xml of the
        // EJB deployment on the server.
        // Since we haven't deployed the application as a .ear, the app name for us will be an empty string
        final String appName = "jboss-book-ear";
        //final String appName = "";
        // This is the module name of the deployed EJBs on the server. This is typically the jar name of the
        // EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
        // In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
        // jboss-as-ejb-remote-app
        final String moduleName = "jboss-book-ejb";
        // AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
        // our EJB deployment, so this is an empty string
        final String distinctName = "";
        // The EJB name which by default is the simple class name of the bean implementation class
        final String beanName = BookManagerImpl.class.getSimpleName();
        // the remote view fully qualified class name
        final String viewClassName = BookManager.class.getName();
        // let's do the lookup
        String objectName = "ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName;
        System.out.println(objectName);
		return (BookManager) context.lookup(objectName);
    }
 
    /**
     * Looks up and returns the proxy to remote stateful counter bean
     *
     * @return
     * @throws NamingException
     */
    /*-private static RemoteCounter lookupRemoteStatefulCounter() throws NamingException {
        final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        final Context context = new InitialContext(jndiProperties);
        // The app name is the application name of the deployed EJBs. This is typically the ear name
        // without the .ear suffix. However, the application name could be overridden in the application.xml of the
        // EJB deployment on the server.
        // Since we haven't deployed the application as a .ear, the app name for us will be an empty string
        final String appName = "";
        // This is the module name of the deployed EJBs on the server. This is typically the jar name of the
        // EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
        // In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
        // jboss-as-ejb-remote-app
        final String moduleName = "jboss-as-ejb-remote-app";
        // AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
        // our EJB deployment, so this is an empty string
        final String distinctName = "";
        // The EJB name which by default is the simple class name of the bean implementation class
        final String beanName = CounterBean.class.getSimpleName();
        // the remote view fully qualified class name
        final String viewClassName = RemoteCounter.class.getName();
        // let's do the lookup (notice the ?stateful string as the last part of the jndi name for stateful bean lookup)
        return (RemoteCounter) context.lookup("ejb:" + appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName + "?stateful");
    }*/
}