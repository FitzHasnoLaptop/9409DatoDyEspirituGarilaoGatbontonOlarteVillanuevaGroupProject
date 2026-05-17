package prog2.finalgroup;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class MyProgram {
    public static void main(String[] args){
        MyProgramUtility listFilters = new MyProgramUtility();


        JFrame frame = new JFrame("Citizen Information System");
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Citizen Information System", JLabel.CENTER);
        frame.add(title, BorderLayout.NORTH);

        String[] columns = {
                "Full Name", "Email", "Address", "Age",
                "Residency", "District", "Gender"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);

        //displays any List<Citizen> inside the JTable
        java.util.function.Consumer<List<Citizen>> displayCitizens = citizens -> {
            tableModel.setRowCount(0);

            for (Citizen citizen : citizens) {
                tableModel.addRow(new Object[]{
                        citizen.getFullName(),
                        citizen.getEmail(),
                        citizen.getAddress(),
                        citizen.getAge(),
                        citizen.getIsResident() ? "Resident" : "Non-Resident",
                        citizen.getDistrict(),
                        citizen.getGender() == 'M' ? "Male" : "Female"
                });
            }
        };

        displayCitizens.accept(listFilters.getCitizens());

        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(300, 0));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));




        JTextField nameField = new JTextField();
        nameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, nameField.getPreferredSize().height));
        nameField.setAlignmentX(Component.LEFT_ALIGNMENT);

        //finding the name of the citizen
        JButton findNameBtn = new JButton("1. Find Name");

        //to edit an entry of a citizen
        JButton editBtn = new JButton("2. Edit Entry");

        //to enter the name you're trying to look for
        leftPanel.add(new JLabel("Enter Name:"));
        leftPanel.add(nameField);
        leftPanel.add(findNameBtn);
        leftPanel.add(editBtn);
        leftPanel.add(Box.createVerticalStrut(20));

        JTextField districtField = new JTextField();
        districtField.setMaximumSize(new Dimension(Integer.MAX_VALUE, districtField.getPreferredSize().height));
        districtField.setAlignmentX(Component.LEFT_ALIGNMENT);
        JButton districtBtn = new JButton("3. Display by District");

        //enter the District number of the citizen you're looking for
        leftPanel.add(new JLabel("Enter District:"));
        leftPanel.add(districtField);
        leftPanel.add(districtBtn);
        leftPanel.add(Box.createVerticalStrut(20));


        //the buttons to display residents and non-residents
        JButton residentsBtn = new JButton("4. Display Residents");
        JButton nonResidentsBtn = new JButton("5. Display Non-Residents");

        leftPanel.add(residentsBtn);
        leftPanel.add(nonResidentsBtn);
        leftPanel.add(Box.createVerticalStrut(20));

        //the choice to add a new citizen to the csv and display the P.O. box
        JButton addCitizenBtn = new JButton("6. Create New Citizen");
        JButton poBoxBtn = new JButton("7. Display P.O. Box");
        JButton resetBtn = new JButton("Reset Table");

        leftPanel.add(addCitizenBtn);
        leftPanel.add(poBoxBtn);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(resetBtn);
        leftPanel.add(Box.createVerticalGlue());

        //Find name
        findNameBtn.addActionListener(e -> {
            String keyword = nameField.getText().trim().toLowerCase();

            List<Citizen> result = listFilters.getCitizens()
                    .stream()
                    .filter(c -> c.getFullName().toLowerCase().contains(keyword))
                    .collect(Collectors.toList());

            displayCitizens.accept(result);
        });

        //Edit entry of a citizen
        editBtn.addActionListener(e -> {
            String keyword = nameField.getText().trim().toLowerCase();

            Citizen foundCitizen = listFilters.getCitizens()
                    .stream()
                    .filter(c -> c.getFullName().toLowerCase().contains(keyword))
                    .findFirst()
                    .orElse(null);

            if (foundCitizen == null) {
                JOptionPane.showMessageDialog(frame, "Citizen not found.");
                return;
            }

            String[] choices = {"Full Name", "Email", "Address", "Age", "Residency", "District", "Gender"};

            String choice = (String) JOptionPane.showInputDialog(
                    frame,
                    "Which information do you want to change?",
                    "Edit Entry",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    choices,
                    choices[0]
            );

            if (choice == null) return;

            String newValue = JOptionPane.showInputDialog(frame, "Enter new " + choice + ":");

            if (newValue == null || newValue.trim().isEmpty()) return;

            try {
                if (choice.equals("Full Name")) {
                    foundCitizen.setFullName(newValue);
                } else if (choice.equals("Email")) {
                    foundCitizen.setEmail(newValue);
                } else if (choice.equals("Address")) {
                    foundCitizen.setAddress(newValue);
                } else if (choice.equals("Age")) {
                    foundCitizen.setAge(Integer.parseInt(newValue));
                } else if (choice.equals("Residency")) {
                    foundCitizen.setIsResident(newValue.equalsIgnoreCase("Resident"));
                } else if (choice.equals("District")) {
                    foundCitizen.setDistrict(Integer.parseInt(newValue));
                } else if (choice.equals("Gender")) {
                    foundCitizen.setGender(newValue.equalsIgnoreCase("Male") ? 'M' : 'F');
                }

                displayCitizens.accept(listFilters.getCitizens());
                JOptionPane.showMessageDialog(frame, "Entry updated successfully.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input.");
            }
        });

        // Display names by district
        districtBtn.addActionListener(e -> {
            try {
                String input = districtField.getText().trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter a district number.");
                    return;
                }
                int district = Integer.parseInt(input);

                displayCitizens.accept(listFilters.getDistrictCitizens(district));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter a valid integer for the district number.");
            }
        });

// Display residents
        residentsBtn.addActionListener(e -> {

            displayCitizens.accept(listFilters.getResidentCitizens());
        });

// Display non-residents
        nonResidentsBtn.addActionListener(e -> {

            displayCitizens.accept(listFilters.getNonResidentCitizens());
        });

// Display P.O. Box
        poBoxBtn.addActionListener(e -> {

            displayCitizens.accept(listFilters.getPOBoxCitizens());
        });

        //Create new citizen
        addCitizenBtn.addActionListener(e -> {
            try {
                String fullName = JOptionPane.showInputDialog(frame, "Full Name:");
                String email = JOptionPane.showInputDialog(frame, "Email:");
                String address = JOptionPane.showInputDialog(frame, "Address:");
                int age = Integer.parseInt(JOptionPane.showInputDialog(frame, "Age:"));
                boolean isResident = JOptionPane.showInputDialog(frame, "Resident or Non-Resident:")
                        .equalsIgnoreCase("Resident");
                int district = Integer.parseInt(JOptionPane.showInputDialog(frame, "District:"));
                char gender = JOptionPane.showInputDialog(frame, "Male or Female:")
                        .equalsIgnoreCase("Male") ? 'M' : 'F';

                Citizen newCitizen = new Citizen(fullName, email, address, age, isResident, district, gender);

                listFilters.getCitizens().add(newCitizen);

                displayCitizens.accept(listFilters.getCitizens());
                JOptionPane.showMessageDialog(frame, "New citizen added.");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid citizen information.");
            }
        });

        //Display P.O. Box
        poBoxBtn.addActionListener(e -> {
            displayCitizens.accept(listFilters.getPOBoxCitizens());
        });

        resetBtn.addActionListener(e -> {
            displayCitizens.accept(listFilters.getCitizens());
        });

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(tableScroll, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}

