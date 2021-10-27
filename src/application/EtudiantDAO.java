package application;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EtudiantDAO {
	
	public static void insertEtudiant(String LastName, String FirstName, String Department, String Age) throws ClassNotFoundException, SQLException
	{
		String sql="insert into etudiant(LastName,FirstName,Department, Age) values('"+LastName+"','"+FirstName+"','"+Department+"','"+Age+"')";
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
	public static void updateEtudiant(int ID, String LastName, String FirstName, String Department, String Age) throws ClassNotFoundException, SQLException
	{
		String sql="update etudiant set LastName='"+LastName+"', FirstName='"+FirstName+"', Department='"+Department+"', Age='"+Age+"' where ID= "+ ID;
		
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
		String sql="delete from etudiant where ID= "+ id;
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
		String sql="select * from etudiant";
		try
		{
			ResultSet rsSet=DBUtilitaires.dbExecute(sql);
			
			
			ObservableList<Tache> EtudiantList=getTaches(rsSet);
			return EtudiantList;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur lors de la recupération de données à afficher"+e);
			e.printStackTrace();
			throw e;
		}
		
	}
	private static ObservableList<Tache> getEtudiantObjects(ResultSet rsSet) throws ClassNotFoundException, SQLException
	{
		try
		{
			
			ObservableList<Tache> EtudiantList=FXCollections.observableArrayList();
			while(rsSet.next())
			{
				Tache tache=new Tache();
				tache.setNum(rsSet.getInt("Num"));
				tache.setNom(rsSet.getString("Nom"));
				tache.setDescription(rsSet.getString("Description"));
				tache.setDateLimite(...);
				tache.setValeurSur10(10);
				EtudiantList.add(tache);
			}
			return EtudiantList;
		}
		catch(SQLException e)
		{
			System.out.println("Erreur au moment de l'affichage de données "+ e);
			e.printStackTrace();
			throw e;
		}
	}
	public static ObservableList<Tache> searchEtudiant(String etudiantId) throws ClassNotFoundException, SQLException
	{
		String sql="select * from etudiant where id="+etudiantId;
		try
		{
		 ResultSet rsSet=DBUtilitaires.dbExecute(sql)	;
		 ObservableList<Tache> list=getEtudiantObjects(rsSet);
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
