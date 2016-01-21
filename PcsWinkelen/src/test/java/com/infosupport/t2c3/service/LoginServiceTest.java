package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.accounts.Credentials;
import com.infosupport.t2c3.domain.accounts.Customer;
import com.infosupport.t2c3.exceptions.BadLoginException;
import com.infosupport.t2c3.model.Token;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.infosupport.t2c3.security.SecurityService;
import junit.framework.TestCase;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Windows 7 on 21-1-2016.
 */
public class LoginServiceTest extends TestCase {

    private LoginService loginService;


    private SecurityService securityService;
    private CustomerRepository customerRepo;
    private Customer customer;
    private Credentials credentials;

    public void setUp() throws Exception {
        super.setUp();

        loginService = new LoginService();

        credentials = mock(Credentials.class);
        when(credentials.getUserName())
                .thenReturn("testUserName");
        when(credentials.getPassword())
                .thenReturn("testPassWord");


        customer = mock(Customer.class);
        when(customer.getFirstName())
                .thenReturn("testVoorNaam");
        when(customer.getLastName())
                .thenReturn("testAchternaam");
        when(customer.getCredentials())
                .thenReturn(credentials);


        securityService = mock(SecurityService.class);
        loginService.setSecurityService(securityService);
        customerRepo = mock(CustomerRepository.class);
        loginService.setCustomerRepo(customerRepo);
        when(securityService.verify("testUserName", "testPassWord"))
                .thenReturn("TOKENFORCUSTOMER");
        when(securityService.verify("WrongUserName", "testPassWord"))
                .thenReturn("");

        when(customerRepo.findByCredentialsUserName("testUserName"))
                .thenReturn(customer);


    }

    public void testLoginWithRightCredentials() throws Exception {

        ResponseEntity<Token> entity = loginService.login(credentials);
        Token token = entity.getBody();

        assertEquals(token.getValue(), "TOKENFORCUSTOMER");

    }

    public void testLoginWithWrongCredentials() throws Exception {
        when(credentials.getUserName())
                .thenReturn("WrongUserName");

       try {
           ResponseEntity<Token> entity = loginService.login(credentials);
           fail("Exception expected");
       }catch(BadLoginException e){

       }


    }
}