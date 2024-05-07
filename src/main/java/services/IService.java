package services;

import models.Reservation;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void ajouter(T t) throws SQLException;

    void modifier(int id, T t) throws SQLException;


    void delete(int id) throws SQLException;

      List<T> recuperer() throws SQLException;
    List<Reservation> getAllReservations() throws SQLException ;
     List<Integer> getReservationStatsByPlace();
    void readById(int id);
}