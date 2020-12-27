package com.example.luservice.service.serviceImpl;

import com.example.luservice.model.User;
import com.example.luservice.repository.UserRepository;
import com.example.luservice.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        User user = this.userRepository.findByEmail(username);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Override
    public User insert(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByIdAndEnabled(Long id, boolean enabled) {
        User found = userRepository.findByIdAndEnabled(id, enabled);
        if (found == null) {
            throw new RuntimeException("Failed to load user.");
        }
        return found;
    }

    @Override
    public User update(User edited) {
        User found;
        try{
            found = userRepository.findById(edited.getId()).get();
            if (found != null){
                return userRepository.save(edited);
            }
            throw new RuntimeException("Failed to update user.");
        }catch (Exception exception){
            throw new RuntimeException("Failed to update user.");
        }

    }

    @Override
    public User findByToken(String token) {
        return userRepository.findByToken(token);
    }
}
