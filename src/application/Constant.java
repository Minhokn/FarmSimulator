package application;

import javafx.scene.paint.Color;

/**
 * Toutes les constantes
 */
public interface Constant {
	
	String TITLE_APP = "Farm Simulator";

	String LAYOUT_PATH = "/application/layouts/";
	String STYLE_PATH = "/application/styles/";
	String IMAGE_PATH = "/application/images/";

	String HOME_LOGIN = "Connexion au compte";
	String PARAMS_ACCOUNT = "Configuration du compte";
	String FIRST_PARAMS_HOME_TITLE = "Configuration";
	String FIRST_PARAMS_INFOS_TITLE = "Configuration des informations";
	String FIRST_PARAMS_BDD_TITLE = "Configuration de la base de données";
	String ACCUEIL_APP_TITLE = "Accueil de la gestion de l'ETA";
	String CLIENT_APP_TITLE = "Gestion des clients de l'ETA";
	String ADD_CLIENT_APP_TITLE = "Ajouter un client";
	String VEHICULE_APP_TITLE = "Gestions des véhicules de l'ETA";
	String ADD_VEHICULE_APP_TITLE = "Ajouter un véhicule à l'ETA";
	String CHAMP_APP_TITLE = "Gestion des champs de l'ETA";
	String ADD_CHAMP_APP_TITLE = "Ajouter un champs à l'ETA";

	int MIN_WIDTH = 910;
	int MIN_HEIGHT = 800;
	int PREF_WIDTH = 1100;
	int PREF_HEIGHT = 850;
	
	Color SUCCESS_COLOR = Color.LIMEGREEN;
	Color ERROR_COLOR = Color.rgb(235, 47, 47);
}