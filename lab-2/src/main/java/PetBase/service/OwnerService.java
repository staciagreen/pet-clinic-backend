package PetBase.service;

import PetBase.dao.Repository.OwnerRepository;
import PetBase.dao.entity.Owner;
import PetBase.service.dto.OwnerDTO;
import PetBase.service.dto.PetDTO;
import PetBase.service.mapping.OwnerMapper;
import PetBase.service.mapping.PetMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public OwnerService(OwnerRepository ownerRepository,
                        PasswordEncoder passwordEncoder) {
        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Для Spring Security: ищем по username и мапим в DTO.
     * Бросаем UsernameNotFoundException(String).
     */
    public OwnerDTO findByUsername(String username) {
        return ownerRepository.findByUsername(username)
                .map(OwnerMapper::toDto)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Owner not found: " + username));
    }

    /** Возвращает всех владельцев в виде DTO */
    public List<OwnerDTO> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(OwnerMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Фильтрация по имени/дате и постраничный вывод.
     * Поддерживает любые комбинации параметров.
     */
    public Page<OwnerDTO> filterOwners(String name, String birthDate, Pageable pageable) {
        if (name != null && birthDate != null) {
            return ownerRepository
                    .findByNameIgnoreCaseContainingAndBirthDateContaining(name, birthDate, pageable)
                    .map(OwnerMapper::toDto);
        } else if (name != null) {
            return ownerRepository
                    .findByNameIgnoreCaseContaining(name, pageable)
                    .map(OwnerMapper::toDto);
        } else if (birthDate != null) {
            return ownerRepository
                    .findByBirthDateContaining(birthDate, pageable)
                    .map(OwnerMapper::toDto);
        } else {
            return ownerRepository
                    .findAll(pageable)
                    .map(OwnerMapper::toDto);
        }
    }

    /** DTO-версия поиска по ID (для GET /api/owners/{id}) */
    public OwnerDTO getOwnerDtoById(Long id) {
        return ownerRepository.findById(id)
                .map(OwnerMapper::toDto)
                .orElse(null);
    }

    /** Сама сущность Owner (для проверки прав и работы с PetRestController) */
    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElse(null);
    }

    /**
     * Создаёт нового владельца.
     * При необходимости можно расширить сигнатуру, чтобы принимать username/password.
     */
    public OwnerDTO createOwner(String name, String birthDate) {
        Owner owner = new Owner();
        owner.setName(name);
        owner.setBirthDate(birthDate);
        // По умолчанию новый юзер имеет роль USER и простой пароль:
        owner.setRoles(Set.of("ROLE_USER"));
        owner.setPassword(passwordEncoder.encode("password123"));
        Owner saved = ownerRepository.save(owner);
        return OwnerMapper.toDto(saved);
    }

    /** Обновление полей существующего владельца */
    public OwnerDTO updateOwner(Owner updatedOwner) {
        Owner existing = ownerRepository.findById(updatedOwner.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Owner not found with id: " + updatedOwner.getId()));
        existing.setName(updatedOwner.getName());
        existing.setBirthDate(updatedOwner.getBirthDate());
        // Если передали новый пароль или роли — обновляем их
        if (updatedOwner.getPassword() != null && !updatedOwner.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedOwner.getPassword()));
        }
        if (updatedOwner.getRoles() != null) {
            existing.setRoles(updatedOwner.getRoles());
        }
        Owner saved = ownerRepository.save(existing);
        return OwnerMapper.toDto(saved);
    }

    /** Удаление по ID с проверкой существования */
    public void deleteOwnerById(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new EntityNotFoundException("Owner not found with id: " + id);
        }
        ownerRepository.deleteById(id);
    }

    /** Удалить всех владельцев */
    public void deleteAllOwners() {
        ownerRepository.deleteAll();
    }

    /**
     * Список DTO питомцев данного владельца.
     * Для GET /api/owners/{id}/pets
     */
    public List<PetDTO> getPetsOfOwner(Long ownerId) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Owner not found with id: " + ownerId));
        return owner.getPets().stream()
                .map(PetMapper::toDTO)
                .collect(Collectors.toList());
    }
}
