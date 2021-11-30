package backendoperations;


/**Class DatabaseCredentials
 * @author Amimul Bhuiyan
 * 
 * Description: Holds the credentials needed to access database
 */
public class DatabaseCredentials{
    private static final String dburl = "jdbc:mysql://localhost:3306/travelblogdb";
    private static final String dbusername = "brokeBoyz";
    private static final String dbpassword = "alwaysBroke2021";
    
    
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