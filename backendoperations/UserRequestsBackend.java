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

import java.util.LinkedList;

/**Class UserRequestsBackend
 * @author Amimul Bhuiyan
 * 
 * Used to perform backend operations on the PostBlogScreen class which includes:
 * posting the blog
 */



public class UserRequestsBackend{
    private final String dburl = DatabaseCredentials.getURL();
    private final String dbusername = DatabaseCredentials.getUsername();
    private final String dbpassword = DatabaseCredentials.getPassword();
    
    private Runnable dbConnectionError;
    
    public UserRequestsBackend( Runnable databaseConnectionError ){
        dbConnectionError = databaseConnectionError;
        
    }
    
    
    /**Gets a list of usernames that requested to become a creator from the database
       @return a LinkedList containing the list of usernames who requested to become a creator.*/
    public LinkedList<String> getCreatorRequests(){
        LinkedList<String> usersthatRequested = new LinkedList<String>();
        
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            PreparedStatement st2 = conn.prepareStatement("CALL getCreatorRequests()");
            ResultSet rs1 = st2.executeQuery();
            
            while( rs1.next() ){
                String requesterUsername = rs1.getString("username");
                
                //add the username to the linkedlist
                usersthatRequested.add( requesterUsername );
            }
            

            //Close all statements and ResultSets
            st1.close();
            st2.close();
            rs1.close();
        }catch( SQLException e ) {
            //throw new IllegalStateException("Cannot connect to the database: \n", e);
            dbConnectionError.run();
        }
        
        return usersthatRequested;
    }
    
    
    /**Make a particular user into a creator by setting their accounttype
       @param usern The username of the user who you want to change the accounttype for
       @return True if the accounttype was successfully changed, false if otherwise*/
    public boolean setAccountToCreator( String usern ){
        boolean success = false;
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            //This query should take in: username, title, country, content
            PreparedStatement st2 = conn.prepareStatement("CALL setAccountToCreator( ? )");
            st2.setString( 1, usern );
            
            st2.executeQuery();
            
            
            //Remove the request when account is set to creator
            PreparedStatement st3 = conn.prepareStatement("CALL removeCreatorRequest( ? )");
            st3.setString( 1, usern );
            st3.executeQuery();
            
            
            //Close all statements and ResultSets
            st1.close();
            st2.close();
            st3.close();
            
            success = true;
        }catch( SQLException e ) {
            throw new IllegalStateException("Cannot connect to the database: \n", e);
            //dbConnectionError.run();
        }
        
        
        return success;
    }
    
    
    /**Removes a particular user creator request. */
    public boolean removeCreatorRequest( String usern ){
        boolean success = false;
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            //This query should take in: username, title, country, content
            PreparedStatement st2 = conn.prepareStatement("CALL removeCreatorRequest( ? )");
            st2.setString( 1, usern );
            
            st2.executeQuery();
            
            
            //Close all statements and ResultSets
            st1.close();
            st2.close();
            
            success = true;
        }catch( SQLException e ) {
            throw new IllegalStateException("Cannot connect to the database: \n", e);
            //dbConnectionError.run();
        }
        
        
        return success;
    }
    
}
