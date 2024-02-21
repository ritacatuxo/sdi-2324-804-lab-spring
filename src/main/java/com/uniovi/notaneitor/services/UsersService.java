package com.uniovi.notaneitor.services;

import com.uniovi.notaneitor.entities.User;
import com.uniovi.notaneitor.repositories.UsersRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder
            bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostConstruct
    public void init() {
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public User getUser(Long id) {
        return usersRepository.findById(id).get();
    }

    public void addUser(User user) {
        // al guardar un objeto usuario cifra el password
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public User getUserByDni(String dni) {
        return usersRepository.findByDni(dni);
    }

    public void updateUser(User user, Long id) {

        User originalUser = getUser(id);
        if(!originalUser.equals(null)) {
            // modificar dni, nombre y apellidos
            originalUser.setDni(user.getDni());
            originalUser.setName(user.getName());
            originalUser.setLastName(user.getLastName());
            usersRepository.save(originalUser); // si el obj no existe -> lo crea. Si existe -> lo modifica
        }

    }

    public List<User> searchUsersByNameAndLastName(String searchText, User user) {

        List<User> users = new LinkedList<User>();
        searchText = "%"+searchText+"%";
        users = usersRepository.searchUsersByNameAndSurname(searchText);
        return users;
    }
}
