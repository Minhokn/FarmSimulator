package application.controlleurs;

import application.Constant;
import application.classes.AlertDialog;
import application.classes.SwitchView;
import application.modeles.UserSQL;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.security.NoSuchAlgorithmException;

/**
 * Controlleur de la vue de connexion au logiciel
 */
public class HomeLoginController {

	/** Layout **/
	@FXML private BorderPane bpane;

	/** Elements **/
	@FXML private JFXTextField user_login; // Champs identifiant
	@FXML private JFXPasswordField password_login; // Champs mot de passe
	
    /**
     * Initializes the controller class.
     */
	public void initialize() {
		bpane.setOnMouseClicked(e -> bpane.requestFocus());
	}

	@FXML private void btnLoginAction() throws NoSuchAlgorithmException {
		String userInput = user_login.getText().trim();
		String passInput = password_login.getText().trim();
		if (!userInput.isEmpty() && !passInput.isEmpty()) {
			login(userInput, passInput);
		} else {
			AlertDialog alert = new AlertDialog("Erreur", null, "Veuillez saisir tous les champs de texte !", Alert.AlertType.ERROR);
			alert.show();
		}
	}

	private void login(String email, String password) throws NoSuchAlgorithmException {
		if(UserSQL.checkIdentifiants(email, password))
			loadHome();
		else {
			AlertDialog alert = new AlertDialog("Erreur", null, "Identifiants incorrectes !\nRéessayez.", Alert.AlertType.ERROR);
			alert.show();
		}
    }

	private void loadHome() {
        SwitchView switchView = new SwitchView("accueil_app", Constant.ACCUEIL_APP_TITLE,bpane);
        switchView.showScene();
	}

}