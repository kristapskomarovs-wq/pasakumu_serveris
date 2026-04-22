package com.komarovs.pasakumu_serveris.services;

import com.komarovs.pasakumu_serveris.models.UserModel;
import com.komarovs.pasakumu_serveris.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Lombok: izveido konstruktoru ar visiem final laukiem
public class UserService {

    private final UserRepository userRepository;

    public Long createUser(UserModel user) {
        user.setEmail(user.getEmail().toLowerCase().trim());

        // Pārbaudīt vai email jau eksistē
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Lietotājs ar šo e-pastu jau eksistē!");
        }

        // Saglabāt DB
        UserModel savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    // Pārbaudīt vai e-pasts jau eksistē
    public Boolean checkEmail(String email) {
        return userRepository.existsByEmail(email.toLowerCase().trim());
    }

    public Long signIn(UserModel user) {
        UserModel foundUser = userRepository.findByEmail(user.getEmail().toLowerCase().trim());
        if (foundUser == null) {
            return null; // Lietotājs neeksistē
        }
        if (!foundUser.getPassword().equals(user.getPassword())) {
            return null; // Nepareiza parole
        }
        return foundUser.getId();
    }
}