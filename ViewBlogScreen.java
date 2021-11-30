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
import backendoperations.ViewBlogBackend;
import backendoperations.MinimalPostDetails;
import javafx.event.*; 

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.Alert.AlertType;

/**Class ViewBlogScreen
 * @author Julian Zelazny
 * @author Amimul Bhuiyan
 * @author Daniel Castro
 * @author Michael Desena
 * This class is the Frontend design for viewing blogs
 */
public class ViewBlogScreen{
    private Label postTitleLB;
    private Label postCountryLB;
    private Label postAuthorLB;
    private Label postTextLB;
    private Button deletePostbtn;
    private Button returnToSSbtn; //return to searchscreen
    private MinimalPostDetails mpd;
    private String blogContent;
    
    /**Default constructor. Sets the blog details*/
    public ViewBlogScreen( MinimalPostDetails m ){
        mpd = m;
        
        //get the content of the blog
        ViewBlogBackend vbb = new ViewBlogBackend( Main.noDatabaseConnectionAlert() );
        blogContent = vbb.getBlogContent( mpd.getpostid() );
        
    }
    
    /**Returns the viewBlogScreen
       @return VBox A VBox containing the viewblog screen*/
    public VBox getViewBlogScreen(){
        /*get a a VBox containing the title, country and the author labels*/
        VBox postDetailsVB = getModuele1();
        
        /*Make a scrollpane and set the content to moduele2VB*/
        ScrollPane sp = new ScrollPane();
        sp.setMaxHeight( 300 );
        sp.setContent( getModuele2VB() );
        sp.setFitToWidth( true );
        //sp.setFitToHeight( true );
        sp.setStyle("-fx-background-color: black;"+
                    "-fx-opacity: 70%;");
        
        /*get moduele3; it contains the deletePost and return to search screen buttons*/
        VBox controlButtonsVB = getModuele3VB();
        
        
        /*Make a VBox to hold all modueles*/
        VBox viewBlogScreenVBox = new VBox();
        viewBlogScreenVBox.setAlignment( Pos.CENTER );
        viewBlogScreenVBox.setSpacing( 20 );
        viewBlogScreenVBox.getChildren().addAll( postDetailsVB, sp, controlButtonsVB );
        
        
        return viewBlogScreenVBox;
    }
    
    
    /*Returns a VBox containing the title, country and the author labels*/
    private VBox getModuele1(){
        //initialize all labels
        postTitleLB = new Label("Title: " + mpd.getPostTitle() );
        postCountryLB = new Label("Country: "+ mpd.getPostCountry() );
        postAuthorLB = new Label("Author: " + mpd.getPosterUsername() );
        
        //Put all labels in a VBox and retunr that
        VBox moduele1VB = new VBox();
        moduele1VB.setAlignment( Pos.CENTER_LEFT );
        moduele1VB.setSpacing( 10 );
        moduele1VB.getChildren().addAll( postTitleLB, postCountryLB, postAuthorLB );
        
        return moduele1VB;
    }
    
    
    /*returns a VB containing Label*/
    private VBox getModuele2VB(){
        /*Initialize the postTextLB*/
        postTextLB = new Label( blogContent );
        
        //make a vbox and put postTextLB inside. Return that vbox
        VBox moduele2VB = new VBox();
        moduele2VB.setAlignment(Pos.CENTER);
        moduele2VB.getChildren().addAll( postTextLB );
        
        //
        moduele2VB.setStyle("-fx-background-color: black");
        
        return moduele2VB;
    }
    
    
    /*get moduele3; it contains the deletePost and return to search screen buttons*/
    private VBox getModuele3VB(){
        deletePostbtn = new Button("Delete Post");
        returnToSSbtn = new Button("Return to Search Screen");
        
        deletePostbtnFunctionality();
        returnToSSbtnFunctionality();
        
        VBox moduele3 = new VBox();
        moduele3.setAlignment( Pos.CENTER );
        moduele3.setSpacing(10);
        moduele3.getChildren().addAll( deletePostbtn, returnToSSbtn );
        
        return moduele3;        
    }
    
    
    /*Sets the functionality for the deletePost button*/
    private void deletePostbtnFunctionality(){
        deletePostbtn.setOnAction((ActionEvent event)->{
            //if it is an admin or the user who wrote the post, then allow them to delete.
            if( CurrentUser.getAccountType() == 1 || CurrentUser.getUsername().equals( mpd.getPosterUsername() ) ){
                ViewBlogBackend vbb = new ViewBlogBackend( Main.noDatabaseConnectionAlert() );
                vbb.deleteBlogById( mpd.getpostid() );
                
                //Tell the user that the blog was deleted
                Main.createAlert( AlertType.INFORMATION, "Delete Successful", 
                                    "The Blog was Successfully deleted", null );
                return;//stop the method
            }
            
            //if the user did not write the post or is not an admin, tell them that they 
            //must be an admin or the author
            Main.createAlert( AlertType.INFORMATION, "Must be author or admin", 
                                "Must be the Author or an admin to delete the post", null);
            
            
        });
    }
    
    
    /*Sets the functionality to return to the search screen*/
    private void returnToSSbtnFunctionality(){
        //When clicked, go back to the seachscreen
        returnToSSbtn.setOnAction((ActionEvent event)->{
            Main.rootNode.setCenter( new SearchScreen().getSearchScreen() );
        });
    }
}
