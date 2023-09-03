package filter;

import trans.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.sql.SQLException;

@WebFilter("*.do")
public class OpenSessionInViewFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        try {
            TransactionManager.begin();
            filterChain.doFilter(servletRequest, servletResponse);
            TransactionManager.commit();
        } catch (Exception e) {
            try {
                TransactionManager.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void destroy() {}
}
