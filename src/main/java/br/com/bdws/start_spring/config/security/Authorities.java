package br.com.bdws.start_spring.config.security;

public enum Authorities {
    EDITOR,
    ADMIN;

    public  String role() {
        return "ROLE_".concat(name());
    }
}
