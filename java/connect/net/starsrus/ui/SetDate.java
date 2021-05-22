package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;

import starsrus.Debug;

public class SetDate extends JFrame{
    JPanel cards;

    JPanel panel;
    JLabel date_label, message;
    JTextField date_text;
    JButton back, submit;

    SetDate(JPanel cards) {
        this.cards = cards;

        back = new JButton("Back");
        message = new JLabel();
        date_label = new JLabel("Date (YYYY-MM-DD): ");
        date_text = new JTextField();
        submit = new JButton("Set Date");

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

                String newDate = date_text.getText();

                Debug d = new Debug();
                d.setDate(newDate);

                message.setText("Set today to " + newDate);
                date_text.setText("");
            }
        });

        panel = new JPanel(new GridLayout(4, 2));
        panel.add(back);
        panel.add(message);
        panel.add(date_label);
        panel.add(date_text);
        panel.add(submit);
    }
}
