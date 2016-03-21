
import javax.swing.*;


public class FFS {
	static JFrame app;
	static MainView main;
	

	public static void main(String[] args) throws Exception
	{
		
		final int window_width=CONSTANTS.WINDOW_WIDTH;
		final int window_height=CONSTANTS.WINDOW_HEIGHT;
		app = new JFrame("FFS");
		main= new MainView(app);
		SwingUtilities.invokeLater(new Runnable() 
	    {
	      @Override
	      public void run()  
	      {  	
	    		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    		app.setSize(window_width, window_height); 		
	    		main.setVisible(true);	
	    		app.add(main);		
	    		app.setVisible(true);
	    	
	      }
	    });
	}

}
