package fr.cils.projet.stage;

import fr.cils.projet.stage.entity.Role;
import fr.cils.projet.stage.entity.Utilisateur;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Controller
{
    @FXML
    private GridPane apparenceGenerale;
    @FXML
    private Button btnConsulterOffre;
    @FXML
    private Button btnAjoutOffre;
    @FXML
    private Button btnAjoutEntreprise;
    @FXML
    private Button btnListeEntreprises;
    @FXML
    private Button btnListeUtilisateurs;
    @FXML
    private Button btnAbout;

    static Utilisateur currentUser;

    static Controller instance;

    @FXML
    public void initialize()
    {
        //impossible de rajouter correctement de graphic depuis le FXML
        //Donc on le fait en code
        btnConsulterOffre.setGraphic(new ImageView("file:res/consulter-offre.png"));
        btnAjoutOffre.setGraphic(new ImageView("file:res/ajouter-offre.png"));
        btnAjoutEntreprise.setGraphic(new ImageView("file:res/ajouter-entreprise.png"));
        btnAbout.setGraphic(new ImageView("file:res/about.png"));
        btnListeEntreprises.setGraphic(new ImageView("file:res/company-list.png"));
        btnListeUtilisateurs.setGraphic(new ImageView("file:res/user-list.png"));

        if(currentUser.role == Role.Utilisateur)
        {
            btnAjoutEntreprise.setDisable(true);
            btnAjoutOffre.setDisable(true);
            btnListeEntreprises.setDisable(true);
            btnListeUtilisateurs.setDisable(true);
        }

        else if(currentUser.role == Role.Entreprise)
        {
            // Horo : En fait, ça aurait plus de sens que les entreprises
            // puissent gérer les entreprises qu'elles ont créées
            // Donc je commente ça pour le moment
//            btnListeEntreprises.setDisable(true);
            btnListeUtilisateurs.setDisable(true);
        }
        instance = this;
    }

    public void switchApparence(Event mouseEvent) throws IOException
    {

        Button boutonClique = (Button) mouseEvent.getSource();
        apparenceGenerale.getChildren().remove(apparenceGenerale.lookup("#fxmlActif"));

        switch (boutonClique.getId())
        {
            case "btnConsulterOffre":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/consulter-offre.fxml")), 1, 0);
                break;

            case "btnAjoutOffre":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/creer-offre.fxml")), 1, 0);
                break;

            case "btnAjoutEntreprise":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/creer-entreprise.fxml")), 1, 0);
                break;
            case "btnListeEntreprises":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/liste-entreprises.fxml")), 1, 0);
                break;
            case "btnListeUtilisateurs":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/liste-utilisateurs.fxml")), 1, 0);
                break;
            case "goModifierEntreprise":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/modifier-entreprise.fxml")), 1, 0);
                break;
            case "goModifierUtilisateur":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/modifier-utilisateur.fxml")), 1, 0);
                break;
            case "modifierEntreprise":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/liste-entreprises.fxml")), 1, 0);
                break;
            case "modifierUtilisateur":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/liste-utilisateurs.fxml")), 1, 0);
                break;
            case "supprimerUtilisateur":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/liste-utilisateurs.fxml")), 1, 0);
                break;
            case "supprimerEntreprise":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/liste-entreprises.fxml")), 1, 0);
                break;
            case "boutonPostulants":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/liste-postulants.fxml")), 1, 0);
                break;
            case "validerPostulant":
                apparenceGenerale.add(FXMLLoader.load(getClass().getResource("ui/liste-postulants.fxml")), 1, 0);
                break;

            default:
                System.out.println("nope");
                break;
        }
    }

    public void showAbout(MouseEvent mouseEvent)
    {
        Alert aboutPopup = new Alert(Alert.AlertType.INFORMATION);
        aboutPopup.setTitle("À propos");
        aboutPopup.setHeaderText("À propos");
        aboutPopup.setContentText("Ce logiciel a été développé par :\n" +
                "- Horoneru\n" +
                "- Antoria\n" +
                "- Bobpty\n" +
                "Vous pouvez trouver le code source de ce logiciel sur Github");
        aboutPopup.showAndWait();
    }

    public static void changerMenuPrincipal(Stage stage, Parent root)
    {
        Scene scene = new Scene(root); // on affiche la nouvelle fenêtre
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Chiffre le mot de passe de l'uitilisateur
     * source : https://www.developpez.net/forums/d92122/java/general-java/apis/securite/algorithme-hachage-type-md5-sha1/
     * @param s le mot de passe de l'utilisateur à chiffrer
     * @return le mot de passe chiffré sous forme de chaîne de caractères
     */
    public static String chiffrementSHA1(String s)
    {

        byte[] uniqueKey = s.getBytes();
        byte[] hash = null;

        try
        {
            hash = MessageDigest.getInstance("SHA1").digest(uniqueKey); //MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error(e);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        StringBuffer hashString = new StringBuffer();
        for (int i = 0; i < hash.length; ++i)
        {
            String hex = Integer.toHexString(hash[i]);
            if ( hex.length() == 1 )
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length()-1));
            }
            else
            {
                hashString.append(hex.substring(hex.length()-2));
            }
        }
        return hashString.toString();
    }
}
