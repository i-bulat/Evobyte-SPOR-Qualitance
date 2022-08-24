package com.evo.qualitanceProject.service;

import com.evo.qualitanceProject.model.*;
import com.evo.qualitanceProject.model.AppUser;
import com.evo.qualitanceProject.model.AuthenticationProvider;
import com.evo.qualitanceProject.model.Product;
import com.evo.qualitanceProject.model.RoleName;
import com.evo.qualitanceProject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private LineItemService lineItemService;

    @Override
    @Transactional
    public List<AppUser> getAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<AppUser> findUser(Long id) {
        Optional<AppUser> user = repository.findById(id);
        return user;
    }

    @Override
    public Optional<AppUser> findUserByUsername(String username) {
        return Optional.ofNullable(repository.findUserByUsername(username));
    }

    @Override
    public AppUser createUser(AppUser user) {
        return repository.save(user);
    }

    @Override
    @Transactional
    public AppUser updateUser(Long id, AppUser user) {
        AppUser updatedUser = repository.findById(id).orElseThrow();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setImageURL(user.getImageURL());
        updatedUser.setEnabled(user.isEnabled());
        updatedUser.setAuthProvider(user.getAuthProvider());
        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<AppUser> filterUsers(String s) {
        return repository.findAllByFirstNameOrLastName(s, s);
    }

    @Override
    @Transactional
    public AppUser addProductToFavorite(Product product) {
        AppUser user = getAuthenticatedUser();

        user.getFavoriteProducts().add(product);
        return user;
    }

    @Override
    @Transactional
    public AppUser removeProductFromFavorite(Product product) {
        AppUser user = getAuthenticatedUser();

        Set<Product> favProd = user.getFavoriteProducts();

        for (Iterator<Product> iterator = favProd.iterator(); iterator.hasNext(); ) {
            Product prod = iterator.next();
            if (prod.getId() == product.getId()) {
                iterator.remove();
            }
        }

        return user;
    }

    @Transactional
    public void banUser(Long id)
    {
        AppUser user = repository.findById(id).orElseThrow();
        user.setBanned(true);
    }

    @Transactional
    public void unbanUser(Long id)
    {
        AppUser user = repository.findById(id).orElseThrow();
        user.setBanned(false);
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return repository.findUserByEmail(email);
    }

    public void createNewCustomerAfterOauthLogin(String firstName,String lastName,String email, AuthenticationProvider provider)
    {
        AppUser newUser = new AppUser();
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setUsername(email);
        newUser.setEnabled(true);
        newUser.setAuthProvider(provider);
        newUser.setRole(RoleName.USER);
        newUser.setPassword(new BCryptPasswordEncoder().encode("password"));

        repository.save(newUser);
    }


    @Override
    @Transactional
    public String getDefaultAddress() {
        return getAuthenticatedUser().getAddress();
    }

    @Override
    @Transactional
    public AppUser saveOrUpdateUserAddress(String address) {
        AppUser user = getAuthenticatedUser();
        user.setAddress(address);
        return user;
    }

    @Override
    @Transactional
    public void deleteUserAddress() {
        getAuthenticatedUser().setAddress(null);
    }

    @Override
    public AppUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return findUserByUsername(authentication.getName()).orElseThrow();
    }

    // find latest purchase of the logged in user

    @Override
    @Transactional
    public List<Product> findUsersAlsoBought() {
        AppUser user = getAuthenticatedUser();

        //get last order
        List<Order> orders = user.getOrders().stream()
                .filter(o -> !o.getStatus().equals(OrderStatusEnum.IN_BASKET))
                .sorted(Comparator.comparing(Order::getUpdateDate))
                .collect(Collectors.toList());
        Order lastOrder = orders.get(0);

        //get last product ordered
        //todo:nullPointerException
        List<LineItem> lineItems = lastOrder.getLineItems().stream()
                .sorted(Comparator.comparing(LineItem::getDate).reversed())
                .collect(Collectors.toList());
        Product latestPurchase = lineItems.get(0).getProduct();
        LocalDate referenceDate = lineItems.get(0).getDate();

        Set<AppUser> users = findUsersWhoBoughtProductX(latestPurchase, referenceDate, user);

        Map<Product, Integer> commonProducts = findCommonProducts(users);

        List<Product> finalResult = sortMap(commonProducts);
        finalResult.remove(latestPurchase);
        if (finalResult.size() >= 10) {
            return finalResult.subList(0, 10);
        }
        return finalResult;
    }


    @Transactional
    public Set<AppUser> findUsersWhoBoughtProductX(Product product, LocalDate date, AppUser user) {
        Set<AppUser> users = new HashSet<>();

        List<LineItem> lineItems = lineItemService.findAllLineItems().stream()
                .filter(l -> !l.getOrder().getUser().equals(user))
                .filter(l -> !l.getOrder().getStatus().equals(OrderStatusEnum.IN_BASKET))
                .filter(l -> l.getOrder().getUpdateDate().isAfter(date.minusWeeks(2)))
                .filter(l -> l.getOrder().getUpdateDate().isBefore(date.plusWeeks(2)))
                .collect(Collectors.toList());

        for (LineItem lineItem : lineItems) {
            if (lineItem.getProduct().equals(product))
                users.add(lineItem.getOrder().getUser());
        }
        return users;
    }


    //returning a map with products and number of users who bought these products
    @Transactional
    public Map<Product, Integer> findCommonProducts(Set<AppUser> users) {
        List<LineItem> lineItems = new ArrayList<>();

        //get all lineItems from PLACED and COMPLETED orders
        for (AppUser user : users) {
            Set<Order> orders = user.getOrders();
            for (Iterator<Order> iterator = orders.iterator(); iterator.hasNext(); ) {
                Order order = iterator.next();
                if (order.getStatus().equals(OrderStatusEnum.IN_BASKET)) {
                    iterator.remove();
                }
            }

            for (Order order : orders) {
                Set<LineItem> itemList = order.getLineItems();
                for (LineItem lineItem : itemList) {
                    lineItems.add(lineItem);
                }
            }
        }

        Map<Product, Integer> products = new HashMap<>();
        for (int i = 0; i < lineItems.size() - 1; i++) {
            if (!products.containsKey(lineItems.get(i).getProduct())) {
                int common = 1;
                for (int j = i + 1; j < lineItems.size(); j++) {
                    if ((lineItems.get(i).getProduct().equals(lineItems.get(j).getProduct())) &&
                            (!lineItems.get(i).getOrder().getUser().equals(lineItems.get(j).getOrder().getUser())))
                        common++;
                }
                if (common > 1)
                    products.put(lineItems.get(i).getProduct(), common);
            }
        }

        return products;
    }


    public List<Product> sortMap(Map<Product, Integer> map) {
        //LinkedHashMap<Product, Integer> sortedMap = new LinkedHashMap<>();
        List<Product> products = new ArrayList<>();

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> products.add(x.getKey()));

        return products;
    }

}
