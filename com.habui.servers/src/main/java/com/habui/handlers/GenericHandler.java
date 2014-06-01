/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.habui.handlers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author habns
 */
public class GenericHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> map = req.getParameterMap();
        printJSON(resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }
    
    private static void printJSON(HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            // Assuming your json object is **jsonObject**, perform the following, it will return your json object  
            //out.print(String.format("{\"result\":\"xin chao\"}"));
            String content = readFile("/home/habns/Working/jsonsample.txt");
            out.print(content);
            out.flush();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static String readFile(String filename) {
        BufferedReader br = null;
        String content = "";
        try {

            String sCurrentLine;
            br = new BufferedReader(new FileReader(filename));

            while ((sCurrentLine = br.readLine()) != null) {
                content += sCurrentLine;
            }

        } catch (IOException e) {
                e.printStackTrace();
        } finally {
            try {
                    if (br != null)br.close();
            } catch (IOException ex) {
                    ex.printStackTrace();
            }
        }
        return content;
    }
    
}
