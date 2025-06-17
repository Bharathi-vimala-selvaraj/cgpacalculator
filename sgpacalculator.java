import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {
    private JTextField[] creditFields;
    private JComboBox<String>[] gradeBoxes;
    private int numSubjects;
    private JPanel inputPanel;

    public Main() {
        setTitle("CGPA Calculator - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        String input = JOptionPane.showInputDialog("Enter number of subjects:");
        try {
            numSubjects = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid number. Exiting.");
            System.exit(0);
        }

        inputPanel = new JPanel(new GridLayout(numSubjects + 1, 3, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Subject Details"));

        creditFields = new JTextField[numSubjects];
        gradeBoxes = new JComboBox[numSubjects];

        String[] grades = {"O", "A+", "A", "B+", "B", "RA"};

        for (int i = 0; i < numSubjects; i++) {
            inputPanel.add(new JLabel("Subject " + (i + 1) + " Credit:"));
            creditFields[i] = new JTextField();
            inputPanel.add(creditFields[i]);

            gradeBoxes[i] = new JComboBox<>(grades);
            inputPanel.add(gradeBoxes[i]);
        }

        JButton calculateBtn = new JButton("Calculate SGPA");
        calculateBtn.addActionListener(e -> calculateSGPA());

        getContentPane().add(inputPanel, BorderLayout.CENTER);
        getContentPane().add(calculateBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private int getGradePoint(String grade) {
        switch (grade) {
            case "O": return 10;
            case "A+": return 9;
            case "A": return 8;
            case "B+": return 7;
            case "B": return 6;
            case "RA": return 0;
            default: return -1;
        }
    }

    private void calculateSGPA() {
        double totalPoints = 0;
        int totalCredits = 0;

        for (int i = 0; i < numSubjects; i++) {
            try {
                int credit = Integer.parseInt(creditFields[i].getText());
                String grade = (String) gradeBoxes[i].getSelectedItem();
                int gradePoint = getGradePoint(grade);

                totalPoints += credit * gradePoint;
                totalCredits += credit;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid credit entered in Subject " + (i + 1));
                return;
            }
        }

        if (totalCredits == 0) {
            JOptionPane.showMessageDialog(this, "Total credits cannot be zero.");
            return;
        }

        double sgpa = totalPoints / totalCredits;
        JOptionPane.showMessageDialog(this, "Your SGPA is: " + String.format("%.2f", sgpa));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
