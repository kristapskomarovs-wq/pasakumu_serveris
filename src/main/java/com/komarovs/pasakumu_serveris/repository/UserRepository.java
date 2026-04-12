package com.komarovs.pasakumu_serveris.repository;

import com.komarovs.pasakumu_serveris.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Boolean existsByEmail(String email);

    UserModel findByEmail(String email);

}