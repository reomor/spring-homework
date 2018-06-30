package task01.model;

public class User {
    private final String sername;
    private final String name;

    public User(String name, String sername) {
        this.sername = sername;
        this.name = name;
    }

    public String getSername() {
        return sername;
    }

    public String getName() {
        return name;
    }
}
