import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class Searchview {

	private JFrame frame;
	private JTextField textField;
	private JScrollPane myresults;
	private final int window_width=1200;
	private final int window_height=768;
	private Database db;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Searchview window = new Searchview();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Searchview() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		db= new Database();
		ArrayList<String> itemnames=new ArrayList<String>();
		ArrayList<String> itemdescr=new ArrayList<String>();
		ArrayList<String> prices=new ArrayList<String>();
		ArrayList<String> auctionids=new ArrayList<String>();
		try {		
			java.sql.ResultSet rs= db.queryTable("select name, description, initialprice, aid from item join auction on item.itemid=auction.itemid");
			parseResult(rs,itemnames, itemdescr, prices,auctionids);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame = new JFrame();
		frame.setSize(window_width, window_height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myresults= new JScrollPane();
		 myresults.setSize(window_width, window_height);
		 myresults.setOpaque(false);
		 myresults.getViewport().setOpaque(false);
		 myresults.setLocation(426,38+30);
		myresults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		myresults.setViewportView(createrow(itemnames,itemdescr,prices,auctionids));
	    frame.getContentPane().add(myresults, BorderLayout.CENTER);
	    frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
		textField = new JTextField("search...");
		frame.getContentPane().add(textField, BorderLayout.NORTH);
		textField.setColumns(10);
		textField.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{ 
					String querySQL = "select name, description, initialprice, aid from item join auction on item.itemid=auction.itemid WHERE item.description LIKE '%"+textField.getText()+"%'";
					try {
						java.sql.ResultSet rs= db.queryTable(querySQL);
						itemnames.clear();
						itemdescr.clear();
						prices.clear();
						auctionids.clear();
						parseResult(rs,itemnames, itemdescr, prices,auctionids);
						myresults.setViewportView(createrow(itemnames,itemdescr,prices,auctionids));
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		
	}
	
	public void parseResult(java.sql.ResultSet rs,ArrayList<String> itemnames,ArrayList<String> itemdescriptions,ArrayList<String> prices,ArrayList<String> auctionids ) throws SQLException{
	    while ( rs.next () ) {
		itemnames.add(rs.getString ( 1 ));
		itemdescriptions.add(rs.getString ( 2 ));
		prices.add(rs.getString ( 3 ));
		auctionids.add(rs.getString ( 4 ));
	    }
	}
	
	public JPanel createrow(ArrayList<String> itemnames,ArrayList<String> itemdescriptions,ArrayList<String> prices,ArrayList<String> auctionids ){		
		 JPanel p = new JPanel();
		 p.setSize(window_width, window_height);
		 p.setLayout(new GridLayout(itemnames.size()-1, 4, 10, 10));
		    for (int row = 1; row < itemnames.size(); row++)  {
		      for (int col = 0; col < 4; col++) {
		        if (col == 0) {
		          JLabel temp= new JLabel(itemnames.get(row));
		          temp.setSize(window_width/5, window_height/10);
		          p.add(temp);
		        } 
		        else if (col == 1) {
		        	JLabel temp= new JLabel(itemdescriptions.get(row));
			         temp.setPreferredSize(new Dimension(window_width/5, window_height/10));
			          p.add(temp);
			    } 
		        else if (col == 2) {
		        	JLabel temp= new JLabel(prices.get(row));
			         temp.setMaximumSize(new Dimension(50, window_height/10));
			          p.add(temp);
			    } 
		        else  {
		        	JButton temp= new JButton("BID");
		        	temp.setSize(10, 10);
		        	temp.setBorderPainted(false);
		        	temp.setOpaque(true);
		        	temp.setBackground(Color.BLACK);
		        	temp.setForeground(Color.white);
					p.add(temp);
					final String temp2=auctionids.get(row);
//					 adding action listener and directing it to the appropriate function
					 temp.addActionListener(new ActionListener() {
				            public void actionPerformed(ActionEvent evt) {
				            	Object result = JOptionPane.showInputDialog(frame, "Enter your bid here");
				            	System.out.println(result);
								try {
									db.insertIntoTable("insert into bid(price, status, email, aid) values("+result+",'Active','ossama.samir.ahmed@gmail.com',"+temp2+")");
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
				            }
					 });
		        } 
		       }
		    }
		    p.setOpaque(false);
		    p.setBackground(new Color(0,0,0,65));
			return p;
	}
}
