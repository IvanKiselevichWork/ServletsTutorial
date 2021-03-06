package by.kiselevich.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "UploadFile", urlPatterns = {"/uploadfile"})
public class UploadFile extends HttpServlet {

    private String filePath;

    @Override
    public void init() throws ServletException {
        // Get the file location where it would be stored.
        filePath = getServletContext().getInitParameter("file-upload");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/FileUploadForm.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(req);
        resp.setContentType("text/html");
        java.io.PrintWriter out = resp.getWriter( );

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
        int maxMemSize = 4 * 1024;
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
                    String fieldName = fi.getFieldName();
                    String fileName = fi.getName();
                    String contentType = fi.getContentType();
                    boolean isInMemory = fi.isInMemory();
                    long sizeInBytes = fi.getSize();

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
            System.out.println(ex);
            ex.printStackTrace(out);
            out.println("</body>");
            out.println("</html>");
        }
    }
}
