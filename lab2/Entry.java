import java.util.Random;

public class Entry {
    private final int phoneNumber;  // phoneNumber will be the key in HashTable

    // following is the value
    private final String name;
    private final String IC;

    // constructor with parameters
    public Entry(int phoneNumber, String name, String IC) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.IC = IC;
    }

    // constructor without parameters
    public Entry() {
        this.phoneNumber = generatePhoneNumber();
        this.name = generateName();
        this.IC = generateIC();
    }

    private int generatePhoneNumber() {
        Random rand = new Random();
        return rand.nextInt(20000000) + 80000000;
    }

    private String generateName() {
        // we may improve this to get real normal names
        // generate a random name consisted of six uppercase letters
        char[] name = new char[6];
        Random rand = new Random();

        // start generating
        for (int i = 0; i < 6; i++) {
            name[i] = (char)(rand.nextInt(26) + 65);
        }

        // convert to String and return
        return new String(name);
    }

    private String generateIC() {
        // generate an ID consisted of an uppercase letter and 8 digits
        char[] IC = new char[9];
        Random rand = new Random();

        // start generating
        IC[0] = (char)(rand.nextInt(26) + 65);
        for (int i = 1; i < 9; i++) {
            IC[i] = (char)(rand.nextInt(10) + 48);
        }

        // convert to String and return
        return new String(IC);
    }

    public String getName() {
        return name;
    }

    public String getIC() {
        return IC;
    }

    public int getKey() {
        return phoneNumber;
    }

    @Override
    public String toString() {
        return "key(phoneNumber) is " + getKey() + ", name is " + getName() + ", IC is " + getIC();
    }
}