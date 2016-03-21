import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JEditorPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class SellingItemView extends JPanel {

	private JFrame frame;
	private final int window_width=1200;
	private final int window_height=768;
	private Database db;
	private JTextField textField;
	private JTextField textField_1;
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public SellingItemView(JFrame x) {
		super();
		frame=x;
		System.out.println("ho");
		this.setOpaque(true);
	    this.setLayout(null);
		initialize();
		this.repaint();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		db= new Database();
		frame.setSize(window_width, window_height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Item Name");
		
		JLabel label = new JLabel("Item Description");
		
		JLabel label_1 = new JLabel("Category");
		
		JEditorPane editorPane = new JEditorPane();
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItem("Furniture");
		comboBox.addItem("Car");
		comboBox.addItem("Sports");
		comboBox.addItem("Clothing");
		comboBox.addItem("Ticket");
		comboBox.addItem("Book");
		comboBox.addItem("Electronics");
		comboBox.addItem("Household");
		JButton btnNewButton = new JButton("Sell Item");
		btnNewButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  try {
					   db.insertIntoTable("insert into item(category,description,name,deliveryoption) values ('"+comboBox.getSelectedItem()+"','"+editorPane.getText()+"','"+textField.getText()+"','pickup')");
					   java.sql.ResultSet rs= db.queryTable("select currval('item_id_seq')");
					   int itemid=0;
					  while ( rs.next () ) {
							itemid= rs.getInt ( 1 );
							System.out.println(itemid);
						    }
					  db.insertIntoTable("insert into auction(initialprice, status,email,itemid) values ("+textField_1.getText()+",'Active','"+"ossama.samir.ahmed@gmail.com"+"',"+itemid+")");
					  swicthtoMainMenu();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			  } 
			} );

		JLabel lblNewLabel_1 = new JLabel("Sell An Item");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Initial Price");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JButton button = new JButton("GO BACK");
		button.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
				  swicthtoMainMenu();
			  } 
			} );
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 487, GroupLayout.PREFERRED_SIZE)))
							.addGap(169)
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(180, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(626, Short.MAX_VALUE)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE)
					.addGap(289))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(27)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addGap(24)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addComponent(textField, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(66)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE))
					.addGap(53)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(42)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(60)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(86, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}
	public void swicthtoMainMenu(){
		frame.remove(this);
		MainView x=new MainView(frame);
		frame.setFocusable(true);
		x.setVisible(true);
		frame.add(x);
	        frame.validate();
	        frame.repaint();
	        x.requestFocusInWindow();
		frame.setVisible(true);
	}
}
