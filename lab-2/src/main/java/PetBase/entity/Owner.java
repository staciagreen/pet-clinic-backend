package PetBase.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String birthDate;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;

    public void setName(String ownerName) {
        name = ownerName;
    }

    public void setBirthDate(String ownerBirthDate) {
        birthDate = ownerBirthDate;
    }

    public void setId(Long ownerId) {
        id = ownerId;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return String.valueOf(id);
    }
}