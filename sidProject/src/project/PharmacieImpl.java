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
            System.out.println("Article deleted with barcode: " + barcode);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error deleting article.", e);
        }
    }

    @Override
    public int incrementerStock(String barcode) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'incrementerStock'");
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
            throw new RemoteException("Error fetching articles from the database.", e);
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
            throw new RemoteException("Error fetching article from the database.");
        } 

        return article;
    }

@Override
public String message(String m) throws RemoteException {
    return m;
}

}
