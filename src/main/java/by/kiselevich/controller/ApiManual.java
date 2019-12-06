package by.kiselevich.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ApiManual", urlPatterns = {"/api"})
public class ApiManual extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        out.println("<h1>" + "API Manual" + "</h1>");
        out.println("<h3>" + "<p>Format 1:</p>" +
                "<pre>GET api/stat/{num1}/{num2}/{...}</pre>" + "</h3>" +
                "<p>where <mark>stat</mark> is some statistic from <mark>sum, max, min, avg,</mark></p>" +
                "<p><mark>num1, num2,</mark> ... are numbers</p>");
        out.println("<h3>" + "<p>Format 2:</p>" +
                "<pre>GET api/stat;numbers=num1,num2,...</pre>" + "</h3>" +
                "<p>where <mark>stat</mark> is some statistic from <mark>sum, max, min, avg,</mark></p>" +
                "<p><mark>num1, num2,</mark> ... are numbers</p>");
        out.println("<h3>" + "<p>Format 3:</p>" +
                "<pre>GET api/stat?where=location&src=path&delimiter=value</pre>" + "</h3>" +
                "<p>where <mark>location</mark> is url or resource,</p>" +
                "<p><mark>path</mark> is a path to a data source with numbers,</p>" +
                "<p><mark>value</mark> is an optional delimiter between numbers inside a data source</p>");
        out.println("<h3>" + "<p>Format 4:</p>" +
                "<pre>POST api/stat/csv</pre>" + "</h3>" +
                "<p>with <mark>enctype=\"multipart/form-data\"</mark></p>");
        out.println("<h3>" + "<p>Examples of requests:</p>" + "</h3>" +
                "<p><a href=\"http://localhost:8080/ServletsTutorial_war_exploded/api/sum/2.5/2/4\">http://localhost:8080/ServletsTutorial_war_exploded/api/sum/2.5/2/4</a></p>" +
                "<p><a href=\"http://localhost:8080/ServletsTutorial_war_exploded/api/avg;numbers=2.5,2,-6.3\">http://localhost:8080/ServletsTutorial_war_exploded/api/avg;numbers=2.5,2,-6.3</a></p>" +
                "<p><a href=\"http://localhost:8080/ServletsTutorial_war_exploded/api/max?where=url&src=file:///C:/Tomcat/webapps/upload_data/1.csv&delimiter=,\">http://localhost:8080/ServletsTutorial_war_exploded/api/max?where=url&src=file:///C:/Tomcat/webapps/upload_data/1.csv&delimiter=,</a></p>" +
                "<p><a href=\"http://localhost:8080/ServletsTutorial_war_exploded/api/min?where=resource&src=2.csv\">http://localhost:8080/ServletsTutorial_war_exploded/api/min?where=resource&src=2.csv</a></p>" +
                "<p><a href=\"http://localhost:8080/ServletsTutorial_war_exploded/api/postcvs\">http://localhost:8080/ServletsTutorial_war_exploded/api/postcvs</a></p>");
    }
}
