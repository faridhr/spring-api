package com.springapi.springapi.model.repos;

import com.springapi.springapi.model.entities.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import java.util.Optional;

public interface UserRepo extends PagingAndSortingRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
