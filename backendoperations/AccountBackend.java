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

/**Class LoginBackend
   @author Amimul Bhuiyan
   
   This class is used to perform backend operations for the login screen.*/
public class AccountBackend{
    private final String dburl = DatabaseCredentials.getURL();
    private final String dbusername = DatabaseCredentials.getUsername();
    private final String dbpassword = DatabaseCredentials.getPassword();
    
    private String u;
    private String acctype;
    
    private Runnable dbConnectionError; //if a database connection error happens, run this function
    
    /**This method is just used to initialize the class
     * @param databaseConnectionError A runnable method is passed so that if database connection issues happen, all the necessary steps needed to be done, are done properly.*/
    public AccountBackend( Runnable databaseConnectionError ){
        u = "-999ERROR";
        acctype = "999ERROR";
        
        dbConnectionError = databaseConnectionError;//if a database connection error happens, run this function
    }
    
    
    /**used to validate user login details and make sure the user
       is a registered user
       @param usern The username
       @param passw The password
       @return A String array containing the username and acctype 
               if login is valid. An array of size 1 if otherwise*/
    public String[] userLogin( String usern, String passw ) {
        String[] a;
        
        //false validate means that the login details are not correct
        if( validateLoginDetails( usern, passw ) == false ){
            a = new String[1];
            return a;
        }
        
        //else the login details are correct
        a = new String[2];
        a[0] = u;
        a[1] = acctype;
        
        return a;
    }
    
    /*Used to validate the login details for user login*/
    private boolean validateLoginDetails( String usern, String passw ){
        boolean result = false;
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            PreparedStatement st2 = conn.prepareStatement("CALL getSpecifiedUser_TB(?, ?);");
            st2.setString( 1, usern );
            st2.setString( 2, passw );
            
            ResultSet rs1 = st2.executeQuery();
            
            if (rs1.next()) { //results found
                u = rs1.getString("username");
                acctype = String.valueOf( rs1.getInt("acctype") );
                
                result = true; //login details are correct
            } else {
               // no results back.login details are incorrecct.
               result = false;
            }

            //Close all statements and ResultSets
            st1.close();
            st2.close();
            rs1.close();
        }catch( SQLException e ) {
            //throw new IllegalStateException("Cannot connect to the database: \n", e);
            dbConnectionError.run();
        }
        
        
        return result;
    }
    
    
    
    /**Creates an New Account for a New User
       @return A String informing of potential mistakes to the password*/
    public String createAccount( String usern, String passw ){
        if( usern.length() > 19 || passw.length() > 19 ){
            return "Username or Password is too long. Keep UNDER 20 characters!";
        }
        
        if( usern.length() < 2 || passw.length() < 2 ){
            return "Username or Password is too short. Keep OVER 1 character!";
        }
        
        /*If accountCreationProcess returns false, it means the username is already taken*/
        if( accountCreationProcess( usern, passw ) == true ){
            return "Account Successfully Created";
            
        }else{
            return "Username is taken";
            
        }
    }
    
    
    /*Returns false if the username already exists.*/
    private boolean accountCreationProcess( String usern, String passw ){
        try( Connection conn = DriverManager.getConnection( dburl, dbusername, dbpassword ) ){
            PreparedStatement st1 = conn.prepareStatement("USE travelblogdb;");
            st1.executeQuery();
            
            //check to see if username exists. If it does, tell user to choose different username
            PreparedStatement st2 = conn.prepareStatement("CALL doesUsernameExist_TB( ? );");
            st2.setString( 1, usern );
            
            ResultSet rs1 = st2.executeQuery();
            if (rs1.next()) {
                //results found. Return false, username does exist
                return false;
            }
            
            //getting here means that that particular username does not exist
            //So create a new account for that user!
            PreparedStatement st3 = conn.prepareStatement("CALL createUser_TB( ?, ? );");
            st3.setString( 1, usern );
            st3.setString( 2, passw );
            st3.executeQuery();
            
            
            //Close all statements and ResultSets
            st1.close();
            st2.close();
            st3.close();
            rs1.close();
        }catch( SQLException e ) {
            //throw new IllegalStateException("Cannot connect to the database: \n", e);
            dbConnectionError.run();
        }
        
        return true;
    }
    
}
