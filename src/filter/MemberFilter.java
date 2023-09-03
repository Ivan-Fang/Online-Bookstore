package filter;

import pojo.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

//@WebFilter(urlPatterns = {"*.do", "*.html"},
//           initParams = {@WebInitParam(name = "whiteList", value = "/BookStore/page.do?operate=accessPage&pagePath=user/login,/BookStore/user.do?null")})
public class MemberFilter implements Filter {

    List<String> whiteList;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String whiteListStr = filterConfig.getInitParameter("whiteList");
        whiteList = Arrays.asList(whiteListStr.split(","));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        String pagePath = req.getRequestURI() + "?" + req.getQueryString();
        User user = (User) session.getAttribute("currUser");
        if (user == null) {     // not member
            if (whiteList.contains(pagePath)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                resp.sendRedirect("page.do?operate=accessPage&pagePath=user/login");
            }
        } else {                 // is member (login status)
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
