package listener;

import ioc.BeanFactory;
import ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // execute when servletContext initialization is detected
        // servletContext is initialized when Tomcat launch

        // create beanFactory (IOC) when servletContext initialized
        ServletContext application = servletContextEvent.getServletContext();
        String path = application.getInitParameter("contextConfigLocation");
        BeanFactory beanFactory = new ClassPathXmlApplicationContext();
        application.setAttribute("beanFactory", beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // execute when servletContext destruction is detected
        // servletContext is destroyed when Tomcat close
    }
}
