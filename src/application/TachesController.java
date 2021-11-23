//Cette classe est le controller principal de l'application. Il s'occupe de la logique general, traite les actions de l'usager, et communique et met a jour le Model et le View. Il utilise la BD a travers la classe TacheDAO.
package application;

//Imports necessaires

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TachesController implements Initializable {

	// Definition des TableView pour les deux onglets:

	private int selectedTache=0;//Variable qui contient l'ID de la tache selectionne
	@FXML
	private TableView<Tache> GTableTaches;// Table view de taches sur l'onglet gestionnaire

	@FXML
	private TableView<Tache> PTableTaches;// Table view de taches sur l'onglet priorisateur

	// Definition de tous les TextField (pour les deux onglets):

	@FXML
	private TextField GTxtNom;// TextField pour le nom d'une tache sur l'onglet gestionnaire

	@FXML
	private TextField GTxtTempsRequise;// TextField pour entrer le temps requise pour une tache sur l'onglet
										// gestionnaire

	@FXML
	private TextField GTxtDescription;// TextField pour entrer la description d'une tache sur l'onglet gestionnaire

	@FXML
	private TextField PTxtDureeSession;// TextField pour entrer la duree de session de travail (min.) sur l'onglet
										// priorisateur

	// Definition du DatePicker

	@FXML
	private DatePicker GDatePickerDateLimite;// Date Picker pour choisir la date limite d'une tache sur l'onglet
												// gestionnaire

	// Definition du ComboBox

	@FXML
	private ComboBox<String> GCboValeurSur10;// ComboBox pour choisir la valeur de la tache sur 10, sur l'onglet
												// gestionnaire

	// Definition des colonnes du TableView sur l'onglet gestionnaire

	@FXML
	private TableColumn<Tache, String> GColNom;// Colonne pour le nom de la tache

	@FXML
	private TableColumn<Tache, String> GColDescription;// Colonne pour la description de la tache

	@FXML
	private TableColumn<Tache, Integer> GColTempsRequise;// Colonne pour le temps requise

	@FXML
	private TableColumn<Tache, String> GColDateLimite;// Colonne pour la date limite

	@FXML
	private TableColumn<Tache, Integer> GColValeurSur10;// Colonne pour la valeur / importance de la tache sur 10

	// Definition des colonnes du TableView sur l'onglet priorisateur

	@FXML
	private TableColumn<Tache, String> PColNum;// Colonne pour le numero de tache (pour l'ordre dans lequel elles
												// doivent etre faites selon le prioritiseur)

	@FXML
	private TableColumn<Tache, String> PColNom;// Colonne pour le nom

	@FXML
	private TableColumn<Tache, String> PColDescription;// Colonne pour la description

	@FXML
	private TableColumn<Tache, Integer> PColTempsRequise;// Colonne pour le temps requise

	@FXML
	private TableColumn<Tache, String> PColDateLimite;// Colonne pour la date limite

	@FXML
	private TableColumn<Tache, Integer> PColValeurSur10;// Colonne pour la valeur / importance de la tache sur 10

	@FXML
	private Button GBtnRecommencer;// Bouton pour vider les champs sur l'onglet gestionnaire

	@FXML
	private Button GBtnAjouter;// Bouton pour ajouter une nouvelle tache sur l'onglet gestionnaire

	@FXML
	private Button GBtnModifier;// Bouton pour editer/actualiser une tache sur l'onglet gestionnaire

	@FXML
	private Button GBtnDone;// Bouton pour marquer une tache comme complete (l'effacer) sur l'onglet
							// gestionnaire

	@FXML
	private Button PBtnPrioriser;// Bouton pour prioriser les taches sur l'onglet priorisateur

	private ObservableList<String> list = (ObservableList<String>) FXCollections.observableArrayList("1", "2", "3", "4",
			"5", "6", "7", "8", "9", "10"); // Elements du ComboBox pour choisir la valeur d'une tache
											// ("GCboValeurSur10")

	public ObservableList<Tache> tacheData = FXCollections.observableArrayList();// ObservableList avec tous les taches

	/**
	 * Methode pour acceder au ObservableList de tous les taches
	 * 
	 * @return le Observable Liste avec tous les taches, "tacheData"
	 */
	public ObservableList<Tache> gettacheData() {
		return tacheData;
	}

	/**
	 * Methode qui initialize les differents elements du gestionnaire et
	 * priorisateur de taches
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		//recupérer les valeurs de la base de données
		try
		{
			//****************************************************BASE DE DONNEES*************************************************//
			tacheData=TacheDAO.getAllRecords();//recuperer tous les taches de la BD et les mettre dans l'ObservableList tacheData
			//*******************************************************************************************************************//

		} catch (ClassNotFoundException | SQLException e)//en cas d'exception
		{
			// TODO Auto-generated catch block
			e.printStackTrace();//Imprimer le stack trace pour le debogage
		}
		
		
		GCboValeurSur10.setItems(list);// Attribuer les options de ComboBox "GCboValeurSur10"

		// Attribuer le ValueFactory pour les cellules dans chaque colonne du TableView
		// de taches sur l'onglet gestionnaire
		GColNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		GColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		GColTempsRequise.setCellValueFactory(new PropertyValueFactory<>("tempsRequise"));
		GColDateLimite.setCellValueFactory(new PropertyValueFactory<>("dateLimite"));
		GColValeurSur10.setCellValueFactory(new PropertyValueFactory<>("valeurSur10"));

		GDatePickerDateLimite.getEditor().setDisable(true);// Ne pas permettre l'usager d'entrer du texte dans le
															// DatePicker

		// Permettre d'avoir plusieurs lignes de texte dans des cellules du tableau pour
		// les descriptions des taches dans l'onglet gestionnaire
		GColDescription.setCellFactory(param -> {
			TableCell<Tache, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(GColDescription.widthProperty().subtract(5));
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});

		// Permettre d'avoir plusieurs lignes de texte dans des cellules du tableau pour
		// les noms des taches dans l'onglet gestionnaire
		GColNom.setCellFactory(param -> {
			TableCell<Tache, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(GColNom.widthProperty().subtract(5));
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});

		// Attribuer le ValueFactory pour les cellules dans chaque colonne du TableView
		// de taches sur l'onglet priorisateur
		PColNum.setCellValueFactory(new PropertyValueFactory<>("num"));
		PColNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		PColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		PColTempsRequise.setCellValueFactory(new PropertyValueFactory<>("tempsRequise"));
		PColDateLimite.setCellValueFactory(new PropertyValueFactory<>("dateLimite"));
		PColValeurSur10.setCellValueFactory(new PropertyValueFactory<>("valeurSur10"));

		// Permettre d'avoir plusieurs lignes de texte dans les cellules du tableau pour
		// les descriptions des taches, dans l'onglet priorisateur
		PColDescription.setCellFactory(param -> {
			TableCell<Tache, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(PColDescription.widthProperty().subtract(5));
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});

		// Permettre d'avoir plusieurs lignes de texte dans les cellules du tableau pour
		// les noms des taches, dans l'onglet priorisateur
		PColNom.setCellFactory(param -> {
			TableCell<Tache, String> cell = new TableCell<>();
			Text text = new Text();
			cell.setGraphic(text);
			cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
			text.wrappingWidthProperty().bind(PColNom.widthProperty().subtract(5));
			text.textProperty().bind(cell.itemProperty());
			return cell;
		});

		GTableTaches.setItems(tacheData);// Mettre tous les taches dans le tableau de taches sur l'onglet gestionnaire

		// Au debut, s'assurer que les boutons pour modifier une tache, effacer une
		// tache, et vider les champs sont desactives (il n'y a pas de taches encore)
		GBtnModifier.setDisable(true);
		GBtnDone.setDisable(true);
		GBtnRecommencer.setDisable(true);

		// Les champs sont vides au debut
		clearFields();

		// Montrer les informations sur la tache selectionne dans les champs
		GTableTaches.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showTaches(newValue));

		// Quand un tache est selectionne, desactiver le bouton pour ajouter (sinon il
		// va ajouter la tache selectionne encore)
		GTableTaches.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> GBtnAjouter.setDisable(true));

	}

	/**
	 * Methode qui permet de s'assurer qu'on met seulment des chiffres dans le
	 * TextField ou on met le temps requise
	 */
	@FXML
	public void verifNum() {
		GTxtTempsRequise.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[0-9](\\.[0-9]+)?$")) {// Si il y a un caractere qui n'est pas un chiffre
				GTxtTempsRequise.setText(newValue.replaceAll("[^\\d*\\.]", ""));// Enlever tous les caracteres
																				// non-numeriques
			}
		});

		PTxtDureeSession.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("^[0-9](\\.[0-9]+)?$")) {// Si il y a un caractere qui n'est pas un chiffre
				PTxtDureeSession.setText(newValue.replaceAll("[^\\d*\\.]", ""));// Enlever tous les caracteres
																				// non-numeriques
			}
		});

	}

	/**
	 * Methode pour ajouter une nouvelle tache au TableView du gestionnaire
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@FXML
	void ajouter() throws ClassNotFoundException, SQLException {
		if (noEmptyInput()) {// Verifier qu'il n'y a pas de champs vides
			// Creer la nouvelle tache
			Tache tmp = new Tache();
			tmp = new Tache();

			// Attribuer tous les proprietes de la tache selon les valeurs entrees dans les
			// champs
			tmp.setNom(GTxtNom.getText());
			tmp.setDescription(GTxtDescription.getText());
			tmp.setTempsRequise(Integer.parseInt(GTxtTempsRequise.getText()));
			tmp.setDateLimite(GDatePickerDateLimite.getValue());
			tmp.setValeurSur10(Integer.parseInt(GCboValeurSur10.getValue()));

			// Ajouter au liste de tout les taches (ce qui l'ajoutera au tableau)
			tacheData.add(tmp);
			//****************************************************BASE DE DONNEES*************************************************//
			TacheDAO.insertTache(GTxtNom.getText(),Integer.parseInt(GTxtTempsRequise.getText()), GTxtDescription.getText(),Integer.parseInt(GCboValeurSur10.getValue()),GDatePickerDateLimite.getValue().toString());
			//Inserer la tache tmp dans la table gtabletaches de la base de donnees, grace a la methode insertTache de la classe TacheDAO.
			
			//*******************************************************************************************************************//
			
			// Vider tous les champs
			clearFields();

			// Desactiver les boutons pour modifier une tache, vider les champs, et effacer
			// une tache
			GBtnModifier.setDisable(true);
			GBtnRecommencer.setDisable(true);
			GBtnDone.setDisable(true);

		}
	}

	/**
	 * Methode qui permet de vider tout les champs / recommencer
	 */
	@FXML
	void clearFields() {
		// Vider tout les champs sur l'onglet gestionnaire
		GTxtNom.setText("");
		GTxtDescription.setText("");
		GTxtTempsRequise.setText("");
		GDatePickerDateLimite.setValue(null);
		GCboValeurSur10.setValue(null);
		
	}

	/**
	 * Montrer la tache dans les champs, et activer les boutons pour modifier,
	 * recommencer, et marquer comme complete
	 */
	@FXML
	void showTaches(Tache tache) {
		if (tache != null) {// Verifier que la tache n'est pas nulle

			// Montrer toutes les informations sur la tache dans les champs correspondantes
			GCboValeurSur10.setValue(String.valueOf(tache.getValeurSur10()));
			GTxtNom.setText(tache.getNom());
			GTxtDescription.setText(tache.getDescription());
			GTxtTempsRequise.setText(String.valueOf(tache.getTempsRequise()));
			GDatePickerDateLimite.setValue(tache.getDateLimite());
			selectedTache=tache.getNum(); //GARDER LA VALEUR DE LA TACHE

			// Activer les boutons pour modifier, effacer, et vider les champs
			GBtnModifier.setDisable(false);
			GBtnDone.setDisable(false);
			GBtnRecommencer.setDisable(false);
	
		} else {
			clearFields();
		}
	}

	/*
	 * Pour modifier les details d'un tache
	 */

	@FXML
	public void updateTache() throws ClassNotFoundException, SQLException {
	
		if (noEmptyInput()) {// Verifier que les champs ne sont pas vides

			// Modifier tous les proprietes de la tache selon les valeurs entres dans les
			// champs
			Tache tache = GTableTaches.getSelectionModel().getSelectedItem();
			
			tache.setNom(GTxtNom.getText());
			tache.setDescription(GTxtDescription.getText());
			tache.setTempsRequise(Integer.parseInt(GTxtTempsRequise.getText()));
			tache.setValeurSur10(Integer.parseInt(GCboValeurSur10.getValue()));
			tache.setDateLimite(GDatePickerDateLimite.getValue());
			//****************************************************BASE DE DONNEES*************************************************//
			TacheDAO.updateTaches(selectedTache, GTxtNom.getText(),Integer.parseInt(GTxtTempsRequise.getText()), GTxtDescription.getText(), Integer.parseInt(GCboValeurSur10.getValue()), GDatePickerDateLimite.getValue().toString());	
			//Mettre a jour la tache selectionne dans la table gtabletaches de la BD a l'aide de la methode updateTaches de la classe TacheDAO. Son id est selectedTache - une variable globale qui stocke le ID de la tache qui est selectionne au moment
			
			//*******************************************************************************************************************//
			
			// Rafraichir le tableau
			GTableTaches.refresh();
			

			// Deselectionner tous les taches
			GTableTaches.getSelectionModel().select(null);

			// Activer le bouton pour ajouter une tache
			GBtnAjouter.setDisable(false);

			// Desactiver les boutons de modification, vider les champs, et effacer (comme
			// rien n'est selectionne)
			GBtnModifier.setDisable(true);
			GBtnRecommencer.setDisable(true);
			GBtnDone.setDisable(true);
			
		}
	}

	/**
	 * Pour enlever une tache de la liste (et l'enlever du tableau). Une alerte est
	 * affiche pour felicier l'tilisateur et confirmer qu'il a en effet complete la
	 * tache et veut bien l'effacer
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	@FXML
	public void deleteTache() throws ClassNotFoundException, SQLException {
		int selectedIndex = GTableTaches.getSelectionModel().getSelectedIndex();// Saisir l'indexe de la tache
																				// selectionne
		
		
		
		if (selectedIndex >= 0) {// Verifier si une tache est selectionne
			Alert alert = new Alert(AlertType.CONFIRMATION);// Afficher une alerte de confirmation
			alert.setTitle("Marquer comme fait");// Mettre le de l'alerte
			alert.setContentText("Bravo ! Confirmer que vous avez complete la tache");// Mettre le contenu de l'alerte
			Optional<ButtonType> result = alert.showAndWait();// Montrer l'alerte et attendre pour une reponse; assigner
																// la reponse d'utilisateur a "result"
			if (result.get() == ButtonType.OK) {// Si l'utilisateur accepte
				GTableTaches.getItems().remove(selectedIndex);// Enlever la tache
				//*********************************************BASE DE DONNEES***************************************//	
				TacheDAO.deleteTacheById(selectedTache);
				//Supprimer la tache selectionne (dont l'ID est stocke dans la varaible globale selected tache) de la table gtabletaches de la BD. La methode deleteTacheById de la classe TacheDAO est utilise.
				
				//***************************************************************************************************//	
				
				
			}
		}
		// Ne pas avoir un tache selectionne
		GTableTaches.getSelectionModel().select(null);

		// Activer le bouton pour ajouter une tache
		GBtnAjouter.setDisable(false);

		// Desactiver les boutons pour modifier les taches, vider les champs, et effacer
		// une tache (parce-que aucun tache n'est selectionne)
		GBtnModifier.setDisable(true);
		GBtnRecommencer.setDisable(true);
		GBtnDone.setDisable(true);
	}

	/**
	 * Verifier si les champs sur l'onglet gestionnaire ont du texte. Si un/des
	 * champ(s) sont vides, afficher un alerte pour avertir l'utilisateur et leur
	 * demander de remplir ces champs.
	 * 
	 * @return true si il n'y a pas de champs vides, false si il existe un (ou plus)
	 *         champs vides
	 */

	private boolean noEmptyInput() {
		String errorMessage = ""; // Message de l'erreur qui indique tous les champs vides.

		// On construit le message d'erreur "errorMessage", au fur et a mesure en
		// ajoutant des messages a chaque fois qu'un champ est vide.
		if (GTxtNom.getText().trim().equals("")) {// Pour le TextField pour le nom vide
			errorMessage += "Le champ \"Nom\" ne doit pas etre vide ! \n";
		}
		if (GTxtDescription.getText().trim().equals("")) {// Pour le TextField pour la description vide
			errorMessage += "Le champ \"Description\" ne doit pas etre vide ! \n";
		}
		if (GTxtTempsRequise.getText().trim().equals("")) {// Pour le TextField pour le temps requise vide
			errorMessage += "Le champ \"Temps Requise\" ne doit pas etre vide ! \n";
		}
		if (GCboValeurSur10.getValue() == null) {// Pour le TextField pour la valeur sur 10 vide
			errorMessage += "Le champ \"Valeur sur 10\" ne doit pas etre vide ! \n";
		}
		if (GDatePickerDateLimite.getValue() == null) {// Pour le TextField pour la date limite vide
			errorMessage += "Le champ \"Date Limite\" ne doit pas etre vide ! \n";
		}

		if (errorMessage.length() == 0) {// Si on n'a aucun champ vide (message d'erreur est vide)
			return true;// Comme il n'y a aucun champ vide, return true
		} else {// Si il y a un champ vide
			// Creer un alerte d'erreur
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Champs manquants");// Mettre le titre de l'alerte
			alert.setHeaderText("Completer les champs manquants");// Mettre l'entete de l'alerte
			alert.setContentText(errorMessage);// Mettre le contenu de l'alerte
			alert.showAndWait();// Afficher l'alerte et attendre que l'utilisateur le resolve
			return false;// Comme il y a un champ vide, return false
		}

	}

	/**
	 * Verifier si le champ pour la duree du session a du texte. Si c'est vide,
	 * afficher un alerte pour avertir l'utilisateur et leur demander de remplir le
	 * champ.
	 * 
	 * @return true c'est pas vide false c'est vide
	 */

	private boolean noEmptySession() {
		String errorMessage = ""; // Message de l'erreur si le champ est vide
		// On construit le message d'erreur "errorMessage", au fur et a mesure en
		// ajoutant des messages a chaque fois qu'un champ est vide.
		if (PTxtDureeSession.getText().trim().equals("")) {
			errorMessage = "Le champ \"Duree de la session\" ne doit pas etre vide ! \n";
		}
		if (errorMessage.length() == 0) {// Si c'est pas vide
			return true;// Comme c'est pas vide, return true
		} else {// Si c'est vide
			// Creer un alerte d'erreur
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Duree de session manquant");// Mettre le titre de l'alerte
			alert.setHeaderText("Entrer la duree de session");// Mettre l'entete de l'alerte
			alert.setContentText(errorMessage);// Mettre le contenu de l'alerte
			alert.showAndWait();// Afficher l'alerte et attendre que l'utilisateur le resolve
			return false;// Comme il y a un champ vide, return false
		}

	}

	/**
	 * Afficher les statistiques sur les donnees: nombre de taches dues sur chaque
	 * jour pendant une semaine (dans un nouveau stage).
	 */
	@FXML
	void handleStats() {
		try {
			// Initializer le FXMLLoader, AnchorPane, Scene, DateStat, et Stage; les
			// elements necessaires pour afficher les statistiques
			FXMLLoader loader = new FXMLLoader(Main.class.getResource("DateStat.FXML"));
			AnchorPane pane = loader.load();
			Scene scene = new Scene(pane);
			DateStat datestat = loader.getController();
			Stage stage = new Stage();

			datestat.setStats(tacheData);// Utiliser mettre les statistiques de sur tacheData (trouves par la methode
											// setStats) dans datestat

			stage.setScene(scene);// Montrer la scene sur le stage
			stage.setTitle("Statistiques");// Mettre le titre
			stage.setResizable(false);// Ne pas permettre de changer la taille du stage / fenetre : ce n'est pas
										// necessaire pour un graphique de statistiques
			stage.show();// Montrer le stage

		} catch (IOException e) {// Try catch pour gerer le IOException s'il y a lieu
			e.printStackTrace();
		}
	}

	/**
	 * Trouver le Path du fichier
	 * 
	 * @return un objet "File": le fichier avec les taches, ou "null" si le file
	 *         path est nulle.
	 */
	@FXML
	public File getTacheFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);// Creer un objet Preferences: les preferences de
																		// l'utilisateur
		// Chercher le file Path
		String filePath = prefs.get("filePath", null);

		if (filePath != null) {// Si le file path n'est pas nulle
			return new File(filePath);// "return" le fichier
		} else {// Sinon
			return null;// "return" une valeur nulle
		}
	}

	/*
	 * Attribuer un path de fichiers
	 */
	public void setTacheFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);// Creer un objet Preferences: les preferences de
																		// l'utilisateur
		if (file != null) {// Si le fichier n'est pas nulle
			prefs.put("filePath", file.getPath());// Attribuer le path de fichiers
		} else {// Sinon
			prefs.remove("filePath");// Enlever le file path des preferences
		}
	}


	/**
	 * Cette methode effectue la priorisation des taches. En utilisant les valeurs
	 * de temps requise, et de valeur / importance sur 10 des taches, il choisit un
	 * ensemble de taches qui a la somme maximale de valeurs d'importance, tout en
	 * s'assurant que le temps totale requise (somme) est moins que la duree du
	 * session. Il fait ceci grace a l'algorithme de programmation dynamique,
	 * "Knapsack" / "Sac a Dos"
	 * 
	 * Il priorise aussi les taches qui sont dues en premier.
	 */
	@FXML
	private void prioriserTaches() {
		if (noEmptySession()) {
			ObservableList<Tache> taches = GTableTaches.getItems();// Creer la liste de toutes les taches
			LocalDate aujourdhui = LocalDate.now();// Creer un objet LocalDate qui est la date d'aujourd'hui

			int n = taches.size(), w = Integer.parseInt(PTxtDureeSession.getText());// Definir n = le nombre de taches,
																					// w = la duree de la session de
																					// travail (en minutes)

			int dp[][] = new int[n + 1][w + 1];// Creer le tableau 2D de programmation dynamique

			// Premier tour de l'algorithme
			for (int i = 1; i <= n; i++) {
				Period periode = Period.between(aujourdhui, taches.get(i - 1).getDateLimite());
				if (periode.getDays() <= 0) {// Pour les taches qui ont un delai qui est deja passe, ou qui sont dues
												// aujourd'hui
					int c = taches.get(i - 1).getTempsRequise(), v = taches.get(i - 1).getValeurSur10();
					for (int j = 1; j <= w; j++) {
						if (c > j)
							dp[i][j] = dp[i - 1][j];
						else
							dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - c] + v);
					}
				}
			}
			// Deuxieme tour de l'algorithme
			for (int i = 1; i <= n; i++) {
				Period periode = Period.between(aujourdhui, taches.get(i - 1).getDateLimite());
				if (periode.getDays() == 1) {// Pour les taches qui sont dues demain
					int c = taches.get(i - 1).getTempsRequise(), v = taches.get(i - 1).getValeurSur10();
					for (int j = 1; j <= w; j++) {
						if (c > j)
							dp[i][j] = dp[i - 1][j];
						else
							dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - c] + v);
					}
				}
			}

			for (int i = 1; i <= n; i++) {
				Period periode = Period.between(aujourdhui, taches.get(i - 1).getDateLimite());
				if (periode.getDays() == 2) {// Pour les taches qui sont dues apres demain
					int c = taches.get(i - 1).getTempsRequise(), v = taches.get(i - 1).getValeurSur10();
					for (int j = 1; j <= w; j++) {
						if (c > j)
							dp[i][j] = dp[i - 1][j];
						else
							dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - c] + v);
					}
				}
			}

			for (int i = 1; i <= n; i++) {
				Period periode = Period.between(aujourdhui, taches.get(i - 1).getDateLimite());
				if (periode.getDays() == 3) {// Pour les taches qui sont dues en 3 jours
					int c = taches.get(i - 1).getTempsRequise(), v = taches.get(i - 1).getValeurSur10();
					for (int j = 1; j <= w; j++) {
						if (c > j)
							dp[i][j] = dp[i - 1][j];
						else
							dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - c] + v);
					}
				}
			}

			for (int i = 1; i <= n; i++) {
				Period periode = Period.between(aujourdhui, taches.get(i - 1).getDateLimite());
				if (periode.getDays() > 3) {// Pour tous les autres taches (dues en 4 ou plus jours apres aujourd'hui)
					int c = taches.get(i - 1).getTempsRequise(), v = taches.get(i - 1).getValeurSur10();
					for (int j = 1; j <= w; j++) {
						if (c > j)
							dp[i][j] = dp[i - 1][j];
						else
							dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - c] + v);
					}
				}
			}

			ObservableList<Tache> toDo = FXCollections.observableArrayList();// La liste de toutes les taches a faire

			// Remplir la liste toDo avec les taches a completer, grace au tableau 2D, dp,
			// qui a ete rempli par les tours de l'algorithme
			int cap = w;// Le temps restante
			int compteur = 1;// Le compteur de taches a faire

			// Tracer en arriere dans le tableau 2D, dp pour trouver toutes les taches a
			// faire
			for (int i = n; i >= 1; i--) {
				int c = taches.get(i - 1).getTempsRequise();
				if (dp[i][cap] == dp[i - 1][cap]) {// La tache n'est pas a faire pendant la session
					continue;
				} else {// La tache est a faire pendant la session
					taches.get(i - 1).setNum(compteur);// Attribuer le numero de tache (pour les numerotter 1, 2, 3,
														// ...)
					compteur++;// Incrementer le numero de tache par 1, pour le prochain
					toDo.add(taches.get(i - 1));// Ajouter la tache a la liste
					cap -= c;// Diminuer le temps restante par le temps requise pour faire la tache qu'on
								// inclut
				}
			}

			// Ajouter tous les taches a faire dans le TableView sur l'onglet priorisateur,
			// et les afficher
			PTableTaches.setItems(toDo);
			PTableTaches.refresh();
		}
	}

	/**
	 * Methode qui vide le TableView de taches a faire sur l'onglet priorisateur
	 * (lorsque l'usager termine la session)
	 */
	@FXML
	private void sessionFini() {
		ObservableList<Tache> vide = FXCollections.observableArrayList();// Creer une liste vide
		PTableTaches.setItems(vide);// Attribuer la liste vide au tableau de taches (ceci enleve tout les taches)
	}

	/**
	 * Pour quitter l'application
	 */
	@FXML
	private void quitter() {
		System.exit(0);// Quitter l'application (fermer le stage)
	}

}
