package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatbotController implements Initializable {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField userInput;


    @FXML
    private VBox chatContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Other initialization...

        if (chatContainer != null) {
            URL cssResource = getClass().getResource("/styles.css");
            if (cssResource != null) {
                chatContainer.getStylesheets().add(cssResource.toExternalForm());
            } else {
                System.err.println("CSS file not found!");
            }
        }
    }




    public void sendMessage() {
        String userMessage = userInput.getText();
        String botResponse = getChatbotResponse(userMessage);

        // Créer une HBox pour le message de l'utilisateur
        HBox userMessageContainer = createMessageContainer("Vous : " + userMessage);
        // Créer une HBox pour la réponse du chatbot
        HBox botResponseContainer = createMessageContainer("Chatbot : " + botResponse);

        // Ajouter les HBox au conteneur principal (VBox)
        chatContainer.getChildren().addAll(userMessageContainer, botResponseContainer);

        // Effacer le champ de texte
        userInput.clear();
    }

    private HBox createMessageContainer(String message) {
        TextArea messageArea = new TextArea(message);
        messageArea.setWrapText(true);
        messageArea.setEditable(false);

        HBox messageContainer = new HBox(messageArea);
        messageContainer.getStyleClass().add("message-container");

        return messageContainer;
    }




    private String getChatbotResponse(String userMessage) {
        // Logique du chatbot
        if (userMessage.toLowerCase().contains("bonjour")) {
            return "Bonjour ! Comment puis-je vous aider aujourd'hui ?";
        } else if (userMessage.toLowerCase().contains("météo")) {
            return "Je suis désolé, je ne peux pas vous fournir la météo pour le moment.";
        } else if (userMessage.toLowerCase().contains("aide")) {
            return "Vous pouvez me poser des questions sur divers sujets. Essayez de demander la météo ou dites simplement bonjour !";
        } else if (userMessage.toLowerCase().contains("réservation")) {
            return "Pour effectuer une réservation, veuillez fournir les détails nécessaires tels que le nombre de places, la date et l'heure.";
        } else if (userMessage.toLowerCase().contains("comment réserver")) {
            return "Vous pouvez effectuer une réservation en fournissant le nombre de places, la date et l'heure souhaités.";
        } else if (userMessage.toLowerCase().contains("notification")) {
            return "Les notifications sont utilisées pour vous informer des mises à jour importantes. Pouvez-vous préciser votre demande ?";
        } else if (userMessage.toLowerCase().contains("comment recevoir des notifications")) {
            return "Pour recevoir des notifications, assurez-vous que vos préférences de notification sont correctement configurées dans votre compte.";
        } else if (userMessage.toLowerCase().contains("contacter cityvibe")) {
            return "Pour contacter l'équipe CityVibe Reservation, veuillez envoyer un e-mail à support@cityvibereservation.com.";
      /*  } else if {
            return "Désolé, je ne comprends pas. Pouvez-vous reformuler votre question ?";
        }*/
        }   else if  (userMessage.toLowerCase().contains("hello")) {
            return "Hello! How can I assist you today?";
        } else if (userMessage.toLowerCase().contains("weather")) {
            return "I'm sorry, I can't provide the weather right now.";
        } else if (userMessage.toLowerCase().contains("help")) {
            return "You can ask me questions on various topics. Try asking about the weather or just say hello!";
        } else if (userMessage.toLowerCase().contains("reservation")) {
            return "To make a reservation, please provide the necessary details such as the number of seats, date, and time.";
        } else if (userMessage.toLowerCase().contains("how to book")) {
            return "You can make a reservation by providing the desired number of seats, date, and time.";
        } else if (userMessage.toLowerCase().contains("notification")) {
            return "Notifications are used to inform you about important updates. Can you specify your request?";
        } else if (userMessage.toLowerCase().contains("how to receive notifications")) {
            return "To receive notifications, make sure your notification preferences are correctly configured in your account.";
        } else if (userMessage.toLowerCase().contains("contact cityvibe")) {
            return "To contact the CityVibe Reservation team, please send an email to support@cityvibereservation.com.";

        }
        if (userMessage.toLowerCase().contains("مرحبًا")) {
            return "مرحبًا! كيف يمكنني مساعدتك اليوم؟";
        } else if (userMessage.toLowerCase().contains("الطقس")) {
            return "آسف، لا أستطيع تقديم توقعات الطقس في الوقت الحالي.";
        } else if (userMessage.toLowerCase().contains("المساعدة")) {
            return "يمكنك طرح الأسئلة على مواضيع مختلفة. جرب أن تسأل عن الطقس أو قل مرحبًا!";
        } else if (userMessage.toLowerCase().contains("الحجز")) {
            return "لإجراء حجز، يرجى توفير التفاصيل اللازمة مثل عدد الأماكن، والتاريخ، والوقت.";
        } else if (userMessage.toLowerCase().contains("كيفية الحجز")) {
            return "يمكنك إجراء حجز عن طريق توفير عدد الأماكن المطلوبة، والتاريخ، والوقت.";
        } else if (userMessage.toLowerCase().contains("التنبيهات")) {
            return "تُستخدم التنبيهات لإعلامك بالتحديثات الهامة. هل يمكنك تحديد طلبك؟";
        } else if (userMessage.toLowerCase().contains("كيفية استلام التنبيهات")) {
            return "لتلقي التنبيهات، تأكد من أن تفضيلات التنبيه الخاصة بك مكونة بشكل صحيح في حسابك.";
        } else if (userMessage.toLowerCase().contains("التواصل مع CityVibe")) {
            return "للتواصل مع فريق حجوزات CityVibe، يرجى إرسال بريد إلكتروني إلى support@cityvibereservation.com.";

        } else if (userMessage.toLowerCase().contains("wetter")) {
            return "Es tut mir leid, ich kann Ihnen das Wetter im Moment nicht mitteilen.";
        } else if (userMessage.toLowerCase().contains("hilfe")) {
            return "Sie können mir Fragen zu verschiedenen Themen stellen. Versuchen Sie, nach dem Wetter zu fragen oder sagen Sie einfach hallo!";
        } else if (userMessage.toLowerCase().contains("reservierung")) {
            return "Um eine Reservierung vorzunehmen, geben Sie bitte die erforderlichen Details wie die Anzahl der Plätze, das Datum und die Uhrzeit an.";
        } else if (userMessage.toLowerCase().contains("wie bucht man")) {
            return "Sie können eine Reservierung vornehmen, indem Sie die gewünschte Anzahl der Plätze, das Datum und die Uhrzeit angeben.";
        } else if (userMessage.toLowerCase().contains("benachrichtigung")) {
            return "Benachrichtigungen werden verwendet, um Sie über wichtige Updates zu informieren. Können Sie Ihre Anfrage näher spezifizieren?";
        } else if (userMessage.toLowerCase().contains("wie man benachrichtigungen empfängt")) {
            return "Um Benachrichtigungen zu erhalten, stellen Sie sicher, dass Ihre Benachrichtigungseinstellungen in Ihrem Konto korrekt konfiguriert sind.";
        } else if (userMessage.toLowerCase().contains("kontakt cityvibe")) {
            return "Um das CityVibe Reservation Team zu kontaktieren, senden Sie bitte eine E-Mail an support@cityvibereservation.com.";

            } else if (userMessage.toLowerCase().contains("meteo")) {
                return "Mi dispiace, al momento non posso fornirti le previsioni del tempo.";
            } else if (userMessage.toLowerCase().contains("aiuto")) {
                return "Puoi farmi domande su vari argomenti. Prova a chiedere del tempo o saluta semplicemente!";
            } else if (userMessage.toLowerCase().contains("prenotazione")) {
                return "Per effettuare una prenotazione, fornisci i dettagli necessari come il numero di posti, la data e l'orario.";
            } else if (userMessage.toLowerCase().contains("come prenotare")) {
                return "Puoi effettuare una prenotazione fornendo il numero desiderato di posti, la data e l'orario.";
            } else if (userMessage.toLowerCase().contains("notifica")) {
                return "Le notifiche vengono utilizzate per informarti sugli aggiornamenti importanti. Puoi specificare la tua richiesta?";
            } else if (userMessage.toLowerCase().contains("come ricevere notifiche")) {
                return "Per ricevere notifiche, assicurati che le tue preferenze di notifica siano correttamente configurate nel tuo account.";
            } else if (userMessage.toLowerCase().contains("contattare cityvibe")) {
                return "Per contattare il team di prenotazioni di CityVibe, invia un'email a support@cityvibereservation.com.";
            } else {
                return "Désolé, je ne comprends pas. Pouvez-vous reformuler votre question ?";}}


}