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

/**Class PostBlogBackend
 * @author Amimul Bhuiyan
 * 
 * Description: Holds the backend operations for the Posting Blogs
 */
public class PostBlogBackend{
    //Realize that in order for the user to even get to the post blog screen, they have to be a creator.
    //Thus we do not have to check if they are a creator or not. We can just do the operations needed
    private final String dburl = DatabaseCredentials.getURL();
    private final String dbusername = DatabaseCredentials.getUsername();
    private final String dbpassword = DatabaseCredentials.getPassword();
    
    private Runnable dbConnectionError; //run this function if there is a database connection error
    
    public PostBlogBackend( Runnable databaseConnectionError ){
        dbConnectionError = databaseConnectionError;
    }
    
    /**Creates and saves a post in the databse
       @param posterUsername The username of the person posting
       @param title The title of the post
       @param country The country for the post
       @param content The content of the post
       @return A boolean containing true if the post was saved successfully. False if otherwise*/
    public boolean postBlog(String posterUsername, String title, String country, String content){
        boolean success = false;
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            //This query should take in: username, title, country, content
            PreparedStatement st2 = conn.prepareStatement("CALL addnewPost(?, ?, ?, ?)");
            st2.setString( 1, posterUsername );
            st2.setString( 2, title );
            st2.setString( 3, country );
            st2.setString( 4, content );
            
            st2.executeQuery();
            
            
            //Close all statements and ResultSets
            st1.close();
            st2.close();
            
            success = true;
        }catch( SQLException e ) {
            //throw new IllegalStateException("Cannot connect to the database: \n", e);
            dbConnectionError.run();
        }
        
        
        return success;
    }
    
}