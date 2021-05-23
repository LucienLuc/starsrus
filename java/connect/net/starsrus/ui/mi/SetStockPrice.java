package starsrus.ui.mi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import starsrus.sql.Debug;

import java.text.DecimalFormat;

public class SetStockPrice extends JFrame{
    JPanel cards;
    JPanel panel;
    JLabel stock_label, price_label, message;
    JTextField stock_text, price_text;
    JButton back, submit;

    SetStockPrice(JPanel cards) {
        this.cards = cards;

        back = new JButton("Back");
        message = new JLabel();
        stock_label = new JLabel("Stock :");
        stock_text = new JTextField();
        price_label = new JLabel("New Price : $");
        price_text = new JTextField();
        submit = new JButton("Set Price");

        //Action listeners
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                message.setText("");
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "MI");
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String aid = stock_text.getText();
                Double newPrice = Double.parseDouble(price_text.getText());

                Debug d = new Debug();
                d.setStockPrice(aid, newPrice);

                message.setText("Set new price of " + aid + " to $" + new DecimalFormat("#.00").format(newPrice));
                stock_text.setText("");
                price_text.setText("");
            }
        });

        panel = new JPanel(new GridLayout(4, 2));
        panel.add(back);
        panel.add(message);
        panel.add(stock_label);
        panel.add(stock_text);
        panel.add(price_label);
        panel.add(price_text);
        panel.add(submit);
    }
}
