package core;

public class Task {

	private int id;
	private String taskTitle, taskDescription;
	private boolean taskStatus;
	
	public Task(int id, String taskTitle, String taskDescription, boolean taskStatus){
		this.id = id;
		this.taskTitle = taskTitle;
		this.taskDescription = taskDescription;
		this.taskStatus = taskStatus;
	}
	//metoda ustawiająca id zadania
	public void setId(int id){
		this.id = id;
	}

	//metoda ustawiająca tytuł zadania
	public void setTaskTitle(String taskTitle){
		this.taskTitle = taskTitle;
	}

	//metoda ustawiająca opis zadania
	public void setTaskDescription(String taskDescription){
		this.taskDescription = taskDescription;
	}

	//metoda ustawiająca status zadania
	public void setTaskStatus(boolean taskStatus){
		this.taskStatus = taskStatus;
	}

	//metoda zwracająca id zadania
	public int getId(){ return id; }

	//metoda zwracająca opis zadania
	public String getTaskDescription(){ return taskDescription; }

	//metoda zwracająca tytuł zadania
	public String getTaskTitle(){ return taskTitle; }

	//metoda zwracająca status zadania
	public boolean getTaskStatus(){ return taskStatus; }
}
