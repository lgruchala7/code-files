import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Sudoku extends JFrame implements ActionListener, MouseListener {

    private JPanel jPanel;
    private int numbers[][], numbers2[];
    private JTextField jTextFields[][];
    private JLabel jLabels[][];
    private JButton restartButton, checkButton;
    private String numberString = "813957642476132985295846731637281459958764213124593867581479326349625178762318594";



    public Sudoku() {
        setTitle("Sudoku");
        setBounds(100, 100, 545, 700);
        getContentPane().setLayout(null);
        this.jPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for(int i = 0; i <= 3; i++) {
                    g.drawLine(12, 168*i + 12, 516, 168*i + 12);
                    g.drawLine(168*i + 12, 12,  168*i + 12, 516);
                }
            }
        };
        this.jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        this.jPanel.setLayout(null);
        setContentPane(jPanel);
        this.numbers = new int[9][9];
        this.numbers2 = new int[9];
        this.jLabels = new JLabel[9][9];
        this.jTextFields = new JTextField[9][9];
        this.checkButton = new JButton("Check");
        this.jPanel.add(checkButton);
        this.checkButton.setBounds(130, 560, 100, 40);
        this.checkButton.addActionListener(this);
        this.restartButton = new JButton("Restart");
        this.jPanel.add(restartButton);
        this.restartButton.setBounds(300, 560, 100, 40);
        this.restartButton.addActionListener(this);
        newGame();
    }



    private void newGame() {
        Random r = new Random();
        int x, row, column;
        numberFilling();

        for(int i = 0; i <= 8; i++) {
            row = r.nextInt(9);
            column = r.nextInt(9);

            x = r.nextInt(2) + 1;

            if(column % 3 == 0){
                for (int j = 0; j <= 8; j++){
                    int temp = numbers[column][j];
                    numbers[column][j] = numbers[column + x][j];
                    numbers[column + x][j] = temp;
                }
            }
            if(column % 3 == 1){
                for (int j = 0; j <= 8; j++){
                    int temp = numbers[column][j];
                    if (x == 1) {
                        numbers[column][j] = numbers[column + x][j];
                        numbers[column + x][j] = temp;
                    }
                    else{
                        numbers[column][j] = numbers[column - x/2][j];
                        numbers[column - x/2][j] = temp;
                    }
                }
            }
            if(column % 3 == 2){
                for (int j = 0; j <= 8; j++){
                    int temp = numbers[column][j];
                    numbers[column][j] = numbers[column - x][j];
                    numbers[column - x][j] = temp;
                }
            }
            if(row % 3 == 0){
                for(int j = 0; j <= 8; j++){
                    int temp = numbers[j][row];
                    numbers[j][row] = numbers[j][row + x];
                    numbers[j][row + x] = temp;
                }
            }
            if(row % 3 == 1){
                for (int j = 0; j <= 8; j++){
                    int temp = numbers[j][row];
                    if (x == 1) {
                        numbers[j][row] = numbers[j][row + x];
                        numbers[j][row + x] = temp;
                    }
                    else{
                        numbers[j][row] = numbers[j][row - x/2];
                        numbers[j][row - x/2] = temp;
                    }
                }
            }
            if (row % 3 == 2){
                for (int j = 0; j <= 8; j++){
                    int temp = numbers[j][row];
                    numbers[j][row] = numbers[j][row - x];
                    numbers[j][row - x] = temp;
                }
            }
        }



        int i = 0;
        while (i <= 1) {
            row = r.nextInt(9);
            column = r.nextInt(9);

            if (numbers[column][row] != 0) {
                numbers[column][row] = 0;
                jTextFields[column][row] = new JTextField("    ");
                jTextFields[column][row].addMouseListener(this);
                jTextFields[column][row].setBounds(column * 50 + 15 * (column / 3) + 25, row * 50 + 15 * (row / 3) + 25, 50, 50);
                jTextFields[column][row].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                //jTextFields[column][row].setCaretPosition(4);
                this.jPanel.add(jTextFields[column][row]);
                i++;
            }
        }
        for (column = 0; column <= 8; column++) {
        for (row = 0; row <= 8; row++) {
            if (numbers[column][row] != 0) {
                    this.jLabels[column][row] = new JLabel(String.valueOf(numbers[column][row]));
                    this.jLabels[column][row].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
                    this.jPanel.add(jLabels[column][row]);
                    this.jLabels[column][row].setBounds(column * 50 + 15 * (column / 3) + 45, row * 50 + 15 * (row / 3) + 25, 50, 50);
                }
            }
        }
    }



    
    private void mistake(){
        JOptionPane.showMessageDialog(jPanel, "You have made a mistake somewhere. Try to find it.","Keep trying", JOptionPane.PLAIN_MESSAGE);
    }

    private void congratulations(){
        JOptionPane.showMessageDialog(jPanel, "You have done everything correctly. Good job!","Congratulations!", JOptionPane.PLAIN_MESSAGE);
    }

    private void numberFilling() {
        for (int i = 0; i <= 8; i++)
            for (int j = 0; j <=8; j++) {
                numbers[j][i] = Character.getNumericValue(numberString.charAt(i*9 + j));
            }
    }



    public static void main(String[] args) {
        Sudoku aplikacja = new Sudoku();
        aplikacja.setDefaultCloseOperation(EXIT_ON_CLOSE);
        aplikacja.setVisible(true);
    }




    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();
        if (source == restartButton) {
            this.setVisible(false);
            this.dispose();
            Sudoku aplikacja = new Sudoku();
            aplikacja.setDefaultCloseOperation(EXIT_ON_CLOSE);
            aplikacja.setVisible(true);
        }

        boolean next = true;

        if (source == checkButton) {
            try {
                outter1:
                for (int column = 0; column <= 8; column++) {
                    for (int i = 0; i <= 8; i++) {
                        numbers2[i] = 0;
                    }
                    for (int row = 0; row <= 8; row++) {
                        if (numbers[column][row] != 0) {
                            ++numbers2[numbers[column][row] - 1];
                        } else {
                            ++numbers2[Integer.parseInt(jTextFields[column][row].getText().trim()) - 1];
                        }
                    }
                    for (int i = 0; i <= 8; i++) {
                        if (numbers2[i] != 1) {
                            mistake();
                            next = false;
                            break outter1;
                        }
                    }
                }
                if (next == true) {
                    outter2:
                    for (int row = 0; row <= 8; row++) {
                        for (int i = 0; i <= 8; i++) {
                            numbers2[i] = 0;
                        }
                        for (int column = 0; column <= 8; column++) {
                            if (numbers[column][row] != 0) {
                                ++numbers2[numbers[column][row] - 1];
                            } else {
                                ++numbers2[Integer.parseInt(jTextFields[column][row].getText().trim()) - 1];
                            }
                        }
                        for (int i = 0; i <= 8; i++) {
                            if (numbers2[i] != 1) {
                                mistake();
                                next = false;
                                break outter2;
                            }
                        }
                    }
                    if (next == true) {
                        outter3:
                        for (int i = 0; i <= 8; i++) {
                            for (int j = 0; j <= 8; j++) {
                                numbers2[j] = 0;
                            }
                            for (int j = 0; j <= 8; j++) {
                                if (numbers[j % 3 + (i / 3) * 3][j / 3 + (i % 3) * 3] != 0) {
                                    ++numbers2[numbers[j % 3 + (i / 3) * 3][j / 3 + (i % 3) * 3] - 1];
                                } else {
                                    ++numbers2[Integer.parseInt(jTextFields[j % 3 + (i / 3) * 3][j / 3 + (i % 3) * 3].getText().trim()) - 1];
                                }
                            }
                            for (i = 0; i <= 8; i++) {
                                if (numbers2[i] != 1) {
                                    mistake();
                                    System.out.println(numbers2[i]);
                                    next = false;
                                    break outter3;
                                }
                            }
                        }
                        if (next == true){
                            congratulations();
                        }
                    }
                }
            }
                catch(ArrayIndexOutOfBoundsException e){
                    mistake();
                }
                catch(java.lang.NumberFormatException e){
                    mistake();
                }
            }
        }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Object source = mouseEvent.getSource();

        for (int column = 0; column <= 8; column++) {
            for (int row = 0; row <= 8; row++) {
                if (numbers[column][row] != 0)
                    jLabels[column][row].setForeground(Color.BLACK);
                else
                    jTextFields[column][row].setForeground(Color.BLACK);
            }
        }
        for (int column = 0; column <= 8; column++) {
            for (int row = 0; row <= 8; row++) {
                if (source == jTextFields[column][row]) {
                    for (int i = 0; i <= 8; i++) {
                        if (numbers[column][i] != 0)
                            jLabels[column][i].setForeground(Color.RED);
                        else
                            jTextFields[column][i].setForeground(Color.RED);
                        if (numbers[i][row] != 0)
                            jLabels[i][row].setForeground(Color.RED);
                        else
                            jTextFields[i][row].setForeground(Color.RED);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
    }
}
