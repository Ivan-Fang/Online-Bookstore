package servlet;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewBaseServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {

        // 1.獲取ServletContext對象
        ServletContext servletContext = this.getServletContext();

        // 2.創建Thymeleaf解析器對象
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

        // 3.給解析器對象設置參數
        // ①HTML是默認模式，明確設置是為了代碼更容易理解
        templateResolver.setTemplateMode(TemplateMode.HTML);

        // ②設置前綴
        String viewPrefix = servletContext.getInitParameter("view-prefix");

        templateResolver.setPrefix(viewPrefix);

        // ③設置後綴
        String viewSuffix = servletContext.getInitParameter("view-suffix");

        templateResolver.setSuffix(viewSuffix);

        // ④設置緩存過期時間（毫秒）
        templateResolver.setCacheTTLMs(60000L);

        // ⑤設置是否緩存
        templateResolver.setCacheable(true);

        // ⑥設置服務器端編碼方式
        templateResolver.setCharacterEncoding("utf-8");

        // 4.創建模板引擎對象
        templateEngine = new TemplateEngine();

        // 5.給模板引擎對象設置模板解析器
        templateEngine.setTemplateResolver(templateResolver);

    }

    protected void processTemplate(String templateName, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1.設置響應體內容類型和字符集
        resp.setContentType("text/html;charset=UTF-8");

        // 2.創建WebContext對象
        WebContext webContext = new WebContext(req, resp, getServletContext());

        // 3.處理模板數據
        templateEngine.process(templateName, webContext, resp.getWriter());
    }
}
