package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.MailAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PersonAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class UserMysqlAdapter implements IUserPersistencePort {
    private final IUserRepository personRepository;
    private final IUserEntityMapper personEntityMapper;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void saveUser(User user) {
        if (personRepository.findByDniNumber(user.getDniNumber()).isPresent()) {
            throw new PersonAlreadyExistsException();
        }

        if (personRepository.existsByMail(user.getMail())){
            throw new MailAlreadyExistsException();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        personRepository.save(personEntityMapper.toEntity(user));
    }
}
