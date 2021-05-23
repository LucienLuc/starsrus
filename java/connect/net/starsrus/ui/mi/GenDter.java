package starsrus.ui.mi;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import starsrus.sql.Customer;
import starsrus.sql.Manager;

import java.util.ArrayList;

public class GenDter extends JFrame{
    public JPanel cards;
	
	public JPanel panel;
    JScrollPane scrollPane;
    public JTable table;
    public JButton back;

    GenDter(JPanel cards) {
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
        ArrayList<Integer> list = m.getDTER();
        Object[][] rows = new Object[list.size()][3];
        Customer c = new Customer();
        for(int i = 0; i < list.size(); i++) {
            rows[i][0] = c.getName(list.get(i));
            rows[i][1] = list.get(i);
            rows[i][2] = c.getState(list.get(i));
        } 
        String[] columns = {"Username", "Taxid", "State"};

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

        panel = new JPanel();
        panel.add(back);
        panel.add(scrollPane);
    }
}
