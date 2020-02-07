package ui;

import core.Task;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TaskTableModel extends AbstractTableModel {

	private String[] columnName = {"Title", "Status", "Description"};
	private final int title = 0, status = 1, description = 2, columnAcces = -1;
	private List<Task> taskList;

	//konstruktor z parametrem typu List<Task> przypisujący polu tego samego typu obiekt
	public TaskTableModel(List<Task> taskList){
		this.taskList = taskList;
	}

	//metoda zwracająca liczbę kolumn
	@Override
	public int getColumnCount(){
		return columnName.length;
	}
	//metoda zwracająca liczbę wierszy
	@Override
	public int getRowCount(){
		return taskList.size();
	}
	//metoda zwracająca nazwę kolumny o wybranym indeksie
	@Override
	public String getColumnName(int col){
		return columnName[col];
	}
	//metoda zwracająca w zależności od podanych parametrów: tytuł zadania, status zadania, opis zadania
	// lub obiekt klasy Task
	@Override
	public Object getValueAt(int row, int col){
		Task task = taskList.get(row);

		if(col == title){
			return task.getTaskTitle();
		}
		else if (col == status){
			return task.getTaskStatus();
		}
		else if (col == description){
			return task.getTaskDescription();
		}
		else if (col == columnAcces){
			return task;
		}
		else {return null;}
	}
	//metoda zwracająca klasę, jakiej są elementy danej kolumny
	@Override
	public Class getColumnClass(int c){
		return getValueAt(0, c).getClass();
	}

}
