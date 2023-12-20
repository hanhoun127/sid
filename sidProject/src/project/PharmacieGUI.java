package project;
import dbConnection.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.util.List;

public class PharmacieGUI extends JFrame {

    private PharmicieInterface server;

    private DefaultTableModel tableModel;
    private JTable articleTable;
    private JButton addButton;
    private JButton deleteButton;

    public PharmacieGUI() {
        initRMIServer();
        setTitle("Pharmacy Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        articleTable = new JTable(tableModel);
        addButton = new JButton("Add Medicament");
        deleteButton = new JButton("Delete Medicament");

        setupUI();
        loadArticleList();

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddArticleDialog();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedArticle();
            }
        });
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(articleTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    private void initRMIServer() {
        try {
            server = new PharmicieImpl();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("Server", server);
            System.out.println("Remote Object Bound");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connecting to the RMI server.");
        }
    }
    
    private void loadArticleList() {
        try {
            List<Article> articles = server.getArticles();
            displayArticlesInTable(articles);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading articles from the server.");
        }
    }

    private void displayArticlesInTable(List<Article> articles) {
        tableModel.setColumnIdentifiers(new String[]{"Barcode", "Medicament Name", "Medicament Price", "Stock"});
        tableModel.setRowCount(0);

        for (Article article : articles) {
            Object[] rowData = {article.getBarcode(), article.getArticleName(), article.getArticlePrice(), article.getStock()};
            tableModel.addRow(rowData);
        }
    }

    private void showAddArticleDialog() {
        // Implement the logic for adding a new article
        // You can use JOptionPane.showInputDialog or create a custom dialog
        // and call server.ajouter() to add the new article
    }

    private void showUpdateArticleDialog() {
        // Implement the logic for updating an existing article
        // You can use JOptionPane.showInputDialog or create a custom dialog
        // and call server.modifier() to update the selected article
    }

    private void deleteSelectedArticle() {
        int selectedArticle = articleTable.getSelectedRow();
        if (selectedArticle > -1) {
        } else {
            JOptionPane.showMessageDialog(this, "Please select an article to delete.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PharmacieGUI gui = new PharmacieGUI();
            gui.setVisible(true);
        });
    }
}
