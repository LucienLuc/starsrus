package starsrus.ui.mi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import starsrus.sql.Customer;
import starsrus.sql.Manager;

import java.util.ArrayList;

public class ListActive extends JFrame{
    public JPanel cards;
	
	public JPanel panel;
    JScrollPane scrollPane;
    public JTable table;
    public JButton back;

    ListActive(JPanel cards) {
        this.cards = cards;
        back = new JButton("Back");

        // Action listeners
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "MI");
            }
        });

        Manager m = new Manager();
        ArrayList<Integer> list = m.getActiveCustomers();
        Object[][] rows = new Object[list.size()][2];
        Customer c = new Customer();
        for(int i = 0; i < list.size(); i++) {
            rows[i][0] = c.getName(list.get(i));
            rows[i][1] = list.get(i);
        } 
        String[] columns = {"Username", "Taxid"};

        table = new JTable(rows, columns) {
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
        // trans_table.getColumnModel().getColumn(0).setPreferredWidth(150);
        // trans_table.getColumnModel().getColumn(5).setPreferredWidth(100);

        // panel.revalidate();
        // panel.repaint();

        panel = new JPanel();
        panel.add(back);
        panel.add(scrollPane);
    }
}
