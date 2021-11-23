import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/***/
public class testingDB{
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/travelblogdb";
        String username = "brokeBoyz";
        String password = "alwaysBroke2021";
        
        System.out.println("Connecting to the database...");
        
        try( Connection conn = DriverManager.getConnection( url, username, password ) ){
            System.out.println( "Database Connected" );
            PreparedStatement s1 = conn.prepareStatement("USE travelblogdb;");
            //ResultSet r1 = s1.executeQuery();
            s1.executeQuery();
            
            //PreparedStatement statement = conn.prepareStatement("CALL getAll( 'Samwel' );");
            //PreparedStatement statement = conn.prepareStatement("CALL getAll( ? );");
            PreparedStatement statement = conn.prepareStatement("CALL getSpecifiedUser_TB( 'marysullivan', 'IamtheMary20' );;");
            String u = "Samwel";
            //statement.setString( 1, u );
            ResultSet r2 = statement.executeQuery();
            
            while( r2.next() == true ) {
                System.out.println( String.format("username=%s", r2.getString("username") ) );
            }
            
            
            /*
            PreparedStatement statement = conn.prepareStatement("SELECT username FROM testpass2");
            ResultSet r2 = statement.executeQuery();
            */
            /*
            while( r2.next() == true ) {
                System.out.println( String.format("username=%s", r2.getString("username") ) );
            }
            */
            /*
            r2.next();
            System.out.println( r2.getString("username") );
            */
            /*
            PreparedStatement statement = conn.prepareStatement("SELECT COUNT(*) FROM testpass2");
            
            ResultSet rs = statement.executeQuery();
            System.out.println( rs.getInt("COUNT(*)") );
            */
            
            statement.close();
            r2.close();
            //r1.close();
            s1.close();
            
            System.out.println( "EXECUTION DONE" );
        }catch( SQLException e ) {
            throw new IllegalStateException("Cannot connect to the database: \n", e);
        }
        
    }
    
}
