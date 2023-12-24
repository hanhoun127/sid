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

    private JTextField inputField;
    private JButton searchButton;
    private JLabel nameLabel;
    private JLabel priceLabel;
    private JLabel stockLabel;
    private JButton payButton;

    public VenteForm() throws RemoteException {
        server= new PharmacieImpl();
        setTitle("Sale Form");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inputField = new JTextField(20);
        searchButton = new JButton("Search");
        nameLabel = new JLabel("Name: ");
        priceLabel = new JLabel("Price: ");
        stockLabel = new JLabel("Stock: ");
        payButton = new JButton("Pay");

        setupUI();

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchMedicament();
            }
        });

        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
             try {
            int result = server.decrementerStock(inputField.getText());
            try {
                Registry registry = LocateRegistry.getRegistry("localhost");
                server = (PharmacieInterface) registry.lookup("PharmacieObject");
                if (result > 0) {
                    String messageS = server.message(4);
                    System.out.println("message from server: " + messageS);
                    Medicament Medicament = server.search(inputField.getText());
                    stockLabel.setText("Stock: " + Medicament.getStock());
                    PharmacieGUI gui=new PharmacieGUI();
                    gui.loadMedicamentList();
                } else if (result == 0) {
                    String messageS = server.message(5);
                    System.out.println("message from server: " + messageS);
                }
            } catch (NotBoundException e) {
                System.err.println("PharmacieObject not bound: " + e.getMessage());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        });
        setResizable(false);
        setVisible(true);
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        JPanel panel=new JPanel();
        panel.add(new JLabel("Enter Barcode or Medicament Name:"));
        panel.add(inputField);
        panel.add(searchButton);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        add(panel,BorderLayout.NORTH);
        JPanel pan=new JPanel();
        pan.add(nameLabel);
        pan.add(priceLabel);
        pan.add(stockLabel);
        pan.setLayout(new BoxLayout(pan,BoxLayout.Y_AXIS));
        add(pan);
        add(payButton,BorderLayout.SOUTH);
    }

    private void searchMedicament() {
        try {
            String input = inputField.getText();
            Medicament Medicament = server.search(input);

            if (Medicament != null) {
                nameLabel.setText("Name: " + Medicament.getMedicamentName());
                priceLabel.setText("Price: " + Medicament.getMedicamentPrice()+"DA");
                stockLabel.setText("Stock: " + Medicament.getStock());
            } else {
                // client
                try {
                    Registry registry = LocateRegistry.getRegistry("localhost");
                    server = (PharmacieInterface) registry.lookup("PharmacieObject");
                    String messageS=server.message(3);
                    System.out.println("message from server: "+messageS);
                }catch (NotBoundException e) {
                    System.err.println("PharmacieObject not bound: " + e.getMessage());
                } catch (RemoteException e) {
                    e.printStackTrace();
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching for the Medicament.");
        }
    }


    public static void main(String[] args) throws RemoteException{
        new VenteForm();
    }
}
