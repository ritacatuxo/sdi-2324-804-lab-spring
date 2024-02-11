package com.uniovi.notaneitor.repositories;

import com.uniovi.notaneitor.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface UsersRepository extends CrudRepository<User, Long> {
    User findByDni(String dni);

}
