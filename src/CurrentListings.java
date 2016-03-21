import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class CurrentListings {

	private JFrame frame;
	private JButton btn;
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
					CurrentListings window = new CurrentListings();
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
	public CurrentListings() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		db= new Database();
		ArrayList<String> itemnames=new ArrayList<String>();
		ArrayList<String> itemdescr=new ArrayList<String>();
		ArrayList<String> category=new ArrayList<String>();
		ArrayList<String> auctionids=new ArrayList<String>();
		ArrayList<String> itemids= new ArrayList<String>();
		try {		
			java.sql.ResultSet rs= db.queryTable("select aid,item.itemid,category,description,name  from auction join item on auction.itemid=item.itemid where email='ossama.samir.ahmed@gmail.com'");
			parseResult(rs,itemnames, itemdescr, category,auctionids, itemids);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame = new JFrame();
		frame.setSize(window_width, window_height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btn= new JButton("go back");
		myresults= new JScrollPane();
		 myresults.setSize(window_width, window_height);
		 myresults.setOpaque(false);
		 myresults.getViewport().setOpaque(false);
		myresults.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		myresults.setViewportView(createrow(category,itemdescr, itemnames,auctionids, itemids));
	    frame.getContentPane().add(myresults, BorderLayout.CENTER);
	    frame.getContentPane().add(btn, BorderLayout.SOUTH);
	    frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	}
	public void parseResult(java.sql.ResultSet rs,ArrayList<String> itemnames,ArrayList<String> itemdescriptions,ArrayList<String> category,ArrayList<String> auctionids, ArrayList<String> itemids ) throws SQLException{
	    while ( rs.next () ) {
	    auctionids.add(rs.getString ( 1 ));
	    itemids.add(rs.getString ( 2 ));
	    category.add(rs.getString ( 3 ));
		itemdescriptions.add(rs.getString ( 4 ));
		itemnames.add(rs.getString ( 5 ));
	    }
	}
	
	public JPanel createrow(ArrayList<String> category,ArrayList<String> description,ArrayList<String> name,ArrayList<String> auctionids, ArrayList<String> itemids ){		
		 JPanel p = new JPanel();
		 p.setSize(window_width, window_height);
		 p.setLayout(new GridLayout(name.size(), 6, 10, 10));
		    for (int row = 0; row < name.size(); row++)  {
		      for (int col = 0; col < 6; col++) {
		        if (col == 0) {
		          JLabel temp= new JLabel(name.get(row));
		          p.add(temp);
		        } 
		        else if (col == 1) {
		        	JLabel temp= new JLabel(description.get(row));
			         temp.setPreferredSize(new Dimension(120,10));
			          p.add(temp);
			    } 
		        else if (col == 2) {
		        	JLabel temp= new JLabel(category.get(row));
			         temp.setMaximumSize(new Dimension(120, 10));
			          p.add(temp);
			    } 
		        else if(col==3) {
		        	JButton temp= new JButton("Delete Item");
		        	temp.setSize(10, 10);
		        	temp.setBorderPainted(false);
		        	temp.setOpaque(true);
		        	temp.setBackground(Color.BLACK);
		        	temp.setForeground(Color.white);
					p.add(temp);
					final String temp2=itemids.get(row);
					final String temp3=auctionids.get(row);
//					 adding action listener and directing it to the appropriate function
					 temp.addActionListener(new ActionListener() {
				            public void actionPerformed(ActionEvent evt) {
								try {
									db.insertIntoTable("delete from bid where aid="+temp3);
									db.insertIntoTable("delete from auction where aid="+temp3);
									db.insertIntoTable("delete from books where itemid="+temp2);
									db.insertIntoTable("delete from clothing where itemid="+temp2);
									db.insertIntoTable("delete from item where itemid="+temp2);
									name.clear();
									description.clear();
									category.clear();
									auctionids.clear();
									itemids.clear();
									java.sql.ResultSet rs= db.queryTable("select aid,item.itemid,category,description,name  from auction join item on auction.itemid=item.itemid where email='ossama.samir.ahmed@gmail.com'");
									parseResult(rs,name, description, category,auctionids, itemids);
									myresults.setViewportView(createrow(category,description,name,auctionids,itemids));
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				            }
					 });
		        }
		    else if(col==4) {
	        	JButton temp= new JButton("View All Bids");
	        	temp.setSize(10, 10);
	        	temp.setBorderPainted(false);
	        	temp.setOpaque(true);
	        	temp.setBackground(Color.BLACK);
	        	temp.setForeground(Color.white);
				p.add(temp);
//				final String temp2=auctionids.get(row);
//				 adding action listener and directing it to the appropriate function
				 temp.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent evt) {
			            }
				 });
		    }
		        else  {
		        	JButton temp= new JButton("Modify Item");
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
