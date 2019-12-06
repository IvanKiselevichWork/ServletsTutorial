package by.kiselevich.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.print.URIException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet(name = "ApiServlet", urlPatterns = {"/api/*"})
public class ApiServlet extends HttpServlet {
    private String filePath;

    @Override
    public void init() throws ServletException {
        filePath = getServletContext().getInitParameter("file-upload");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer requestUrl = req.getRequestURL();
        String strippedUrl = requestUrl.substring(requestUrl.lastIndexOf("/api"));
        if("/api/postcvs".equals(strippedUrl)) {
            req.getRequestDispatcher("/jsp/PostCsv.jsp").forward(req, resp);
            return;
        }

        String query = req.getQueryString();
        if(query != null) {
            strippedUrl += "?" + query;
        }

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h1>" + "API TEST" + "</h1>");
        out.println("<p> stripped url: " + strippedUrl + "</p>");
        out.println("<p> regexp: " + "" + "</p>");

        Pattern patternFormat1 = Pattern.compile("(/api/(sum|max|min|avg))(/([-+]?[0-9]*\\.?[0-9]+))+");
        Matcher matcherFormat1 = patternFormat1.matcher(strippedUrl);
        if(matcherFormat1.matches()) {
            format1Handler(strippedUrl, matcherFormat1, out);
            return;
        }

        Pattern patternFormat2 = Pattern.compile("(/api/(sum|max|min|avg);numbers=)(([-+]?[0-9]*\\.?[0-9]+),)*([-+]?[0-9]*\\.?[0-9]+)");
        Matcher matcherFormat2 = patternFormat2.matcher(strippedUrl);
        if(matcherFormat2.matches()) {
            format2Handler(strippedUrl, matcherFormat2, out);
            return;
        }

        Pattern patternFormat3 = Pattern.compile("(/api/(sum|max|min|avg)).*");
        Matcher matcherFormat3 = patternFormat3.matcher(strippedUrl);
        if(matcherFormat3.matches()) {
            format3Handler(req, matcherFormat3, out);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer requestUrl = req.getRequestURL();
        String strippedUrl = requestUrl.substring(requestUrl.lastIndexOf("/api"));
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        if("/api/postcvs".equals(strippedUrl)) {
            // Check that we have a file upload request
            boolean isMultipart = ServletFileUpload.isMultipartContent(req);
            if( !isMultipart) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet upload</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<p>No file uploaded</p>");
                out.println("</body>");
                out.println("</html>");
                return;
            }

            DiskFileItemFactory factory = new DiskFileItemFactory();

            // maximum size that will be stored in memory
            int maxMemSize = 1024 * 1024;
            factory.setSizeThreshold(maxMemSize);

            // Location to save data that is larger than maxMemSize.
            factory.setRepository(new File("c:\\temp"));

            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);

            // maximum file size to be uploaded.
            int maxFileSize = 50 * 1024;
            upload.setSizeMax(maxFileSize);

            try {
                // Parse the request to get file items.
                List fileItems = upload.parseRequest(req);

                // Process the uploaded file items
                Iterator i = fileItems.iterator();

                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet upload</title>");
                out.println("</head>");
                out.println("<body>");

                while ( i.hasNext () ) {
                    FileItem fi = (FileItem)i.next();
                    if ( !fi.isFormField () ) {
                        // Get the uploaded file parameters
                        String fileName = fi.getName();

                        //todo extension check (cvs)

                        // Write the file
                        File file;
                        if( fileName.lastIndexOf("\\") >= 0 ) {
                            file = new File( filePath + fileName.substring( fileName.lastIndexOf("\\"))) ;
                        } else {
                            file = new File( filePath + fileName.substring(fileName.lastIndexOf("\\")+1)) ;
                        }
                        fi.write(file) ;
                        out.println("Uploaded Filename: " + fileName + "<br>");
                    }
                }
                out.println("</body>");
                out.println("</html>");
            } catch(Exception ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace(out);
                out.println("</body>");
                out.println("</html>");
            }
        } else {
            out.println("<h3>Wrong POST request</h3>");
        }
    }

    private void format1Handler(String strippedUrl, Matcher matcher, PrintWriter out) {
        String function = matcher.group(2);
        out.println("<h1> format 1: </h1>");
        out.println("<p> function: " + function + "</p>");
        String argsRaw = strippedUrl.substring(matcher.group(1).length() + 1);
        out.println("<p> args raw : " + argsRaw + "</p>");
        out.println("<p> args parsed : ");
        boolean isArgsValid = true;
        List<Double> args = new ArrayList<>();
        String[] argsParsed = argsRaw.split("/");
        for (String arg : argsParsed) {
            out.println("<p> arg: " + arg + "</p>");
            try {
                args.add(Double.parseDouble(arg));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                isArgsValid = false;
            }
        }
        if(isArgsValid) {
            double result = chooseFunctionAndGetResult(function, args);
            out.println("<p> result: " + result + "</p>");
        } else {
            out.println("invalid args");
        }
    }


    private void format2Handler(String strippedUrl, Matcher matcher, PrintWriter out) {
        String function = matcher.group(2);
        out.println("<h1> format 2: </h1>");
        out.println("<p> function: " + function + "</p>");
        String argsRaw = strippedUrl.substring(matcher.group(1).length());
        out.println("<p> args raw : " + argsRaw + "</p>");
        out.println("<p> args parsed : ");
        boolean isArgsValid = true;
        List<Double> args = new ArrayList<>();
        String[] argsParsed = argsRaw.split(",");
        for (String arg : argsParsed) {
            out.println("<p> arg: " + arg + "</p>");
            try {
                args.add(Double.parseDouble(arg));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                isArgsValid = false;
            }
        }
        if(isArgsValid) {
            double result = chooseFunctionAndGetResult(function, args);
            out.println("<p> result: " + result + "</p>");
        } else {
            out.println("invalid args");
        }
    }

    private void format3Handler(HttpServletRequest req, Matcher matcher, PrintWriter out) {
        String function = matcher.group(2);
        out.println("<h1> format 3: </h1>");
        out.println("<p> function: " + function + "</p>");
        String paramsRaw = req.getQueryString();
        out.println("<p> args raw : " + paramsRaw + "</p>");
        out.println("<p> args parsed : ");
        String whereParameter = req.getParameter("where");
        String srcParameter = req.getParameter("src");
        String delimiterParameter = req.getParameter("delimiter");
        if (delimiterParameter == null) {
            delimiterParameter = ";";
        }
        if (whereParameter == null || srcParameter == null) {
            out.println("<p> " + "no args" + "</p>");
        } else {
            out.println("<p> whereParameter: " + whereParameter + "</p>");
            out.println("<p> srcParameter: " + srcParameter + "</p>");
            out.println("<p> delimiterParameter: " + delimiterParameter + "</p>");
            String argsRaw;
            try {
                if (whereParameter.equals("url")) {
                    argsRaw = getRawArgsByUrl(srcParameter);
                } else if (whereParameter.equals("resource")) {
                    argsRaw = getRawArgsByPath("C:/Tomcat/webapps/upload_data/" + srcParameter);
                } else {
                    out.println("<p> " + "wrong \"where\" parameter" + "</p>");
                    return;
                }
                String[] argsStr = argsRaw.split(delimiterParameter);
                List<Double> args = new ArrayList<>();
                for (String arg : argsStr) {
                    out.println("<p> arg: " + arg + "</p>");
                    args.add(Double.parseDouble(arg));
                }
                double result = chooseFunctionAndGetResult(function, args);
                out.println("<p> result: " + result + "</p>");
            } catch (URISyntaxException | IOException | NumberFormatException e) {
                e.printStackTrace();
                out.println("<p> Exception: " + e.getMessage() + "</p>");
            }
        }
    }

    private String getRawArgsByPath(String pathStr) throws IOException{
        Path path = Paths.get(pathStr);
        List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
        return list.get(0);
    }

    private String getRawArgsByUrl(String url) throws IOException, URISyntaxException {
        URI uri = new URI(url);
        Path path = Paths.get(uri);
        List<String> list = Files.readAllLines(path, StandardCharsets.UTF_8);
        return list.get(0);
    }

    private double chooseFunctionAndGetResult(String function, List<Double> args) {
        double result = 0;
        switch (function) {
            case "sum":
                result = sum(args);
                break;
            case "max":
                result = max(args);
                break;
            case "min":
                result = min(args);
                break;
            case "avg":
                result = avg(args);
                break;
        }
        return result;
    }

    private double sum(List<Double> values) {
        double result = 0;
        for (double value: values) {
            result += value;
        }
        return result;
    }

    private double max(List<Double> values) {
        double result = Double.MIN_VALUE;
        for (double value: values) {
            if (value > result) {
                result = value;
            }
        }
        return result;
    }

    private double min(List<Double> values) {
        double result = Double.MAX_VALUE;
        for (double value: values) {
            if (value < result) {
                result = value;
            }
        }
        return result;
    }

    private double avg(List<Double> values) {
        double result = 0;
        for (double value: values) {
            result += value;
        }
        return (result / (double)(values.size()));
    }
}

