package com.traumaticevolutions.tevosales_backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.List;

/**
 * Servicio encargado de generar y validar tokens JWT usando el username.
 * 
 * @author Ángel Aragón
 */
@Service
public class JwtService {

    private final Key secretKey;

    /**
     * Constructor que inicializa la clave secreta para firmar los tokens JWT.
     * 
     * Utiliza una clave de 256 bits generada a partir de una cadena de texto.
     */
    public JwtService(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Genera un token JWT válido por 24 horas con el username como subject.
     *
     * @param username Nombre de usuario autenticado.
     * @param roles    Lista de roles del usuario.
     * @param id       ID del usuario.
     * @return Token JWT generado.
     */
    public String generateToken(String username, List<String> roles, Long id) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .claim("id", id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Extrae el username desde el token JWT.
     *
     * @param token Token recibido.
     * @return Username contenido en el token.
     */
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
