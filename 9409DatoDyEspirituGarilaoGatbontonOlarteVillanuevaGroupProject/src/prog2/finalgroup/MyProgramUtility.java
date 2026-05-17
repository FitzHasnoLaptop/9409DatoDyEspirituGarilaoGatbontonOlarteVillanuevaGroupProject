package prog2.finalgroup;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.stream.*;

public class MyProgramUtility {
    private Scanner fileReader; // reads lines from the file
    private File inputFile; //change file path if ever not working (right click -> copy path/reference)
    public List<Citizen> citizens; // list of all citizens

    //constructors

    public MyProgramUtility() {
        inputFile = new File("data.csv");
        citizens = new ArrayList<>();

        try (Scanner fileReader = new Scanner(inputFile)) { // Using try-with-resources to automatically close the file
            while (fileReader.hasNextLine()) {
                String line = fileReader.nextLine().trim();
                if (line.isEmpty()) continue; // Skip empty rows

                // Split by comma, ignoring commas inside quotes
                String[] cells = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");

                if (cells.length >= 8) {
                    String name = cells[0].trim() + " " + cells[1].trim();
                    String email = cells[2].trim();
                    String address = cells[3].trim().replace("\"", ""); // Clear quotes from address
                    int age = Integer.parseInt(cells[4].trim());
                    boolean isResident = cells[5].trim().equalsIgnoreCase("Resident");
                    int district = Integer.parseInt(cells[6].trim());
                    char gender = cells[7].trim().equalsIgnoreCase("Male") ? 'M' : 'F';

                    Citizen currentCitizen = new Citizen(name, email, address, age, isResident, district, gender);
                    citizens.add(currentCitizen);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: data.csv file not found. Ensure it is in the project root directory.");
        } catch (Exception e) {
            System.out.println("Error parsing CSV data: " + e.getMessage());
        }
    }

    //setters and getters
    public List<Citizen> getCitizens() {
        return citizens;
    }

    //methods: IMPORTANT, use any of these for filters
    public void printList(){
        for (Citizen currentCitizen : citizens) {
            System.out.println(currentCitizen);
        }
    }
    public List<Citizen> getMaleCitizens() {
        return citizens.stream()
                .filter(citizen -> citizen.getGender() == 'M')
                .collect(Collectors.toList());
    }
    public List<Citizen> getFemaleCitizens() {
        return citizens.stream()
                .filter(citizen -> citizen.getGender() == 'F')
                .collect(Collectors.toList());
    }
    public List<Citizen> getPOBoxCitizens() {
        return citizens.stream()
                .filter(citizen -> citizen.getAddress().contains("P.O. Box"))
                .collect(Collectors.toList());
    }
    public List<Citizen> getDistrictCitizens(int districtNum) {
        return citizens.stream()
                .filter(citizen -> citizen.getDistrict() == districtNum)
                .collect(Collectors.toList());
    }
    public List<Citizen> getResidentCitizens() {
        return citizens.stream()
                .filter(citizen -> citizen.getIsResident())
                .collect(Collectors.toList());
    }
    public List<Citizen> getNonResidentCitizens() {
        return citizens.stream()
                .filter(citizen -> !citizen.getIsResident())
                .collect(Collectors.toList());
    }
    public List<Citizen> getCitizensByEmailDomain(String domain) {
        return citizens.stream()
                .filter(citizen -> citizen.getEmail().toLowerCase().endsWith("@" + domain.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
    }
}
