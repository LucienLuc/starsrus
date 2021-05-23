package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import starsrus.Debug;
import starsrus.Sys;
import starsrus.Transaction;

public class ManagerInterface extends JFrame {
	public JPanel cards;
	
	public JPanel panel;
    public JLabel message;
	public JButton add_interest, gen_stmt, list_active, gen_dter, cust_rep, del_trans, 
	open_market, close_market, set_stock_price, set_date; //debug ops
	public JPanel add_interest_panel, set_stock_price_panel, list_active_panel, set_date_panel, 
	gen_stmt_panel, cust_rep_panel, gen_dter_panel;
	
	ManagerInterface(JPanel cards) {
		this.cards = cards;
		
		// Add Interest
		add_interest = new JButton("Add Interest to all Market Accounts");	
		add_interest_panel = new AddInterest(cards).panel;
		
		// Generate Monthly Statement
		gen_stmt = new JButton("Generate Monthly Statement");
		gen_stmt_panel = new GenStmt(cards).panel; 
		
		// List Active Customers
		list_active = new JButton("List Active Customers");
		list_active_panel = new ListActive(cards).panel;

		// Generate DTER
		gen_dter = new JButton("Generate Government Drug & Tax Evasion Report");	
		gen_dter_panel = new GenDter(cards).panel;	
		
		// Customer Report
		cust_rep = new JButton("Generate Customer Report");
		cust_rep_panel = new CustRep(cards).panel; // need another class for this
		
		// Delete Transactions
		del_trans = new JButton("Delete all Transactions");	
		
		// Increment day by one
		open_market = new JButton("Open Market");

		// Store daily balance for all market accounts
		// Store daily closing price for all stocks
		// Prevent people from trading stocks?
		close_market = new JButton("Close Market");

		set_stock_price = new JButton("Set Stock Price");
		set_stock_price_panel = new SetStockPrice(cards).panel;

		set_date = new JButton("Set Date");
		set_date_panel = new SetDate(cards).panel;

		// Action listeners
		add_interest.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "ADDINTEREST");
			}
		});

		gen_stmt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, "GENSTMT");
			}
		});

		list_active.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.remove(list_active_panel);
				list_active_panel = new ListActive(cards).panel;
				cards.add(list_active_panel, "LISTACTIVE");

				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, "LISTACTIVE");
			}
		});

		gen_dter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.remove(gen_dter_panel);
				gen_dter_panel = new GenDter(cards).panel;
				cards.add(gen_dter_panel, "GENDTER");

				CardLayout cl = (CardLayout)cards.getLayout();
				cl.show(cards, "GENDTER");
			}
		});

		cust_rep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "CUSTREP");
            }
        });

		del_trans.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Transaction t = new Transaction();
				t.deleteAllTransactions();
				message.setText("Deleted all transactions.");
			}
		});

		// DEBUG OPS
		open_market.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug d = new Debug();
				boolean success = d.openMarket();
				if (success) {
					Sys s = new Sys();
					String newDate = s.getToday();
					message.setText("Opened market on " + newDate);
				}
				else {
					message.setText("Market is already open");
				}
			}
		});

		close_market.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Debug d = new Debug();
				boolean success = d.closeMarket();
				if (success) {
					message.setText("Closed market for today.");
				}
				else {
					message.setText("Market is already closed");
				}
			}
		});

		set_stock_price.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "SETSTOCK");
			}
		});

		set_date.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "SETDATE");
			}
		});



		// add to cards
		cards.add(add_interest_panel, "ADDINTEREST");
		cards.add(set_stock_price_panel, "SETSTOCK");
		cards.add(set_date_panel, "SETDATE");
		cards.add(gen_stmt_panel, "GENSTMT");
		cards.add(list_active_panel, "LISTACTIVE");
		cards.add(gen_dter_panel, "GENDTER");
		cards.add(cust_rep_panel, "CUSTREP");

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