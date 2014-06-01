/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.habui.filter;

import com.habui.utils.GZipResponseWrapper;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author habns
 */
public class GZipFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // make sure we are dealing with HTTP
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req =  (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            // check for the HTTP header that
            // signifies GZIP support
            String ae = req.getHeader("accept-encoding");
            
            if (ae != null && ae.indexOf("gzip") != -1) {
                System.out.println("GZIP supported, compressing.");
                GZipResponseWrapper wrappedResponse = new GZipResponseWrapper(resp);
                chain.doFilter(request, wrappedResponse);
                wrappedResponse.finishResponse();
                return;
             }
            
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        
    }
    
}
