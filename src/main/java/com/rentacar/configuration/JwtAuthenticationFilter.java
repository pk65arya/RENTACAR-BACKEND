package com.rentacar.configuration;

import com.rentacar.services.jwt.UserService;
import com.rentacar.util.Jwtutil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
 @Autowired
    Jwtutil jwtutil;
 @Autowired
    UserService userService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
final String authHeader=request.getHeader("Authorization");
final String jwt;
final String userEmail;

if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader,"Bearer")){
    filterChain.doFilter(request,response);
    return;
}
jwt=authHeader.substring(7);
userEmail=jwtutil.extractuserName(jwt);
if (StringUtils.isEmpty(userEmail)
    &&SecurityContextHolder.getContext().getAuthentication()==null){
    UserDetails userDetails=userService.userDetailsService().loadUserByUsername(userEmail);
    if (jwtutil.isTokenValid(jwt,userDetails)){
        SecurityContext context=SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
    }
    }
filterChain.doFilter(request,response);
    }
}
