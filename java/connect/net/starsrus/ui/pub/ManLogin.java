package starsrus.ui.pub;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import starsrus.sql.Manager;
import starsrus.ui.mi.ManagerInterface;

public class ManLogin extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JLabel user_label, password_label, message;
    public JTextField username_text;
    public JPasswordField password_text;
    public JButton submit;
    public String user;

    public ManLogin(JPanel cards) {
        this.cards = cards;
        // User Label
        user_label = new JLabel();
        user_label.setText("Manager Username :");
        username_text = new JTextField();
        
        // Password
        password_label = new JLabel();
        password_label.setText("Manager Password :");
        password_text = new JPasswordField();

        // Submit

        submit = new JButton("Login");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String username = username_text.getText();
                String password = String.valueOf(password_text.getPassword());
            
                Manager m = new Manager();
                boolean isSuccess = m.login(username, password);
                if (!isSuccess) {
                    message.setText(" Invalid username and password combination. ");
                }
                else {
                    user = username;
                    JPanel mi = new ManagerInterface(cards).panel;
                    cards.add(mi, "MI");

                    CardLayout cl = (CardLayout)cards.getLayout();
                    cl.show(cards, "MI");
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
