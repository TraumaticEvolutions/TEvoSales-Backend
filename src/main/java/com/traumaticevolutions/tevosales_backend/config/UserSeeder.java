package com.traumaticevolutions.tevosales_backend.config;

import com.traumaticevolutions.tevosales_backend.model.Role;
import com.traumaticevolutions.tevosales_backend.model.User;
import com.traumaticevolutions.tevosales_backend.repository.RoleRepository;
import com.traumaticevolutions.tevosales_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * Seeder de usuarios iniciales con distintos roles para pruebas.
 * Requiere que los roles ya existan en la base de datos.
 * 
 * @author Ángel Aragón
 */
@Component
@Order(2)
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (userRepository.findByUsername("admin").isEmpty()) {
            Optional<Role> adminRole = roleRepository.findByName("ROLE_ADMIN");
            Optional<Role> clienteRole = roleRepository.findByName("ROLE_CLIENTE");
            if (adminRole.isPresent()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setName("Administrador");
                admin.setEmail("admin@tevosales.com");
                admin.setNif("00000000T");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(new HashSet<>(List.of(adminRole.get(), clienteRole.get())));
                userRepository.save(admin);
            }
        }

        if (userRepository.findByUsername("HUCA").isEmpty()) {
            Optional<Role> entidadRole = roleRepository.findByName("ROLE_ENTIDAD");
            Optional<Role> clienteRole = roleRepository.findByName("ROLE_CLIENTE");
            if (entidadRole.isPresent()) {
                User entidad = new User();
                entidad.setUsername("HUCA");
                entidad.setName("Hospital Universitario Central de Asturias");
                entidad.setEmail("huca@sespa.es");
                entidad.setNif("Q3369023A");
                entidad.setPassword(passwordEncoder.encode("huca123"));
                entidad.setRoles(new HashSet<>(List.of(entidadRole.get(), clienteRole.get())));
                userRepository.save(entidad);
            }
        }
    }
}
