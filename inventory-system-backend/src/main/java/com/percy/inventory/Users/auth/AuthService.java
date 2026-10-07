package com.percy.inventory.Users.auth;

import com.percy.inventory.Users.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.username(),
                                request.password()
                        )
                );

        Users user =
                (Users) authentication.getPrincipal();

        String token =
                jwtService.generateToken(user);

        Long warehouseId =
                user.getWarehouse() != null
                        ? user.getWarehouse().getWarehouseId()
                        : null;

        String warehouseName =
                user.getWarehouse() != null
                        ? user.getWarehouse().getName()
                        : null;

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getAccountType(),
                user.getRole(),
                warehouseId,
                warehouseName
        );
    }
}