/**Class CurrentUser
 * @author Amimul Bhuiyan
 * 
 * Used to keep track of the current user that is using the program
 */
public class CurrentUser{
    private static String username; //used to specify what user is currently using the account
    private static int accountType; //used to store the account type of the user
    
    /**Set default values for username and accountType"*/
    public CurrentUser(){
        username = "NO CURRENT USER";
        accountType = -99; //-99 used to specify that there is no account
    }
    
    
    /**Used to get the username of the current user
       @return A String containing the username of the current user. Returns 
               NO CURRENT USER if there is no one logged in*/
    public static String getUsername(){
        return username;
    }
    
    
    /**Used to get the account type of the current user
       @return A integer containing the account type of the current user. 
               Returns -99 if no one is logged in*/
    public static int getAccountType(){
        return accountType;
    }
    
    
    /**Puts the username and accountType back to its default state*/
    public static void signout(){
        username = "NO CURRENT USER";
        accountType = -99; //-99 used to specify that there is no account
    }
    
    
    /**Stores the current users username and account type based on the parameter*/
    public static void signin(String u, int a){
        username = u;
        accountType = a;
    }
    
    /**Used to check if there is a current user
       @return true if the current user exists and false if otherwise*/
    public static boolean doesCurrentUserExist(){
        if( username.equals("NO CURRENT USER") ){
            return false;
        }
        
        return true;
    }
    
}
