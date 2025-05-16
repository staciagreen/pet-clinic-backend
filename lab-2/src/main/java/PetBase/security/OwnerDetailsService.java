package PetBase.security;

import PetBase.dao.Repository.OwnerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class OwnerDetailsService implements UserDetailsService {
    private final OwnerRepository ownerRepo;

    public OwnerDetailsService(OwnerRepository ownerRepo) {
        this.ownerRepo = ownerRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        return ownerRepo.findByUsername(username)
                .map(OwnerDetails::new)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));
    }
}
