package services;

import entities.Payment;
import utils.MyDatabase;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import  java.util.Date;
import javafx.scene.image.Image;

public class ServicePersonne implements IService<Payment> {
    Connection connection;

    public ServicePersonne() {
        connection = MyDatabase.getInstance().getConnection();
    }

    // (NULL, 'a', 'b', 'c', 'd', '2024-04-17', '20', '1')

    public void ajouter(Payment personne) throws SQLException {
        String req = "INSERT INTO payment (id, first_name, last_name, email, payment_options, date, amount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        //  preparedStatement.setInt(1, personne.getId());
        preparedStatement.setString(2, personne.getFirstName());
        preparedStatement.setString(3, personne.getLastName());
        preparedStatement.setString(4, personne.getEmail());
        preparedStatement.setString(5, personne.getPaymentOptions());
        preparedStatement.setDate(6, new java.sql.Date(personne.getDate().getTime()));
        preparedStatement.setInt(7, personne.getAmount());

        preparedStatement.executeUpdate();
        System.out.println("Personne ajoutée avec succès !");
    }


    public void ajouter2(Payment personne) throws SQLException {
      Date adata= new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(adata);
        String req = "INSERT INTO payment (id, first_name, last_name, email, payment_options, date, amount, userid) VALUES (NULL, ?, ?, ?, ?, ?, ?, 1)";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, personne.getFirstName());
        preparedStatement.setString(2,personne.getLastName());
        preparedStatement.setString(3,personne.getEmail());
        preparedStatement.setString(4,personne.getPaymentOptions());
        preparedStatement.setString(5,formattedDate);
        preparedStatement.setInt(6,personne.getAmount());


        preparedStatement.executeUpdate();

    }

    @Override
    public void modifier(Payment personne) throws SQLException {

        String req = "UPDATE payment SET first_name=?, last_name=?, email=?, payment_options=?, date=?, amount=?  WHERE id=?";

        PreparedStatement preparedStatement = connection.prepareStatement(req);
        preparedStatement.setString(1, personne.getFirstName());
        preparedStatement.setString(2, personne.getLastName());
        preparedStatement.setString(3, personne.getEmail());
        preparedStatement.setString(4, personne.getPaymentOptions());
        preparedStatement.setString(5, personne.getDate().toString());
        preparedStatement.setInt(6, personne.getAmount());
        preparedStatement.setInt(7, personne.getId());
        preparedStatement.executeUpdate();


    }

    @Override
    public void supprimer(int id) throws SQLException {

        String req = "delete from payment where id=" + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);

    }

    @Override
    public List<Payment> afficher() throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String req = "select * from payment";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {

            Payment payment = new Payment();
            payment.setId(rs.getInt("id"));
            payment.setFirstName(rs.getString("first_name"));
            payment.setLastName(rs.getString("last_name"));
            payment.setEmail(rs.getString("email"));
            payment.setPaymentOptions(rs.getString("payment_options"));
            payment.setDate(rs.getDate("date"));
            payment.setAmount(rs.getInt("amount"));

            payments.add(payment);

        }
        return payments;
    }


    public List<Payment> afficherbyid(int i) throws SQLException {
        List<Payment> payments = new ArrayList<>();
        String req = "select * from payment where userid=" + i;
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {


            Payment payment = new Payment();
            payment.setId(rs.getInt("id"));
            payment.setFirstName(rs.getString("first_name"));
            payment.setLastName(rs.getString("last_name"));
            payment.setEmail(rs.getString("email"));
            payment.setPaymentOptions(rs.getString("payment_options"));
            payment.setDate(rs.getDate("date"));
            payment.setAmount(rs.getInt("amount"));

            payments.add(payment);

        }
        return payments;
    }


}