package starsrus;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Register extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JLabel user_label, password_label, name_label, state_label, 
    phone_label, email_label, address_label, taxid_label, message;
    public JTextField username_text, name_text, state_text, phone_text, email_text, address_text, taxid_text;
    public JPasswordField password_text;
    public JButton submit;

    Register(JPanel cards) {
        this.cards = cards;

        name_label = new JLabel();
        name_label.setText("Name :");
        name_text = new JTextField();
        
        user_label = new JLabel();
        user_label.setText("Username :");
        username_text = new JTextField();
        
        // Password
        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();

        submit = new JButton("Register");

        panel = new JPanel(new GridLayout(2, 3));

        panel.add(name_label);
        panel.add(name_text);
        panel.add(user_label);
        panel.add(username_text);
        panel.add(password_label);
        panel.add(password_text);

        message = new JLabel();
        panel.add(message);
        panel.add(submit);
    }
}
