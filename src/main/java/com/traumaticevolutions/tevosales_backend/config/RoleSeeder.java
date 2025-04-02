package com.traumaticevolutions.tevosales_backend.config;

import com.traumaticevolutions.tevosales_backend.model.Role;
import com.traumaticevolutions.tevosales_backend.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Seeder que inserta los roles iniciales en la base de datos al arrancar la aplicación.
 * Roles: ADMIN, CLIENTE, ENTIDAD
 * @author Ángel Aragón
 */

@Component
@RequiredArgsConstructor
public class RoleSeeder implements CommandLineRunner {

    private final RoleService roleService;

    @Override
    public void run(String... args) {
        List<String> roleNames = List.of("ADMIN", "CLIENTE", "ENTIDAD");

        roleNames.forEach(roleName -> {
            roleService.findByName(roleName).orElseGet(() -> {
                Role role = new Role();
                role.setName(roleName);
                return roleService.saveRole(role);
            });
        });
    }
}
