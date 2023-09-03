package servlet;

import exception.DispatcherServletException;
import ioc.BeanFactory;
import ioc.ClassPathXmlApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;


@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private BeanFactory beanFactory;

    public DispatcherServlet() {
    }

    @Override
    public void init() throws ServletException {
        super.init();

        // beanFactory = new ClassPathXmlApplicationContext();
        Object beanFactoryObj = getServletContext().getAttribute("beanFactory");
        if (beanFactoryObj != null) {
            beanFactory = (ClassPathXmlApplicationContext) beanFactoryObj;
        } else {
            throw new DispatcherServletException(">>> IOC container (beanFactory) acquisition failed ...");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        // to prevent chinese garbled characters
        try {
            req.setCharacterEncoding("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DispatcherServletException("DispatcherServlet.service() has some errors...");
        }

        // ex. "/fruit.do" -> "fruit"
        String servletPath = req.getServletPath();  // ex. /fruit.do
        servletPath = servletPath.substring(1);
        int dotLocation = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, dotLocation);

        // "fruit" -> FruitController
        Object controllerObj = beanFactory.getBean(servletPath);

        // get "operate" (method name)
        String operation = req.getParameter("operate");
        if (operation == null) {
            operation = "index";
        }

        // execute the method based on "operate"
        Class clazz = controllerObj.getClass();
        try {
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(operation)) {
                    // 1. get parameters
                    // (add "-parameters" in "File/Settings/Build,Execution,Deployment/Compiler/Java Compiler/Additional command line parameters")
                    Parameter[] parameters = method.getParameters();
                    Object[] parameterValues = new Object[parameters.length];

                    for (int i = 0; i < parameters.length; i++) {
                        String parameterName = parameters[i].getName();
                        if (parameterName.equals("req")) {
                            parameterValues[i] = req;
                        } else if (parameterName.equals("resp")) {
                            parameterValues[i] = resp;
                        } else if (parameterName.equals("session")) {
                            parameterValues[i] = req.getSession();
                        } else {
                            Object parameterObj = req.getParameter(parameterName);

                            // req.getParameter() only returns String type
                            // therefore, we should convert the type based on typeName
                            String typeName = parameters[i].getType().getName();
                            if (parameterObj != null) {
                                if (typeName.equals("java.lang.Integer")) {
                                    parameterObj = Integer.parseInt((String) parameterObj);
                                } else if (typeName.equals("java.lang.Float")) {
                                    parameterObj = Float.parseFloat((String) parameterObj);
                                } else if (typeName.equals("java.lang.Double")) {
                                    parameterObj = Double.parseDouble((String) parameterObj);
                                }
                            }
                            parameterValues[i] = parameterObj;
                        }
                    }

                    // 2. call controller method (ex. FruitController)
                    method.setAccessible(true);
                    Object methodReturnObj = method.invoke(controllerObj, parameterValues);

                    // 3. view processing
                    String methodReturnStr = (String) methodReturnObj;

                    if (methodReturnStr == null || methodReturnStr.isEmpty()) {
                        return;
                    } else if (methodReturnStr.startsWith("redirect:")) {
                        String redirectStr = methodReturnStr.substring("redirect:".length());
                        resp.sendRedirect(redirectStr);
                    } else if (methodReturnStr.startsWith("json:")) {
                        resp.setContentType("application/json");
                        String jsonStr = methodReturnStr.substring("json:".length());
                        PrintWriter writer = resp.getWriter();
                        writer.print(jsonStr);
                        writer.flush();
                    } else {
                        super.processTemplate(methodReturnStr, req, resp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DispatcherServletException("DispatcherServlet.service() has some errors...");
        }
    }
}
