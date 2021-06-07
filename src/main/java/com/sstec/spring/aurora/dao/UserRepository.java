package com.sstec.spring.aurora.dao;

import com.sstec.spring.aurora.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findTop10ByOrderByCreationDateDesc();
}