package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;

import starsrus.MarketAccount;
import starsrus.Debug;

public class ManagerInterface extends JFrame {
	public JPanel cards;
	
	public JPanel panel;
    public JLabel message;
	public JButton add_interest, gen_stmt, list_active, gen_dter, cust_rep, del_trans, 
	open_market, close_market, set_stock_price, set_date; //debug ops
	public JPanel gen_stmt_panel, cust_rep_panel;
	
	ManagerInterface(JPanel cards) {
		this.cards = cards;
		
		
		// Add Interest
		add_interest = new JButton("Add Interest to all Market Accounts");		
		
		// Generate Monthly Statement
		gen_stmt = new JButton("Generate Monthly Statement");
		//gen_stmt_panel = new SOMETHING; // need another class
		// cards.add(gen_stmt_panel, "GENSTMT");
		// gen_stmt.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         CardLayout cl = (CardLayout)cards.getLayout();
        //         cl.show(cards, "GENSTMT");
        //     }
        // });
		
		// List Active Customers
		list_active = new JButton("List Active Customers");
			
		// Generate DTER
		gen_dter = new JButton("Generate Government Drug & Tax Evasion Report");		
		
		// Customer Report
		cust_rep = new JButton("Generate Customer Report");
		//cust_rep_panel = new SOMETHING; // need another class for this
		// cards.add(cust_rep_panel, "CUSTREP");
		// cust_rep.addActionListener(new ActionListener() {
        //     public void actionPerformed(ActionEvent e) {
        //         CardLayout cl = (CardLayout)cards.getLayout();
        //         cl.show(cards, "CUSTREP");
        //     }
        // });
		
		// Delete Transactions
		del_trans = new JButton("Delete all Transactions");	
		
		// Increment day by one
		open_market = new JButton("Open Market");

		// Store daily balance for all market accounts
		// Prevent people from trading stocks?
		close_market = new JButton("Close Market");

		set_stock_price = new JButton("Set Stock Price");
		set_date = new JButton("Set Date");

		// Action listeners

		close_market.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug d = new Debug();
				d.closeMarket();
				message.setText("Closed market for today.");
			}
		});

		message = new JLabel();
		
		panel = new JPanel(new GridLayout(4,3));
		panel.add(add_interest);
		panel.add(gen_stmt);
		panel.add(list_active);
		panel.add(gen_dter);
		panel.add(cust_rep);
		panel.add(del_trans);
		panel.add(open_market);
		panel.add(close_market);
		panel.add(set_stock_price);
		panel.add(set_date);
		panel.add(message);
		
	}
	
	
}