package starsrus.ui.ti;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import starsrus.sql.MarketAccount;

import java.text.DecimalFormat;

public class DepWith extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JLabel amount_label, message;
    public JTextField amount_text;
    public JButton back, deposit, withdraw;
    int taxid;

    DepWith(JPanel cards, int taxid, TraderInterface ti) {
        this.cards = cards;
        this.taxid = taxid;

        amount_label = new JLabel();
        amount_label.setText("Amount: $");
        amount_text = new JTextField();

        deposit = new JButton("Deposit");
        withdraw = new JButton("Withdraw");
        back = new JButton("Back");

        // Action listeners

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                message.setText("");
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "TI");
            }
        });

        deposit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(amount_text.getText());
                MarketAccount ma = new MarketAccount(taxid);

                ma.deposit(value);
                message.setText("Deposited $" + new DecimalFormat("#.00").format(value) + " into your account.");
                amount_text.setText("");

                //update balance display
                double balance = ma.getBalance();
                ti.balance_label.setText("Balance: $" + new DecimalFormat("#.00").format(balance));
            }
        });

        withdraw.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(amount_text.getText());
                MarketAccount ma = new MarketAccount(taxid);

                boolean isSuccess = ma.withdraw(value);
                if (isSuccess) {
                    message.setText("Withdrew $" +  new DecimalFormat("#.00").format(value) + " from your account.");
                }
                else {
                    message.setText("Error: Insufficient funds.");
                }
                
                amount_text.setText("");
                //update balance display
                double balance = ma.getBalance();
                ti.balance_label.setText("Balance: $" + new DecimalFormat("#.00").format(balance));
            }
        });

        panel = new JPanel(new GridLayout(2, 3));
        panel.add(back);
        panel.add(amount_label);
        panel.add(amount_text);
        panel.add(deposit);
        panel.add(withdraw);

        message = new JLabel();
        panel.add(message);
    }

}