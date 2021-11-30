package backendoperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import java.util.LinkedList;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**Class SearchScreenBackend
 * @author Amimul Bhuiyan
 * 
 * Description Used for doing the backend operations for the SearchScreen class
 */
public class SearchScreenBackend{
    private final String dburl = DatabaseCredentials.getURL();
    private final String dbusername = DatabaseCredentials.getUsername();
    private final String dbpassword = DatabaseCredentials.getPassword();
    
    private Runnable dbConnectionError;//Run this function if there is a database connection error
    
    public SearchScreenBackend( Runnable databaseConnectionError ){
        dbConnectionError = databaseConnectionError;
    }
    
    /**Gets a linkedlist of Post Details
       @return a linkedlist containing the postdetails*/
    public LinkedList<MinimalPostDetails> getAllPosts(){
        LinkedList<MinimalPostDetails> postDetails = new LinkedList<MinimalPostDetails>(); // holds post details
        
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            PreparedStatement st2 = conn.prepareStatement("CALL getAllBlogPosts()");
            ResultSet rs1 = st2.executeQuery();

            //get list of blogs from database
            while( rs1.next() ){
                int postId = rs1.getInt("id");
                String posterUsername = rs1.getString("username");
                String postTitle = rs1.getString("title");
                String postCountry = rs1.getString("country");
                
                //add the details to the linkedlist
                postDetails.add( new MinimalPostDetails( postId, posterUsername, postTitle, postCountry ) );
            }
            

            //Close all statements and ResultSets
            st1.close();
            st2.close();
            rs1.close();
        }catch( SQLException e ) {
            //throw new IllegalStateException("Cannot connect to the database: \n", e);
            dbConnectionError.run();
        }
        
        
        return postDetails;
    }
    
    
    /**Gets a linkedList of Post Details based on the specified country
       @param specifiedCountry The Specified country that the blog should be for
       @return a linkedlist containing the post details for the specified country*/
    public LinkedList<MinimalPostDetails> getPostByCountry( String specifiedCountry ){
        LinkedList<MinimalPostDetails> postDetails = new LinkedList<MinimalPostDetails>();
        
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            PreparedStatement st2 = conn.prepareStatement("CALL getBlogByCountry( ? )");
            st2.setString( 1, specifiedCountry );
            ResultSet rs1 = st2.executeQuery();
            
            while( rs1.next() ){
                int postId = rs1.getInt("id");
                String posterUsername = rs1.getString("username");
                String postTitle = rs1.getString("title");
                String postCountry = rs1.getString("country");
                
                //add the details to the linkedlist
                postDetails.add( new MinimalPostDetails( postId, posterUsername, postTitle, postCountry ) );
            }
            

            //Close all statements and ResultSets
            st1.close();
            st2.close();
            rs1.close();
        }catch( SQLException e ) {
            //throw new IllegalStateException("Cannot connect to the database: \n", e);
            dbConnectionError.run();
        }
        
        
        return postDetails;
    }
    
    
    /**Used to set a request to become a creator by the user*/
    public boolean requestToBeCreator( String username ){
        boolean changeSuccessful = false;
        
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            PreparedStatement st2 = conn.prepareStatement("CALL setCreatorRequests( ? )");
            st2.setString( 1, username );
            st2.executeQuery();
            changeSuccessful = true;

            //Close all statements and ResultSets
            st1.close();
            st2.close();
        }catch(SQLIntegrityConstraintViolationException e){
            changeSuccessful = false;
            return false;
        }catch( SQLException e ) {
            //throw new IllegalStateException("Cannot connect to the database: \n", e);
            dbConnectionError.run();
        }
        
        return changeSuccessful;
    }
    
}