package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import starsrus.Movie;

public class MovieInfo extends JFrame{
    public JPanel cards;

    public JPanel panel;
    JScrollPane scrollPane;
    public JTable table;
    public JButton back, top_movies, reviews;
    public int taxid;

    MovieInfo(JPanel cards) {
        this.cards = cards;
        back = new JButton("Back");
        top_movies = new JButton("Get Top Movies");
        reviews = new JButton("Get reviews");

         // Action listeners 
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "TI");
            }
        });

        top_movies.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "TOPMOVIES");
            }
        });
        
        Movie m = new Movie();
        Object[][] list = m.listMovies();
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

        // allow scroll
        scrollPane = new JScrollPane(table);
        panel = new JPanel();
        panel.add(back);
        panel.add(top_movies);
        panel.add(reviews);
        panel.add(scrollPane);

    }

}
