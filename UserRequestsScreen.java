import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color; 
import javafx.scene.transform.Scale; 
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.geometry.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.*; 
import backendoperations.UserRequestsBackend;
import java.util.LinkedList;

/**Class UserRequestScreen
 * @author Julian Zelazny
 * @author Amimul Bhuiyan
 * 
 * This screen can only be accessed by the admin and shows a list of users who have requested
 * to become a creator. 
 */
public class UserRequestsScreen{
    private ScrollPane requestsSP;
    
    /*Returns a vbox containing the userRequests Screen*/
    public VBox getUserRequestsScreen(){
        Label titleLabel = new Label("User Requests Screen");
        titleLabel.setStyle("-fx-background-color: black");
        
        //make a button to return to the main menu
        Button returnToMainMenubtn = new Button("Return to Main Menu");
        //add functionality to make the button return to main menu
        returnToMainMenubtn.setOnAction((ActionEvent event)->{
            Main.rootNode.setCenter( new SearchScreen().getSearchScreen() );
        });
        
        
        
        /*Initialize a scrollpane to put the list of user requests in. */
        requestsSP = new ScrollPane();
        requestsSP.setFitToWidth( true );
        requestsSP.setFitToHeight( true );
        requestsSP.setStyle("-fx-background-color: black;"+
                    "-fx-opacity: 70%;");
        configurerequestsSP();
        
        
        /*Make a VBox to hold all*/
        VBox userRequestsScreenVB = new VBox();
        userRequestsScreenVB.setAlignment( Pos.CENTER );
        userRequestsScreenVB.setSpacing( 40 );
        userRequestsScreenVB.getChildren().addAll( titleLabel, returnToMainMenubtn, requestsSP );
        
        
        return userRequestsScreenVB;
    }
    
    
    /*Used to configure the Scrollpane and add */
    private void configurerequestsSP(){
        /*Get the list of users who made the requests to become a creator from the database*/
        UserRequestsBackend urb = new UserRequestsBackend( Main.noDatabaseConnectionAlert() );
        LinkedList<String> usersthatRequested = urb.getCreatorRequests(); //list of users who made the request
                
        requestsSP.setContent( getuserRequestsVB( usersthatRequested, urb ) ); //Set the scrollpane with the userRequestsVBox
        
    }

    
    /*Makes a vbox containing all the user requests as well as the button options to
       accept or deny the requests*/
    private VBox getuserRequestsVB( LinkedList<String> usersthatRequested, UserRequestsBackend urb ){
        VBox userRequestsVB = new VBox(); //vbox to be returned with all the userRequests and accpt/deny options
        userRequestsVB.setAlignment( Pos.CENTER );
        userRequestsVB.setSpacing( 20 );
        userRequestsVB.setStyle("-fx-background-color: black");
                
        /*Go through the usersthatRequestedList, and make accept or deny buttons for each user.
           Then add that to the userRequestsVB*/
        for(int i = 0; i<usersthatRequested.size(); i++ ){
            String usern = usersthatRequested.get(i); // get String
            Button acceptbtn = new Button("Accept Request"); //used to accept request
            Button denybtn = new Button("Deny Request"); //used to deny request
            
            
            //When accept button is clicked, set the account type of the user to a creator account
            acceptbtn.setOnAction((ActionEvent event)->{
                
                if( urb.setAccountToCreator( usern ) == true ){
                    Main.createAlert( AlertType.INFORMATION, "Success", usern + " now has creator priviledges", null );
                    
                }
                
                configurerequestsSP(); //reset the Requests list
            });
            
            
            //When deny button is created, just remove the request
            denybtn.setOnAction((ActionEvent event)->{
                
                if( urb.removeCreatorRequest( usern ) == true ){
                    Main.createAlert( AlertType.INFORMATION, "Success", usern + " has been denied creator priviledges", null );
                    
                }
                
                configurerequestsSP(); //reset the requests list
            });
            
            
            
            //make a container to hold the buttons with thier corresponding username
            HBox container = new HBox();
            container.setAlignment( Pos.CENTER );
            container.setSpacing( 30 );
            container.getChildren().addAll( new Label( usern ), acceptbtn, denybtn );
            
            //Add container to userRequestsVB
            userRequestsVB.getChildren().add( container );
        }
        
        
        return userRequestsVB;
    }
    
}
