package ui;

import core.Employee;
import core.Task;
import core.User;
import dao.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TaskList extends JFrame implements ActionListener {

    private DBConnection dbConnection;
    private User user;
    private Employee employee;
    private JPanel layout;
    private JLabel userName, name, surname, position, email;
    private JTable taskTable;
    private JButton statusChangeButton, descriptionShowButton, logoutButton, userDataChange;
    private JScrollPane jScrollPane;

    //konstruktor z 2 parametrami: klasy DBConnection oraz User
    public TaskList(DBConnection dbConnection, User user){
        //inicjacja pól, ustawienie właściwości okna, ustawienie layoutów
        this.dbConnection = dbConnection;
        this.user = user;
        this.setTitle("Task List");
        this.setBounds(100,100,584,300);
        this.getContentPane().setLayout(new BorderLayout());
        this.layout = new JPanel();
        this.layout.setBorder(new EmptyBorder(5,5,5,5));
        this.layout.setLayout(new BorderLayout());
        setContentPane(layout);
        //utworzenie lokalnych obiektów do przechwoywania danych pracownika oraz użytkownika i dodanie ich do okna
        JPanel jPanel = new JPanel();
        this.layout.add(jPanel, BorderLayout.NORTH);
        jPanel.setLayout(new BorderLayout());
        JPanel jPanel1 = new JPanel();
        jPanel.add(jPanel1, BorderLayout.NORTH);
        //dalsze inicjowanie pól i umieszczanie ich w odpowiednich kontenerach,
        //a także tworzenie nowych obiektów służacych jako kontenery
        this.userName = new JLabel();
        jPanel1.add(userName);
        JPanel jPanel2 = new JPanel();
        jPanel.add(jPanel2);
        this.name = new JLabel();
        jPanel2.add(name);
        this.surname = new JLabel();
        jPanel2.add(surname);
        this.email = new JLabel();
        jPanel2.add(email);
        this.position = new JLabel();
        jPanel2.add(position);
        this.jScrollPane = new JScrollPane();
        this.layout.add(jScrollPane, BorderLayout.CENTER);
        this.taskTable = new JTable();
        this.jScrollPane.setViewportView(taskTable);
        this.dataLoad();
        this.reloadView();
        JPanel buttonGroup = new JPanel();
        this.layout.add(buttonGroup, BorderLayout.SOUTH);
        this.descriptionShowButton = new JButton("Show task description");
        this.descriptionShowButton.addActionListener(this);
        this.logoutButton = new JButton("Log out");
        this.logoutButton.addActionListener(this);
        this.statusChangeButton = new JButton("Change status");
        this.statusChangeButton.addActionListener(this);
        this.userDataChange = new JButton("Change user data");
        this.userDataChange.addActionListener(this);
        buttonGroup.add(descriptionShowButton);
        buttonGroup.add(logoutButton);
        buttonGroup.add(statusChangeButton);
        buttonGroup.add(userDataChange);
        //przypisanie windowlistenera do okna  wywołującego metodę connectionClose w razie zamknięcia okna
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dbConnection.connectionClose(true, null);
                System.exit(0);
            }
        });
    }

    //metoda z parametrem typu DBConnection uruchamiająca panel logowania
    public static void runLoginPanel(DBConnection dbConnection){
        LoginPanel loginPanel = new LoginPanel(dbConnection);
        loginPanel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginPanel.setVisible(true);
    }

    //metoda pobierająca dane z bazy danych i inicjalizująca pola obiektami o tych danych
    public void dataLoad(){
            String queryString = "SELECT * FROM `Employees` WHERE `user_id` = " + user.getId();
            ResultSet resultSet = dbConnection.query(queryString);
            if (resultSet != null) {
                try {
                    boolean dataDownload = resultSet.next();
                    if (dataDownload == true) {
                        this.employee = new Employee(resultSet.getInt(1), resultSet.getString(3),
                                resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
                    } else {
                        System.out.println("Not working " + user.getId());
                    }
                    dbConnection.objectDestroy(resultSet);
                } catch (SQLException e) {
                    System.out.println("Data base error: " + e.getMessage());
                    e.printStackTrace();
                }
            }

            queryString = "SELECT * FROM `Tasks` WHERE `employee_id` = " + employee.getId();
            resultSet = dbConnection.query(queryString);
            if(resultSet != null) {
                try {
                    ArrayList<Task> taskList = new ArrayList<>();
                    while (resultSet.next()) {
                        Task task = new Task(resultSet.getInt(1), resultSet.getString(3),
                                resultSet.getString(4), resultSet.getBoolean(5));
                        taskList.add(task);
                    }
                    employee.setTaskList(taskList);
                    dbConnection.objectDestroy(resultSet);
                } catch (SQLException e) {
                    System.out.println("Data base error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
    }

    //metoda odświeżająca widok listy zadań, wywoływana po zmianie danych w niej
    public void reloadView() {
        this.position.setText(employee.getJob());
        this.email.setText(employee.getEmail());
        this.surname.setText(employee.getSurname());
        this.name.setText(employee.getName());
        this.userName.setText(user.getUserName());

        TaskTableModel taskTableModel = new TaskTableModel(employee.getTaskList());

        this.taskTable.setModel(taskTableModel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //implementacja przycisku wylogowania, zamykane jest okno z listą zadań i otwierany panel logowania
        if (e.getSource() == logoutButton) {
            runLoginPanel(this.dbConnection);
            this.setVisible(false);
            this.dispose();
        }
        //implementacja przycisku wyświetlenia opisu wybranego zadania
        // w razie niepowodzenia wyskakuje komunikat o błędzie
        else if (e.getSource() == descriptionShowButton) {
            int rowIndex = this.taskTable.getSelectedRow();
            if (rowIndex < 0) {
                JOptionPane.showMessageDialog(TaskList.this, "A problem has occured",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                //tworzony jest nowy obiekt klasy TaskDescription, yśwwietlone zostaje okno dialogowe
                TaskDescription taskDescription = new TaskDescription((Task) this.taskTable.getValueAt(rowIndex,
                        JComponent.UNDEFINED_CONDITION));
                taskDescription.setVisible(true);
            }
        }
        //implementacja przycisku zmiany statusu wybranego zadania, w razie niepowodzenia wyskakuje komunikat o błędzie
        else if (e.getSource() == statusChangeButton) {
            int rowIndex = this.taskTable.getSelectedRow();
            if (rowIndex < 0) {
                JOptionPane.showMessageDialog(TaskList.this, "A problem has occured",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            else {
                //sczytanie danych zadania z bazy danych
                Task task = (Task) taskTable.getValueAt(rowIndex, JComponent.UNDEFINED_CONDITION);
                //sprawdzenie obecnego statusu zadania
                String queryString;
                if (task.getTaskStatus() == false)
                    queryString = "UPDATE `Tasks` SET `status` = 1 WHERE `id` = " + task.getId();
                else
                    queryString = "UPDATE `Tasks` SET `status` = 0 WHERE `id` = " + task.getId();
                //sprawdzenie, czy zaszła zmiana w bazie danych dokonana przy pomocy metody modify
                if (this.dbConnection.modify(queryString) == true) {
                    queryString = "SELECT * FROM `Tasks` WHERE `employee_id` = " + employee.getId();
                    ResultSet resultSet = dbConnection.query(queryString);
                    if (resultSet != null) {
                        try {
                            //przypisanie pracownikowi zmienionej listy zadań i odświeżenie widoku okna z listą
                            ArrayList<Task> taskList = new ArrayList<>();
                            while (resultSet.next()) {
                                Task task1 = new Task(resultSet.getInt(1), resultSet.getString(3),
                                        resultSet.getString(4), resultSet.getBoolean(5));
                                taskList.add(task1);
                            }
                            employee.setTaskList(taskList);
                            reloadView();

                        }
                    catch(SQLException event){
                            System.out.println("Data base error: " + event.getMessage());
                            event.printStackTrace();
                        }
                    }
                    else { System.out.println("resultSet == null");}
                }
                else {
                    JOptionPane.showMessageDialog(TaskList.this, "A problem has occured",
                            "Error", JOptionPane.ERROR_MESSAGE);return;
                }
            }
        }
        //implementacja przycisku zmiany danych użytkownika i pracownika
        // stworzenie i zainicjalizowanie obiektu typu ZadanieNa5_lab6
        else if (e.getSource() == userDataChange) {
            ZadanieNa5_lab6 zadanieNa5_lab6 = new ZadanieNa5_lab6(this.dbConnection, this.user, this.employee);
            zadanieNa5_lab6.setVisible(true);
            zadanieNa5_lab6.setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
    }

    //metoda main klasy TaskList
    public static void main(String[] argc) {
        DBConnection dbConnection = DBConnection.dbconnection();
        runLoginPanel(dbConnection);
        try {
            //pobieranie wszystkich danych z tabeli Users
            ResultSet rs = dbConnection.query("SELECT * FROM `Users` ORDER BY `id`");

            while (rs.next()) {
                System.out.println("\n" + rs.getString(1) + " ");

                System.out.println(rs.getString(2) + " ");
            }
            dbConnection.objectDestroy(rs);

            //pobieranie danych konkretnego użytkownika z tabeli Users
            rs = dbConnection.query("SELECT * FROM `Users` WHERE `id` = 2");
            rs.next();
            User user2 = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
            dbConnection.objectDestroy(rs);

            //pobieranie danych konkretnego użytkownika z tabeli Employees
            rs = dbConnection.query("SELECT * FROM `Employees` WHERE `user_id` = 2");
            rs.next();
            Employee employee2 = new Employee(rs.getInt(1), rs.getString(3), rs.getString(4),
                    rs.getString(5), rs.getString(6));
            dbConnection.objectDestroy(rs);

            //pobieranie danych konkretnego użytkownika z tabeli Tasks
            rs = dbConnection.query("SELECT * FROM `Tasks` WHERE `employee_id` = 2");
            int i = 0;
            while (rs.next()) {
                Task task = new Task(rs.getInt(1), rs.getString(3), rs.getString(4), rs.getBoolean(5));
                employee2.addTask(task);
                i++;
            }
            dbConnection.objectDestroy(rs);

            //ponowne pobieranie danych konkretnego użytkownika z tabeli Tasks
            rs = dbConnection.query("SELECT * FROM `Tasks` WHERE `employee_id` = 2");
            System.out.println(user2.getUserName());
            System.out.println(employee2.getName());
            System.out.println(employee2.getSurname());
            System.out.println(employee2.getEmail());
            System.out.println(employee2.getJob());
            i = 1;
            while (rs.next()) {
                System.out.println(employee2.getTaskId(i));
                System.out.println(employee2.getTaskTitle(i));
                System.out.println(employee2.getTaskDescription(i));
                System.out.println(employee2.getTaskStatus(i));
                i++;
            }
            dbConnection.objectDestroy(rs);

            //zmienianie statusu zadania w bazie danych
            boolean statusChanged = dbConnection.modify("UPDATE `Tasks` SET `status`= 1 WHERE `id` = 3");
            if (statusChanged == true){
                System.out.println("Something has been changed");
            }
            else{
                System.out.println("No changes have been made");
            }

            //Sprawdzenie statusu zmienionego zadania
            rs = dbConnection.query("SELECT * FROM `Tasks` WHERE `id` = 3");
            rs.next();
            System.out.println("Status of the modified task is: " + rs.getString("status"));
            dbConnection.objectDestroy(rs);
        }
        catch (SQLException e) {
            System.out.println("Data base error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}



