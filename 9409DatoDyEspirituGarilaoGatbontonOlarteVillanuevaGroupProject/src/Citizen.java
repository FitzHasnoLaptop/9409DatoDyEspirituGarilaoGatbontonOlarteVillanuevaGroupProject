package prog2.finalgroup;
import java.lang.Comparable;

public class Citizen implements Comparable<Citizen>{
    private String fullName; //first name and last name
    private String email;
    private String address; //should not contain double quotes
    private int age;
    private boolean isResident; //true if the citizen is a resident, false of otherwise
    private int district;
    private char gender; //M for males, F for Females

    //citizens
    public Citizen(String fullName, String email, String address, int age, boolean isResident, int district, char gender){
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.age = age;
        this.isResident = isResident;
        this.district = district;
        this.gender = gender;
    }

    //setters and getters
    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setAge(int age){
        this.age = age;
    }
    public void setIsResident(boolean isResident){
        this.isResident = isResident;
    }
    public void setDistrict(int district){
        this.district = district;
    }
    public void setGender(char Gender){
        this.gender = gender;
    }
    public String getFullName(){
        return fullName;
    }
    public String getEmail(){
        return email;
    }
    public String getAddress(){
        return address;
    }
    public int getAge(){
        return age;
    }
    public boolean getIsResident(){
        return isResident;
    }
    public int getDistrict(){
        return district;
    }
    public char getGender(){
        return gender;
    }

    //methods
    public String toString(){
        String genderWord = (gender == 'M') ? "Male" : "Female";
        String Residency = (isResident) ? "Resident" : "Non-Resident";
        return fullName + "," + email + "," + address + "," + age + "," + Residency + "," + district + "," + genderWord;
    }

    public int compareTo(Citizen o) {
        return 0;
    }
}
