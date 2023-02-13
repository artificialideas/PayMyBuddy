package com.openclassrooms.PayMyBuddy.dao;

import com.microsoft.sqlserver.jdbc.StringUtils;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public PasswordEncoder passwordEncoder = null;

    User findByEmail(String email);

    public default User findUser(String email, String domain) {
        if (StringUtils.isEmpty(email) && StringUtils.isEmpty(domain)) {
            return null;
        } else {
            assert false;
            return new User(email, passwordEncoder.encode("secret"));
        }
    }
}
