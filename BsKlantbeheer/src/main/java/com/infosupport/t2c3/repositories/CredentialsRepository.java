package com.infosupport.t2c3.repositories;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.accounts.Credentials;
import org.springframework.stereotype.Component;

/**
 * Created by Windows 7 on 06/01/2016.
 */
@Component
public interface CredentialsRepository extends BasicRepository<Credentials> {

    /**
     * Find credentials by username.
     * @param userName the username
     * @return the credentials
     */
    Credentials findByUserName(String userName);


    /**
     * Find credentials by token.
     * @param token the token
     * @return the credentials
     */
    Credentials findByToken(String token);

}
