package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;

import starsrus.StockAccount;
import starsrus.MarketAccount;
import starsrus.Stock;

public class Sell {
    public JPanel cards;

    public JPanel panel;
    public JLabel stock_label, quantity_label, original_price_label, message;
    public JTextField stock_text, original_price_text, quantity_text;
    public JButton back, sell;
    int taxid;

    Sell(JPanel cards, int taxid, TraderInterface ti) {
        this.cards = cards;
        this.taxid = taxid;

        back = new JButton("Back");
        message = new JLabel();
        stock_label = new JLabel("Stock: ");
        stock_text = new JTextField();
        quantity_label = new JLabel("Quantity: ");
        quantity_text = new JTextField();
        original_price_label = new JLabel("Original Price: $");
        original_price_text = new JTextField();
        sell = new JButton("Sell");

        // Action listeners 
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                message.setText("");
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "TI");
            }
        });

        sell.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String aid = stock_text.getText();
                int quantity = Integer.parseInt(quantity_text.getText());
                double original_price = Double.parseDouble(original_price_text.getText());

                StockAccount sa = new StockAccount(taxid);
                boolean isSuccess = sa.sell(aid, quantity, original_price);
                
                if (isSuccess) {
                    // get current price of stock
                    Stock stock = new Stock();
                    double curr_price = stock.getPrice(aid);

                    message.setText("Sold " + Integer.toString(quantity) + " shares of " + aid + 
                    " at current price of $" + new DecimalFormat("#.00").format(curr_price) + 
                    " from bought price of $" + new DecimalFormat("#.00").format(original_price));
                }
                else {
                    message.setText("Error: You do not own at least " + Integer.toString(quantity) + " shares of " + aid + 
                    " at specified buy price of $" + new DecimalFormat("#.00").format(original_price)); 
                }

                // clear fields
                stock_text.setText("");
                quantity_text.setText("");
                original_price_text.setText("");

                // update balance
                MarketAccount ma = new MarketAccount(taxid);
                double balance = ma.getBalance();
                ti.balance_label.setText("Balance: $" + new DecimalFormat("#.00").format(balance));
            }
        });

        panel = new JPanel(new GridLayout(5, 2));
        panel.add(back);
        panel.add(message);
        panel.add(stock_label);
        panel.add(stock_text);
        panel.add(quantity_label);
        panel.add(quantity_text);
        panel.add(original_price_label);
        panel.add(original_price_text);
        panel.add(sell);
    }

}
