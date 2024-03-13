package com.cencoe.cencoe.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter { //OncePerRequestFilter, una clase que garantiza que este filtro se ejecute solo una vez por solicitud HTTP.
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtService jwtService;


    public JwtFilter(CustomUserDetailsService customUserDetailsService, JwtService jwtService) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userNumDoc;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userNumDoc = jwtService.getUserName(jwt); //ya que tenemos el jwt lo pasamos a un servicio que pueda decodificar el token y extraer informacion como el numero de documento
        if (userNumDoc != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(userNumDoc);
            if (jwtService.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }


}

/*HttpServletRequest representa la solicitud HTTP que se está procesando
HttpServletResponse representa la respuesta HTTP que se enviará
FilterChain es un objeto que proporciona un mecanismo para pasar la solicitud y la respuesta al siguiente filtro en la cadena.
 */

/*Este código verifica si la solicitud HTTP tiene una cabecera de autorización y si esa cabecera comienza con "Bearer".
 Si alguna de estas condiciones no se cumple, la solicitud se deja pasar al siguiente paso sin procesamiento adicional.
 */