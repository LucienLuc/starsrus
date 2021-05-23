package starsrus.ui.ti;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.text.DecimalFormat;
import javax.swing.table.JTableHeader;

import starsrus.sql.Contracts;
import starsrus.sql.Stock;

public class StockInfo extends JFrame{
    public JPanel cards;

    public JPanel panel;
    public JLabel stock_label, message;
    public JTextField stock_text;
    public JButton back, submit;

    JScrollPane scrollPane;
    public JTable table;

    StockInfo(JPanel cards) {
        this.cards = cards;

        back = new JButton("Back");
        stock_label = new JLabel("Stock");
        stock_text = new JTextField("", 10);
        submit = new JButton("Get Info");
        message = new JLabel();

        // Action listeners
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "TI");
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String stock = stock_text.getText();
                Stock s = new Stock();
                double price = s.getPrice(stock);
                if (price == -1) {
                    message.setText("Error: Invalid stock.");

                    // Clear old table
                    panel.remove(scrollPane);
                    table = new JTable();
                    scrollPane = new JScrollPane(table);

                    panel.add(scrollPane);
                    panel.revalidate();
                    panel.repaint();
                }
                else {
                    message.setText("Current Price: $" + new DecimalFormat("#.00").format(price));

                    // get contracts
                    Contracts c = new Contracts();
                    String[] columns = {"Title", "Role", "Year", "Value"};
                    Object[][] list = c.getContracts(stock);

                    // create new table
                    table = new JTable(list, columns) {
                            public boolean isCellEditable(int row, int column) {                
                                return false;               
                            };
                        };
                    // allow scroll
                    panel.remove(scrollPane);
                    scrollPane = new JScrollPane(table);
                    table.setFillsViewportHeight(true);

                    // Change header colors
                    JTableHeader header = table.getTableHeader();
                    header.setBackground(Color.black);
                    header.setForeground(Color.yellow);

                    table.getColumnModel().getColumn(0).setPreferredWidth(150);
                    table.getColumnModel().getColumn(1).setPreferredWidth(25);
                    table.getColumnModel().getColumn(2).setPreferredWidth(25);
                    table.getColumnModel().getColumn(3).setPreferredWidth(150);
                    
                    // add new table and update panel
                    panel.add(scrollPane);
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });
        table = new JTable();
        scrollPane = new JScrollPane(table);

        panel = new JPanel();
        panel.add(back);
        panel.add(message);
        panel.add(stock_label);
        panel.add(stock_text);
        panel.add(submit);

        panel.add(scrollPane);
        
    }
}
