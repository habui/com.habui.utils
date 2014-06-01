/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.servers;

import com.habui.filter.CorsFilter;
import com.habui.filter.GZipFilter;
import org.eclipse.jetty.server.Server;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;

import org.eclipse.jetty.servlet.ServletHandler;

/**
 *
 * @author habns
 */
public class JettyServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Server server = new Server(8585);
            
            //ContextHandlerCollection contexts = new ContextHandlerCollection();
            //server.setHandler(contexts);
            //ServletContextHandler context = new ServletContextHandler(contexts, "/");
            //context.addServlet(com.hab.demo.handler.GenericHandler.class, "/test");
            
            ServletHandler handler = new ServletHandler();
            server.setHandler(handler);
            handler.addServletWithMapping(com.habui.handlers.GenericHandler.class, "/test");
            
            FilterHolder filterHolder = new FilterHolder(new CorsFilter());
            FilterHolder gzipFilterHolder = new FilterHolder(new GZipFilter());
            
            //FilterHolder filterHolder = new FilterHolder(CrossOriginFilter.class);
            //filterHolder.setInitParameter("allowedOrigins", "*");
            //filterHolder.setInitParameter("allowedMethods", "GET, POST");
            handler.addFilterWithMapping(filterHolder, "/*", FilterMapping.REQUEST);
            handler.addFilterWithMapping(gzipFilterHolder, "/*", FilterMapping.REQUEST);

            server.start();
            server.join();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
