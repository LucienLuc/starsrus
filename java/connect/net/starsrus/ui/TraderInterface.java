package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;

import starsrus.MarketAccount;
public class TraderInterface extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JLabel user_label, balance_label;
    public JTextField deposit_value, withdraw_value;
    public JPasswordField password_text;
    public JButton dep_with, buy, sell, stock_info, movie_info, trans_history, debug;
    public JPanel dep_with_panel, buy_panel, sell_panel, trans_history_panel, stock_info_panel, movie_info_panel, top_movies_panel, reviews_panel;
    public int taxid;
    public String user;

    TraderInterface(JPanel cards, int taxid, String user) {
        this.cards = cards;

        this.taxid = taxid;
        this.user = user;
        // Replace with some kind of query

        MarketAccount ma = new MarketAccount(taxid);
        double balance = ma.getBalance(); 

        // User Label
        user_label = new JLabel();
        user_label.setText("User: " + user);
        
        // Balance
        balance_label = new JLabel();
        balance_label.setText("Balance: $" + new DecimalFormat("#.00").format(balance));

        //Buttons for actions
        trans_history = new JButton("View my Transaction History");
        dep_with = new JButton("Deposit/Withdraw");
        buy = new JButton("Buy Stocks");
        sell = new JButton("Sell Stocks");
        stock_info = new JButton("Get Stock Info");
        movie_info = new JButton("Get Movie Info");

        // Create Panels for each action
        dep_with_panel = new DepWith(cards, taxid, this).panel;
        buy_panel = new Buy(cards, taxid, this).panel;
        sell_panel = new Sell(cards, taxid, this).panel;
        trans_history_panel = new TransHistory(cards, taxid).panel;
        stock_info_panel = new StockInfo(cards).panel;
        movie_info_panel = new MovieInfo(cards).panel;
        top_movies_panel = new TopMovies(cards).panel;
        reviews_panel = new Reviews(cards).panel;

        // Add to cards
        cards.add(dep_with_panel, "DEPWITH");
        cards.add(buy_panel, "BUY");
        cards.add(sell_panel, "SELL");
        cards.add(trans_history_panel, "TRANSHIST");
        cards.add(stock_info_panel, "STOCKINFO");
        cards.add(movie_info_panel, "MOVIEINFO");
        cards.add(top_movies_panel, "TOPMOVIES");
        cards.add(reviews_panel, "REVIEWS");

        // Action listeners
        trans_history.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                trans_history_panel = new TransHistory(cards, taxid).panel;
                cards.add(trans_history_panel, "TRANSHIST");
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "TRANSHIST");
            }
        });

        dep_with.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "DEPWITH");
            }
        });

        buy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "BUY");
            }
        });

        sell.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "SELL");
            }
        });

        stock_info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "STOCKINFO");
            }
        });

        movie_info.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cards.remove(movie_info_panel);
				movie_info_panel = new MovieInfo(cards).panel;
				cards.add(movie_info_panel, "MOVIEINFO");

                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "MOVIEINFO");
            }
        });

        panel = new JPanel(new GridLayout(2, 4));
        panel.add(user_label);
        panel.add(balance_label);
        panel.add(trans_history);
        panel.add(dep_with);
        panel.add(buy);
        panel.add(sell);
        panel.add(stock_info);
        panel.add(movie_info);
    }
}
