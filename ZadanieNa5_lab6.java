package ui;

import core.Employee;
import core.User;
import dao.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ZadanieNa5_lab6 extends JFrame implements ActionListener {

    private DBConnection dbConnection;
    private User user;
    private Employee employee;
    private JPanel layout;
    private JButton firstNameChangeButton, lastNameChangeButton, positionChangeButton, emailChangeButton,
            userNameChangeButton, passwordChangeButton, closeButton;
    private JTextField firstNameTextField, lastNameTextField, positionTextField, emailTextField,
            userNameTextField, passwordTextField;

    //konstruktor z parametrami typu: DBConnection, User oraz Employee
    public ZadanieNa5_lab6(DBConnection dbConnection, User user, Employee employee) {
        //przypisanie polom obiektów i ustawienie właściwości okna oraz layoutów
        this.dbConnection = dbConnection;
        this.user = user;
        this.employee = employee;
        this.setTitle("User data modification");
        this.setBounds(100, 100, 600, 400);
        this.getContentPane().setLayout(new BorderLayout());
        this.layout = new JPanel();
        this.layout.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add(this.layout, BorderLayout.CENTER);
        this.layout.setLayout(null);

        //inicjalizacja przycisków oraz dodanie actionlistenera
        firstNameChangeButton = new JButton("Change first name");
        firstNameChangeButton.addActionListener(this);
        lastNameChangeButton = new JButton("Change last name");
        lastNameChangeButton.addActionListener(this);
        positionChangeButton = new JButton("Change position");
        positionChangeButton.addActionListener(this);
        emailChangeButton = new JButton("Change email");
        emailChangeButton.addActionListener(this);
        userNameChangeButton = new JButton("Change user name");
        userNameChangeButton.addActionListener(this);
        passwordChangeButton = new JButton("Change password");
        passwordChangeButton.addActionListener(this);

        //inicjalizacja pól tekstowych
        firstNameTextField = new JTextField();
        lastNameTextField = new JTextField();
        positionTextField = new JTextField();
        emailTextField = new JTextField();
        userNameTextField = new JTextField();
        passwordTextField = new JTextField();

        //dodanie do okna pól tekstowych w odpowiednich miejscach
        layout.add(firstNameTextField);
        firstNameTextField.setBounds(10, 10, 200, 30);
        layout.add(lastNameTextField);
        lastNameTextField.setBounds(10, 60, 200, 30);
        layout.add(positionTextField);
        positionTextField.setBounds(10, 110, 200, 30);
        layout.add(emailTextField);
        emailTextField.setBounds(10, 160, 200, 30);
        layout.add(userNameTextField);
        userNameTextField.setBounds(10, 210, 200, 30);
        layout.add(passwordTextField);
        passwordTextField.setBounds(10, 260, 200, 30);

        //dodanie do okna przycisków w odpowiednich miejscach
        layout.add(firstNameChangeButton);
        firstNameChangeButton.setBounds(250, 10, 200, 30);
        layout.add(lastNameChangeButton);
        lastNameChangeButton.setBounds(250, 60, 200, 30);
        layout.add(positionChangeButton);
        positionChangeButton.setBounds(250, 110, 200, 30);
        layout.add(emailChangeButton);
        emailChangeButton.setBounds(250, 160, 200, 30);
        layout.add(userNameChangeButton);
        userNameChangeButton.setBounds(250, 210, 200, 30);
        layout.add(passwordChangeButton);
        passwordChangeButton.setBounds(250, 260, 200, 30);

        //inicjalizacja, dodanie do okna w odpowiednich miejscach przycisku zamknięcia okna oraz dodanie actionlistenera
        closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        layout.add(closeButton);
        closeButton.setBounds(480, 320, 80, 30);
    }




    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        //implementacja przycisku zmiany nazwy użytkownika, zmiana danych w bazie danych
        // i wyświetlenie komunikatu o (nie)powodzeniu
        if(source == userNameChangeButton) {
            String query = "UPDATE `Users` SET `name`= '" + userNameTextField.getText().trim() +
                    "' WHERE `name` = '" + user.getUserName() + "'";
            boolean statusChanged = dbConnection.modify(query);
            if(statusChanged)
                JOptionPane.showMessageDialog(this, "Modification successful.",
                        "Success", JOptionPane.PLAIN_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "A problem has occured.",
                        "Error", JOptionPane.ERROR_MESSAGE);
        }

        //implementacja przycisku zmiany imienia pracownika, zmiana danych w bazie danych
        // i wyświetlenie komunikatu o (nie)powodzeniu
        else if(source == firstNameChangeButton) {
            String query = "UPDATE `Employees` SET `first_name`= '" + firstNameTextField.getText().trim() +
                    "' WHERE `first_name` = '" + employee.getName() + "'";
            boolean statusChanged = dbConnection.modify(query);
            if(statusChanged)
                JOptionPane.showMessageDialog(this, "Modification successful.",
                        "Success", JOptionPane.PLAIN_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "A problem has occured.",
                        "Error", JOptionPane.ERROR_MESSAGE);
        }

        //implementacja przycisku zmiany nazwiska pracownika, zmiana danych w bazie danych
        // i wyświetlenie komunikatu o (nie)powodzeniu
        else if(source == lastNameChangeButton) {
            String query = "UPDATE `Employees` SET `last_name`= '" + lastNameTextField.getText().trim() +
                    "' WHERE `last_name` = '" + employee.getSurname() + "'";
            boolean statusChanged = dbConnection.modify(query);
            if(statusChanged)
                JOptionPane.showMessageDialog(this, "Modification successful.",
                        "Success", JOptionPane.PLAIN_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "A problem has occured.",
                        "Error", JOptionPane.ERROR_MESSAGE);
        }

        //implementacja przycisku zmiany stanowiska pracownika, zmiana danych w bazie danych
        // i wyświetlenie komunikatu o (nie)powodzeniu
        else if(source == positionChangeButton) {
            String query = "UPDATE `Employees` SET `position`= '" + positionTextField.getText().trim() +
                    "' WHERE `position` = '" + employee.getJob() + "'";
            boolean statusChanged = dbConnection.modify(query);
            if(statusChanged)
                JOptionPane.showMessageDialog(this, "Modification successful.",
                        "Success", JOptionPane.PLAIN_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "A problem has occured.",
                        "Error", JOptionPane.ERROR_MESSAGE);
        }

        //implementacja przycisku zmiany emaila pracownika, zmiana danych w bazie danych
        // i wyświetlenie komunikatu o (nie)powodzeniu
        else if(source == emailChangeButton) {
            String query = "UPDATE `Employees` SET `email`= '" + emailTextField.getText().trim() +
                    "' WHERE `email` = '" + employee.getEmail() + "'";
            boolean statusChanged = dbConnection.modify(query);
            if(statusChanged)
                JOptionPane.showMessageDialog(this, "Modification successful.",
                        "Success", JOptionPane.PLAIN_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "A problem has occured.",
                        "Error", JOptionPane.ERROR_MESSAGE);
        }

        //implementacja przycisku zmiany hasła użytkownika, zmiana danych w bazie danych
        // i wyświetlenie komunikatu o (nie)powodzeniu
        else if(source == passwordChangeButton) {
            String query = "UPDATE `Users` SET `password`= '" + passwordTextField.getText().trim() +
                    "' WHERE `password` = '" + user.getPassword() + "'";
            boolean statusChanged = dbConnection.modify(query);
            if(statusChanged)
                JOptionPane.showMessageDialog(this, "Modification successful.",
                        "Success", JOptionPane.PLAIN_MESSAGE);
            else
                JOptionPane.showMessageDialog(this, "A problem has occured.",
                        "Error", JOptionPane.ERROR_MESSAGE);
        }

        //implementacja przycisku zamykającego okno
        else if(source == closeButton) {
            this.setVisible(false);
            this.dispose();
        }
    }
}
