package PetBase.security;

import PetBase.dao.model.Owner;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class OwnerDetails implements UserDetails {
    private final Owner owner;

    public OwnerDetails(Owner owner) {
        this.owner = owner;
    }

    public Long getId() {
        return owner.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return owner.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return owner.getPassword();
    }

    @Override
    public String getUsername() {
        return owner.getUsername();
    }

    @Override public boolean isAccountNonExpired()  { return true; }
    @Override public boolean isAccountNonLocked()   { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled()            { return true; }
}
