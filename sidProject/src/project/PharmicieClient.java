package project;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List; // Add this import statement

import javax.swing.SwingUtilities;

public class PharmicieClient {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                PharmicieInterface server = (PharmicieInterface) registry.lookup("Server");

                // Now you can use the 'server' reference to interact with the remote methods

                // Example: Get the list of articles from the server
                List<Article> articles = server.getArticles();
                for (Article article : articles) {
                    System.out.println(article.getBarcode() + " - " + article.getArticleName());
                }

            } catch (Exception e) {
                System.err.println("Client Exception: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
