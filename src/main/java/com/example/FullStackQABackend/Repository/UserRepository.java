package com.example.FullStackQABackend.Repository;

import com.example.FullStackQABackend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
