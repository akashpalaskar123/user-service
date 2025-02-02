package com.blue.user_service.repository;

import com.blue.user_service.entity.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
  Optional<Role> findByname(String roleTitle);
}
