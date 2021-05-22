package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import starsrus.Manager;

public class AddInterest extends JFrame{
    JPanel cards;
    JPanel panel;
    JLabel amount_label, message;
    JTextField amount_text;
    JButton back, submit;

    AddInterest(JPanel cards) {
        this.cards = cards;

        back = new JButton("Back");
        message = new JLabel();
        amount_label = new JLabel("Interest Rate (in percent) :");
        amount_text = new JTextField();
        submit = new JButton("Add Interest");

        // Action listeners
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                message.setText("");
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "MI");
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amount = amount_text.getText();
                double percent = Double.parseDouble(amount);

                Manager m = new Manager();
                m.addInterest(percent);
                amount_text.setText("");
                message.setText("Accrued interest for all Market Accounts at " + amount + "%");
            }
        });

        panel = new JPanel(new GridLayout(3, 2));
        panel.add(back);
        panel.add(message);
        panel.add(amount_label);
        panel.add(amount_text);
        panel.add(submit);

    }
}
