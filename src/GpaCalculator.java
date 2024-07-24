import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GpaCalculator implements ActionListener {

    // Declare static variables for GUI components
    static JFrame frame;
    static JLabel header, dropdown_statement, gpa_heading, credit_heading;
    static JComboBox<String> drop_menu;
    static ArrayList<JComboBox<String>> gpaBoxes = new ArrayList<>();
    static ArrayList<JComboBox<String>> creditBoxes = new ArrayList<>();

    // Arrays to hold GPA and credit hours options
    static String[] gpa = {"4.00", "3.66", "3.33", "3.00", "2.66", "2.33", "2.00", "1.66", "1.30", "1.00"};
    static String[] credit_hours = {"1.0", "2.0", "3.0", "4.0"};
    static String[] dropdown_content = {"1 Course" , "2 Courses" , "3 Courses", "4 Courses", "5 Courses", "6 Courses"};

    // Fonts for different components
    static Font header_font = new Font("Times New Roman", Font.BOLD, 32);
    static Font myfont = new Font("Times New Roman", Font.BOLD, 20);
    static Font h2_font = new Font("Times New Roman", Font.BOLD, 17);

    // Main method to launch the GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GpaCalculator gpaCalculator = new GpaCalculator();
            gpaCalculator.createGui();
        });
    }

    // Method to create the GUI
    public void createGui() {
        // Initialize the JFrame
        frame = new JFrame("Gpa Calculator");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // Add header label
        header = new JLabel("Gpa Calculator");
        header.setBounds(82, 25, 250, 50);
        header.setFont(header_font);
        header.setForeground(Color.BLUE);
        frame.add(header);

        // Add dropdown statement label
        dropdown_statement = new JLabel("Select one of the following: ");
        dropdown_statement.setBounds(60, 70, 250, 50);
        dropdown_statement.setFont(myfont);
        dropdown_statement.setForeground(Color.BLACK);
        frame.add(dropdown_statement);

        // Add dropdown menu for course selection
        drop_menu = new JComboBox<>(dropdown_content);
        drop_menu.setBounds(60, 110, 250, 25);
        drop_menu.setFont(myfont);
        drop_menu.setBackground(Color.WHITE);
        drop_menu.addActionListener(this);
        frame.add(drop_menu);

        // Make the frame visible
        frame.setVisible(true);
    }

    // Handle action events
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == drop_menu) {
            // Handle dropdown menu selection
            String selected_item = (String) drop_menu.getSelectedItem();
            int numCourses = Integer.parseInt(selected_item.split(" ")[0]);
            updateFields(numCourses);
        } else if (e.getSource() instanceof JComboBox) {
            // Handle GPA and credit boxes selection
            JComboBox<String> source = (JComboBox<String>) e.getSource();
            if (gpaBoxes.contains(source) || creditBoxes.contains(source)) {
                // Store values because it is needed
                System.out.println("Selected Value: " + source.getSelectedItem());
            }
        }
    }

    // Method to update fields based on the number of courses selected
    private void updateFields(int numCourses) {
        // Clear all components
        frame.getContentPane().removeAll();
        // Recreate the original GUI components
        createGui();

        // Add headings for GPA and credit hours
        gpa_heading = new JLabel("GPA");
        gpa_heading.setBounds(90, 150, 100, 25);
        gpa_heading.setFont(h2_font);
        frame.add(gpa_heading);

        credit_heading = new JLabel("Credit Hours");
        credit_heading.setBounds(210, 150, 100, 25);
        credit_heading.setFont(h2_font);
        frame.add(credit_heading);

        // Clear previous boxes
        gpaBoxes.clear();
        creditBoxes.clear();

        // Add GPA and credit boxes based on the number of courses selected
        int y = 180; // Initial vertical position
        for (int i = 0; i < numCourses; i++) {
            JComboBox<String> gpaBox = new JComboBox<>(gpa);
            gpaBox.setBounds(60, y, 100, 25);
            gpaBox.setFont(myfont);
            gpaBox.setBackground(Color.YELLOW);
            gpaBox.addActionListener(this);
            gpaBoxes.add(gpaBox);
            frame.add(gpaBox);

            JComboBox<String> creditBox = new JComboBox<>(credit_hours);
            creditBox.setBounds(210, y, 100, 25);
            creditBox.setFont(myfont);
            creditBox.setBackground(Color.YELLOW);
            creditBox.addActionListener(this);
            creditBoxes.add(creditBox);
            frame.add(creditBox);

            y += 30; // Move down for the next row
        }

        // Add a button to calculate GPA
        JButton calculateButton = new JButton("Calculate GPA");
        calculateButton.setBounds(60, y, 250, 30);
        calculateButton.setFont(myfont);
        calculateButton.addActionListener(e -> calculateGPA());
        calculateButton.setBackground(Color.GREEN);

        frame.add(calculateButton);

        // Revalidate and repaint the frame
        frame.revalidate();
        frame.repaint();
    }

    // Method to calculate GPA
    private void calculateGPA() {
        double totalGPA = 0;
        double totalCredits = 0;

        // Calculate total GPA and total credits
        for (int i = 0; i < gpaBoxes.size(); i++) {
            try {
                String gpaStr = (String) gpaBoxes.get(i).getSelectedItem();
                String creditStr = (String) creditBoxes.get(i).getSelectedItem();

                double gpaValue = Double.parseDouble(gpaStr);
                double creditValue = Double.parseDouble(creditStr);

                totalGPA += gpaValue * creditValue;
                totalCredits += creditValue;
            } catch (NumberFormatException ex) {
                // Handle potential parsing errors
                System.err.println("Error parsing GPA or Credit value: " + ex.getMessage());
            }
        }

        // Calculate final GPA
        double finalGPA = totalCredits == 0 ? 0 : totalGPA / totalCredits;

        // Set a larger font for the JOptionPane message
        UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 18));
        UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 20));

        // Show the final GPA in a message dialog
        JOptionPane.showMessageDialog(frame, "Your GPA is: " + String.format("%.2f", finalGPA));
    }
}
