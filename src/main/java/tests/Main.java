package tests;

import entities.Transports;
import entities.guide;
import services.ServiceGuide;
import services.ServiceTransports;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
         guide guidee1= new guide(43,"5","2","fff","fff");
         guide guidee= new guide("55","fff","fff","fff");
         Transports transport= new Transports("55","55",55,16);



        ServiceGuide serviceguide= new ServiceGuide();
        ServiceTransports serviceTransports= new ServiceTransports();

        try{
            serviceTransports.supprimer(16);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }


        try {
            System.out.println(serviceTransports.afficher());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}