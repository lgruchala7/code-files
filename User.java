package core;

public class User {
	
	private int id;
	private String userName, password;

	//konstruktor z parametrami typu int oraz String
	public User(int id, String userName, String password){
		this.id = id;
		this.password = password;
		this.userName = userName;
	}

	//metoda ustawiająca id użytkownika
	public void setId(int id){
		this.id = id;
	}

	//metoda ustawiająca nazwę użytkownika
	public void setUserName(String userName){
		this.userName = userName;
	}

	//metoda ustawiająca hasło użytkownika
	public void setPassword(String password){
		this.password = password;
	}

	//metoda zwracająca id używtkownika
	public int getId(){ return id; }

	//metoda zwracająca nazwę używtkownika
	public String getUserName(){ return userName; }

	//metoda zwracająca hasło używtkownika
	public String getPassword(){ return password; }
}
