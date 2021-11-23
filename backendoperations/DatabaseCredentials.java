package backendoperations;


/**Class DatabaseCredentials
 * @author Amimul Bhuiyan
 * 
 * Description: Holds the credentials needed to access database
 */
public class DatabaseCredentials{
    /*THE DATABASE CREDENTIALS WERE CHANGED. THE ONES BELOW ARE NOT THE ACTUAL CREDENTIALS.
       THIS WAS DONE TO PROTECT THE DATABASE. THE ONES YOU SEE BELOW ARE RANDOM CREDENTIALS
       THAT WE ADDED*/
    private static final String dburl = "RANDOMCREDENTIALS";
    private static final String dbusername = "RANDOMCREDENTIALS";
    private static final String dbpassword = "RANDOMCREDENTIALS";
    
    
    /**Used to get the url for the database
       @return A String that holds the database url*/
    public static String getURL(){
        return dburl;
    }
    
    
    /**Used to get the username for the database
       @return A String that holds the username for the database*/
    public static  String getUsername(){
        return dbusername;
    }
    
    
    /**Used to get the password to access the database
       @return A String containing the db password*/
    public static String getPassword(){
        return dbpassword;
    }
}