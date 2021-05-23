package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import starsrus.Movie;

public class Reviews extends JFrame{
    public JPanel cards;

    public JPanel panel;

    public JLabel movieid_label, message;
    public JTextField movieid_text;
    public JButton back, submit;

    Reviews(JPanel cards) {
        this.cards = cards;
        back = new JButton("Back");
        movieid_label = new JLabel("Movie ID: ");
        movieid_text = new JTextField("", 5);
        submit = new JButton("Get Rating");
        message = new JLabel();

        // Action Listeners
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movieid_text.setText("");
                message.setText("");
                
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "MOVIEINFO");
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int movieid = Integer.parseInt(movieid_text.getText());
                Movie m = new Movie();
                double rating = m.getRating(movieid);
                if (rating == -1) {
                    message.setText("Error: Unknown movie ID");
                }
                else {
                    message.setText("Rating for movie is " + Double.toString(rating));
                }
            }
        });

        panel = new JPanel();
        panel.add(back);
        panel.add(movieid_label);
        panel.add(movieid_text);
        panel.add(submit);
        panel.add(message);

    }
}
