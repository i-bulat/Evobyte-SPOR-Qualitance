package com.evo.qualitanceProject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NamedEntityGraphs({@NamedEntityGraph(name = "userWithOrderAndFavProducts",
        attributeNodes = {@NamedAttributeNode(value = "orders", subgraph = "sub.lineItems"),
                @NamedAttributeNode(value = "favoriteProducts")},
        subgraphs = {@NamedSubgraph(name = "sub.lineItems", attributeNodes =
        @NamedAttributeNode("lineItems"))})})

@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "users")
public class AppUser extends BaseEntity<Long> {
    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    @Column(nullable = true)
    private String address;

    @ToString.Exclude
    private String imageURL;

    @OneToMany(cascade = CascadeType.MERGE)
    private Set<Product> favoriteProducts = new HashSet<>();

    @ToString.Exclude
    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Order> orders = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private RoleName role;

    @Column(unique = true)
    private String username;

    private boolean enabled;

    private boolean isBanned;

    @Enumerated(EnumType.STRING)
    private AuthenticationProvider authProvider;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AppUser)) return false;
        AppUser appUser = (AppUser) o;
        return getId() != null && Objects.equals(getId(), appUser.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public AppUser(Long aLong, String firstName, String lastName, String email, AuthenticationProvider authProvider) {
        super(aLong);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.authProvider = authProvider;
    }

    public AppUser(Long aLong, String firstName, String lastName, String email, String username, AuthenticationProvider authProvider) {
        super(aLong);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.authProvider = authProvider;
    }
}
