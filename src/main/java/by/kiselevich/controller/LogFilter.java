package by.kiselevich.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

/**
 * https://www.tutorialspoint.com/servlets/servlets-writing-filters.htm
 */
@WebFilter(
        urlPatterns = {"/iplogfilter"},
        initParams = @WebInitParam(
                name = "test-param",
                value = "test-param_value"),
        filterName = "LogFilter",
        description = "Log IP addresses")
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Get init parameter
        String testParam = filterConfig.getInitParameter("test-param");

        //Print the init parameter
        System.out.println("Log filter init, test-param: " + testParam);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Get the IP address of client machine.
        String ipAddress = servletRequest.getRemoteAddr();

        // Log the IP address and current timestamp.
        System.out.println("IP "+ ipAddress + ", Time " + new Date().toString());

        // Pass request back down the filter chain
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        /* Called before the Filter instance is removed from service by the web container*/
        System.out.println("LogFilter destroy");
    }
}
