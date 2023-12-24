package project;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class PharmacieServer {
    public static void main(String[] args){
        try {
            PharmacieInterface pharmacieObject=new PharmacieImpl();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("PharmacieObject", pharmacieObject);
            System.out.println("Remote Object Bound");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
