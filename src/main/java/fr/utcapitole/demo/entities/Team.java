package fr.utcapitole.demo.entities;

public enum Team {
    GRYFFINDOR("Gryffindor"),
    HUFFLEPUFF("Hufflepuff"),
    RAVENCLAW("Ravenclaw"),
    SLYTHERIN("Slytherin");

    private final String name;
    Team(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
