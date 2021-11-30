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
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.geometry.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.*; 
import backendoperations.SearchScreenBackend;
import backendoperations.MinimalPostDetails;
import java.util.LinkedList;

/**Class SearchScreen
 * @author Julian Zelazny
 * @author Amimul Bhuiyan
 * @author Daniel Castro
 * @author Michael Desena
 * 
 * This class is the Frontend design for the search screen
 */
public class SearchScreen {
    private TextField searchTF;    
    private Button searchbtn;
    private Button resetbtn;
    
    private Button signinAndOutbtn;
    private Button createPostbtn;
    private Button requestToBeCreatorbtn;
    private Button seeUserRequestsbtn;
    
    private VBox blogListVB;
    private ScrollPane blogListSP;

    public VBox getSearchScreen(){
        /*Get moduele1HB. This includes textbox, search button amd reset search button*/
        HBox moduele1HB = getModuele1HBox();
        
        /*Get moduele2VBox This contains Sign-in/out, create  post button, requestToBeCreator button*/
        HBox moduele2HB = getModuele2HBox();
        
        /*Put moduele1 and 2 inside a vBox*/
        VBox moduele1and2VB = new VBox();
        moduele1and2VB.setAlignment( Pos.CENTER );
        moduele1and2VB.setSpacing( 35 );
        moduele1and2VB.getChildren().addAll( moduele2HB, moduele1HB );
               
        /*initialize a scrollpane to set the blog list on. Get a VB to get the blogs into 
         * and set it inside scrollpane*/
        blogListSP = new ScrollPane();
        blogListSP.setFitToWidth( true );
        blogListSP.setFitToHeight( true );
        blogListSP.setStyle("-fx-opacity: 80%");
        configureBlogList(false);
                       
        
        /*Make a VBox to hold all*/
        VBox searchscreenVB = new VBox();
        searchscreenVB.setAlignment( Pos.CENTER );
        searchscreenVB.setSpacing( 20 );
        searchscreenVB.getChildren().addAll( moduele1and2VB, blogListSP );
        
        
        return searchscreenVB;
    }
    
    
    /*initializes search textfield, search button and resetbutton. Returns them in a HBox*/
    private HBox getModuele1HBox(){
        //Make the search textfield
        searchTF = new TextField();
        searchTF.setPrefWidth( 200 );
        searchTF.setPromptText("Search by Country");
        
        //Make the search button and the resetsearch button
        searchbtn = new Button("Search");
        resetbtn = new Button("Reset Search");
        
        //Set the button functionality
        searchButtonFunctionality();
        resetButtonFunctionality();
        
        
        //Add the buttons and the textfields to the HBox
        HBox moduele1HB = new HBox();
        moduele1HB.setAlignment( Pos.CENTER );
        moduele1HB.setSpacing( 10 );
        moduele1HB.getChildren().addAll( searchTF, searchbtn, resetbtn );
        
        //return the HBox
        return moduele1HB;
    }
    
    
    /*Sets the functionality for the search button*/
    private void searchButtonFunctionality(){
        searchbtn.setOnAction((ActionEvent event)->{
            if( searchTF.getText().toLowerCase().trim().equals("") ){
                createAlert( AlertType.INFORMATION, "No Country Specified", "Please Specify a Country", null );
                return;
            }
            
            configureBlogList(true);
        });
    }
    
    
    /*Sets the functionality for the reset button*/
    private void resetButtonFunctionality(){
        resetbtn.setOnAction((ActionEvent event)->{
            configureBlogList(false);
            searchTF.clear();
        });
    }
    
    
    /*initializes the signin/outbutton and returns them in a VBox*/
    private HBox getModuele2HBox(){
        signinAndOutbtn = new Button("Sign-in/Out");
        createPostbtn = new Button("Create Post");
        seeUserRequestsbtn = new Button("See User Requests");
        
        //Set the button functionailties
        signinAndOutbtnFunctionality();
        createPostbtnFuntionality();
                
        //create vbox and enter the buttons in
        HBox moduele2HB = new HBox();
        moduele2HB.setAlignment( Pos.CENTER );
        moduele2HB.setSpacing( 10 );
        moduele2HB.getChildren().addAll( signinAndOutbtn, createPostbtn );
        
        /*Only add requestToBeCreatorbtn if the user is a viewer*/
        if( CurrentUser.getAccountType() == 2 ){
            requestToBeCreatorbtn = new Button("Request to Be Creator");
            requestToBeCreatorbtnFunctionality();
            moduele2HB.getChildren().add( requestToBeCreatorbtn );
        }
        
        /*Only add the seeUserRequestsbtn if the user is an admin*/
        if( CurrentUser.getAccountType() == 1 ){
            seeUserRequestsbtn = new Button("See User Requests");
            seeUserRequestsbtnFunctionality();
            moduele2HB.getChildren().add( seeUserRequestsbtn );
        }
        
        return moduele2HB; //return the vbox
    }
    
