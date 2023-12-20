package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class VenteForm extends JFrame {

    private PharmacieInterface server;

    private JTextField codeBarreField;
    private JButton searchButton;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JButton payButton;

    public VenteForm() {
        // client
        try {
            Registry registry = LocateRegistry.getRegistry("localhost",1099);
            PharmacieInterface server = (PharmacieInterface) registry.lookup("PharmacieObject");
            String m=server.message("hi");
            System.out.println("message from server: "+m);
        }catch (NotBoundException e) {
            System.err.println("PharmacieObject not bound: " + e.getMessage());
        } catch (RemoteException e) {
            e.printStackTrace();
        } 
    

        setTitle("Sale Form");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        codeBarreField = new JTextField(20);
        searchButton = new JButton("Search");
        nameLabel = new JLabel("Name: ");
        priceLabel = new JLabel("Price: ");
        payButton = new JButton("Pay");

        setupUI();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchArticle();
            }
        });

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payForArticle();
            }
        });
    }

    private void setupUI() {
        setLayout(new FlowLayout());

        add(new JLabel("Enter Barcode or Article Name:"));
        add(codeBarreField);
        add(searchButton);
        add(nameLabel);
        add(priceLabel);
        add(payButton);
    }

    private void searchArticle() {
        try {
            String codeBarre = codeBarreField.getText();
            Article article = server.getArticle(codeBarre);

            if (article != null) {
                nameLabel.setText("Name: " + article.getArticleName());
                priceLabel.setText("Price: " + article.getArticlePrice());
            } else {
                JOptionPane.showMessageDialog(this, "Article not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for the article.");
        }
    }

    private void payForArticle() {
        try {
            String codeBarre = codeBarreField.getText();
            int result = server.incrementerStock(codeBarre);

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Payment successful. Stock decremented by 1.");
            } else {
                JOptionPane.showMessageDialog(this, "Payment failed. Stock may be insufficient.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error processing payment.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VenteForm venteForm = new VenteForm();
            venteForm.setVisible(true);
        });
    }
}
