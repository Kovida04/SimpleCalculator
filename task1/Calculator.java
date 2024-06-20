import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Calculator extends JFrame implements ActionListener {

    private JTextField display;
    private double tempNumber1, tempNumber2;
    private boolean isNewNumber;
    private String operation;

    public Calculator() {
        createUI();
    }

    private void createUI() {
        setTitle("Calculator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(new Font("Arial", Font.BOLD, 24));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4));

        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C", "√", "^", "%"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 24));
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        isNewNumber = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            switch (command) {
                case "C":
                    display.setText("");
                    isNewNumber = true;
                    tempNumber1 = 0;
                    tempNumber2 = 0;
                    break;
                case "=":
                    tempNumber2 = Double.parseDouble(display.getText());
                    performCalculation();
                    isNewNumber = true;
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                case "^":
                    tempNumber1 = Double.parseDouble(display.getText());
                    operation = command;
                    isNewNumber = true;
                    break;
                case "√":
                    tempNumber1 = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(Math.sqrt(tempNumber1)));
                    isNewNumber = true;
                    break;
                case "%":
                    tempNumber1 = Double.parseDouble(display.getText());
                    display.setText(String.valueOf(tempNumber1 / 100));
                    isNewNumber = true;
                    break;
                default:
                    if (isNewNumber) {
                        display.setText(command);
                        isNewNumber = false;
                    } else {
                        display.setText(display.getText() + command);
                    }
                    break;
            }
        } catch (NumberFormatException ex) {
            display.setText("Error");
        } catch (ArithmeticException ex) {
            display.setText("Math Error");
        }
    }

    private void performCalculation() {
        double result = 0;
        switch (operation) {
            case "+":
                result = tempNumber1 + tempNumber2;
                break;
            case "-":
                result = tempNumber1 - tempNumber2;
                break;
            case "*":
                result = tempNumber1 * tempNumber2;
                break;
            case "/":
                if (tempNumber2 == 0) {
                    throw new ArithmeticException();
                }
                result = tempNumber1 / tempNumber2;
                break;
            case "^":
                result = Math.pow(tempNumber1, tempNumber2);
                break;
        }
        display.setText(String.valueOf(result));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }
}
