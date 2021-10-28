package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TacheDAO {
	
	public static void insertTache(String Nom, String TempsRequis, String Description, String ValeurSur10,String GDateLimite) throws ClassNotFoundException, SQLException
	{
		String sql="insert into gtabletaches(GTxtNom,GTxtTempsRequis,GTxtDescription, GTxtValeurSur10,GDateLimite) values('"+Nom+"','"+TempsRequis+"','"+Description+"','"+ValeurSur10+"','"+GDateLimite+"')";
		try
		{ 
			DBUtilitaires.dbExecuteQuery(sql);
		}
		catch(SQLException e)
		{
			System.out.println("Erreur à l'insertion de données " + e);
			e.printStackTrace();
			throw e;
		}
	}
	public static void updateEtudiant(int ID, String Nom, String TempsRequis, String Description, String ValeurSur10,String GDateLimite) throws ClassNotFoundException, SQLException
	{
		String sql="update gtabletaches set GTxtNom='"+Nom+"', GTempsRequis='"+TempsRequis+"', Description='"+Description+"', ValeurSur10='"+ValeurSur10+"', DateLimite='"+GDateLimite+"' where ID= "+ ID;
		
		try
		{
			DBUtilitaires.dbExecuteQuery(sql);
		}
		catch(SQLException e)
		{
			System.out.println("Erreur lors de la mise à jour");
			e.printStackTrace();
			throw e;
		}
		
	}
	public static void deleteEtudiantById(int id) throws ClassNotFoundException, SQLException
	{
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
	public static ObservableList<Tache> getAllRecords() throws ClassNotFoundException, SQLException
	{
		String sql="select * from gtabletaches";
		try
		{
			ResultSet rsSet=DBUtilitaires.dbExecute(sql);
			
			
			ObservableList<Tache> TacheList=getTacheObjects(rsSet);
			return TacheList;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur lors de la recupération de données à afficher"+e);
			e.printStackTrace();
			throw e;
		}
		
	}
	private static ObservableList<Tache> getTacheObjects(ResultSet rsSet) throws ClassNotFoundException, SQLException
	{
		try
		{
			
			ObservableList<Tache> TacheList=FXCollections.observableArrayList();
			while(rsSet.next())
			{
				Tache tache=new Tache();
				tache.setNum(rsSet.getInt("ID"));
				tache.setNom(rsSet.getString("GTxtNom"));
				tache.setTempsRequise(rsSet.getInt("GTxtTempsRequis"));
				tache.setDescription(rsSet.getString("GTxtDescription"));
				tache.setValeurSur10(rsSet.getInt("GTxtValeurSur10"));
				tache.setDateLimite(LocalDate.parse(rsSet.getString("GDateLimite")));
				
				TacheList.add(tache);
			}
			return TacheList;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur au moment de l'affichage de données "+ e);
			e.printStackTrace();
			throw e;
		}
	}
	public static ObservableList<Tache> searchTache(int tacheId) throws ClassNotFoundException, SQLException
	{
		String sql="select * from gtabletaches where ID="+tacheId;
		try
		{
		 ResultSet rsSet=DBUtilitaires.dbExecute(sql)	;
		 ObservableList<Tache> list=getTacheObjects(rsSet);
		 return list;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur pendant la recherche de données " +e);
			e.printStackTrace();
			throw e;
		}
	}	
	
	
	
	
	
	
	

}
