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

public class BidsView {

	private JFrame frame;
	private JTextField textField;
	private JScrollPane myresults;
	private final int window_width=1200;
	private final int window_height=768;
	private Database db;
	private int aid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BidsView window = new BidsView();
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
	public BidsView() {
		aid=3;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		db= new Database();
		ArrayList<String> bidprice=new ArrayList<String>();
		ArrayList<String> bidid=new ArrayList<String>();
		ArrayList<String> rating=new ArrayList<String>();
		try {		
			java.sql.ResultSet rs= db.queryTable(" select price, bidid, rating from bid join users on users.email=bid.email where aid="+aid+"and status='Active'");
			parseResult(rs,bidprice, bidid, rating);
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
		myresults.setViewportView(createrow(bidprice,bidid,rating));
	    frame.getContentPane().add(myresults, BorderLayout.CENTER);
	    frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
		
		
	}
	
	public void parseResult(java.sql.ResultSet rs,ArrayList<String> bidprice,ArrayList<String> bidid,ArrayList<String> rating) throws SQLException{
	    while ( rs.next () ) {
	    bidprice.add(rs.getString ( 1 ));
		bidid.add(rs.getString ( 2 ));
		rating.add(rs.getString ( 3 ));
	    }
	}
	
	public JPanel createrow(ArrayList<String> bidprice,ArrayList<String> bidid,ArrayList<String> rating){		
		 JPanel p = new JPanel();
		 p.setSize(window_width, window_height);
		 p.setLayout(new GridLayout(bidprice.size(), 3, 10, 10));
		    for (int row = 0; row < bidprice.size(); row++)  {
		      for (int col = 0; col < 3; col++) {
		        if (col == 0) {
		          JLabel temp= new JLabel(bidprice.get(row));
		          temp.setSize(10,10);
		          p.add(temp);
		        } 
		        else if (col == 1) {
		        	JLabel temp= new JLabel(rating.get(row));
			         temp.setPreferredSize(new Dimension(10, 10));
			          p.add(temp);
			    } 
		        else  {
		        	JButton temp= new JButton("Accept BID");
		        	temp.setSize(10, 10);
		        	temp.setBorderPainted(false);
		        	temp.setOpaque(true);
		        	temp.setBackground(Color.BLACK);
		        	temp.setForeground(Color.white);
					p.add(temp);
					final String temp2=bidid.get(row);
//					 adding action listener and directing it to the appropriate function
					 temp.addActionListener(new ActionListener() {
				            public void actionPerformed(ActionEvent evt) {
								try {
									db.insertIntoTable("insert into approves (date, time, seller_email, buyer_email, bidid) values (current_date, current_time, (select auction.email from bid inner join auction on bid.aid = auction.aid where bidid ="+ temp2+"),(select bid.email from bid inner join auction on bid.aid = auction.aid where bidid ="+temp2+"),(select bid.bidid from bid inner join auction on bid.aid = auction.aid where bidid ="+temp2+"))");
									db.insertIntoTable("Update bid set status='Approved' where bidid="+temp2);
									db.insertIntoTable("Update bid set status='Closed' where aid="+aid+" and status<>'Approved'");
									db.insertIntoTable("Update auction set status='Closed' where aid="+aid);
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

