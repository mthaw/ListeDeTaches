//Cette classe permet de se connecter et se deconnecter de la BD. Il gere aussi les fonctionnalites BD generales (lecture, modification, suppression, et affichage des erreurs).
package application;

//Tous les imports necessaires
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.rowset.CachedRowSet;
import com.sun.rowset.CachedRowSetImpl;

public class DBUtilitaires {

	//Defifnir le pilote a utiliser poru se connecter a la BD
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	//L'object connection qui representera une session unique avec la BD.
	private static Connection connection = null;
	
	//Definir la base de donnes auquel il faut se connecter. A l'instant, c'est le mien.
	private static final String connStr="jdbc:mysql://ics4userver.tfs.ca:3306/martin?useSSL=false&allowPublicKeyRetrieval=true";
	
	/**
	 * Connexion a la BD. Apres l'execution de ce methode, tout manipulation sera fait sur la bonne BD.
	 * @throws SQLException (exception si il y a probleme avec le code sql et son execution)
	 * @throws ClassNotFoundException (exception si une classe auquel on fait reference n'est pas defini)
	 */
	public static void dbConnect() throws SQLException, ClassNotFoundException {
		try {//try-catch pour exceptions lors de la selection du pilote
			Class.forName(JDBC_DRIVER);//Selectionner le Pilote a utiliser pour effectuer la connexion
		} catch (ClassNotFoundException e) {//s'il y a un exception
			System.out.println("Pilote MySQL n'a pas été trouvé");//imprimer au console
			e.printStackTrace();//imprimer le stack trace pour le debogage
			throw e;//declarer l'erreur
		}

		try {//try-catch pour exceptions lors de la definition de la connection
			//definir la connexion avec la base de donnees. On fournit comme arguments: l'identificateur de BD (connStr), le nom d'usager, et le mot de passe.
			connection=DriverManager.getConnection(connStr, "martin", "************************MOT DE PASSE**************************");

		} catch (SQLException e) {//en cas d'erreur
			System.out.println("Connection non-réussi. Consulter la console " + e);//imprimer l'erreur au console
			throw e;//declarer l'exception
		}
	}

	/**
	 * Deconnexion de la base de donnees. On le "ferme" et on ne le manipule plus
	 * 
	 * Comme auparavant:
	 * 
	 * @throws SQLException
	 */
	public static void dbDisconnect() throws SQLException {
		try {//try-catch pour erreurs en fermant la connexion
			if (connection != null && !connection.isClosed()) {//Si la connexion est en marche (par nulle et pas encore ferme)
				connection.close();//ferme-le
			}
		} catch (Exception e) {//en cas d'erreur
			throw e;//le declarer
		}
	}

	/**
	 * Executer des statements qui n'impliquent pas de lecture ni recuperation de donnees (pas de return, alors void).
	 * @param sqlStmt Le statement sql a executer
	 *
	 *Memes qu'auparavant:
	 * @throws SQLException 
	 * @throws ClassNotFoundException
	 */
	public static void dbExecuteQuery(String sqlStmt) throws SQLException, ClassNotFoundException {
		Statement stmt = null;//L'objet statement qui representera le statement a executer (sqlStmt)
		try {
			dbConnect();//Connecter a la BD
			stmt = connection.createStatement();//Initializer un nouveau statement pour la BD auquel on vient de se connecter
			stmt.executeUpdate(sqlStmt);//a l'aide de l'objet stmt, executer le statement sqlStmt
		} catch (SQLException e) {//en case d'exception
			System.out.println("Problème lors de l'exécutions de la requete " + e);//imprimer l'erreur
			throw e;//declarer l'exception
		} finally {//en tout cas ("finally")
			if (stmt != null)//Si le statement stmt n'est pas nulle, fermer le statement (enlever l'allocation de stmt)
				stmt.close();
		}
		dbDisconnect();//Deconnexion de la BD.
	}

	/**
	 * Executer les queries de lecture/recuperation de donnees (ils ont besoin de retourner quelquechose, alors return ResultSet)
	 * Remarque: un ResultSet est un ensemble de rangees d'une table d'une BD
	 * @param sqlQuery le statement sql a executer
	 * @return un ResultSet comprenant les rangees de la table cherchees
	 * 
	 * Meme qu'auparavant:
	 *
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static ResultSet dbExecute(String sqlQuery) throws ClassNotFoundException, SQLException {
		Statement stmt = null;//L'objet statement qui representera le statement a executer (sqlQuery)
		ResultSet rs = null;//L'objet ResultSet qu'on va retourner
		CachedRowSet crs = null; //L'objet CachedRowSet qu'on pourra modifier sans necessite de lien a la source originelle de donnees (BD)
		try {
			dbConnect();//connexion a la BD
			stmt = connection.createStatement();//Initializer un nouvement statement pour la BD auquel on vient de se connecter
			rs = stmt.executeQuery(sqlQuery);//grace a l'objet stmt, executer le statement sqlQuery et garder le resultat dans rs.
			crs = new CachedRowSetImpl();//Initialiser le cached rowset
			crs.populate(rs);//remplir le CachedRowSet crs avec les elements de RowSet rs

		} catch (SQLException e) {//en cas d'exception
			System.out.println("Erreur lors de l'exécution de l'opération dbExecute " + e);//imprimer l'erreur
			throw e;//declarer l'exception
		} finally {//en tout cas ("finally")
			if (rs != null) {//Si le ResultSet rs n'est pas nulle
				rs.close();//fermer le ResultSet rs
			}
			if (stmt != null) {//Si le Statement stmt n'est psa nulle
				stmt.close();//le fermer aussi
			}
			dbDisconnect();//Se deconnecter de la BD
		}
		return crs;//retourner le CachedRowSet crs
	}

}
