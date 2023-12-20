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
        setSize(800, 400);
        setResizable(false);
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
        tableModel.setColumnIdentifiers(new String[]{"Barcode", "Medicament Name", "Medicament Price (DA)", "Stock"});
        tableModel.setRowCount(0);

        for (Article article : articles) {
            Object[] rowData = {article.getBarcode(), article.getArticleName(), article.getArticlePrice(), article.getStock()};
            tableModel.addRow(rowData);
        }
    }

    private void showAddArticleDialog() {
        try {
            // Create a new Article object to hold the data
            Article newArticle = new Article();
            
            // You can create a custom dialog or use JOptionPane.showInputDialog
            String barcode = JOptionPane.showInputDialog(this, "Enter Barcode:");
            if (barcode != null) {
                newArticle.setBarcode(barcode);
                
                String articleName = JOptionPane.showInputDialog(this, "Enter Medicament Name:");
                if (articleName != null) {
                    newArticle.setArticleName(articleName);
                    
                    String articlePriceStr = JOptionPane.showInputDialog(this, "Enter Medicament Price:");
                    if (articlePriceStr != null) {
                        double articlePrice = Double.parseDouble(articlePriceStr);
                        newArticle.setArticlePrice(articlePrice);
                        String articleStock = JOptionPane.showInputDialog(this, "Enter Medicament Stock:");
                        if (articleStock != null) {
                            int stock = Integer.parseInt(articleStock);
                            newArticle.setStock(stock);
                        pharmacieObject.ajouter(newArticle);
                        
                        // Refresh the table
                        loadArticleList();
                    }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding a new article.");
        }
    }
    
    private void deleteSelectedArticle() {
        int selectedRow = articleTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get the barcode of the selected article from the table
            String barcode = (String) tableModel.getValueAt(selectedRow, 0);
    
            try {
                // Call the RMI method to delete the selected article
                pharmacieObject.supprimer(barcode);
                
                // Refresh the table
                loadArticleList();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting the selected article.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an article to delete.");
        }
    }

    public static void main(String[] args) throws RemoteException {
        new PharmacieGUI(); 
    }
}
