package PetBase.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "pets")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String birthDate;
    private String breed;
    private String color;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToMany
    @JoinTable(
            name = "pet_friends",
            joinColumns = @JoinColumn(name = "pet_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<Pet> friends;

    public void setName(String petName) {
        name = petName;
    }
    public void setBirthDate(String petBirthDate) {
        birthDate = petBirthDate;
    }
    public void setBreed(String petBreed) {
        breed = petBreed;
    }
    public void setColor(String petColor) {
        color = petColor;
    }
    public String getName(){
        return name;
    }

    public void setOwner(Owner inOwner) {
        owner = inOwner;
    }

    public String getId() {
        return String.valueOf(id);
    }

    public Owner getOwner() {
        return owner;
    }

    public void setId(long l) {
        id = l;
    }

    public String getBreed() {
        return breed;
    }

    public String getColor() {
        return color;
    }
}
