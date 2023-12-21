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
    public Article ajouter(Article article) throws RemoteException {
        Connection con = null;
        try {
            con = DbConnection.connect();
            String query = "INSERT INTO pharmacie (barcode, articleName, articlePrice, stock) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
                preparedStatement.setString(1, article.getBarcode());
                preparedStatement.setString(2, article.getArticleName());
                preparedStatement.setDouble(3, article.getArticlePrice());
                preparedStatement.setInt(4, article.getStock());
                preparedStatement.executeUpdate();
                con.commit(); // Commit the transaction
            }
            System.out.println("Article added: " + article);
            return article;
        } catch (SQLException e) {
            e.printStackTrace();
            
        }
        return article;
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
    public Article search(String searchInput) throws RemoteException {
    try {
        Connection con = DbConnection.connect();

        // Search for an article by either barcode or name in the database
        String query = "SELECT * FROM pharmacie WHERE barcode = ? OR articleName = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, searchInput);
            pstmt.setString(2, searchInput);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    // Article found
                    Article article = new Article();
                    article.setBarcode(resultSet.getString("barcode"));
                    article.setArticleName(resultSet.getString("articleName"));
                    article.setArticlePrice(resultSet.getDouble("articlePrice"));
                    article.setStock(resultSet.getInt("stock"));

                    return article;
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RemoteException("Error searching for the article.", e);
    }

    //medicament not found
    return null;
}
   
@Override
public int decrementerStock(String input) throws RemoteException {
    try (Connection con = DbConnection.connect()) {
        // Check if the article with the given barcode exists
        String checkQuery = "SELECT stock FROM pharmacie WHERE barcode = ? OR articleName= ?";
        try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
            checkStmt.setString(1, input);
            checkStmt.setString(2, input);
            try (ResultSet resultSet = checkStmt.executeQuery()) {
                if (resultSet.next()) {
                    int currentStock = resultSet.getInt("stock");

                    // Check if there's enough stock to decrement
                    if (currentStock > 0) {
                        // Update the stock
                        String updateQuery = "UPDATE pharmacie SET stock = ? WHERE barcode = ? OR articleName= ?";
                        try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                            updateStmt.setInt(1, currentStock - 1);
                            updateStmt.setString(2, input);
                            updateStmt.setString(3, input);
                            int rowsAffected = updateStmt.executeUpdate();

                            if (rowsAffected > 0) {
                                con.commit();
                                return 1; // Successful stock decrement
                            } else {
                                con.rollback(); // Rollback if the update didn't affect any rows
                                return 0; // Insufficient stock
                            }
                        }
                    } else {
                        return 0; // Insufficient stock
                    }
                } else {
                    return -1; // Article not found
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new RemoteException("Error decrementing stock.", e);
    }
}



    @Override
    public List<Article> getArticles() throws RemoteException{
        
            List<Article> articles = new ArrayList<>();
            try (Connection connection = DbConnection.connect();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM pharmacie ORDER BY articleName");
             ResultSet resultSet = statement.executeQuery()) {
    
                while (resultSet.next()) {
                    Article article = new Article();
                    article.setBarcode(resultSet.getString("barcode"));
                    article.setArticleName(resultSet.getString("articleName"));
                    article.setArticlePrice(resultSet.getDouble("articlePrice"));
                    article.setStock(resultSet.getInt("stock"));
    
                    articles.add(article);
                }
            
    
            return articles;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error fetching Medicaments from the database.", e);
        } 
    }
 @Override
    public Article getArticle(String barcode) throws RemoteException{
        Article article = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnection.connect(); // Assuming DbConnection has a static connect() method

            // Prepare the SQL query
            String query = "SELECT * FROM pharmacie WHERE barcode = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, barcode);

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Check if a result is found
            if (resultSet.next()) {
                article = new Article();
                article.setBarcode(resultSet.getString("barcode"));
                article.setArticleName(resultSet.getString("articleName"));
                article.setArticlePrice(resultSet.getDouble("articlePrice"));
                article.setStock(resultSet.getInt("stock"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error fetching Medicament from the database.");
        } 

        return article;
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
