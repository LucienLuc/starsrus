package starsrus.ui.ti;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import starsrus.sql.Movie;

public class TopMovies extends JFrame{
    public JPanel cards;

    public JPanel panel;
    JScrollPane scrollPane;
    public JTable table;
    public JLabel start_date_label, end_date_label, rating_label;
    public JTextField start_date_text, end_date_text, rating_text;
    public JButton back, submit;
    public int taxid;

    TopMovies(JPanel cards) {
        this.cards = cards;

        back = new JButton("Back");
        start_date_label = new JLabel("From: ");
        start_date_text = new JTextField("", 5);
        end_date_label = new JLabel("To: ");
        end_date_text = new JTextField("", 5);
        rating_label = new JLabel("Min Rating: ");
        rating_text = new JTextField("", 5);
        submit = new JButton("Get Top Movies");
        
        // Action listeners 
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                start_date_text.setText("");
                end_date_text.setText("");
                rating_text.setText("");

                // remove table
                panel.remove(scrollPane);

                table = new JTable();
                scrollPane = new JScrollPane(table);

                panel.add(scrollPane);
                panel.revalidate();
                panel.repaint();
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "MOVIEINFO");
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int start_year = Integer.parseInt(start_date_text.getText());
                int end_year = Integer.parseInt(end_date_text.getText());
                double rating = Double.parseDouble(rating_text.getText());

                Movie m = new Movie();
                Object[][] list = m.topMovies(start_year, end_year, rating);

                panel.remove(scrollPane);

                String[] columns = {"ID", "Title", "Year", "Genre", "Rating", "Revenue (Millions)"};
                table = new JTable(list, columns) {
                    public boolean isCellEditable(int row, int column) {                
                        return false;               
                    };
                };

                // allow scroll
                scrollPane = new JScrollPane(table);
                table.setFillsViewportHeight(true);

                // Change header colors
                JTableHeader header = table.getTableHeader();
                header.setBackground(Color.black);
                header.setForeground(Color.yellow);

                // Set column sizes
                table.getColumnModel().getColumn(0).setPreferredWidth(25);
                table.getColumnModel().getColumn(1).setPreferredWidth(100);
                table.getColumnModel().getColumn(2).setPreferredWidth(25);
                table.getColumnModel().getColumn(3).setPreferredWidth(35);
                table.getColumnModel().getColumn(4).setPreferredWidth(35);
                table.getColumnModel().getColumn(5).setPreferredWidth(100);

                panel.add(scrollPane);
                panel.revalidate();
                panel.repaint();
            }
        });

        // allow scroll
        table = new JTable();
        scrollPane = new JScrollPane(table);
        panel = new JPanel();
        panel.add(back);
        panel.add(start_date_label);
        panel.add(start_date_text);
        panel.add(end_date_label);
        panel.add(end_date_text);
        panel.add(rating_label);
        panel.add(rating_text);
        panel.add(submit);
        panel.add(scrollPane);

    }

}
