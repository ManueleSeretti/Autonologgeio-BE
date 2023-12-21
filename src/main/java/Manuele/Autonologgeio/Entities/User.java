package Manuele.Autonologgeio.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@JsonIgnoreProperties({"password", "authorities", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})

public class User implements UserDetails {

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String surname;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private List<Role> role;

    @OneToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (this.role != null) {
            authorities.addAll(this.role.stream()
                    .map(role -> new SimpleGrantedAuthority(role.name()))
                    .toList());
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
