import javafx.application.Application;

import javafx.stage.Screen;
import javafx.stage.Stage;

import javafx.geometry.Rectangle2D;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**Class Main
 * @author Julian Zelazny
 * @author Amimul Bhuiyan
 * @author Daniel Castro
 * @author Michael Desena
 * 
 * This class is used to start the travelblog application
 */
public class Main extends Application {
    public static BorderPane rootNode;
    
    public static void main(String[] args){
        try{
            launch(args);
            
        }catch( Exception e ){
            e.printStackTrace();
            
        }finally{
            System.exit(0);
            
        }
    }

    public void start(Stage stage){
        //get screen size
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        
        //LoginScreen loginScreen = new LoginScreen();
        
        stage.setTitle("Travel Blog");
        
        rootNode = new BorderPane();
        Scene scene = new Scene( rootNode, 850, 600 );
        scene.getStylesheets().add("assets/stylesheet.css");
        stage.setScene(scene);
        
        //rootNode.getStylesheets().add("assets/stylesheet.css");
        /*rootNode.setStyle("-fx-background-image: url(assets/850x600_beach.jpg"); " + 
                          "-fx-background-position: center center; " + 
                          "-fx-background-repeat: stretch; ");*/
        rootNode.setCenter(new LoginScreen().getScreen(primaryScreenBounds));
        
        stage.setResizable(false);
        stage.show();
    }
    
    
    /**This method creates generic alert with the given parameters set to them
       @param alertType The type of alert
       @param title The title of the alter
       @param content The content of the alert
       @param image An image you might want to include for the alert. (A generic one will be included if null)*/
    public static void createAlert( AlertType alertType, String title, String content, Image image ){
        /*Create an alert, set its title, header, and content*/
        Alert genericAlert = new Alert( alertType );
        genericAlert.setTitle( title );
        genericAlert.setHeaderText( null );
        genericAlert.setContentText( content );
        
        /*If an image is sent, set the image*/
        if( image != null ){
            genericAlert.setGraphic( new ImageView( image ) );
        }
        
        /*Show the alert*/
        genericAlert.showAndWait();
    }
    
    
    /**Returns a runnable function that can be run in the case of a database error
       @returns A runnable runction that Creates an alert in case of a database error*/
    public static Runnable noDatabaseConnectionAlert(){
        Runnable nodbConnection = 
            () ->{
                createAlert( AlertType.ERROR, "No Connection to Database", "Connection to the database cannot be made", null);
                
            };
        
        return nodbConnection;
    }
}
