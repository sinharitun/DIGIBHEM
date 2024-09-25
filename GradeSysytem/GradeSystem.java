package GradeSysytem;

// public class GradeSystem {
    
// }
// package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

class Student {
    private String name;
    private String studentId;
    private Map<String, Integer> grades;

    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.grades = new HashMap<>();
    }

    public void addGrade(String subject, int grade) {
        grades.put(subject, grade);
    }

    public void displayGrades() {
        if (grades.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No grades available for " + name + " (" + studentId + ").");
        } else {
            StringBuilder gradeReport = new StringBuilder("Grades for " + name + " (" + studentId + "):\n");
            for (Map.Entry<String, Integer> entry : grades.entrySet()) {
                gradeReport.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            gradeReport.append("Average: ").append(calculateAverage());
            JOptionPane.showMessageDialog(null, gradeReport.toString());
        }
    }

    public double calculateAverage() {
        if (grades.isEmpty()) return 0;
        int total = 0;
        for (int grade : grades.values()) {
            total += grade;
        }
        return (double) total / grades.size();
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }
}

public class GradeSystem extends JFrame {

    private static List<Student> students = new ArrayList<>();
    private JTextField studentNameField, studentIdField, subjectField, gradeField;
    private JComboBox<String> studentIdComboBox;

    public GradeSystem() {
        // Create the UI components
        setTitle("Gradebook Management System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add Student Section
        JLabel nameLabel = new JLabel("Student Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(nameLabel, gbc);

        studentNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        add(studentNameField, gbc);

        JLabel idLabel = new JLabel("Student ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(idLabel, gbc);

        studentIdField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(studentIdField, gbc);

        JButton addStudentButton = new JButton("Add Student");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(addStudentButton, gbc);
        addStudentButton.addActionListener(new AddStudentListener());

        // Add Grade Section
        JLabel studentIdSelectLabel = new JLabel("Select Student:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(studentIdSelectLabel, gbc);

        studentIdComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        add(studentIdComboBox, gbc);

        JLabel subjectLabel = new JLabel("Subject:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        add(subjectLabel, gbc);

        subjectField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.WEST;
        add(subjectField, gbc);

        JLabel gradeLabel = new JLabel("Grade:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.EAST;
        add(gradeLabel, gbc);

        gradeField = new JTextField(5);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        add(gradeField, gbc);

        JButton addGradeButton = new JButton("Add Grade");
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.WEST;
        add(addGradeButton, gbc);
        addGradeButton.addActionListener(new AddGradeListener());

        // Display Grades Section
        JButton displayGradesButton = new JButton("Display Grades");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        add(displayGradesButton, gbc);
        displayGradesButton.addActionListener(new DisplayGradesListener());

        // Remove Student Section
        JButton removeStudentButton = new JButton("Remove Student");
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.anchor = GridBagConstraints.WEST;
        add(removeStudentButton, gbc);
        removeStudentButton.addActionListener(new RemoveStudentListener());
    }

    private class AddStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = studentNameField.getText();
            String studentId = studentIdField.getText();

            if (name.isEmpty() || studentId.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Both name and student ID are required.");
                return;
            }

            for (Student student : students) {
                if (student.getStudentId().equals(studentId)) {
                    JOptionPane.showMessageDialog(null, "Student ID already exists.");
                    return;
                }
            }

            students.add(new Student(name, studentId));
            studentIdComboBox.addItem(studentId);
            JOptionPane.showMessageDialog(null, "Student " + name + " added successfully!");

            // Clear the fields
            studentNameField.setText("");
            studentIdField.setText("");
        }
    }

    private class AddGradeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String studentId = (String) studentIdComboBox.getSelectedItem();
            String subject = subjectField.getText();
            String gradeText = gradeField.getText();

            if (studentId == null || subject.isEmpty() || gradeText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            int grade;
            try {
                grade = Integer.parseInt(gradeText);
                if (grade < 0 || grade > 100) {
                    JOptionPane.showMessageDialog(null, "Grade must be between 0 and 100.");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid grade.");
                return;
            }

            Student student = findStudentById(studentId);
            if (student != null) {
                student.addGrade(subject, grade);
                JOptionPane.showMessageDialog(null, "Grade added for " + student.getName() + " in " + subject + ".");
            }
        }
    }

    private class DisplayGradesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String studentId = (String) studentIdComboBox.getSelectedItem();
            if (studentId == null) {
                JOptionPane.showMessageDialog(null, "No students available.");
                return;
            }

            Student student = findStudentById(studentId);
            if (student != null) {
                student.displayGrades();
            }
        }
    }

    private class RemoveStudentListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String studentId = (String) studentIdComboBox.getSelectedItem();
            if (studentId == null) {
                JOptionPane.showMessageDialog(null, "No students available.");
                return;
            }

            if (students.removeIf(student -> student.getStudentId().equals(studentId))) {
                studentIdComboBox.removeItem(studentId);
                JOptionPane.showMessageDialog(null, "Student removed successfully.");
            } else {
                JOptionPane.showMessageDialog(null, "Student not found.");
            }
        }
    }

    private Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GradeSystem gradebookApp = new GradeSystem();
            gradebookApp.setVisible(true);
        });
    }
}