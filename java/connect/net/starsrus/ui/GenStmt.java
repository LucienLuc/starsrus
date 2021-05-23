package starsrus.ui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;

import java.text.DecimalFormat;

import starsrus.Transaction;
import starsrus.Customer;

public class GenStmt extends JFrame{
    public JPanel cards;
	
	public JPanel panel, info_panel;
    public JLabel message, taxid_label, name_label, email_label, 
    initial_bal_label, final_bal_label, earnings_label, commissions_paid_label;
    public JTextField taxid_text;

    JScrollPane trans_scrollPane;
    public JTable trans_table;
    public JButton back, submit;

    GenStmt(JPanel cards) {
        this.cards = cards;

        back = new JButton("Back");
        message = new JLabel();
        taxid_label = new JLabel("Taxid: ");
        taxid_text = new JTextField("", 10);
        submit = new JButton("Generate");

        name_label = new JLabel();
        email_label = new JLabel(); 
        initial_bal_label = new JLabel();
        final_bal_label = new JLabel(); 
        earnings_label= new JLabel();
        commissions_paid_label= new JLabel();

        //Action Listeners
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // clear all labels
                taxid_text.setText("");
                message.setText("");
                name_label.setText("");
                email_label.setText("");
                initial_bal_label.setText("");
                final_bal_label.setText("");
                earnings_label.setText("");
                commissions_paid_label.setText("");

                // remove old table
                panel.remove(trans_scrollPane);
                trans_table = new JTable(); 
                trans_scrollPane = new JScrollPane(trans_table);

                panel.add(trans_scrollPane);
                panel.revalidate();
                panel.repaint();

                CardLayout cl = (CardLayout)cards.getLayout();
                cl.show(cards, "MI");
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int taxid = Integer.parseInt(taxid_text.getText());
                // Check if customer exists
                Customer c = new Customer();
                String name = c.getName(taxid);
                String email = c.getEmail(taxid);

                if (name.equals("") || email.equals("")) {
                    message.setText("User with taxid not found");

                    // clear all labels
                    name_label.setText("");
                    email_label.setText("");
                    initial_bal_label.setText("");
                    final_bal_label.setText("");
                    earnings_label.setText("");
                    commissions_paid_label.setText("");

                    // remove old table

                    panel.remove(trans_scrollPane);
                    trans_table = new JTable(); 
                    trans_scrollPane = new JScrollPane(trans_table);

                    panel.add(trans_scrollPane);
                    panel.revalidate();
                    panel.repaint();
                }
                else {
                    message.setText("");
                    name_label.setText("Name: " + name);
                    email_label.setText("Email: " + email);

                    double initial_bal = c.getInitialMonthlyBalance(taxid);
                    double final_bal = c.getFinalMonthlyBalance(taxid);
                    double earnings = final_bal - initial_bal;
                    double commissions_paid = c.countStockTrades(taxid) * 20;

                    initial_bal_label.setText("Inital Balance: $" + new DecimalFormat("#.00").format(initial_bal));
                    final_bal_label.setText("Final Balance: $" + new DecimalFormat("#.00").format(final_bal)); 
                    earnings_label.setText("Net Earnings: $" + new DecimalFormat("#.00").format(earnings));
                    commissions_paid_label.setText("Commissions Paid: $" + new DecimalFormat("#.00").format(commissions_paid));

                    panel.remove(trans_scrollPane);

                    // Get transaction history
                    Transaction t = new Transaction();
                    Object[][] trans_list = t.getTransactionHistory(taxid);
                    String[] trans_columns = {"Date", "Type", "Shares", "AID", "Price", "Total"};

                    trans_table = new JTable(trans_list, trans_columns) {
                        public boolean isCellEditable(int row, int column) {                
                            return false;               
                        };
                    };

                    // allow scroll
                    trans_scrollPane = new JScrollPane(trans_table);
                    trans_table.setFillsViewportHeight(true);

                    // Change header colors
                    JTableHeader trans_header = trans_table.getTableHeader();
                    trans_header.setBackground(Color.black);
                    trans_header.setForeground(Color.yellow);

                    // Set column sizes
                    trans_table.getColumnModel().getColumn(0).setPreferredWidth(150);
                    trans_table.getColumnModel().getColumn(5).setPreferredWidth(100);

                    panel.add(trans_scrollPane);
                    panel.revalidate();
                    panel.repaint();
                }
            }
        });

        trans_table = new JTable();
        trans_scrollPane = new JScrollPane(trans_table);

        panel = new JPanel();
        info_panel = new JPanel(new GridLayout(3, 2));
        panel.add(back);
        panel.add(message);
        panel.add(taxid_label);
        panel.add(taxid_text);
        panel.add(submit);
        panel.add(info_panel);
        panel.add(trans_scrollPane);

        info_panel.add(name_label);
        info_panel.add(email_label);
        info_panel.add(initial_bal_label);
        info_panel.add(final_bal_label);
        info_panel.add(earnings_label);
        info_panel.add(commissions_paid_label);
    }
}
