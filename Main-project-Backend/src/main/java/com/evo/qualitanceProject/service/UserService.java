package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.AppUser;
import com.evo.qualitanceProject.model.AuthenticationProvider;
import com.evo.qualitanceProject.model.Product;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<AppUser> getAllUsers();

    Optional<AppUser> findUser(Long id);

    Optional<AppUser> findUserByUsername(String username);

    AppUser createUser(AppUser user);

    AppUser updateUser(Long id, AppUser user);

    void deleteUser(Long id);

    List<AppUser> filterUsers(String s);

    AppUser addProductToFavorite(Product product);

    AppUser removeProductFromFavorite(Product product);

    String getDefaultAddress();

    AppUser saveOrUpdateUserAddress(String address);

    void deleteUserAddress();

    AppUser getAuthenticatedUser();

    List<Product> findUsersAlsoBought();

    public void unbanUser(Long id);

    public void banUser(Long id);

    AppUser findUserByEmail(String email);

    void createNewCustomerAfterOauthLogin(String firstName,String lastName, String email, AuthenticationProvider provider);
}
