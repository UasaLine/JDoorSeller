package com.jds.service;

import com.jds.dao.repository.OrderDAO;
import com.jds.dao.repository.UserDAO;
import com.jds.dao.entity.UserEntity;
import com.jds.dao.entity.UserSetting;
import com.jds.model.Role;

import com.jds.model.backResponse.ResponseMassage;
import com.jds.model.enumClasses.PriceGroups;
import com.jds.model.ui.*;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService, UserServ {

    @Autowired
    private UserDAO dAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private MaineService maineService;

    private static final String ADMIN_NAME = "admin";
    private static final String ADMIN_PASS =
            "$2a$10$DMD6ILHkU12.yg7Xup91XO/ByVVV5Y3G1c/lct8nZNAnxUS9gJ9Ei";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return userHolder(username);
    }

    public UserEntity userHolder(String username) {

        if (ADMIN_NAME.equals(username)) {
            List<Role> roleList = new ArrayList<>();
            roleList.add(Role.ADMIN);

            return UserEntity.builder()
                    .id(9300)
                    .username(username)
                    .password(ADMIN_PASS)
                    .authorities(roleList)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(true)
                    .priceGroup(PriceGroups.RETAIL_PRICE)
                    .build();
        }

        UserEntity user = dAO.getUserByName(username);
        if (user != null) {
            List<Role> roleList = new ArrayList<>();
            roleList.add(user.getRole());

            user.setAuthorities(roleList);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            user.setEnabled(true);
        }
        return user;
    }

    public List<UserEntity> getUsers() {

        return setUpUsersForView(dAO.getUsers());

    }

    private List<UserEntity> setUpUsersForView(List<UserEntity> list) {

        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);

        list.stream()
                .peek(user -> setEnabledAndRole(user, roleList))
                .peek(this::setOrderCount)
                .collect(Collectors.toList());

        list.add(userHolder(ADMIN_NAME));

        return list;
    }

    private void setOrderCount(UserEntity user) {
        long orderCount = orderDAO.orderCountRows(null, 0, 0, user.getId());
        user.setOrderCounter((int) orderCount);

        long isWorkingOrderCountRows = orderDAO.isWorkingOrderCountRows(user.getId());
        user.setWorkOrderCounter((int) isWorkingOrderCountRows);
    }

    public void saveUser(@NonNull String userId,
                         @NonNull String username,
                         @NonNull String password,
                         int discount,
                         boolean enabledCheckbox,
                         PriceGroups priceGroups,
                         Role role) {

        boolean needToEncode = true;
        if ("0".equals(userId) && ("".equals(username) || "".equals(password))) {
            throw new IllegalArgumentException("username or password in saveUser can not be empty!");
        } else if (!"0".equals(userId) && "".equals(password)) {
            password = dAO.getUser(Integer.parseInt(userId)).getPassword();
            needToEncode = false;
        }

        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        int idInt = Integer.parseInt(userId);

        int id = dAO.saveOrUpdateUser(UserEntity.builder()
                .id(idInt)
                .username(username)
                .password(insertPassword(password, needToEncode))
                .authorities(roleList)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .discount(discount)
                .enabled(enabledCheckbox)
                .priceGroup(priceGroups)
                .role(role)
                .build());

        dAO.saveUserSetting(new UserSetting(id));
    }

    public String insertPassword(String pass, boolean needToEncode) {
        if ("".equals(pass)) {
            return getCurrentUser().getPassword();
        } else if (needToEncode) {
            return new BCryptPasswordEncoder().encode(pass);
        } else {
            return pass;
        }
    }

    public UserEntity getUser(@NonNull String userId) {

        int idInt = Integer.parseInt(userId);


        if (idInt == 9300) {
            return userHolder(ADMIN_NAME);
        } else if (idInt == 0) {
            return UserEntity.builder()
                    .username("newUser")
                    .build();
        }

        UserEntity user = dAO.getUser(idInt);
        setOrderCount(user);

        return user;

    }

    public void setEnabledAndRole(UserEntity user, List<Role> roleList) {
        user.setAuthorities(roleList);
        user.setEnabled(true);
    }

    @Override
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }

    public UserSetting getUserSetting() {
        return dAO.getUserSetting(getCurrentUser().getId());
    }

    public void saveCurrentUserSetting(int retailMargin, int salesTax, boolean includesTax) {

        dAO.saveUserSetting(
                UserSetting.builder()
                        .id(getCurrentUser().getId())
                        .retailMargin(retailMargin)
                        .salesTax(salesTax)
                        .includesTax(maineService.booleanToInt(includesTax))
                        .build());
    }

    public Set<Role> getRoles() {
        return EnumSet.allOf(Role.class);
    }

    public MainSidePanel getSidePanel(UserEntity user) {

        UiBuilder uiBuilder;

        if (user.isAdmin()) {
            uiBuilder = new AdminUiBuilder();
        } else {
            uiBuilder = new SellerUiBuilder();
        }

        return uiBuilder.mainSidePanel();
    }

    public ResponseMassage delete(int id) {
        UserEntity user = dAO.getUser(id);
        dAO.delete(user);
        return new ResponseMassage(true, "ok");
    }
}
