package com.example.idea_y.models;

import com.example.idea_y.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.LifecycleState;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Entity(name="users")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String email;
    @Column(length = 2550000)
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="profile_id")
    private Profile profile;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<CompanyOffers> companyOffers=new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "user")
    private List<PeopleOffers> peopleOffers=new ArrayList<>();
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name="user_role", joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles=new HashSet<>();
    private LocalDateTime dateOfCreate;

    @PrePersist
    private void init(){
        dateOfCreate=LocalDateTime.now();
    }

    public void addPeopleToUser(PeopleOffers peopleOffer) {
        peopleOffer.setUser(this);
        peopleOffers.add(peopleOffer);
    }
    public void addComapnyToUser(CompanyOffers companyOffer) {
        companyOffer.setUser(this);
        companyOffers.add(companyOffer);
    }
    public boolean isAdmin(){return roles.contains(Role.ROLE_ADMIN);}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
    @Override
    public String getPassword(){
        return password;
    }
    @Override
    public String getUsername() {
        return email;
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
