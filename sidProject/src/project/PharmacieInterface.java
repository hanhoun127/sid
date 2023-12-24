package project;
import java.rmi.*;
import java.util.List;
public interface PharmacieInterface extends Remote{
    Medicament ajouter(Medicament medicament) throws RemoteException;
    void supprimer(String barcode) throws RemoteException;
    Medicament  search(String searchInput) throws RemoteException;
    int decrementerStock(String barcode) throws RemoteException;
    List<Medicament> getMedicaments() throws RemoteException;
    Medicament getMedicament(String barcode) throws RemoteException;
    String message(int n) throws RemoteException;
}
