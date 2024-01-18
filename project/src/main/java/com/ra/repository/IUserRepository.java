package com.ra.repository;

import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    Boolean existsUserByUserName(String userName);
    Page<User> findAllByUserNameContainingIgnoreCase(Pageable pageable,String name);
    User findUserByUserName(String userName);
}
