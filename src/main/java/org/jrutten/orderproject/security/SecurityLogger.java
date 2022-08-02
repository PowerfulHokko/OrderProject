package org.jrutten.orderproject.security;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.jrutten.orderproject.customer.CustomerController;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.*;
import javax.transaction.Transactional;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
public class SecurityLogger {
    final Logger logger = LoggerFactory.getLogger(this.getClass());
    //final SecurityServiceWizard securityServiceWizard;

    public void log(Class<? extends CustomerController> aClass, String message, String token) {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getTokenAttributes().get("email"));
    }


    private void saveLogMessage(Class<? extends CustomerController> aClass, String message, String email) {
        SecurityLog securityLog = new SecurityLog();
        securityLog.setInvokedBy(email);
        securityLog.setAclass(aClass.getName());
        securityLog.setDate(LocalDateTime.now());
        securityLog.setMessage(message);

        //databaseWizard.save(securityLog);
        logger.info("Log saved");
    }
}


