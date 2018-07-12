package task05.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(sername, user.sername) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sername, name);
    }
}
