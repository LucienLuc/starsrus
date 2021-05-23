package starsrus.ui.pub;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LogReg extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JButton manager_login, login, register;

    public LogReg(JPanel cards) {
        this.cards = cards;

        manager_login = new JButton("Manager Login");
        login = new JButton("Existing User");
        register = new JButton("New User");

        // Action Listeners
        manager_login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout c = (CardLayout)cards.getLayout();
                c.show(cards, "MAN_LOGIN");
            }
        });

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

        panel.add(manager_login);
        panel.add(login);
        panel.add(register);
    }

    public JPanel getPanel() {
        return panel;
    }
}
