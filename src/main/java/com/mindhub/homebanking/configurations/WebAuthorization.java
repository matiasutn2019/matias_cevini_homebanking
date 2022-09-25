package com.mindhub.homebanking.configurations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@EnableWebSecurity
@Configuration
public class WebAuthorization extends WebSecurityConfigurerAdapter {

    private static final String[] SWAGGER = {
            "/swagger-resources/**",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/api/docs",
            "/api/docs/**",
            "/v3/api-docs/**",
            "/api/docs/swagger-ui",
            "/swagger-ui.html"
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // turn off checking for CSRF tokens
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(SWAGGER)
                .permitAll()
                // index page
                .antMatchers("/web/index.html", "/web/js/index.js",
                        "/web/css/style.css", "/web/assets/**")
                .permitAll()
                // other sites
                .antMatchers(POST, "/api/clients").permitAll()//register client
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/web/**").hasAuthority("CLIENT")
                .antMatchers("/api/**").hasAuthority("CLIENT")
                .anyRequest()
                .authenticated()
                .and()
                //controller login
                .formLogin(login -> login
                        .loginPage("/api/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        // if login is successful, just clear the flags asking for authentication
                        .successHandler((req, res, auth) -> clearAuthenticationAttributes(req))
                        // if login fails, just send an authentication failure response
                        .failureHandler((req, res, exc) -> {
                            //res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                            ObjectMapper objectMapper = new ObjectMapper();
                            //ObjectMapper provides functionality for reading and writing JSON
                            res.setStatus(UNAUTHORIZED.value());
                            //Sets the status code for this response
                            Map<String, Object> data = new HashMap<>();
                            //An object that maps keys to values. A map cannot contain duplicate keys
                            data.put("exception", exc.getMessage());
                            //Returns a ServletOutputStream suitable for writing binary data in the response
                            res.getOutputStream()
                                    .println(objectMapper.writeValueAsString(data));
                        })
                )
                //controller logout
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/api/logout", "POST"))
                        //.logoutUrl("/api/logout")
                        .logoutSuccessUrl("/web/index.html")
                        // if logout is successful, just send a success response
                        // If this is specified, logoutSuccessUrl(String) is ignored
                        .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(OK))
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                )
                // invalid session id - maximum sessions allowed - redirect if session expired
                .sessionManagement(sm -> sm
                        .invalidSessionUrl("/web/index.html")
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                        .expiredUrl("/web/index.html")
                );

        //disabling frameOptions so h2-console can be accessed
        http.headers().frameOptions().disable();

        // if user is not authenticated, just send an authentication failure response
        http.exceptionHandling().authenticationEntryPoint((req, res, exc)
                -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}
