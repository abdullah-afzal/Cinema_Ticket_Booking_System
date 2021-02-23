
import javax.swing.JFrame;


public class MovieDetails {

    JFrame MDetailsFr;
    String given_id;
    
    
    
    public MovieDetails() {
        initMovieDetails();
    }
    
    public void initMovieDetails(){
        
        MDetailsFr = new JFrame("Details");
        
      
        MDetailsFr.setLayout(null);
        MDetailsFr.setBounds(100, 0, 700, 529);
        MDetailsFr.setResizable(false);
        MDetailsFr.setVisible(true);
        
        
    }
    
}
