package starsrus.ui;

import java.awt.*;
// import java.awt.event.*;
import javax.swing.*;

import starsrus.ui.pub.*;

public class UserInterface extends JFrame{
    public JPanel cards;

    public UserInterface() {
        cards = new JPanel(new CardLayout());

        // Choose Login or Register Page
        JPanel lg = new LogReg(cards).panel;

        // Login Page
        JPanel login = new Login(cards).panel;

        // Register Page
        JPanel register = new Register(cards).panel;

        // Manager login page
        JPanel man_login = new ManLogin(cards).panel;

        // Trader Interface added after successful login

        // Manager Interface added after successful login
        
        // Adding cards
        cards.add(lg, "LG");
        cards.add(login, "LOGIN");
        cards.add(register, "REGISTER");
        cards.add(man_login, "MAN_LOGIN");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(cards, BorderLayout.CENTER);
        setTitle("Stars R Us");
        setSize(1000, 600);
        setVisible(true);
        
        // Show Login/Register Page
        CardLayout c = (CardLayout)cards.getLayout();
        c.show(cards, "LG");
    }
}
