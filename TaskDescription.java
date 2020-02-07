package ui;

import core.Task;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TaskDescription extends JDialog implements ActionListener {
    private JPanel layout;
    private JLabel taskName;
    private JTextArea taskDescription;
    private JButton closeButton;

    //konstruktor z parametrem typu Task
    TaskDescription(Task task){
        //ustawianie właściwości okna, inicjalizacja pól, ustawienie layoutów
        this.setTitle("Task Description");
        this.setBounds(100,100,450,168);
        getContentPane().setLayout(new BorderLayout());
        this.layout = new JPanel();
        this.layout.setBorder(new EmptyBorder(5,5,5,5));
        getContentPane().add(layout, BorderLayout.CENTER);
        this.layout.setLayout(new BorderLayout());
        this.taskName = new JLabel();
        this.layout.add(taskName, BorderLayout.NORTH);
        this.taskDescription = new JTextArea(task.getTaskDescription());
        this.taskDescription.setLineWrap(true);
        this.layout.add(taskDescription, BorderLayout.CENTER);
        //utworzenie lokalnego obiektu grupującego przyciski i dodanie go do okna
        JPanel buttonGroup = new JPanel();
        buttonGroup.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.layout.add(buttonGroup, BorderLayout.SOUTH);
        this.closeButton = new JButton("Close");
        this.closeButton.addActionListener(this);
        buttonGroup.add(closeButton);
    }

    public void actionPerformed(ActionEvent e){
        //implementacja przycisku zamykającego okno z opisem zadania
        if(e.getSource() == closeButton) {
            this.setVisible(false);
            this.dispose();
        }
    }
}
