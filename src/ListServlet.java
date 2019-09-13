import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

@WebServlet(name = "ListServlet",
        urlPatterns = "/list")
public class ListServlet extends HttpServlet {

    private final String DRIVER_NAME = "jdbc:derby:";
    private final String DATABASE_PATH = "/WEB-INF/lib/adoption";
    private final String USER = "jason";
    private final String PW = "jason";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String path = getServletContext().getRealPath(DATABASE_PATH);
            conn = DriverManager.getConnection(DRIVER_NAME + path, USER, PW);
            stmt = conn.createStatement();

            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM DOGS");

            rset = stmt.executeQuery(sql.toString());

            StringBuilder html = new StringBuilder("<html><body><p><H2>Here are the Doggos!</H2></p><ul>");
            /*while (rset.next()){
                String dogName = rset.getString("NAME");
                String dogBreed = rset.getString("BREED");
                html.append("<li>").append(dogName).append("<nbsp><nbsp><nbsp><nbsp>").append(dogBreed).append("</li>");
            }*/
            DecimalFormat f = new DecimalFormat("##.00");
            while (rset.next()){
                int id = rset.getInt("DOG_ID");
                String name = rset.getString("NAME");
                String breed = rset.getString("BREED");
                String declawed = rset.getString("DECLAWED");
                String gender = rset.getString("GENDER");
                int age = rset.getInt("AGE");
                double price = rset.getDouble("PRICE");
                String priceDisplay = f.format(price);
                String imageFile = rset.getString("IMAGEFILE");
                html.append("<p>Dog ID: ").append(id).append("<p>Name: ").append(name).append("</p>Breed: ").append(breed).append("</p>Declawed: ").append(declawed).append("</p>Gender: ").append(gender).append("</p>Age: ").append(age).append("<p>Price: $").append(priceDisplay).append("</p>Image File Name: ").append(imageFile).append("</p>").append("<p><hr></p>");
            }

            html.append("</ul><body></html>");
            response.getWriter().print(html.toString());

        } catch (ClassNotFoundException |  SQLException e){
            response.getWriter().print(e.getMessage());
        } finally {
            if(rset !=null){
                try{
                    rset.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(stmt !=null){
                try{
                    stmt.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(conn !=null){
                try{
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }
}
