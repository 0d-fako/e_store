package org.estore.estore.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.estore.estore.security.dto.request.AuthRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static java.time.Instant.now;

@Component
@AllArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        // 1. Obtain Auth credentials
        InputStream requestStream = request.getInputStream();
        AuthRequest authRequest = mapper.readValue(requestStream, AuthRequest.class);

        //2. send credentials to the authentication
        Authentication authentication = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                authRequest.getPassword());

        Authentication authResult = authenticationManager.authenticate(authentication);

        if(!authResult.isAuthenticated()) {
            throw new BadCredentialsException("Invalid credentials");
        }

        // 3. generate access token if authenticated
        String jwt = JWT.create()
                .withSubject(authRequest.getUsername())
                .withIssuer("e-store")
                .withIssuedAt(now())
                .withExpiresAt(now().plusSeconds(86_400))
                .sign(Algorithm.HMAC512("secret".getBytes()));

        Map<String, String> authResponse = new HashMap<>();

        authResponse.put("access_token", jwt);
        response.getOutputStream().write(mapper.writeValueAsString(authResponse).getBytes());
        response.flushBuffer();
        filterChain.doFilter(request, response);
    }




}
