package ui;

import dao.DBConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ZadanieNa5_lab4 {
    public static void main(String[] argc) {
        DBConnection dbConnection = DBConnection.dbconnection();
        BufferedReader bufferedReader;
        String choice = null;
        do {
            try {
                ResultSet rs = dbConnection.query("SELECT * FROM `Users` ORDER BY `id`");
                System.out.println("Please select user whose data you would like to see");
                while (rs.next()) {
                    System.out.print("\n" + rs.getString(1) + ". ");

                    System.out.println(rs.getString(2));
                }
                dbConnection.objectDestroy(rs);
                System.out.println("\n4. Wyjście");
            } catch (SQLException e) {
                System.out.println("Data base error: " + e.getMessage());
                e.printStackTrace();
            }

            try {
                bufferedReader = new BufferedReader(new InputStreamReader(System.in));
                choice = bufferedReader.readLine();
                if (!choice.equals("4")) {
                    try {
                        ResultSet rs = dbConnection.query("SELECT * FROM `Users` WHERE `id` = " + choice);
                        rs.next();
                        System.out.println("\nNumer identyfikacyjny: " + rs.getString(1));

                        System.out.println("Hasło: " + rs.getString(3));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    try {
                        ResultSet rs = dbConnection.query("SELECT * FROM `Employees` WHERE `user_id` = " + choice);
                        rs.next();
                        System.out.println("Imię: " + rs.getString(4));

                        System.out.println("Nazwisko: " + rs.getString(3));

                        System.out.println("Nazwisko: " + rs.getString(5));

                        System.out.println("Posada: " + rs.getString(6));

                        dbConnection.objectDestroy(rs);

                        rs = dbConnection.query("SELECT * FROM `Tasks` WHERE `employee_id` = " + choice);
                        while (rs.next()) {
                            System.out.println("Tytył zadania: " + rs.getString(3));
                            System.out.println("Opis zadania: " + rs.getString(4));
                            System.out.println("Status zadania: " + rs.getString(5));
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
        } while(!choice.equals("4"));
    }
}