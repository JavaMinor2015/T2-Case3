package com.infosupport.t2c3.security;

import com.infosupport.t2c3.domain.customers.Credentials;
import com.infosupport.t2c3.domain.customers.Customer;
import com.infosupport.t2c3.repositories.CredentialsRepository;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Windows 7 on 11-1-2016.
 */
@Component
public class SecurityService {

    @Autowired
    private CredentialsRepository credentialsRepo;

    @Autowired
    private CustomerRepository customerRepo;

    public Credentials createCredentials(String userName, String password) {
        String hashedPassword = hash(password);
        return new Credentials(userName, hashedPassword, "");
    }


    public String verify(String userName, String password) {
        boolean loginSuccessful = false;
        String encodedPassword = hash(password);

        Credentials c = credentialsRepo.findByUserName(userName);
        if (c != null && encodedPassword.equals(c.getHashedPassword())) {
            return createToken(c);
        }
        return "";
    }

    private String createToken(Credentials c) {
        Customer customer = customerRepo.findByCredentialsUserName(c.getUserName());

        String token = hash(customer.getFirstName() + customer.getLastName() + (new SecureRandom().nextInt() + 12345));
        customer.getCredentials().setToken(token);
        credentialsRepo.save(customer.getCredentials());
        System.out.println(token);
        return token;
    }

    @PostConstruct
    private void init() {
        Customer cust = new Customer();
        cust.setFirstName("Remco");
        cust.setLastName("Groenenboom");
        cust.setCredentials(createCredentials("remco", "password"));
        customerRepo.save(cust);
    }

    private String hash(String toHash) {
        MessageDigest mda = null;
        try {
            mda = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String hashedString = HexBin.encode(mda.digest(toHash.getBytes()));
        return hashedString;
    }

    public void logout(String token) {
        System.out.println(token);
        Credentials credentials = credentialsRepo.findByToken(token);
        credentials.setToken("");
        credentialsRepo.save(credentials);
    }

    public String registrate(Customer customer) {
        Credentials oldCredentials = customer.getCredentials();
        Credentials newCredentials = createCredentials(oldCredentials.getUserName(), oldCredentials.getHashedPassword());

        customer.setCredentials(newCredentials);
        customerRepo.save(customer);
        return createToken(customer.getCredentials());
    }
}
