package PetBase.dao.Repository;

import PetBase.dao.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByColorIgnoreCase(String color);

    List<Pet> findByColorIgnoreCaseAndBreedIgnoreCase(String color, String breed);

    List<Pet> findByColorIgnoreCaseAndBreedIgnoreCaseAndBirthDateAfter(String color, String breed, String birthDate);

    Page<Pet> findAll(Pageable pageable);

}
