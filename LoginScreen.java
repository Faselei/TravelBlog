import javafx.geometry.Rectangle2D;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.transform.Scale; 

import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import javafx.event.*; 

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Alert.AlertType;

import backendoperations.AccountBackend;

/**Class LoginScreen
 * @author Julian Zelazny
 * @author Amimul Bhuiyan
 * 
 * This class is the frontend display for the travelblog login scree
 */
public class LoginScreen {
    private TextField loginUsername;
    private PasswordField loginPassword;
    private Button login;
    private Button guestButton;
    
    private TextField newUsername;
    private PasswordField newPassword1;
    private PasswordField newPassword2;
    
    private Button createAccountButton;

    /**
     * Constructor for objects of class LoginScreen
     */
    public HBox getScreen(Rectangle2D primaryScreenBounds) {
        //get screen size
        //Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        HBox layoutHB = new HBox(primaryScreenBounds.getMaxX() / 4);
        layoutHB.setAlignment(Pos.CENTER);

        /* */
        VBox loginLayout = new VBox(25);
        loginLayout.setAlignment(Pos.CENTER);

        VBox createAcountLayout = new VBox(25);
        createAcountLayout.setAlignment(Pos.CENTER);

        layoutHB.getChildren().addAll(loginLayout,createAcountLayout);

        /* position everything */

        /* returning user */
        Label returningUserLabel = new Label("Returning Users");
        returningUserLabel.setScaleX(2);
        returningUserLabel.setScaleY(2);
        
        /* create account */
        Label createNewUserLabel = new Label("Create New User");
        createNewUserLabel.setScaleX(2);
        createNewUserLabel.setScaleY(2);
        

        
        /*Call the setupLogintextfieldsAndButtons method to 
           initialize the login textfields as well as the login buttons*/
        setupLogintextfieldsAndButtons();
        
        /*Call the setupcreateUsertextfieldsAndButtons method to 
           initialize the createnewuser textfields as well as the create account buttons*/
        setupcreateUsertextfieldsAndButtons();
        
        
        /*Sets the actions of the buttons*/
        setupButtonActions();
        

        
        //login side javafx stuff
        loginLayout.getChildren().addAll(returningUserLabel,loginUsername,loginPassword,login, guestButton);

        //create account javafx stuff
        createAcountLayout.getChildren().addAll(createNewUserLabel,newUsername,newPassword1,newPassword2,createAccountButton);


        return layoutHB;
    }
    
    
    /*Call the setupLogintextfieldsAndButtons method to 
           initialize the login textfields as well as the login buttons*/
    private void setupLogintextfieldsAndButtons(){
        //username text input
        loginUsername = new TextField();
        loginUsername.setPromptText("Username");
        //loginUsername.setScaleX(2);
        //loginUsername.setScaleY(2);

        //password text imput
        loginPassword = new PasswordField();
        loginPassword.setPromptText("Password");
        //loginPassword.setScaleX(2);
        //loginPassword.setScaleY(2);

        //login button
        login = new Button("login");
        //login.setScaleX(2);
        //login.setScaleY(2);
        
        
        //guest button
        guestButton = new Button("Continue As Guest");
    }
    
    
    /*Call the setupcreateUsertextfieldsAndButtons method to 
           initialize the createnewuser textfields as well as the create account buttons*/
    private void setupcreateUsertextfieldsAndButtons(){
         //new username
        newUsername = new TextField();
        newUsername.setPromptText("username");
        //newUsername.setScaleX(2);
        //newUsername.setScaleY(2);

        //password 1 & 2
        newPassword1 = new PasswordField();
        newPassword1.setPromptText("Password");
        //newPassword1.setScaleX(2);
        //newPassword1.setScaleY(2);
        
        newPassword2 = new PasswordField();
        newPassword2.setPromptText("ReEnter Password");
        //newPassword2.setScaleX(2);
        //newPassword2.setScaleY(2);

        //create account button
        //login button
        createAccountButton = new Button("Create Account");
        //createAccountButton.setScaleX(2);
        //createAccountButton.setScaleY(2);
    }
    
    
    /*Sets the actions of the buttons*/
    private void setupButtonActions(){
        AccountBackend accountbackend = new AccountBackend( Main.noDatabaseConnectionAlert() );
        
        /*Get the username and password.
           get credentials from database
           update currentuser*/
        login.setOnAction((ActionEvent event)->{
            String u = loginUsername.getText();
            String p = loginPassword.getText();
            
            String[] credentials =  accountbackend.userLogin( u, p );
            if( credentials.length == 1 ){ //no account found
                //Tell the user that the login credentials are invalid
                createAlert( AlertType.ERROR, "Login Error" ,"INCORRECT LOGIN CREDENTIALS", null );
                return;
            }
            
            //If the code reaches here, it means the login was successfull
            CurrentUser.signin( credentials[0], Integer.parseInt( credentials[1] ) );
            
            
            //Nothing has to be passed here because The CurrentUserClass is Static
            Main.rootNode.setCenter( new SearchScreen().getSearchScreen() );
        });

        
        
        
        //what happends when the user presses Create Account
        createAccountButton.setOnAction((ActionEvent event)->{
            String u = newUsername.getText();
            String p = newPassword1.getText();
            String p2 = newPassword2.getText();
            
            
            //Check if fields are blank
            if( newUsername.getText().trim().equals("") || newPassword1.getText().trim().equals("") || 
                    newPassword2.getText().trim().equals("") ){
                Main.createAlert( AlertType.WARNING, "Blank fields", "When creating an account, make sure fields are not blank", null );
            }
            
            
            //The passwords do not match
            if( p.equals( p2 ) == false ){
                createAlert( AlertType.WARNING, "Account Creation Error" ,"Passwords do not match", null );
                return;
            }
            
            
            String message = accountbackend.createAccount( u, p );
            createAlert( AlertType.INFORMATION, "Information" , message , null );
            newUsername.clear();
            newPassword1.clear();
            newPassword2.clear();
        });
        
        
        
        
        
        //Dont have to login, just open the search blog screen
        guestButton.setOnAction((ActionEvent event)->{
            //SHOULD OPEN THE SEARCH SCREEN
            CurrentUser.signin( "Guest", -50 );
            Main.rootNode.setCenter( new SearchScreen().getSearchScreen() );
        });
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

