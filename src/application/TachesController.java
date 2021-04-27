package application;

import java.io.File;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;

public class TachesController implements Initializable {

	@FXML
	private TableView<Tache> GTableTaches;

	@FXML
	private TextField GTxtNom;

	@FXML
	private DatePicker GDatePickerDateLimite;

	@FXML
	private TableColumn<Tache, String> GColDescription;

	@FXML
	private Button GBtnRecommencer;

	@FXML
	private TableColumn<Tache, String> GColNom;

	@FXML
	private Button GBtnAjouter;

	@FXML
	private Button GBtnModifier;

	@FXML
	private Button GBtnDone;

	@FXML
	private TableColumn<Tache, String> GColDateLimite;

	@FXML
	private ComboBox<String> GCboValeurSur10;

	@FXML
	private TableColumn<Tache, Integer> GColValeurSur10;

	@FXML
	private TextField GTxtDescription;

	private ObservableList<String> list = (ObservableList<String>) FXCollections.observableArrayList("1", "2", "3", "4",
			"5", "6", "7", "8", "9", "10");

	public ObservableList<Tache> tacheData = FXCollections.observableArrayList();

	public ObservableList<Tache> gettacheData() {
		return tacheData;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		GCboValeurSur10.setItems(list);

		GColNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		GColDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		GColDateLimite.setCellValueFactory(new PropertyValueFactory<>("dateLimite"));
		GColValeurSur10.setCellValueFactory(new PropertyValueFactory<>("valeurSur10"));

		GTableTaches.setItems(tacheData);

		GBtnModifier.setDisable(true);
		GBtnDone.setDisable(true);
		GBtnRecommencer.setDisable(true);

		showTaches(null);

		GTableTaches.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showTaches(newValue));

	}

	@FXML
	void ajouter() {
		Tache tmp = new Tache();
		tmp = new Tache();
		tmp.setNom(GTxtNom.getText());
		tmp.setDescription(GTxtDescription.getText());
		tmp.setDateLimite(GDatePickerDateLimite.getValue());// DOES THIS CONVERT DATE TO STRING CORRECTLY?
		tmp.setValeurSur10(Integer.parseInt(GCboValeurSur10.getValue()));
		tacheData.add(tmp);
		clearFields();
	}

	@FXML
	void clearFields() {
		GTxtNom.setText("");
		GTxtDescription.setText("");
		GDatePickerDateLimite.setValue(null);
		GCboValeurSur10.setValue(null);
	}

	/*
	 * Montrer la tache dans le Table, et activer les boutons pour modifier,
	 * recommencer, et marquer comme complete
	 */
	@FXML
	void showTaches(Tache tache) {
		if (tache != null) {
			GCboValeurSur10.setValue(String.valueOf(tache.getValeurSur10()));
			GTxtNom.setText(tache.getNom());
			GTxtDescription.setText(tache.getDescription());
			GDatePickerDateLimite.setValue(tache.getDateLimite());
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
	public void updateTache() {
		Tache tache = GTableTaches.getSelectionModel().getSelectedItem();
		tache.setNom(GTxtNom.getText());
		tache.setDescription(GTxtDescription.getText());
		tache.setValeurSur10(Integer.parseInt(GCboValeurSur10.getValue()));
		tache.setDateLimite(GDatePickerDateLimite.getValue());
		GTableTaches.refresh();
	}

	/*
	 * Pour enlever une tache de la liste
	 */
	@FXML
	public void deleteTache() {
		int selectedIndex = GTableTaches.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			GTableTaches.getItems().remove(selectedIndex);
		}
	}

	/*
	 * Trouver le path du fichier
	 */
	@FXML
	public File getTacheFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
	}

	/*
	 * Attribuer un path de fichiers
	 */
	public void setTacheFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
		} else {
			prefs.remove("filePath");
		}
	}

	/*
	 * Prendre les donnees XML et les convertir en donnees de type JavaFX
	 */

	public void loadTacheDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(TacheListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();

			TacheListWrapper wrapper = (TacheListWrapper) um.unmarshal(file);
			tacheData.clear();
			tacheData.addAll(wrapper.getTaches());
			setTacheFilePath(file);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Les donnees n'ont pas ete trouvees");
			alert.setContentText("Les donnees ne pouvaient pas etre trouvees dans le fichier: \n" + file.getPath());
			alert.showAndWait();
		}
	}

	/*
	 * Prendre les donnees de type JavaFX et les convertir en donnees XML
	 */
	public void saveTacheDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(TacheListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			TacheListWrapper wrapper = new TacheListWrapper();
			wrapper.setTaches(tacheData);

			m.marshal(wrapper, file);

			setTacheFilePath(file);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Donnees non sauvegardees");
			alert.setContentText("Les donnees ne pouvaient pas etre sauvegardees dans le fichier: \n" + file.getPath());
			alert.showAndWait();
		}
	}

	/*
	 * Commencer un nouveau fichier
	 */
	@FXML
	private void handleNew() {
		// Faire le re-set de tous les taches et le file path
		gettacheData().clear();
		setTacheFilePath(null);
	}

	/*
	 * Permettre a l'usager de choisir le fichier a ouvrir
	 */
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();// Creer un objet FilelChooser
		// Creer un filtre sur l'extension du fichier a chercher
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Faire afficher la dialogue
		File file = fileChooser.showOpenDialog(null);

		// Charger le data du fichier "file"
		if (file != null) {
			loadTacheDataFromFile(file);
		}
	}

	/*
	 * Sauvegarde le fichier correspondant au tache qui est actif Si il n'y a pas de
	 * fichier, faire que le menu "sauvegarder sous" s'affiche
	 */

	@FXML
	private void handleSave() {
		File tacheFile = getTacheFilePath();
		if (tacheFile != null) {
			saveTacheDataToFile(tacheFile);
		} else {
			handleSaveAs();
		}
	}

	/*
	 * Ouvrir le FileChooser afin de trouver le chemin
	 */
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		File file = fileChooser.showSaveDialog(null);

		if (file != null) {
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			saveTacheDataToFile(file);
		}
	}

}
