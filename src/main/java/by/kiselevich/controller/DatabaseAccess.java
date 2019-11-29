package by.kiselevich.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "DatabaseAccess", urlPatterns = {"/databaseaccess"})
public class DatabaseAccess extends HttpServlet {

    // JDBC driver name and database URL
    private static final String DB_URL="jdbc:mysql://localhost:3306/TEST";

    //  Database credentials
    private static final String USER = "root";
    private static final String PASS = "1234";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Set response content type
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String title = "Database Result";

        String docType =
                "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";

        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor = \"#f0f0f0\">\n" +
                "<h1 align = \"center\">" + title + "</h1>\n");
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }
        try (
                // Open a connection
                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                Statement stmt = conn.createStatement();
        ) {

            // Execute SQL query
            String sql;
            sql = "SELECT id, first, last, age FROM Employees";
            ResultSet rs = stmt.executeQuery(sql);

            // Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                int age = rs.getInt("age");
                String first = rs.getString("first");
                String last = rs.getString("last");

                //Display values
                out.println("ID: " + id);
                out.println(", Age: " + age);
                out.println(", First: " + first);
                out.println(", Last: " + last + "<br>");
            }
            out.println("</body></html>");

            // Clean-up environment
            rs.close();
        } catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
