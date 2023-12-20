package project;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class PharmicieServer {
    public static void main(String[] args){
        try {
            PharmicieInterface pharmicieObject=new PharmicieImpl();
            LocateRegistry.createRegistry(1099);
            Naming.rebind("PharmicieObject", pharmicieObject);
            System.out.println("Remote Object Bound");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
