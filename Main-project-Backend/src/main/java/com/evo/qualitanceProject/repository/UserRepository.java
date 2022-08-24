package com.evo.qualitanceProject.repository;

import com.evo.qualitanceProject.model.AppUser;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends DefaultRepository<AppUser, Long> {
    @Query("select distinct u from AppUser u")
    @EntityGraph(value = "userWithOrderAndFavProducts", type =
            EntityGraph.EntityGraphType.LOAD)
    List<AppUser> findAll();

    @Query("select distinct u from AppUser u where u.id=?1")
    @EntityGraph(value = "userWithOrderAndFavProducts", type =
            EntityGraph.EntityGraphType.LOAD)
    Optional<AppUser> findById(Long id);

    @Query("select distinct u from AppUser u where lower(u.firstName) like %:#{#firstName.toLowerCase()}% " +
            "or lower(u.lastName) like %:#{#lastName.toLowerCase()}%")
    @EntityGraph(value = "userWithOrderAndFavProducts", type =
            EntityGraph.EntityGraphType.LOAD)
    List<AppUser> findAllByFirstNameOrLastName
            (@Param("firstName") String firstName, @Param("lastName") String lastName);

    @EntityGraph(value = "userWithOrderAndFavProducts", type =
            EntityGraph.EntityGraphType.LOAD)
    AppUser findUserByUsername(String username);

    @Query("select distinct u from AppUser u where lower(u.email) like %:#{#email.toLowerCase()}% ")
    @EntityGraph(value = "userWithOrderAndFavProducts", type =
            EntityGraph.EntityGraphType.LOAD)
    AppUser findUserByEmail(@Param("email") String email);
}
