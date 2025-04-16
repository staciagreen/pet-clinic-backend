package PetBase.dao.entity;

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

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Long getId() {
        return id;
    }

    public String getBirthDate() {
        return birthDate;
    }
}