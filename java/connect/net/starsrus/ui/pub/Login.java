package starsrus.ui.pub;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import starsrus.sql.Customer;
import starsrus.ui.ti.TraderInterface;

public class Login extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JLabel user_label, password_label, message;
    public JTextField username_text;
    public JPasswordField password_text;
    public JButton submit;
    public int taxid;
    public String user;

    public Login(JPanel cards) {
        this.cards = cards;
        // User Label
        user_label = new JLabel();
        user_label.setText("Username :");
        username_text = new JTextField();
        
        // Password
        password_label = new JLabel();
        password_label.setText("Password :");
        password_text = new JPasswordField();

        // Submit

        submit = new JButton("Login");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String username = username_text.getText();
                String password = String.valueOf(password_text.getPassword());
            
                Customer c = new Customer();
                taxid = c.login(username, password);
                if (taxid == -1) {
                    message.setText(" Invalid username and password combination. ");
                }
                else {
                    user = username;
                    JPanel ti = new TraderInterface(cards, taxid, user).panel;
                    cards.add(ti, "TI");

                    CardLayout cl = (CardLayout)cards.getLayout();
                    cl.show(cards, "TI");
                }
            }
        });

        panel = new JPanel(new GridLayout(3, 1));

        panel.add(user_label);
        panel.add(username_text);
        panel.add(password_label);
        panel.add(password_text);

        message = new JLabel();
        panel.add(message);
        panel.add(submit);
    }

}
