package com.evo.qualitanceProject.controller;

import com.evo.qualitanceProject.converter.ProductConverter;
import com.evo.qualitanceProject.converter.UserConverter;
import com.evo.qualitanceProject.dto.ProductDto;
import com.evo.qualitanceProject.dto.UserDto;
import com.evo.qualitanceProject.model.AppUser;
import com.evo.qualitanceProject.model.Product;
import com.evo.qualitanceProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private ProductConverter productConverter;

    @GetMapping
    List<UserDto> getAllUsers() {
        return new ArrayList<>(userConverter.convertModelsToDtos(service.getAllUsers()));
    }

    @GetMapping("/{id}")
    UserDto findUserById(@PathVariable Long id) {
        return userConverter.convertModelToDto(service.findUser(id).get());
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    UserDto createNewUser(@RequestBody AppUser user) {
        return userConverter.convertModelToDto(service.createUser(user));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    UserDto updateUser(@PathVariable Long id,
                       @RequestBody AppUser user) {
        return userConverter.convertModelToDto(service.updateUser(id, user));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        service.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/filterUsers")
    List<UserDto> filterUsers(@RequestBody String s) {
        return new ArrayList<>(userConverter.convertModelsToDtos(service.filterUsers(s)));
    }

    @PutMapping("/addToFav")
    AppUser addProductToFavorite(@RequestBody Product product) {
        return service.addProductToFavorite(product);
    }

    @DeleteMapping("/deleteFromFav")
    AppUser deleteProductFromFavorite(@RequestBody Product product) {
        return service.removeProductFromFavorite(product);
    }

    @GetMapping("/address")
    String getDefaultAddress() {
        return service.getDefaultAddress();
    }

    @PutMapping("/address/saveOrUpdate")
    AppUser saveOrUpdateUserAddress(@RequestBody String address) {
        return service.saveOrUpdateUserAddress(address);
    }

    @DeleteMapping("/address")
    ResponseEntity<?> deleteUserAddress() {
        service.deleteUserAddress();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findByUsername/{username}")
    Optional<AppUser> findUserByUsername(@PathVariable String username) {
        return service.findUserByUsername(username);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/banUser/{id}")
    void banUser(@PathVariable Long id)
    {
        service.banUser(id);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/unbanUser/{id}")
    void unbanUser(@PathVariable Long id)
    {
        service.banUser(id);
    }

    @GetMapping("/usersAlsoBought")
    Set<ProductDto> findUsersAlsoBought(){
        return productConverter.convertModelsToDtos(service.findUsersAlsoBought());
    }
    @GetMapping("/findByEmail/{email}")
    AppUser  findUserByEmail(@PathVariable String email)
    {
        return service.findUserByEmail(email);
    }
}
