package org.jrutten.orderproject.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "security_logs")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Setter
@Getter
public class SecurityLog {
    @Id
    @SequenceGenerator(name = "security_logs_id_seq", sequenceName = "security_logs_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "security_logs_id_seq")
    int id;

    @Column(columnDefinition = "DATETIME", name = "invoked_at")
    LocalDateTime date;

    @Column(name = "invoked_by")
    String invokedBy;

    @Column(name = "class")
    String aclass;

    @Column(name = "message")
    String message;

}
