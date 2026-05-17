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
        try {
            fileReader = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.print("Error. File not found");
        }
        citizens = new ArrayList<>(); //initialize before adding

        //make list of citizens
        while(fileReader.hasNext()){
            String line = fileReader.nextLine();
            //initialization of variables
            String[] cells = line.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
            String name = cells[0] + " " + cells [1];
            String email = cells[2];
            String address = cells[3];
            int age = Integer.parseInt(cells[4]);
            boolean isResident = cells[5].equalsIgnoreCase("Resident"); //if else determines if they are a resident or not
            int district = Integer.parseInt(cells[6]);
            char gender = cells[7].equalsIgnoreCase("Male") ? 'M' : 'F';
            //contructor of citizen and adding to the list
            Citizen currentCitizen = new Citizen(name, email, address, age, isResident, district, gender);
            citizens.add(currentCitizen);
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
