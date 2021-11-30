package backendoperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**Class ViewBlogBackend
 * @author Amimul Bhuiyan
 * 
 * Description: Used to perform backend operations for the ViewBlog class
 */
public class ViewBlogBackend{
    private final String dburl = DatabaseCredentials.getURL();
    private final String dbusername = DatabaseCredentials.getUsername();
    private final String dbpassword = DatabaseCredentials.getPassword();
    
    private Runnable dbConnectionError;
    
    public ViewBlogBackend( Runnable databaseConnectionError ){
        dbConnectionError = databaseConnectionError;
        
    }
    
    /**Used to get the contents of a specified blog
       @param id The id of the blog that is needed
       @return A String containing the contents of the blog*/
    public String getBlogContent( int id ){
        String blogContent = "";
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            PreparedStatement st2 = conn.prepareStatement("CALL getSpecificBlog( ? )");
            st2.setInt( 1, id );
            ResultSet rs1 = st2.executeQuery();
            
            //Get the content of the blog. 
            //you do not have to check if it exists since the only way to get this info is to click a 
            //button created from a saved blog
            while( rs1.next() ){
                blogContent = rs1.getString("bcontext");
            }
            

            //Close all statements and ResultSets
            st1.close();
            st2.close();
            rs1.close();
        }catch( SQLException e ) {
            //throw new IllegalStateException("Cannot connect to the database: \n", e);
            dbConnectionError.run();
        }
        
        return blogContent;
    }
    
    
    /**Used to delete a specific blog; specified by id
       @param id The id of the blog that you want deleted. */
    public void deleteBlogById( int id ){
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            PreparedStatement st2 = conn.prepareStatement("CALL deleteSpecificBlog( ? )");
            st2.setInt( 1, id );
            st2.executeQuery();
            
            
            //Close all statements
            st1.close();
            st2.close();
        }catch( SQLException e ) {
            //throw new IllegalStateException("Cannot connect to the database: \n", e);
            dbConnectionError.run();
        }
    }
    
}