package ovsyanik.project.data;

public enum TypeUser {
    ADMIN("ADMIN"),
    DEFAULT("DEFAULT");

    private String name;

    TypeUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TypeUser{" +
                "name='" + name + '\'' +
                '}';
    }
}
