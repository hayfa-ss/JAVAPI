package services;

import java.sql.SQLException;
import java.util.List;

public interface NService<N> {

    void ajouter(N t) throws SQLException;

    void modifier(int idn, N t) throws SQLException;


    void delete(int idn) throws SQLException;

    List<N> recuperer() throws SQLException;


    void readById(int idn);
}
