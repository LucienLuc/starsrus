package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.text.DecimalFormat;

import starsrus.Customer;
import starsrus.Manager;

public class CustRep extends JFrame{
    public JPanel cards;
	
	public JPanel panel;

    public JLabel taxid_label, balance_label;
    public JTextField taxid_text;
    JScrollPane scrollPane;
    public JTable table;
    public JButton back, submit;

    CustRep(JPanel cards) {
        this.cards = cards;
        back = new JButton("Back");
        taxid_label = new JLabel("Taxid: ");
        taxid_text = new JTextField("", 10);
        submit = new JButton("Get Report");
        balance_label = new JLabel();

        // Action listeners
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                //reset table
                panel.remove(scrollPane);
                table = new JTable(); 
                scrollPane = new JScrollPane(table);

                panel.add(scrollPane);
                panel.revalidate();
                panel.repaint();

                taxid_text.setText("");
                balance_label.setText("");
                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "MI");
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                panel.remove(scrollPane);
                int taxid = Integer.parseInt(taxid_text.getText());

                //get balance
                Customer c = new Customer();
                double balance = c.getBalance(taxid);
                balance_label.setText("Balance: $" + new DecimalFormat("#.00").format(balance));

                // get report
                Manager m = new Manager();
                Object[][] list = m.getCustReport(taxid);

                String[] columns = {"Shares", "AID", "Buy Price"};

                table = new JTable(list, columns) {
                    public boolean isCellEditable(int row, int column) {                
                        return false;               
                    };
                };

                // allow scroll
                scrollPane = new JScrollPane(table);
                table.setFillsViewportHeight(true);

                // Change header colors
                JTableHeader trans_header = table.getTableHeader();
                trans_header.setBackground(Color.black);
                trans_header.setForeground(Color.yellow);

                // Set column sizes
                // table.getColumnModel().getColumn(0).setPreferredWidth(150);
                // table.getColumnModel().getColumn(5).setPreferredWidth(100);

                panel.add(scrollPane);
                panel.revalidate();
                panel.repaint();
            }
        });

        table = new JTable();
        scrollPane = new JScrollPane(table);

        panel = new JPanel();
        panel.add(back);
        panel.add(taxid_label);
        panel.add(taxid_text);
        panel.add(submit);
        panel.add(balance_label);
        panel.add(scrollPane);

    }
}
