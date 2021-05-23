package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import starsrus.Transaction;

public class TransHistory extends JFrame{
    public JPanel cards;

    public JPanel panel;
    JScrollPane scrollPane;
    public JTable table;
    public JButton back;
    public int taxid;


    TransHistory(JPanel cards, int taxid) {
        this.cards = cards;

        back = new JButton("Back");

        Transaction t = new Transaction();
        Object[][] list = t.getTransactionHistory(taxid);
        String[] columns = {"Date", "Type", "Shares", "AID", "Price", "Total"};

        table = new JTable(list, columns) {
            private static final long serialVersionUID = 1L;

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
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);

        // Action listeners 
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "TI");
            }
        });

        panel = new JPanel();
        panel.add(back);
        panel.add(scrollPane);
    }

    // void update() {
    //     Transaction t = new Transaction();
    //     Object[][] list = t.getTransactionHistory(taxid);
    //     String[] columns = {"Date", "Type", "Shares", "AID", "Price", "Total"};

    //     table = new JTable(list, columns) {
    //         private static final long serialVersionUID = 1L;

    //         public boolean isCellEditable(int row, int column) {                
    //             return false;               
    //         };
    //     };

    //     // allow scroll
    //     scrollPane = new JScrollPane(table);
    //     table.setFillsViewportHeight(true);

    //     // Change header colors
    //     JTableHeader header = table.getTableHeader();
    //     header.setBackground(Color.black);
    //     header.setForeground(Color.yellow);

    //     // Set column sizes
    //     table.getColumnModel().getColumn(0).setPreferredWidth(150);
    //     table.getColumnModel().getColumn(5).setPreferredWidth(100);

    //     panel = new JPanel();
    //     panel.add(back);
    //     panel.add(scrollPane);
    // }
}