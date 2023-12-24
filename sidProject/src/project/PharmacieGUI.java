package project;

import java.awt.BorderLayout;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PharmacieGUI extends JFrame {

    private PharmacieInterface pharmacieObject;

    private DefaultTableModel tableModel;
    private JTable MedicamentTable;
    private JButton addButton;
    private JButton deleteButton;

    public PharmacieGUI() throws RemoteException {
        pharmacieObject = new PharmacieImpl();
        setTitle("Pharmacy Management System");
        setSize(800, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        MedicamentTable = new JTable(tableModel);
        addButton = new JButton("Add Medicament");
        deleteButton = new JButton("Delete Medicament");

        setupUI();
        loadMedicamentList();

        addButton.addActionListener(e -> showAddMedicamentDialog());
        deleteButton.addActionListener(e -> deleteSelectedMedicament());
        setVisible(true);
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(MedicamentTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    

    public void loadMedicamentList() {
        try {
            System.out.println("Loading Medicaments...");
            System.out.println("pharmacieObject: " + pharmacieObject);
            List<Medicament> Medicaments = pharmacieObject.getMedicaments();
            displayMedicamentsInTable(Medicaments);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading Medicaments from the server.");
        }
    }
    

    private void displayMedicamentsInTable(List<Medicament> Medicaments) {
        tableModel.setColumnIdentifiers(new String[]{"Barcode", "Medicament Name", "Medicament Price (DA)", "Stock"});
        tableModel.setRowCount(0);

        for (Medicament Medicament : Medicaments) {
            Object[] rowData = {Medicament.getBarcode(), Medicament.getMedicamentName(), Medicament.getMedicamentPrice(), Medicament.getStock()};
            tableModel.addRow(rowData);
        }
    }

    private void showAddMedicamentDialog() {
        try {
            Medicament newMedicament = new Medicament();
            
            String barcode = JOptionPane.showInputDialog(this, "Enter Barcode:");
            if (barcode != null) {
                newMedicament.setBarcode(barcode);
                
                String MedicamentName = JOptionPane.showInputDialog(this, "Enter Medicament Name:");
                if (MedicamentName != null) {
                    newMedicament.setMedicamentName(MedicamentName);
                    
                    String MedicamentPriceStr = JOptionPane.showInputDialog(this, "Enter Medicament Price:");
                    if (MedicamentPriceStr != null) {
                        double MedicamentPrice = Double.parseDouble(MedicamentPriceStr);
                        newMedicament.setMedicamentPrice(MedicamentPrice);
                        String MedicamentStock = JOptionPane.showInputDialog(this, "Enter Medicament Stock:");
                        if (MedicamentStock != null) {
                            int stock = Integer.parseInt(MedicamentStock);
                            newMedicament.setStock(stock);
                        pharmacieObject.ajouter(newMedicament);
                        // client
                        try {
                            Registry registry = LocateRegistry.getRegistry("localhost");
                            PharmacieInterface server = (PharmacieInterface) registry.lookup("PharmacieObject");
                            String messageS=server.message(1);
                            System.out.println("message from server: "+messageS);
                        }catch (NotBoundException e) {
                            System.err.println("PharmacieObject not bound: " + e.getMessage());
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        } 
                        // Refresh the table
                        loadMedicamentList();
                    }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding a new Medicament.");
        }
    }
    
    private void deleteSelectedMedicament() {
        int selectedRow = MedicamentTable.getSelectedRow();
        if (selectedRow != -1) {
            // Get the barcode of the selected Medicament from the table
            String barcode = (String) tableModel.getValueAt(selectedRow, 0);
    
            try {
                // Call the RMI method to delete the selected Medicament
                pharmacieObject.supprimer(barcode);
                // client
                try {
                    Registry registry = LocateRegistry.getRegistry("localhost");
                    PharmacieInterface server = (PharmacieInterface) registry.lookup("PharmacieObject");
                    String messageS=server.message(2);
                    System.out.println("message from server: "+messageS);
                }catch (NotBoundException e) {
                    System.err.println("PharmacieObject not bound: " + e.getMessage());
                } catch (RemoteException e) {
                    e.printStackTrace();
                } 
                // Refresh the table
                loadMedicamentList();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error deleting the selected medicament.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an medicament to delete.");
        }
    }

    public static void main(String[] args) throws RemoteException {
        new PharmacieGUI(); 
    }
}
