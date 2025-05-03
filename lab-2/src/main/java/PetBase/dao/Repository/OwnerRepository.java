package PetBase.dao.Repository;

import PetBase.dao.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Page<Owner> findByNameIgnoreCaseContainingAndBirthDateContaining(String name, String birthDate, Pageable pageable);

    Page<Owner> findByNameIgnoreCaseContaining(String name, Pageable pageable);

    Page<Owner> findByBirthDateContaining(String birthDate, Pageable pageable);
}
