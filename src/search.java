import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;

@WebServlet(name = "search",
        urlPatterns = "/search")
public class search extends HttpServlet {
    private final String DRIVER_NAME = "jdbc:derby:";
    private final String DATABASE_PATH = "/WEB-INF/lib/adoption";
    private final String USER = "jason";
    private final String PW = "jason";
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rset = null;

        try{

            String searchTerm = request.getParameter("searchTerm");
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            String path = getServletContext().getRealPath(DATABASE_PATH);
            conn = DriverManager.getConnection(DRIVER_NAME + path, USER, PW);

            String sql = "SELECT * FROM DOGS WHERE NAME = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, searchTerm);
            rset = pstmt.executeQuery();
            StringBuilder html = new StringBuilder("<html><body><p><H2>Is This Your Future Dog?</H2></p>");
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
                html.append("<p>Dog ID: ").append(id).append("<p>Name: ").append(name).append("</p>Breed: ").append(breed).append("</p>Declawed: ").append(declawed).append("</p>Gender: ").append(gender).append("</p>Age: ").append(age).append("<p>Price: $").append(priceDisplay).append("</p>Image File Name: ").append(imageFile).append("</p>");
            }
            html.append("</body></html>");
            response.getWriter().print(html.toString());

        } catch (SQLException | ClassNotFoundException e) {
            response.getWriter().print(e.getMessage());
        } finally {
            if(rset !=null){
                try{
                    rset.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(pstmt !=null){
                try{
                    pstmt.close();
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
