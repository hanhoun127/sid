package project;
import java.rmi.*;
import java.util.List;
public interface PharmicieInterface extends Remote{
    Article ajouter(Article article) throws RemoteException;
    Article supprimer(String barcode) throws RemoteException;
    int incrementerStock(String barcode) throws RemoteException;
    List<Article> getArticles() throws RemoteException;
    Article getArticle(String barcode) throws RemoteException;
}
