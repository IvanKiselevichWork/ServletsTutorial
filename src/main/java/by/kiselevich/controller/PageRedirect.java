package by.kiselevich.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PageRedirect", urlPatterns = {"/pageredirect"})
public class PageRedirect extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type
        resp.setContentType("text/html");

        // New location to be redirected
        String site = new String("http://localhost:8080/ServletsTutorial_war_exploded/");

        resp.setStatus(resp.SC_MOVED_TEMPORARILY);
        resp.setHeader("Location", site);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
