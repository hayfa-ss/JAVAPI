package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import models.Reservation;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    public static void generatePDF(String filePath, List<Reservation> reservations) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Ajouter le titre
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 19, Font.BOLD);
            Paragraph title = new Paragraph("Liste des Réservations", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Ajouter la photo
            Image image = Image.getInstance("/123.jpg"); // Remplacez "path/vers/votre/photo.jpg" par le chemin de votre photo
            image.setAlignment(Element.ALIGN_CENTER);
            image.scaleToFit(2, 2); // Ajustez la taille de l'image selon vos besoins
            document.add(image);

            // Ajouter une table avec les réservations
            PdfPTable table = new PdfPTable(7);
            // Ajouter les en-têtes de colonnes
            table.addCell("ID");
            table.addCell("Nombre de places");
            table.addCell("ID utilisateur");
            table.addCell("ID événement");
            table.addCell("Téléphone");
            table.addCell("Adresse");
            table.addCell("Email");

            // Ajouter les données de chaque réservation dans la table
            for (Reservation reservation : reservations) {
                table.addCell(String.valueOf(reservation.getId()));
                table.addCell(String.valueOf(reservation.getNbrplace()));
                table.addCell(String.valueOf(reservation.getId_user()));
                table.addCell(String.valueOf(reservation.getId_event()));
                table.addCell(String.valueOf(reservation.getTelephone()));
                table.addCell(reservation.getAdresse());
                table.addCell(reservation.getEmail());
            }

            // Ajouter la table au document
            document.add(table);

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }
}
