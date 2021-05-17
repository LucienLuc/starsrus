package starsrus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TraderInterface extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JLabel user_label, balance_label, message;
    public JTextField deposit_value, withdraw_value;
    public JPasswordField password_text;
    public JButton deposit, withdraw, trans_history;
    public int taxid;
    public String user;
    public double balance;

    TraderInterface(JPanel cards, int taxid, String user) {
        this.cards = cards;

        this.taxid = taxid;
        this.user = user;
        // Replace with some kind of query
        this.balance = 2000; 

        // User Label
        user_label = new JLabel();
        user_label.setText("User: " + user);
        
        // Balance
        balance_label = new JLabel();
        balance_label.setText("Balance: $" + String.valueOf(balance));

        //Transaction history
        trans_history = new JButton("View my Transaction History");

        // Deposit
        deposit_value = new JTextField();
        deposit = new JButton("Deposit");
        
        // Withdraw
        withdraw_value = new JTextField();
        withdraw = new JButton("Withdraw");

        panel = new JPanel(new GridLayout(3, 3));

        panel.add(user_label);
        panel.add(balance_label);
        panel.add(trans_history);
        panel.add(deposit_value);
        panel.add(withdraw_value);
        // panel.add(deposit_value);

        message = new JLabel("hello");
        panel.add(message);
        panel.add(deposit);
        panel.add(withdraw);
        
        setVisible(true);
    }
}
