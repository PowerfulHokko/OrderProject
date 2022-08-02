package org.jrutten.orderproject.customer;

import io.restassured.RestAssured;
import org.jrutten.orderproject.customer.representations.Address;
import org.jrutten.orderproject.customer.representations.CreateCustomerDTO;
import org.jrutten.orderproject.customer.representations.CustomerDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
class CustomerControllerIntegrationTest {

    @LocalServerPort
    int port;

    @Test
    @DisplayName("Given a Customer return a Customer")
    void givenACustomerReturnACustomer() {
        Address address = new Address("Bosweg", 14, "", "B2440", "Geel");
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO("Jeff", "Maes" , "J.Maes@gmail.com", address);

        CustomerDTO customer = RestAssured
                .given()
                .port(port)
                .body(createCustomerDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CustomerDTO.class);

        assertEquals(createCustomerDTO.getFirstName(), customer.getFirstName());
        assertEquals(createCustomerDTO.getLastName(), customer.getLastName());
    }

    @Test
    @DisplayName("Given two clients with the same email adress should fail")
    void givenTwoClientsWithTheSameEmailAdressShouldFail() {
        Address address = new Address("Bosweg", 14, "", "B2440", "Geel");
        CreateCustomerDTO createCustomerDTO = new CreateCustomerDTO("Jeff", "Maes" , "J.Maes2@gmail.com", address);
        CreateCustomerDTO createCustomerDTO2 = new CreateCustomerDTO("Fred", "Maes" , "J.Maes2@gmail.com", address);

        CustomerDTO customer = RestAssured
                .given()
                .port(port)
                .body(createCustomerDTO)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(CustomerDTO.class);

        RestAssured
                .given()
                .port(port)
                .body(createCustomerDTO2)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .post("/customers")
                .then()
                .assertThat()
                .statusCode(HttpStatus.CONFLICT.value());

    }


}