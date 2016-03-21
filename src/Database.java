/**
 * Make sure the Postgresql JDBC driver is in your classpath.
 * You can download the JDBC 4 driver from here if required.
 * https://jdbc.postgresql.org/download.html
 *
 * take care of the variables usernamestring and passwordstring to use 
 * appropriate database credentials before you compile !
 *
**/

import java.sql.* ;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;



public class Database
{
	private String url = "jdbc:postgresql://comp421.cs.mcgill.ca:5432/cs421";
	private String usernamestring ="cs421g16";
	private String passwordstring ="Lsamz16!";
	private Statement statement;
	private Connection con;
	private String tableName = "Hi";
    private int sqlCode=0;      // Variable to hold SQLCODE
    private String sqlState="00000";  // Variable to hold SQLSTATE
	
	
    public Database()
    {
	    try {
	    DriverManager.registerDriver ( new org.postgresql.Driver() ) ;
	    	} catch (Exception cnfe){
	    System.out.println("Class not found");
	    }
		try {
			con = DriverManager.getConnection (url,usernamestring, passwordstring);
			statement = con.createStatement ( ) ;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
   }
    

	public void createTable(String createSQL){
	try {
//	    String createSQL = "CREATE TABLE " + tableName + " (id INTEGER, name VARCHAR (25)) ";
	    System.out.println (createSQL ) ;
	    statement.executeUpdate (createSQL ) ;
	    System.out.println ("DONE");
	}catch (SQLException e)
            {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            }
	}
	
	public java.sql.ResultSet insertIntoTable(String insertSQL) throws SQLException{
	// Inserting Data into the table
	try {
//	    String insertSQL = "INSERT INTO " + tableName + " VALUES ( 1 , \'Vicki\' ) " ;
	    System.out.println ( insertSQL ) ;
	    statement.executeUpdate ( insertSQL ) ;
	    System.out.println ( "DONE" ) ;
	} catch (SQLException e)
            {
                sqlCode = e.getErrorCode(); // Get SQLCODE
                sqlState = e.getSQLState(); // Get SQLSTATE           
                // Your code to handle errors comes here;
                // something more meaningful than a print would be good
                System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
            }
		return statement.getResultSet();
	}
	
	
	public java.sql.ResultSet queryTable(String querySQL) throws SQLException {
	// Querying a table
//	    String querySQL = "SELECT id, name from " + tableName + " WHERE NAME = \'Vicki\'";
	    System.out.println (querySQL) ;
	    java.sql.ResultSet rs = statement.executeQuery ( querySQL ) ;
//	    while ( rs.next ( ) ) {
//		int id = rs.getInt ( 1 ) ;
//		String name = rs.getString (2);
//		System.out.println ("id:  " + id);
//		System.out.println ("name:  " + name);
//	    }
	    System.out.println ("DONE");
	    return rs;
	}

	public void updateTable(String updateSQL){
	//Updating a table
    	try {
//	    String updateSQL = "UPDATE " + tableName + " SET NAME = \'Mimi\' WHERE id = 3";
	    System.out.println(updateSQL);
	    statement.executeUpdate(updateSQL);
	    System.out.println("DONE");

	    // Dropping a table
	    String dropSQL = "DROP TABLE " + tableName;
	    System.out.println ( dropSQL ) ;
	    statement.executeUpdate ( dropSQL ) ;
	    System.out.println ("DONE");
	} catch (SQLException e)
	    {
		sqlCode = e.getErrorCode(); // Get SQLCODE
		sqlState = e.getSQLState(); // Get SQLSTATE
                
		// Your code to handle errors comes here;
		// something more meaningful than a print would be good
		System.out.println("Code: " + sqlCode + "  sqlState: " + sqlState);
	    }

	}
	public void closeDB(){
	// Finally but importantly close the statement and connection
	try {
		statement.close ( ) ;
		con.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
}

