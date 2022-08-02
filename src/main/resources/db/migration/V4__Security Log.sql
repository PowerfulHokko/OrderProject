CREATE TABLE orderproject.security_logs(
    id serial,
    invoked_at date NOT NULL DEFAULT NOW(),
    invoked_by character varying NOT NULL,
    class character varying NOT NULL,
    message character varying NOT NULL,
    PRIMARY KEY (id)
);