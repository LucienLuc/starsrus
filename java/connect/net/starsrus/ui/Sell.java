package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;

import starsrus.StockAccount;
import starsrus.MarketAccount;

public class Sell {
    public JPanel cards;

    public JPanel panel;
    public JLabel stock_label, quantity_label, original_price_label, message;
    public JTextField stock_text, original_price_text, quantity_text;
    public JButton back, sell;
    int taxid;

    Sell(JPanel cards, int taxid, TraderInterface ti) {
        this.taxid = taxid;

        back = new JButton("Back");
        message = new JLabel();
        stock_label = new JLabel("Stock: ");
        stock_text = new JTextField();
        quantity_label = new JLabel("Quantity: ");
        quantity_text = new JTextField();

        sell = new JButton("Buy");
    }

}
