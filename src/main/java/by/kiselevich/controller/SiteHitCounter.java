package by.kiselevich.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "SiteHitCounter", urlPatterns = {"/*"})
public class SiteHitCounter implements Filter {
    private int hitCount;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        hitCount = 0;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // increase counter by one
        hitCount++;

        // Print the counter.
        System.out.println("Site visits count :"+ hitCount );

        // Pass request back down the filter chain
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
