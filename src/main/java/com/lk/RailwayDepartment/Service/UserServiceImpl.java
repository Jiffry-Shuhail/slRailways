package com.lk.RailwayDepartment.Service;

import com.lk.RailwayDepartment.Entity.Role;
import com.lk.RailwayDepartment.Entity.TrainSchedule;
import com.lk.RailwayDepartment.Entity.User;
import com.lk.RailwayDepartment.Model.SubscribersListFilter;
import com.lk.RailwayDepartment.Repository.RoleRepository;
import com.lk.RailwayDepartment.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    public static SimpleDateFormat FORMATTER = new SimpleDateFormat("MMMM d, yyyy");

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("USER");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRole(role);
        User save = userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> findAllActiveUsers() {
        return userRepository.findAllByActive();
    }

    @Override
    public List<User> filter(SubscribersListFilter subscribersListFilter) {
        String stringQuery = "SELECT u FROM User u WHERE u.active = :active";

        if (subscribersListFilter.getRole() != null) {
            stringQuery += " AND u.role = :role";
        }

        if (!subscribersListFilter.getFromDate().isBlank()) {
            stringQuery += " AND u.date >= :fromDate";
        }

        if (!subscribersListFilter.getToDate().isBlank()) {
            stringQuery += " AND u.date <= :toDate";
        }

        TypedQuery<User> query = entityManager.createQuery(stringQuery, User.class);

        query.setParameter("active", subscribersListFilter.isActive());

        if (subscribersListFilter.getRole() != null) {
            query.setParameter("role", subscribersListFilter.getRole());
        }

        if (!subscribersListFilter.getFromDate().isBlank()) {
            try {
                query.setParameter("fromDate", FORMATTER.parse(subscribersListFilter.getFromDate()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        if (!subscribersListFilter.getToDate().isBlank()) {
            try {
                query.setParameter("toDate", FORMATTER.parse(subscribersListFilter.getToDate()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        List<User> users = new ArrayList<>();

        try {
            if (query.getResultList() != null)
                users = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    private Role checkRoleExist() {
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }
}
