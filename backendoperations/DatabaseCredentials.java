package backendoperations;


/**Class DatabaseCredentials
 * @author Amimul Bhuiyan
 * 
 * Description: Holds the credentials needed to access database
 */
public class DatabaseCredentials{
    /*THE URL, USERNAME, AND PASSWORD BELOW ARE INTENTIALLY CHANGED TO INCORRECT ONES IN ORDER
    TO PROTECT THE PRIVACY OF THE DATABASE. THUS, NO DATABASE CONNECTION WILL ACTUALLY OCCUR.*/
    private static final String dburl = "BLAH BLAH BLAH";
    private static final String dbusername = "BLAH BLAH BLAH";
    private static final String dbpassword = "BLAH BLAH BLAH";
    
    
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
