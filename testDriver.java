import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import backendoperations.AccountBackend;

/***/
public class testDriver{
    public static void main(String[] args){
        
        AccountBackend accountbackend = new AccountBackend();
        
        String[] a =  accountbackend.userLogin( "marysullivan", "IamtheMary20" );
        System.out.println( a[0] );
        System.out.println( a[1] );
        
        String[] b =  accountbackend.userLogin( "marysulliva", "IamtheMary20" );
        if( b.length == 1 ){
            System.out.println( b.length );
            System.out.println( a.length );
        }
        
        /*
        if( accountbackend.createAccount( "oscar", "ropes" ) == true ){
            String[] c =  accountbackend.userLogin( "oscar", "ropes" );
            System.out.println( c[0] );
            System.out.println( c[1] );
        }else{
            System.out.println("False!");
        }
        */
       
       String usern = "19";
       String passw = "jgjgjgj";     
       
       if( usern.length() > 19 || passw.length() > 19 ){
           System.out.println("WRONG");
        }
    }
}
