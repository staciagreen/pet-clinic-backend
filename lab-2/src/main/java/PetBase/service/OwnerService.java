package PetBase.service;

import PetBase.dao.Repository.OwnerRepository;
import PetBase.dao.model.Owner;
import PetBase.service.dto.OwnerEntityDto;
import PetBase.service.dto.PetEntityDto;
import PetBase.service.mapping.OwnerEntityMapper;
import PetBase.service.mapping.PetEntityMapper;
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

    public OwnerEntityDto registerNewOwner(String username, String rawPassword,
                                           String name, String birthDate) {
        if (ownerRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        Owner o = new Owner();
        o.setUsername(username);
        o.setPassword(passwordEncoder.encode(rawPassword));
        o.setName(name);
        o.setBirthDate(birthDate);
        o.setRoles(Set.of("ROLE_USER"));
        Owner saved = ownerRepository.save(o);
        return OwnerEntityMapper.toEntityDto(saved);
    }

    public OwnerEntityDto findByUsername(String username) {
        Owner o = ownerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Owner not found: " + username));
        return OwnerEntityMapper.toEntityDto(o);
    }

    public List<OwnerEntityDto> getAllOwners() {
        return ownerRepository.findAll().stream()
                .map(OwnerEntityMapper::toEntityDto)
                .collect(Collectors.toList());
    }

    public Page<OwnerEntityDto> filterOwners(String name, String birthDate, Pageable pageable) {
        if (name != null && birthDate != null) {
            return ownerRepository
                    .findByNameIgnoreCaseContainingAndBirthDateContaining(name, birthDate, pageable)
                    .map(OwnerEntityMapper::toEntityDto);
        } else if (name != null) {
            return ownerRepository
                    .findByNameIgnoreCaseContaining(name, pageable)
                    .map(OwnerEntityMapper::toEntityDto);
        } else if (birthDate != null) {
            return ownerRepository
                    .findByBirthDateContaining(birthDate, pageable)
                    .map(OwnerEntityMapper::toEntityDto);
        }
        return ownerRepository.findAll(pageable)
                .map(OwnerEntityMapper::toEntityDto);
    }

    public OwnerEntityDto getOwnerDtoById(Long id) {
        return ownerRepository.findById(id)
                .map(OwnerEntityMapper::toEntityDto)
                .orElse(null);
    }

    public OwnerEntityDto getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .map(OwnerEntityMapper::toEntityDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Owner not found with id: " + id));
    }

    public OwnerEntityDto updateOwner(Long id, OwnerEntityDto dto) {
        OwnerEntityDto existing = getOwnerById(id);
        OwnerEntityDto new_owner = new OwnerEntityDto(dto.id(), dto.username(), dto.password(), dto.name(), dto.birthDate(), dto.pets(), dto.roles());
        return OwnerEntityMapper.toEntityDto(ownerRepository.save(OwnerEntityMapper.fromEntityDTO(new_owner)));
    }

    public OwnerEntityDto createOwner(String name, String birthDate) {
        Owner o = new Owner();
        o.setName(name);
        o.setBirthDate(birthDate);
        o.setRoles(Set.of("ROLE_USER"));
        o.setPassword(passwordEncoder.encode("password123"));
        Owner saved = ownerRepository.save(o);
        return OwnerEntityMapper.toEntityDto(saved);
    }

    public void deleteOwnerById(Long id) {
        if (!ownerRepository.existsById(id)) {
            throw new EntityNotFoundException("Owner not found with id: " + id);
        }
        ownerRepository.deleteById(id);
    }

    public void deleteAllOwners() {
        ownerRepository.deleteAll();
    }

    public List<PetEntityDto> getPetsOfOwner(Long ownerId) {
        Owner o = ownerRepository.findById(ownerId)
                .orElseThrow(() -> new EntityNotFoundException("Owner not found with id: " + ownerId));
        return o.getPets().stream()
                .map(PetEntityMapper::toEntityDto)
                .collect(Collectors.toList());
    }
}