    private void seeUserRequestsbtnFunctionality(){
        /*Allows for admins to go to a screen which allows them to see
           and deal with userrequests
           This button only appears if user is an admin so no need to check account type*/
        seeUserRequestsbtn.setOnAction((ActionEvent ae)->{
            Main.rootNode.setCenter( new UserRequestsScreen().getUserRequestsScreen() );
        });
    }
    
    
    /*Adds the functionality to the requestToBeCreatorbtn*/
    private void requestToBeCreatorbtnFunctionality(){
        /*Viewer Requests to become a creator
           This button only appears if the user is a viewer so no need to check current user account type*/
        requestToBeCreatorbtn.setOnAction((ActionEvent event)->{
            SearchScreenBackend ssb = new SearchScreenBackend( Main.noDatabaseConnectionAlert() );
            
            if( ssb.requestToBeCreator( CurrentUser.getUsername() ) == true ){
                    Main.createAlert(AlertType.INFORMATION, "Request Made", "Request to be Creator made Successfully", null);
            }else{
                Main.createAlert(AlertType.INFORMATION, "Request Previously Made", "Your Request is Currently Being Reviewed", null);
            }
                       
        });
    }
    
    
    /*Adds the functionality for the signin button*/
    private void signinAndOutbtnFunctionality(){
        /*If you are signedin, then clicking this should bring you back to loginscreen and sign you out. 
           if you are not signed in, then clicking this should still bring you back to loginscreen
           
           to lessen the code, do the following: signout, and then go back to loginscreen.*/
        signinAndOutbtn.setOnAction((ActionEvent event)->{
            CurrentUser.signout();
            Main.rootNode.setCenter( new LoginScreen().getScreen( Screen.getPrimary().getVisualBounds() ) );
        });
        
    }
    
    
    /*Adds the functionality for the signin button*/
    private void createPostbtnFuntionality(){
        /*Must be logged in. And user must have a creator account
           User CANNOT be allowed to join if any one of these are not true*/
        createPostbtn.setOnAction((ActionEvent event)->{
            
            if( CurrentUser.doesCurrentUserExist() == false || CurrentUser.getAccountType() != 3 ){
                String content = "You must have an account with creator privlidges in order to make a post";
                createAlert( AlertType.INFORMATION, "MUST BE A CREATOR", content, null );
                return; //Stop execution of the method
            }
            
            //if the user has the right account, let user into the create blog screen
            Main.rootNode.setCenter( new PostBlogScreen().getPostBlogScreen() );
            
        });
    }
    

    /*Uswed to configure blogList
       if the country is specified, then get that particular country.*/
    private void configureBlogList(boolean isCountrySpecified){
        SearchScreenBackend ssb = new SearchScreenBackend( Main.noDatabaseConnectionAlert() );
        LinkedList<MinimalPostDetails> bList;
        if( isCountrySpecified == true ){ //a country is specified
            bList = ssb.getPostByCountry( searchTF.getText().toLowerCase().trim() );
            
        }else{
            //If you are here, it means there is no specified blogs. Just list all Blogs
            bList = ssb.getAllPosts();
        }
        
        
        blogListSP.setContent( getBlogListVB( bList ) );        
    }
    
    
    /*Initializes and adds ALL blogs in the form of buttons to bloglistVB*/
    private VBox getBlogListVB(LinkedList<MinimalPostDetails> bList){
        blogListVB = new VBox(); //VBox that will be returned with all posts
        blogListVB.setAlignment( Pos.CENTER );
        blogListVB.setSpacing( 20 );
        
        //Go through list, make a button with each list and post information on that button
        for(int i = 0; i<bList.size(); i++){
            MinimalPostDetails mpd = bList.get(i); //get object
            
            String cntry = mpd.getPostCountry();
            String title = mpd.getPostTitle();
            String author = mpd.getPosterUsername();
            
            String details = "Author: "+ author+"     Country: "+ cntry + "     Title: "+ title;
            
            //Create temporary Button
            Button temp = new Button( details );
            temp.setPrefWidth(500);
            
            /*Add functionality to the temporary button. All it does is take id and
               opens ViewBlogScreen with it*/
            temp.setOnAction((ActionEvent event)->{
                Main.rootNode.setCenter( new ViewBlogScreen( mpd ).getViewBlogScreen() );
            
            });
               
            //add the button to the blogListVB
            blogListVB.getChildren().add( temp );
        }
        
        return blogListVB;
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
