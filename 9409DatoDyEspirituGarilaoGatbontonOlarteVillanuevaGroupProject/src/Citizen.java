public class Citizen implements Comparable<Citizen> {
    private String fullName;
    private String email;
    private String address;
    private int age;
    private boolean resident;
    private int district;
    private char gender;

    public Citizen(String fullName, String email, String address, int age,
                   boolean resident, int district, char gender) {
        this.fullName = fullName;
        this.email = email;
        this.address = address.replace("\"", "");
        this.age = age;
        this.resident = resident;
        this.district = district;
        this.gender = gender;
    }

    public String getFullName() {

        return fullName;
    }

    public String getEmail() {

        return email;
    }

    public String getAddress() {

        return address;
    }

    public int getAge() {

        return age;
    }

    public boolean isResident() {

        return resident;
    }

    public int getDistrict() {

        return district;
    }

    public char getGender() {

        return gender;
    }

    @Override
    public int compareTo(Citizen other) {

        return this.fullName.compareToIgnoreCase(other.fullName);
    }

    @Override
    public String toString() {
        return fullName + " | " + email + " | " + address + " | Age: " + age +
                " | Resident: " + resident + " | District: " + district +
                " | Gender: " + gender;
    }
}
