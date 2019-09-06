package wctc.edu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "Servlet", urlPatterns = "/hello")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String p = request.getParameter("param3");
        String p1 = request.getParameter("param4");
        String message = "<html><body>Post:" + p + " " + p1 + "</body></html>";
        response.getWriter().print(message);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String p = request.getParameter("param1");
        String p1 = request.getParameter("param2");
        String message = "<html><body>Get:" + p + " " + p1 + "</body></html>";
        response.getWriter().print(message);
    }
}
