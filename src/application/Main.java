/**
 * @Auteur : Martin Thaw
 * @Date : Le 4 Mai, 2021
 * @Description : 
 * Une Application (Java) qui permet de 1. gerer les taches, 2. prioriser les taches a completer pendant un session de travail, et 3. presenter le flux de travail de l'usager 
 * 
 * 1. L'application permet a l'usager d'entrer leurs taches a completer (nom, description, temps requise, date limite, valeur / importance sur 10), et les affiche en liste dans un tableau. L'usager peut modifier des taches, recommencer la definition d'une tache (vider tout les champs), et marquer un tache comme fait (l'enlever de la liste / l'effacer)
 * 
 * 2. L'application priorise aussi les sessions de travail de l'usager selon la duree de temps de la session, entré par l'usager. L'ensemble de taches a completer durant la session (c-ad la somme du temps requise de tout les taches doit etre inferieure ou egal au duree de la session), qui maximizent l'utilisation du temps / la productivite (c-ad qui a la somme maximale de valeurs/importance) sont affiches dans un tableau. Ces taches sont a completer pendant la session.
 * 
 * 3. Finalement, l'application permet de presenter a l'usager, leur flux de travail / charge de travail, en representant le nombre de taches due à chaque jour pendant une semaine, sur une graphique a barres.
 */

package application;

//Imports necessaires
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Taches.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Taches");//Mettre le titre du Stage / fenetre de l'application 
			primaryStage.setResizable(false);//Ne pas permettre le changement de taille du Stage / fenetre: ce n'est pas necessaire etant donne la nature de l'application
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
