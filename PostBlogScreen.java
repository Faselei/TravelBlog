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
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.geometry.*;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.*;

import javafx.scene.control.Alert.AlertType;
import backendoperations.PostBlogBackend;

/**Class PostBlogScreen
 * @author Julian Zelazny
 * @author Amimul Bhuiyan
 * 
 * This class is the Frontend design for the Posting Blog screen
 */
public class PostBlogScreen{
    private TextField blogTitleTF;
    private TextField countryTF;
    private TextArea postTA;
    private Button postbtn;
    private Button returnToSSbtn; //return to search screen

    public VBox getPostBlogScreen(){
        /*Returns the pagetitle HBox. Just holds title of screen*/
        HBox pageTitleHB = getModuele1HB();
        
        
        /*This HBox contains textfields which ask for the details regarding the post. like post title and country
           that the post is talking about. */
        HBox postDetailsHB = getModuele2HB();
        
        
        /*Make a textarea for the user to type in*/
        postTA = new TextArea();
        postTA.setPromptText("Start Writing!!!");

        
        /*Holds the buttons like post and return to search screen*/
        VBox controlButtonsVBox = getModuele4();
        
        
        /*Make a VBox to hold all*/
        VBox blogPostScreenVBox = new VBox();
        blogPostScreenVBox.setAlignment( Pos.CENTER );
        blogPostScreenVBox.setSpacing( 20 );
        blogPostScreenVBox.getChildren().addAll( pageTitleHB, postDetailsHB, postTA, controlButtonsVBox );
        
        
        return blogPostScreenVBox;
    }
    
    
    /*Just returns the label for the screen*/
    private HBox getModuele1HB(){
        Label pagetitle = new Label("Create Post");
        
        //put the title label in a HBox and return that hbox
        HBox pageTitleHB = new HBox();
        pageTitleHB.setAlignment(Pos.CENTER);
        pageTitleHB.setSpacing( 20 );
        pageTitleHB.getChildren().add( pagetitle );
        
        
        
        return pageTitleHB;
    }
    
    
    /*Returns a HBox containing textfields for taking down country information*/
    private HBox getModuele2HB(){
        //Make Labels specifying each textfield
        Label blogTitleLabel = new Label("Blog Title: ");
        Label countryLabel = new Label("Country: ");
        
        //Make a textfield for post title and one for country title
        blogTitleTF = new TextField();
        blogTitleTF.setPromptText("Title of your Post");
        
        countryTF = new TextField();
        countryTF.setPromptText("Name of the Country");
        
        
        //Put blogTitleLabel and countryLabel in the same HBoxes
        HBox pTitleHB = new HBox();
        pTitleHB.setAlignment( Pos.CENTER );
        pTitleHB.setSpacing( 10 );
        pTitleHB.getChildren().addAll( blogTitleLabel, blogTitleTF );
        
        
        //Put countryLabel and countryTF in the same HBox
        HBox pCountryHB = new HBox();
        pCountryHB.setAlignment( Pos.CENTER );
        pCountryHB.setSpacing( 10 );
        pCountryHB.getChildren().addAll( countryLabel, countryTF );
        
        
        //Put the Two HBoxes you created above in another HBox and return that.
        HBox moduele2HB = new HBox();
        moduele2HB.setAlignment( Pos.CENTER );
        moduele2HB.setSpacing( 30 );
        moduele2HB.getChildren().addAll( pTitleHB, pCountryHB );
        
        
        return moduele2HB;
    }
    
    
    /*Holds buttons like post and return to search screen*/
    private VBox getModuele4(){
        postbtn = new Button("Post");
        returnToSSbtn = new Button("Return to Search Screen");
        
        postbtnFunctionality();
        returnToSSbtnFunctionality();
        
        VBox moduele4VBox = new VBox();
        moduele4VBox.setAlignment( Pos.CENTER );
        moduele4VBox.setSpacing( 10 );
        moduele4VBox.getChildren().addAll( postbtn, returnToSSbtn );
        
        return moduele4VBox;
    }
    
    
    /*Adds functionality to the post blog button*/
    private void postbtnFunctionality(){        
        postbtn.setOnAction((ActionEvent event)->{
            //Check to make sure that the textfields are not empty
           if( areFieldsEmpty() == true ){
               createAlert(AlertType.INFORMATION, "Empty Fields", "Make sure to fill all fields!", null);
               return; //stop the continuation of the method
           }
           
           //Check to see if the textfields are over the limit
           if( isOverCharacterLimit() == true ){
               createAlert(AlertType.INFORMATION, "Over character Limit", "Keep below the following limit:\nTitle: 50 characters\nCountry: 50 characters\nContent: 1000 characters", null);
               return; //stop the continuation of the method
           }
            
           /*You do not need to check if this user is a creator since in order to reach this page, you have
           to be a creator account anyways*/
           PostBlogBackend pb = new PostBlogBackend( Main.noDatabaseConnectionAlert() );
           pb.postBlog( CurrentUser.getUsername(), blogTitleTF.getText().trim(), countryTF.getText().toLowerCase().trim(), postTA.getText() );
           
           //Tell user that their post was successful
           createAlert( AlertType.INFORMATION, "Post Successful", "Your post was successfully saved!", null );
           
        });
    }
    
    
    /*Adds functionality to the return to search screen button*/
    private void returnToSSbtnFunctionality(){
        returnToSSbtn.setOnAction((ActionEvent event)->{
            Main.rootNode.setCenter( new SearchScreen().getSearchScreen() );
        });
    }
    
    
    /*Used to check if any of the fields are empty like the title, country and content textFileds/areas*/
    private boolean areFieldsEmpty(){        
        if( blogTitleTF.getText().trim().isEmpty() || countryTF.getText().trim().isEmpty()
            || postTA.getText().trim().isEmpty() ){
            return true;
        }
        
        return false;
    }
    
    /*Used to hceck if the textfields are over the limit. */
    private boolean isOverCharacterLimit(){
        if( blogTitleTF.getText().length() > 50 || countryTF.getText().length() > 50 || postTA.getText().length() > 1000 ){
            return true; //over the character limit
        }
        
        return false; //not over the limit
    }
    
    
    /*This method creates generic alert with the given parameters set to them*/
    private void createAlert( AlertType alertType, String title, String content, Image image ){
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
}
