package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;

import starsrus.MarketAccount;
public class TraderInterface extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JLabel user_label, balance_label;
    public JTextField deposit_value, withdraw_value;
    public JPasswordField password_text;
    public JButton dep_with, buy_sell, stock_info, movie_info, trans_history, debug;
    public JPanel dep_with_panel;
    public int taxid;
    public String user;

    TraderInterface(JPanel cards, int taxid, String user) {
        this.cards = cards;

        this.taxid = taxid;
        this.user = user;
        // Replace with some kind of query

        MarketAccount ma = new MarketAccount(taxid);
        double balance = ma.getBalance(); 

        // User Label
        user_label = new JLabel();
        user_label.setText("User: " + user);
        
        // Balance
        balance_label = new JLabel();
        balance_label.setText("Balance: $" + new DecimalFormat("#.00").format(balance));

        //Buttons for actions
        trans_history = new JButton("View my Transaction History");
        dep_with = new JButton("Deposit/Withdraw");
        buy_sell = new JButton("Buy/Sell Stocks");
        stock_info = new JButton("Get Stock Info");
        movie_info = new JButton("Get Movie Info");

        // Create Panels for each action
        dep_with_panel = new DepWith(cards, taxid, this).panel;

        // Add to cards
        cards.add(dep_with_panel, "DEPWITH");

        // Action listeners
        dep_with.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "DEPWITH");
            }
        });

        panel = new JPanel(new GridLayout(2, 4));

        panel.add(user_label);
        panel.add(balance_label);
        panel.add(trans_history);
        panel.add(dep_with);
        panel.add(buy_sell);
        panel.add(stock_info);
        panel.add(movie_info);
    }
}
