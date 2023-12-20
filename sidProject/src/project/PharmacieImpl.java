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
    public PharmacieImpl() throws RemoteException {
        super();
    }

    @Override
    public Article ajouter(Article article) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ajouter'");
    }

    @Override
    public Article supprimer(String barcode) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'supprimer'");
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
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM pharmacie");
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
