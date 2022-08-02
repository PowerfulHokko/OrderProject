package org.jrutten.orderproject.security;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.io.*;
import java.nio.file.FileSystems;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class KeycloakGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    private final Logger logger = Logger.getLogger(KeycloakGrantedAuthoritiesConverter.class.getName());
    private final Properties springProperties;

    public KeycloakGrantedAuthoritiesConverter() {
        this.springProperties = getPropertiesFromSpringApplicationProperties();
    }

    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        Map<String, Object> resourceAccess = source.getClaimAsMap("resource_access");
        Map<String, Object> clientAccess = (Map<String, Object>) resourceAccess.get(springProperties.getProperty("keycloak.resource"));
        List<String> roles = (List<String>) clientAccess.get("roles");
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }



    private Properties getPropertiesFromSpringApplicationProperties() {
        Properties properties = new Properties();
        File propertiesFile = FileSystems.getDefault().getPath("src", "main", "resources", "application.properties").toFile();
        loadProperties(properties, propertiesFile);
        return properties;
    }

    private void loadProperties(Properties properties, File propertiesFile) {
        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(propertiesFile);
            properties.load(fileInputStream);
        } catch (IOException e) {
            logger.severe("FAILED TO LOAD PROPERTIES");
            logger.severe(e.getMessage());
            System.exit(500);
        }
    }

}

