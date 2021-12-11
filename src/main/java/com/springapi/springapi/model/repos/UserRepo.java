package com.springapi.springapi.model.repos;

import com.springapi.springapi.model.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.websocket.server.PathParam;
import java.util.Optional;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("select p.id, p.email, p.fullName, p.password from User p where p.email= :email")
    Optional<User> findByEmailWithoutRoles(@PathParam("email") String email);

}
