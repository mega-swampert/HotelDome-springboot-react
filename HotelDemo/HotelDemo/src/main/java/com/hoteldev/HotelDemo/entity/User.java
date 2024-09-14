package com.hoteldev.HotelDemo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * ClassName: User
 * Package: com.hoteldev.HotelDemo.entity
 * Description:
 *
 * @Author MegaSwampert
 * @Create 5/09/2024 10:29 am
 * @Version 1.0
 */

/*
data: auto-generate getters, setters, and other utility methods
entity: marks this class as a JPA entity, meaning it maps to a database table
table: connects name of database table
*/

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    /*id: promary key of entity
    * generatedValue: auto-generated*/

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*notblank: to ensure the specific field is not null/empty; provides a error message if invalidation
    * column unique: must have unique values in database */
    @NotBlank(message = "Email is required")
    @Column(unique = true)
    private String email;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
    @NotBlank(message = "Password is required")
    private String password;
    private String role;

    /*onetomany: defines a relationship between user and booking entities
    * mappedBy: the user field in the booking class owns the relationship
    * fetch: lazy loading
    * cascade = CascadeType.ALL: all CRUD on user will cascade to associated booking entities*/
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    /*GrantedAuthority: it represents the permissions or roles a user has(e.g., USER,ADMIN)
    * These are used to determine whether a user is authorised to access certain resources.
    *
    * SimpleGrantedAuthority is the most commonly used implementation,
    * which accepts a string representing the authority*/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return List.of(new SimpleGrantedAuthority(role));
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
