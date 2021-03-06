package starsrus.ui.ti;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import starsrus.sql.MarketAccount;
import starsrus.sql.StockAccount;

import java.text.DecimalFormat;

public class Buy {
    public JPanel cards;

    public JPanel panel;
    public JLabel stock_label, quantity_label, message;
    public JTextField stock_text, quantity_text;
    public JButton back, buy;
    int taxid;

    Buy(JPanel cards, int taxid, TraderInterface ti) {
        this.taxid = taxid;

        back = new JButton("Back");
        message = new JLabel();
        stock_label = new JLabel("Stock: ");
        stock_text = new JTextField();
        quantity_label = new JLabel("Quantity: ");
        quantity_text = new JTextField();

        buy = new JButton("Buy");

        // Action listeners
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                message.setText("");
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "TI");
            }
        });

        buy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String aid = stock_text.getText();
                int quantity = Integer.parseInt(quantity_text.getText());

                StockAccount sa = new StockAccount(taxid);
                boolean isSuccess = sa.buy(aid, quantity);
                
                if (isSuccess) {
                    message.setText("Bought " + Integer.toString(quantity) + " shares of " +  aid);
                }
                else {
                    message.setText("Error: Invalid Stock or insufficient funds.");
                }

                // clear fields
                stock_text.setText("");
                quantity_text.setText("");

                // update balance
                MarketAccount ma = new MarketAccount(taxid);
                double balance = ma.getBalance();
                ti.balance_label.setText("Balance: $" + new DecimalFormat("#.00").format(balance));
            }
        });

        panel = new JPanel(new GridLayout(4, 2));
        panel.add(back);
        panel.add(message);
        panel.add(stock_label);
        panel.add(stock_text);
        panel.add(quantity_label);
        panel.add(quantity_text);
        panel.add(buy);
    }
}
