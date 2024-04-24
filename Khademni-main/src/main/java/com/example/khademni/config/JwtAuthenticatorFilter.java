package com.example.khademni.config;

import com.example.khademni.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
@RequiredArgsConstructor
public class JwtAuthenticatorFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
//    private final UserDetailsServiceImp userDetailService;
    private final UserDetailsService userDetailsService;
    private String jwt;
    private String userEmail;
    private String authHeader;
    private UserDetails userDetails;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);//en passe au prochain filtre (log, request parsing ...)
            return;
        }
        jwt = authHeader.substring(7); // compter a partir du bearer + " "
        userEmail = jwtService.extractUserName(jwt);
        //responsable de la gestion de la securite dans le thread
        //l'objet Authentication est responsable du status de l'authentification du l'utilisateur
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            updateSecurityContext(request);
        }
        filterChain.doFilter(request,response);
    }
    private void  updateSecurityContext(HttpServletRequest request){
        if(jwtService.isTokenIsValid(jwt, userDetails)){
            //Token permet de garder les informations de l'utilisateur dans le security context
            //pour passer au  Security's authentication manager pour la valider
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            //permet de creer une instance pour sauvgarder des details sur les web-based authentications
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
}}
