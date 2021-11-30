package backendoperations;


/**Class MinimalPostDetails
 * @author Amimul Bhuiyan
 * 
 * Holds Minimal Post Details
 */
public class MinimalPostDetails{
    private int postId;
    private String posterUsername;
    private String postTitle;
    private String postCountry;
    
    /**Initializes the Class
       @param pid The post id
       @param pUsern The username of the poster
       @param pTitle The title of the post
       @param pCountry The title of the country the post is about, */
    public MinimalPostDetails( int pid, String pUsern, String pTitle, String pCountry ){
        postId = pid;
        posterUsername = pUsern;
        postTitle = pTitle;
        postCountry = pCountry;
        
    }
    
    
    /**Gets the PostID
       @return The postid*/
    public int getpostid(){
        return postId;
    }
    
    
    /**Gets the username of the article author
       @return The username of the artile author*/
    public String getPosterUsername(){
        return posterUsername;
    }
    
    
    /**Gets the post title
       @return The title of the post*/
    public String getPostTitle(){
        return postTitle;
    }
    
    
    /**Gets the Country the post is about
       @return a String contianing the Country that the post is about*/
    public String getPostCountry(){
        return postCountry;
    }
    
}