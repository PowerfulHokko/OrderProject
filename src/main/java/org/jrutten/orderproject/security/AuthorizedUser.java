package org.jrutten.orderproject.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class AuthorizedUser {
    String sid;
    String name;
    String uName;
    String fName;
    String lName;
    String email;

    public AuthorizedUser(Jwt jwt) {
        this.sid = jwt.getClaimAsString("sid");
        this.name = jwt.getClaimAsString("name");
        this.uName = jwt.getClaimAsString("preferred_username");
        this.fName = jwt.getClaimAsString("given_name");
        this.lName = jwt.getClaimAsString("family_name");
        this.email = jwt.getClaimAsString("email");
    }

    public AuthorizedUser() {
        this.sid = "N/A";
        this.name = "N/A";
        this.uName = "N/A";
        this.fName = "N/A";
        this.lName = "N/A";
        this.email = "N/A";
    }
}
