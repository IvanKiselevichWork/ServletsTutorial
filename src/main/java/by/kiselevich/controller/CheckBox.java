package by.kiselevich.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * https://www.tutorialspoint.com/servlets/servlets-form-data.htm
 */
@WebServlet(name = "CheckBox", urlPatterns = {"/checkbox"})
public class CheckBox extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/CheckBox.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        String title = "Reading Checkbox Data";
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType +
                        "<html>\n" +
                        "<head><title>" + title + "</title></head>\n" +
                        "<body bgcolor = \"#f0f0f0\">\n" +
                        "<h1 align = \"center\">" + title + "</h1>\n" +
                        "<ul>\n" +
                        "  <li><b>Maths Flag : </b>: "
                        + req.getParameter("maths") + "\n" +
                        "  <li><b>Physics Flag: </b>: "
                        + req.getParameter("physics") + "\n" +
                        "  <li><b>Chemistry Flag: </b>: "
                        + req.getParameter("chemistry") + "\n" +
                        "</ul>\n" +
                        "</body>" +
                "</html>"
        );
    }
}
