package services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void ajouter(T t) throws SQLException;

    void modifier(int id, T t) throws SQLException;


    void delete(int id) throws SQLException;

    List<T> recuperer() throws SQLException;


    void readById(int id);
}
