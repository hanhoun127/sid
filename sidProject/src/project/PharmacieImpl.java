package project;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbConnection.DbConnection;

public class PharmacieImpl extends UnicastRemoteObject implements PharmacieInterface{
    private static final long serialVersionUID = 1L;
    public PharmacieImpl() throws RemoteException {
        super();
    }
    @Override
    public Medicament ajouter(Medicament Medicament) throws RemoteException {
        Connection con = null;
        try {
            con = DbConnection.connect();
            String query = "INSERT INTO pharmacie (barcode, name, price, stock) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, Medicament.getBarcode());
                preparedStatement.setString(2, Medicament.getMedicamentName());
                preparedStatement.setDouble(3, Medicament.getMedicamentPrice());
                preparedStatement.setInt(4, Medicament.getStock());
                preparedStatement.executeUpdate();
                con.commit(); // Commit the transaction
            }
            System.out.println("Medicament added: " + Medicament);
            return Medicament;
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return Medicament;
    }
    

    @Override
    public void supprimer(String barcode) throws RemoteException {
        try {
            Connection con = DbConnection.connect();
            String query = "DELETE FROM pharmacie WHERE barcode = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, barcode);
                preparedStatement.executeUpdate();
            }
            System.out.println("Medicament deleted with barcode: " + barcode);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error deleting Medicament.", e);
        }
    }

    @Override
    public Medicament search(String searchInput) throws RemoteException {
    try {
        Connection con = DbConnection.connect();

        // Search for an Medicament by either barcode or name in the database
        String query = "SELECT * FROM pharmacie WHERE barcode = ? OR name = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, searchInput);
            pstmt.setString(2, searchInput);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    // Medicament found
                    Medicament Medicament = new Medicament();
                    Medicament.setBarcode(resultSet.getString("barcode"));
                    Medicament.setMedicamentName(resultSet.getString("name"));
                    Medicament.setMedicamentPrice(resultSet.getDouble("price"));
                    Medicament.setStock(resultSet.getInt("stock"));

                    return Medicament;
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RemoteException("Error searching for the Medicament.", e);
    }

    //medicament not found
    return null;
}
   
@Override
public int decrementerStock(String input) throws RemoteException {
    try (Connection con = DbConnection.connect()) {
        // Check if the Medicament with the given barcode exists
        String checkQuery = "SELECT stock FROM pharmacie WHERE barcode = ? OR name= ?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
            checkStmt.setString(1, input);
            checkStmt.setString(2, input);
            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next()) {
                    int currentStock = resultSet.getInt("stock");

                    if (currentStock > 0) {
                        String updateQuery = "UPDATE pharmacie SET stock = ? WHERE barcode = ? OR name= ?";
                        try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, currentStock - 1);
                            updateStmt.setString(2, input);
                            updateStmt.setString(3, input);
                            int rowsAffected = updateStmt.executeUpdate();

                            if (rowsAffected > 0) {
                                con.commit();
                                return 1; 
                            } else {
                                con.rollback(); 
                                return 0; 
                            }
                        }
                    } else {
                        String deleteQuery = "DELETE stock FROM pharmacie  WHERE stock= ?";
                        try (PreparedStatement deleteStmt = con.prepareStatement(deleteQuery)) {
                            deleteStmt.setInt(1, 0);
                            deleteStmt.executeUpdate();
                            return 0;
                        }
                    }
                } else {
                    return -1; // Medicament not found
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RemoteException("Error decrementing stock.", e);
    }
}



    @Override
    public List<Medicament> getMedicaments() throws RemoteException{
        
            List<Medicament> Medicaments = new ArrayList<>();
            try (Connection connection = DbConnection.connect();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM pharmacie ORDER BY name");
             ResultSet resultSet = statement.executeQuery()) {
    
                while (resultSet.next()) {
                    Medicament Medicament = new Medicament();
                    Medicament.setBarcode(resultSet.getString("barcode"));
                    Medicament.setMedicamentName(resultSet.getString("name"));
                    Medicament.setMedicamentPrice(resultSet.getDouble("price"));
                    Medicament.setStock(resultSet.getInt("stock"));
    
                    Medicaments.add(Medicament);
                }
            
    
            return Medicaments;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error fetching Medicaments from the database.", e);
        } 
    }
 @Override
    public Medicament getMedicament(String barcode) throws RemoteException{
        Medicament Medicament = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnection.connect();

            String query = "SELECT * FROM pharmacie WHERE barcode = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, barcode);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Medicament = new Medicament();
                Medicament.setBarcode(resultSet.getString("barcode"));
                Medicament.setMedicamentName(resultSet.getString("name"));
                Medicament.setMedicamentPrice(resultSet.getDouble("price"));
                Medicament.setStock(resultSet.getInt("stock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error fetching Medicament from the database.");
        } 

        return Medicament;
    }

@Override
public String message(int n) throws RemoteException {
    String message=null;
    switch(n){
        case 1:
            message="medicament added successfully";
            break;
        case 2:
            message="medicament removed successfully";
            break;
        case 3:
            message="Medicament not found.";
            break;
        case 4:
            message="Stock decremented by 1";
            break;
        case 5:
            message="Payment failed. Stock may be insufficient";
            break;
    }
    return message;
}


}
