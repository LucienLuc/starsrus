package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LogReg extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JButton login, register;

    LogReg(JPanel cards) {
        this.cards = cards;

        login = new JButton("Existing User");
        register = new JButton("New User");

        // Action Listeners
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)cards.getLayout();
                c.show(cards, "LOGIN");
            }
        });

        register.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)cards.getLayout();
                c.show(cards, "REGISTER");
            }
        });

        panel = new JPanel(new GridLayout(3, 2));

        panel.add(login);
        panel.add(register);

        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // add(panel, BorderLayout.CENTER);
        // setTitle("Welcome to Stars R Us");
        // setSize(750, 250);
        // setVisible(true);
    }

    public JPanel getPanel() {
        return panel;
    }
}
