import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainView extends JPanel {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public MainView(JFrame x) {
		super();
		frame=x;
		this.setOpaque(true);
	    this.setLayout(null);
		initialize();
		this.repaint();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
JButton btnNewButton = new JButton("New button");
		
		JButton btnViewListings = new JButton("View Listings");
		
		JButton button = new JButton("Add Listing");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddListingactionPerformed(e);
			}
		});
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(167)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnViewListings)
						.addComponent(btnNewButton))
					.addContainerGap(156, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(48)
					.addComponent(btnNewButton)
					.addGap(18)
					.addComponent(btnViewListings)
					.addGap(33)
					.addComponent(button)
					.addContainerGap(114, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
	public void AddListingactionPerformed(ActionEvent e) {
		
		frame.remove(this);
		SellingItemView x=new SellingItemView(frame);
		frame.setFocusable(true);
		x.setVisible(true);
		frame.add(x);
	        frame.validate();
	        frame.repaint();
	        x.requestFocusInWindow();
		frame.setVisible(true);
	}

}
