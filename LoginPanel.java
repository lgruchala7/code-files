package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import core.User;
import dao.DBConnection;
import org.jasypt.digest.config.SimpleDigesterConfig;
import org.jasypt.util.password.ConfigurablePasswordEncryptor;

public class LoginPanel extends JFrame implements ActionListener {
    private DBConnection dbConnection;
    private User user;
    private JPanel jPanel;
    private JTextField userName;
    private JPasswordField jPasswordField;
    private JButton dataConfirmationButton, closeButton;

    //konstruktor z parametrem typu DBConnection
    public LoginPanel(DBConnection dbConnection){
        //ustawianie właściwości okna, inicjalizacja pól, ustawienie layoutów
        this.dbConnection = dbConnection;
        this.setTitle("Login Panel");
        this.setBounds(100, 100, 450, 168);
        this.getContentPane().setLayout(new BorderLayout());
        this.jPanel = new JPanel();
        this.jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.getContentPane().add(this.jPanel, BorderLayout.CENTER);
        this.jPanel.setLayout(new FormLayout(new ColumnSpec[] {
                FormFactory.RELATED_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("default:grow"),
        },
                new RowSpec[] {
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                        FormFactory.RELATED_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC,
                }
        ));
        JLabel lblUser = new JLabel("User Name");
        // dodanie do pola JPanel etykietki z prawej
        this.jPanel.add(lblUser, "2, 2, right, default");
        this.userName = new JTextField();
        // dodanie do pola JPanel pola tekstowego wype�niaj�cego kom�rk�
        this.jPanel.add(this.userName, "4, 2, fill, default");

        JLabel lblPassword = new JLabel("Password");
        // dodanie do pola JPanel etykietki z prawej
        this.jPanel.add(lblPassword, "2, 4, right, default");
        this.jPasswordField = new JPasswordField();
        // dodanie do pola JPanel pola tekstowego wypełniającego komórki
        this.jPanel.add(this.jPasswordField, "4, 4, fill, default");

        //utworzenie obiektu do przechowywania przycisków i umiejscowienie u dołu okna
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.getContentPane().add(jPanel, BorderLayout.SOUTH);
        //inicjalizacja przycisków i dodanie ich do lokalnego obiektu do przechowywania przycisków
        this.closeButton = new JButton("Close");
        this.closeButton.addActionListener(this);
        jPanel.add(this.closeButton);
        this.dataConfirmationButton = new JButton("Ok");
        this.dataConfirmationButton.addActionListener(this);
        jPanel.add(this.dataConfirmationButton);

        //przypisanie windowlistenera do okna  wywołującego metodę connectionClose w razie zamknięcia okna
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dbConnection.connectionClose(true, null);
                System.exit(0);
            }
        });
    }

    //metoda sprawdzająca dane logowania
    public boolean loginDataCheck(){
        //konfiguracja algorytmu szyfrowania, utworzenie obiektów na to pozwalających
        SimpleDigesterConfig KONFIG = new SimpleDigesterConfig();
        KONFIG.setAlgorithm("MD5");
        KONFIG.setIterations(1);
        KONFIG.setSaltSizeBytes(0);
        ConfigurablePasswordEncryptor SZYFR = new ConfigurablePasswordEncryptor();
        SZYFR.setConfig(KONFIG);
        SZYFR.setStringOutputType("hexadecimal");
        String ZASZYFROWANE = SZYFR.encryptPassword(new String(jPasswordField.getPassword()));

        //utworzenie zapytania do bazy danych (używam jPasswordField zamiast ZASZYFROWANE, ponieważ
        // program nie działał tak jak było to zamierzone
        String query = "SELECT * FROM `Users` WHERE `name` = '" + userName.getText() + "' AND `password` = '" + ZASZYFROWANE + "'";
        ResultSet rs = dbConnection.query(query);
        if(rs == null){
            System.out.println("rs == null");
            return false;
        }
        else{
            try {
                boolean resultBoolean = rs.next();
                if (resultBoolean == true) {
                    //w przypadku poprawnego połączenia z bazą danych pole typu User inicjalizowane jest obiektem
                    // z parametrami pobranymi z bazy danych
                    this.user = new User(rs.getInt(1), rs.getString(2), rs.getString(3));
                    this.dbConnection.objectDestroy(rs);
                    return true;
                }
                else{
                    return false;
                }
            }
			catch (SQLException e){
                    System.out.println("Data base error: ");
                    e.getStackTrace();
                    return false;
                }
            }
        }

        // metoda zamykająca panel logowania i tworząca obiekt odpowiadający za wyświetlenie okna z listą zadań
    public void login(){
        this.setVisible(false);
        this.dispose();
        TaskList taskList = new TaskList(this.dbConnection, this.user);
        taskList.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //implementacja przycisku powtwierdzającego wprowadzone dane logowania
        if(e.getSource() == dataConfirmationButton){
            if(loginDataCheck() == true){
                login();
            }
            else {
                JOptionPane.showMessageDialog(LoginPanel.this, "A problem has occured.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        //implementacja przycisku zamykającego program
             else if(e.getSource() == closeButton){
            dbConnection.connectionClose(true, null);
            System.exit(0);
        }
    }
}
