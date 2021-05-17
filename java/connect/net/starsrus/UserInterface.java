package starsrus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class UserInterface extends JFrame{
    public JPanel cards;

    UserInterface() {
        cards = new JPanel(new CardLayout());

        // Choose Login or Register Page
        JPanel lg = new LogReg(cards).panel;

        // Login Page
        JPanel login = new Login(cards).panel;

        // Register Page
        JPanel register = new Register(cards).panel;

        // Trader Interface added after successful login

        // Manager Interface

        
        // Adding cards
        cards.add(lg, "LG");
        cards.add(login, "LOGIN");
        cards.add(register, "REGISTER");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(cards, BorderLayout.CENTER);
        setTitle("Welcome to Stars R Us");
        setSize(750, 250);
        setVisible(true);
        
        // Show Login/Register Page
        CardLayout c = (CardLayout)cards.getLayout();
        c.show(cards, "LG");
    }
}
