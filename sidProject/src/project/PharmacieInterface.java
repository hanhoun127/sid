package project;
import java.rmi.*;
import java.util.List;
public interface PharmacieInterface extends Remote{
    Article ajouter(Article article) throws RemoteException;
    void supprimer(String barcode) throws RemoteException;
    Article  search(String searchInput) throws RemoteException;
    int decrementerStock(String barcode) throws RemoteException;
    List<Article> getArticles() throws RemoteException;
    Article getArticle(String barcode) throws RemoteException;
    String message(int n) throws RemoteException;
}
