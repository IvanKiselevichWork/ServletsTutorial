package by.kiselevich.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SetCookies", urlPatterns = {"/setcookies"})
public class SetCookies extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/SetCookies.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Create cookies for first and last names.
        Cookie cookie = new Cookie(req.getParameter("cookie_name"), req.getParameter("cookie_value"));

        // Set expiry date after 24 Hrs for both the cookies.
        cookie.setMaxAge(60*60*24);

        // Add both the cookies in the response header.
        resp.addCookie( cookie );

        // Set response content type
        resp.setContentType("text/html");

        PrintWriter out = resp.getWriter();
        String title = "Setting Cookies Example";
        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType +
                "<html>\n" +
                "<head> <title>" + title + "</title> </head>\n" +

        "<body bgcolor = \"#f0f0f0\">\n" +
                "<h1 align = \"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>Cookie name</b>: "
                + req.getParameter("cookie_name") + "\n" +
                "  <li><b>Cookie value</b>: "
                + req.getParameter("cookie_value") + "\n" +
                "</ul>\n" +
                "</body> </html>"
      );
    }
}
