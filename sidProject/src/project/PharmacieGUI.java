package project;

import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PharmacieGUI extends JFrame {

    private PharmacieInterface pharmacieObject;

    private DefaultTableModel tableModel;
    private JTable articleTable;
    private JButton addButton;
    private JButton deleteButton;

    public PharmacieGUI() throws RemoteException {
        pharmacieObject = new PharmacieImpl();
        setTitle("Pharmacy Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        articleTable = new JTable(tableModel);
        addButton = new JButton("Add Medicament");
        deleteButton = new JButton("Delete Medicament");

        setupUI();
        loadArticleList();

        addButton.addActionListener(e -> showAddArticleDialog());
        deleteButton.addActionListener(e -> deleteSelectedArticle());
        setVisible(true);
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

    

    private void loadArticleList() {
        try {
            System.out.println("Loading articles...");
            System.out.println("pharmacieObject: " + pharmacieObject); // Add this line
            List<Article> articles = pharmacieObject.getArticles();
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

    private void deleteSelectedArticle() {
        int selectedArticle = articleTable.getSelectedRow();
        if (selectedArticle > -1) {
            // Implement logic for deleting the selected article
        } else {
            JOptionPane.showMessageDialog(this, "Please select an article to delete.");
        }
    }

    public static void main(String[] args) throws RemoteException {
        new PharmacieGUI(); 
    }
}
