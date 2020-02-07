package core;

import java.util.ArrayList;

public class Employee {
	
	private int id;
	private String name, surname, job, email;
	private ArrayList<Task> taskList;

	//konstruktor z parametrami typu: int oraz String przypisujący polom odpowiednie obiekty
	public Employee(int id, String surname, String name, String email, String job){
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.job = job;
		this.email = email;
		this.taskList = new ArrayList();
	}

	//metoda ustawiająca id pracownika
	public void setId(int id){
		this.id = id;
	}

	//metoda ustawiająca imię
	public void setName(String name){
		this.name = name;
	}

	//metoda ustawiająca nazwisko
	public void setSurname(String surname){
		this.surname = surname;
	}

	//metoda ustawiająca stanowisko
	public void setJob(String job){
		this.job = job;
	}

	//metoda ustawiająca emaila
	public void setEmail(String email){
		this.email = email;
	}

	//metoda ustawiająca listę zadań
	public void setTaskList(ArrayList<Task> taskList) { this.taskList = taskList; }

	//metoda dodająca do listy zadań obiekt typu Task
	public void addTask(Task task) { this.taskList.add(task);}

	//metoda zwracająca imię
	public String getName(){ return this.name; }

	//metoda zwracająca nazwisko
	public String getSurname(){ return this.surname; }

	//metoda zwracająca email
	public String getEmail(){ return this.email; }

	//metoda zwracająca stanowisko
	public String getJob(){ return this.job; }

	//metoda zwracająca id pracownika
	public int getId(){return this.id;}

	//metoda zwracająca listę zadań
	public ArrayList<Task> getTaskList() { return taskList;}

	//metoda zwracająca id zadania
	public int getTaskId(int i){ return taskList.get(i-1).getId(); }

	//metoda zwracająca tytuł zadania
	public String getTaskTitle(int i){ return taskList.get(i-1).getTaskTitle();}

	//metoda zwracająca opis zadania
	public String getTaskDescription(int i){ return taskList.get(i-1).getTaskDescription();}

	//metoda zwracająca status zadania
	public boolean getTaskStatus(int i){ return taskList.get(i-1).getTaskStatus();}
}