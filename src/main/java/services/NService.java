package services;

import javax.mail.MessagingException;
import java.sql.SQLException;
import java.util.List;

public interface NService<N> {

    void ajouter(N t) throws SQLException;

    void modifier(int idn, N t) throws SQLException;


    void delete(int idn) throws SQLException;

    List<N> recuperer() throws SQLException;

    // Nouvelle méthode pour récupérer les notifications avec jointure
    List<N> recupererAvecJointure() throws SQLException;
    int readById(int idn);
    void sendEmail(String to, String descri) throws MessagingException;
}