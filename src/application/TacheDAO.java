//Cette classe fournit les fonctionnalites specifiques a l'application concernant la BD. Pour ce faire, il utilise la classe DBUtilitaires pour executer des statements en sql. Remarque: le Controller utilise cette classe.
package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TacheDAO {
	/**
	 * Gerer les quotes: aider a "echapper" les quotes en sql
	 * @param a Le string a traiter
	 * @return Le string avec chaque caractere ' remplace par '' (on le double pour "l'echapper").
	 */
	public static String qt(String a)
	{
		return a.replace("'", "''");
	}
	
	/**
	 * Inserer une tache dans la table gtabletaches de la BD.
	 * Les arguments ci-dessous sont les attributs de la tache a inserer.
	 * @param Nom 
	 * @param TempsRequis
	 * @param Description
	 * @param ValeurSur10
	 * @param GDateLimite
	 * 
	 * @throws ClassNotFoundException (pour gerer l'erreur si une classe n'est pas defini)
	 * @throws SQLException (pour nous faire savoir s'il y a un erreur d'access a la base de donnees)
	 */
	public static void insertTache(String Nom, int TempsRequis, String Description, int ValeurSur10,String GDateLimite) throws ClassNotFoundException, SQLException
	{
		String sql="insert into gtabletaches(GTxtNom,GTxtTempsRequise,GTxtDescription, GCboValeurSur10,GDatePickerDateLimite) values('"+qt(Nom)+"',"+TempsRequis+",'"+qt(Description)+"',"+ValeurSur10+",'"+GDateLimite+"')"; //Le code sql a executer pour inserer la tache dans la BD.
		try //Try-Catch pour attraper des erreurs qui se produisent lors de l'execution du sql
		{ 
			DBUtilitaires.dbExecuteQuery(sql); //Grace a DBUtilitaires, exectuter le statement
		}
		catch(SQLException e)
		{
			//Gerer l'erreur si il y en a une
			System.out.println("Erreur à l'insertion de données " + e); // message console
			e.printStackTrace(); //(imprimer le Stack Trace pour faciliter la recherche de l'erreur)
			throw e; //declarer l'exception
		}
	}
	/**
	 * Mettre a jour la tache qui a pour id ID, de la table gtabletaches dans la BD.
	 * Attributs de la tache a mettre a jour (on a besoin de ID pour connaitre laquelle)
	 * @param ID
	 * @param Nom
	 * @param TempsRequis
	 * @param Description
	 * @param ValeurSur10
	 * @param GDateLimite
	 * 
	 * @throws ClassNotFoundException (pour gerer l'erreur si une classe auquel on fait reference n'est pas defini)
	 * @throws SQLException (pour gerer les erreurs qui peuvent se produire avec SQL (interaction avec BD))
	 */
	public static void updateTaches(int ID, String Nom, int TempsRequis, String Description, int ValeurSur10,String GDateLimite) throws ClassNotFoundException, SQLException
	{
		String sql="update gtabletaches set GTxtNom='"+qt(Nom)+"', GTxtTempsRequise="+TempsRequis+", GTxtDescription='"+qt(Description)+"', GCboValeurSur10="+ValeurSur10+", GDatePickerDateLimite='"+GDateLimite+"' where ID= "+ ID; //Le code sql a executer pour faire la mise a jour
		
		try //try-catch  pour attraper des erreurs du a l'execution du sql
		{
			DBUtilitaires.dbExecuteQuery(sql); //executer le statement a l'aide de DBUtilitaires
		}
		catch(SQLException e)//Si il y un exception
		{
			System.out.println("Erreur lors de la mise à jour");//imprimer dans le console
			e.printStackTrace();//presenter le Stack Trace pour facilitier le debogage
			throw e; //declarer l'erreur
		}
		
	}
	/**
	 * Supprimer de la table gtabletaches dans la BD, le tache qui a pour ID, id.
	 * @param id (le ID de la tache a supprimer)
	 * 
	 * Meme qu'auparavant:
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void deleteTacheById(int id) throws ClassNotFoundException, SQLException
	{//Commentaires identiques a celles de insertTache et updateTaches:
		String sql="delete from gtabletaches where ID= "+ id;
		try
		{
			DBUtilitaires.dbExecuteQuery(sql);
		}
		catch(SQLException e)
		{
			System.out.println("Erreur lors de la suppression de données");
			e.printStackTrace();
			throw e;
		}
	}
	/**
	 * Retourner un ObservableList contenant tous les taches dans la table "gtabletaches" de la BD
	 * @return un ObservableList contenant tous les taches dans la table "gtabletaches" de la BD
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ObservableList<Tache> getAllRecords() throws ClassNotFoundException, SQLException
	{
		String sql="select * from gtabletaches"; //code sql pour selectionner tous les colonnes (*) de la table
		try//try-catch pour gerer les erreurs dans l'execution du query
		{
			ResultSet rsSet=DBUtilitaires.dbExecute(sql);//executer le query grace a la methode dbExecute de la class DBUtilitaires
			//Il nous retourne les donnees de la table
			
			ObservableList<Tache> TacheList=getTacheObjects(rsSet);//Mettre tous les donnees dans un observable list
			return TacheList;//return le observableList.
		}
		catch(SQLException e)//Si un exception se produit
		{
			System.out.println("Erreur lors de la recupération de données à afficher"+e); //imprimer dans la console
			e.printStackTrace();//imprimer le stack trace pour faciliter le debogage
			throw e;//declarer l'erreur
		}
		
	}
	/**
	 * A partir d'une table (sour forme ResultSet), chercher tous les taches et les mettre dans un observable list a retourner
	 * @param rsSet table dans lequel il faut chercher les taches
	 * @return ObservableList avec les taches appartenant a rsSet
	 * 
	 * Meme qu'auparavant:
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	private static ObservableList<Tache> getTacheObjects(ResultSet rsSet) throws ClassNotFoundException, SQLException
	{
		try //try-catch pour gerer les erreurs lors qu'on travaille avec l'objet ResultSet
		{
			
			ObservableList<Tache> TacheList=FXCollections.observableArrayList(); //liste dans lequel on mettra les taches de rsSet
			while(rsSet.next()) //pour chaque rangee de rsSet
			{
				Tache tache=new Tache();//Creer une nouvelle tache
				//Asisgner les valeurs de tous les attributs pour la tache (a partir des valeurs pour la tache dans la rangee actuelle):
				tache.setNum(rsSet.getInt("ID"));
				tache.setNom(rsSet.getString("GTxtNom"));
				tache.setTempsRequise(rsSet.getInt("GTxtTempsRequise"));
				tache.setDescription(rsSet.getString("GTxtDescription"));
				tache.setValeurSur10(rsSet.getInt("GCboValeurSur10"));
				tache.setDateLimite(LocalDate.parse(rsSet.getString("GDatePickerDateLimite")));//(remarque: il faut convertir le string en objet LocalDate)
				
				TacheList.add(tache);//ajouter la tache construite a la liste tache
			}
			return TacheList;//retourner la liste
		}
		catch(SQLException e)//si il y a un exception
		{
			System.out.println("Erreur au moment de l'affichage de données "+ e);//imprimer au console
			e.printStackTrace();//imprimer le stack trace  pour le debogage
			throw e; //declarer l'erreur
		}
	}
		

}
