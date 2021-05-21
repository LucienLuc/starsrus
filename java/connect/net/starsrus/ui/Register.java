package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import starsrus.Customer;

public class Register extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JLabel user_label, password_label, name_label, state_label, 
    phone_label, email_label, address_label, taxid_label, ssn_label, message;
    public JTextField username_text, name_text, state_text, phone_text, email_text, address_text, taxid_text, ssn_text;
    public JPasswordField password_text;
    public JButton submit;

    Register(JPanel cards) {
        this.cards = cards;

		// Name
        name_label = new JLabel();
        name_label.setText("Name : ");
        name_text = new JTextField();
        
		// Username
        user_label = new JLabel();
        user_label.setText("Username : ");
        username_text = new JTextField();
        
        // Password
        password_label = new JLabel();
        password_label.setText("Password : ");
        password_text = new JPasswordField();
		
		// Address
		address_label = new JLabel();
		address_label.setText("Address : ");
		address_text = new JTextField();
		
		// State
		state_label = new JLabel();
		state_label.setText("State : ");
		state_text = new JTextField();
		
		// Phone
		phone_label = new JLabel();
		phone_label.setText("Phone : ");
		phone_text = new JTextField();
		
		// Email
		email_label = new JLabel();
		email_label.setText("Email : ");
		email_text = new JTextField();
		
		// Taxid
		taxid_label = new JLabel();
		taxid_label.setText("Taxid : ");
		taxid_text = new JTextField();
		
		// SSN
		ssn_label = new JLabel();
		ssn_label.setText("SSN : ");
		ssn_text = new JTextField();
		
		

        submit = new JButton("Register");
		submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

				String name = name_text.getText();
                String username = username_text.getText();
                String password = String.valueOf(password_text.getPassword());
				String address = address_text.getText();
				String state = state_text.getText();
				String phone = phone_text.getText();
				String email = email_text.getText();
				int taxid = Integer.parseInt(taxid_text.getText());
				String ssn = ssn_text.getText();
				
                Customer c = new Customer();
                boolean check = c.register(name, username, password, address, state, phone, email, taxid, ssn, 1000.0);
                if (!check) {
                    message.setText(" Error with Registration fields. ");
                } else {
					CardLayout cl = (CardLayout)cards.getLayout();
                    cl.show(cards, "LG");
				}
            }
        });

        panel = new JPanel(new GridLayout(5, 2));

        panel.add(name_label);
        panel.add(name_text);
        
		panel.add(user_label);
        panel.add(username_text);
        
		panel.add(password_label);
        panel.add(password_text);
		
		panel.add(address_label);
		panel.add(address_text);
		
		panel.add(phone_label);
		panel.add(phone_text);
		
		panel.add(email_label);
		panel.add(email_text);
		
		panel.add(state_label);
		panel.add(state_text);
		
		panel.add(taxid_label);
		panel.add(taxid_text);
		
		panel.add(ssn_label);
		panel.add(ssn_text);
			
        message = new JLabel();
        panel.add(message);
        panel.add(submit);
    }
}
