package Models;

public class Author {
    private int id;
    private String name;
    private String country;
    private int birthYear;

    public Author(String name, String country, int birthYear) {
        this.name = name;
        this.country = country;
        this.birthYear = birthYear;
    }

    public Author(int id, String name, String country, int birthYear) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.birthYear = birthYear;
    }

    public Author() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public String toString() {
        return id + "\t" + name + "\t" + country + "\t" + birthYear + "\n";
    }
}
