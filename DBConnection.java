package dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {

	private Connection connection;
	private static DBConnection dbconnection;

	//konstruktor bez parametrów
	private DBConnection(){
		Properties data = new Properties();
		//sczytanie z pliku danych logowania do bazy danych i zainicjalizowanie pola typu Connection
		try{
		data.load(new FileInputStream("db.properties"));
		String user = data.getProperty("user");
		String password = data.getProperty("password");
		String dburl = data.getProperty("dburl");
		
		connection = DriverManager.getConnection(dburl, user, password);
		}

		catch (SQLException e) {
			System.out.println("Data base error: " + e.getMessage());
			e.printStackTrace();
		}
		
		catch (FileNotFoundException e){
            System.out.println("FileNotFoundException: " + e.getMessage());
		}
		
		catch (IOException e){
            System.out.println("IOException: " + e.getMessage());
		}
		
	}

	//metoda tworząca połączenie z bazą danych, jeśli takie nie jest nawiązane
	public static DBConnection dbconnection(){
		if(dbconnection == null){
			dbconnection = new DBConnection();
		}
		return dbconnection;
	}

	//metoda wysyłająca podane jako parametr typu String zapytanie do bazy danych
	public ResultSet query (String queryString){
		ResultSet result = null;
		try {
			Statement statement = connection.createStatement();
			result = statement.executeQuery(queryString);
		}
		catch (SQLException e) {
			System.out.println("Data base error: " + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	//metoda modyfikująca dane w bazie danych
	public boolean modify (String string){
		int recordsModified = 0;
		try {
			Statement statement = connection.createStatement();
			recordsModified = statement.executeUpdate(string);
		}
		catch (SQLException e) {
			System.out.println("Data base error: " + e.getMessage());
			e.printStackTrace();
				return false;
			}
			if (recordsModified > 0) {
				return true;
			}
			else {
				return false;
			}
		}

    //metoda zamykająca połączenie z bazą danych
	public void connectionClose(boolean bool, ResultSet result){
		try{
			if (bool == true){
				connection.close();
			}
		}
		catch (SQLException e) {
			System.out.println("Data base error: " + e.getMessage());
			e.printStackTrace();
		}

		try{
			if(result != null){
				result.close();
			}
		}
		catch (SQLException e) {
			System.out.println("Data base error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	//metoda niszcząca obiekt typu ResultSet
	public void objectDestroy(ResultSet result){
		connectionClose(false, result);
	}
}
