package org.tevlrp.sportapp.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User extends BaseEntity {
    @Size(min = 2, max = 30, message = "Name size value is not valid")
    @Column(name = "first_name")
    private String firstName;

    @Size(min = 2, max = 40, message = "Lastname size value is not valid")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Email is not valid")
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;
}
