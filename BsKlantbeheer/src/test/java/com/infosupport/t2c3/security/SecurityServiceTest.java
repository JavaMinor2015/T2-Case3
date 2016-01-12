package com.infosupport.t2c3.security;

import com.infosupport.t2c3.domain.customers.Credentials;
import com.infosupport.t2c3.domain.customers.Customer;
import com.infosupport.t2c3.repositories.CredentialsRepository;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import junit.framework.TestCase;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Windows 7 on 12-1-2016.
 */
public class SecurityServiceTest extends TestCase {

    private SecurityService service;
    private CustomerRepository customerRepo;
    private CredentialsRepository credentialsRepo;
    private Credentials expected;
    private Customer customer;

    public void setUp() throws Exception {
        super.setUp();

        service = new SecurityService();

        customerRepo = mock(CustomerRepository.class);
        credentialsRepo = mock(CredentialsRepository.class);

        service.setCredentialsRepo(credentialsRepo);
        service.setCustomerRepo(customerRepo);

        expected = new Credentials("testUserName", hash("testPassword"), "");
        customer = mock(Customer.class);
        when(customer.getCredentials())
                .thenReturn(expected);
        when(customer.getFirstName())
                .thenReturn("testCustomerFirstName");
        when(credentialsRepo.findByUserName("testUserName"))
                .thenReturn(expected);
        when(customerRepo.findByCredentialsUserName("testUserName"))
                .thenReturn(customer);

    }

    public void testCreateCredentials() throws Exception {

        Credentials actual = service.createCredentials("testUserName", "testPassword");

        assertEquals(expected.getUserName(), actual.getUserName());
        assertEquals(expected.getPassword(), actual.getPassword());
        assertEquals(expected.getToken(), actual.getToken());
    }

    public void testVerifySucces() throws Exception {

        assertEquals(customer.getCredentials().getToken(), "");

        service.verify("testUserName", "testPassword");

        assertFalse(customer.getCredentials().getToken().equals(""));

    }

    public void testVerifyUnknownUserName(){

        assertEquals(customer.getCredentials().getToken(), "");

        service.verify("wrongUserName", "testPassword");

        assertEquals(customer.getCredentials().getToken(), "");
    }

    public void testVerifyIncorrectPassword(){

        assertEquals(customer.getCredentials().getToken(), "");

        service.verify("testUserName", "wrongPassword");

        assertEquals(customer.getCredentials().getToken(), "");
    }

    public void testVerifyWrongUserNameAndPassword(){

        assertEquals(customer.getCredentials().getToken(), "");

        service.verify("wrongUserName", "wrongPassword");

        assertEquals(customer.getCredentials().getToken(), "");
    }

    public void testLogout() throws Exception {
        expected.setToken("AABBCCDD123456");
        when(credentialsRepo.findByToken("AABBCCDD123456"))
                .thenReturn(expected);


        assertEquals(customer.getCredentials().getToken(), "AABBCCDD123456");

        service.logout("AABBCCDD123456");

        assertEquals(customer.getCredentials().getToken(), "");
    }

    public void testRegister() throws Exception {
        customer = new Customer();
        customer.setCredentials(expected);
        customer.setFirstName("testFirstName");
        customer.setLastName("testLastName");

        assertFalse(service.register(customer).equals(""));

        assertFalse(customer.getCredentials().getPassword().equals(expected.getPassword()));
    }

    private String hash(String toHash) {

        MessageDigest mda = null;
        try {
            mda = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
        }
        String hashedString = "";
        if (mda != null) {
            hashedString = HexBin.encode(mda.digest(toHash.getBytes(Charset.forName("UTF-8"))));
        }
        return hashedString;
    }
}
