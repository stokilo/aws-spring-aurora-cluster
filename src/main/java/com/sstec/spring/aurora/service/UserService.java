package com.sstec.spring.aurora.service;

import com.sstec.spring.aurora.config.TransactionalOverReadReplica;
import com.sstec.spring.aurora.dao.UserRepository;
import com.sstec.spring.aurora.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createRandomUser() {
        User user = new User();
        user.setName(String.format("test-user-%s", System.currentTimeMillis()));
        user.setEmail("test-email@email.com");
        user.setCreationDate(LocalDateTime.now());
        userRepository.save(user);
    }

    @Transactional(value = TransactionalOverReadReplica.READ_REPLICA)
    public List<User> fetchLastCreated() {
        return userRepository.findTop10ByOrderByCreationDateDesc();
    }

}
