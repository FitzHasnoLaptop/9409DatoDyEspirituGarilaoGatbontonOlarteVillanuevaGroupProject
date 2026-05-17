import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MyProgramUtility {
    public ArrayList<Citizen> convertCSVToCitizenList(String filename) {

        ArrayList<Citizen> citizens = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(filename))) {

            // Skip header row
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            // Read each line from the CSV file
            while (scanner.hasNextLine()) {

                String[] data = scanner.nextLine().split(",");

                // Create Citizen object from CSV data
                Citizen citizen = new Citizen(
                        data[0].trim(),
                        data[1].trim(),
                        data[2].replace("\"", "").trim(),
                        Integer.parseInt(data[3].trim()),
                        Boolean.parseBoolean(data[4].trim()),
                        Integer.parseInt(data[5].trim()),
                        data[6].trim().charAt(0)
                );

                // Add Citizen object to the list
                citizens.add(citizen);
            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        }

        return citizens;
    }

    // Returns the total number of citizens
    public int getTotalCitizens(ArrayList<Citizen> citizens) {
        return citizens.size();
    }

    // Counts how many citizens are residents
    public int getTotalResidents(ArrayList<Citizen> citizens) {

        int count = 0;

        for (Citizen citizen : citizens) {
            if (citizen.isResident()) {
                count++;
            }
        }

        return count;
    }

    // Computes the average age of all citizens
    public double getAverageAge(ArrayList<Citizen> citizens) {

        if (citizens.isEmpty()) {
            return 0;
        }

        int totalAge = 0;

        for (Citizen citizen : citizens) {
            totalAge += citizen.getAge();
        }

        return (double) totalAge / citizens.size();
    }
}

