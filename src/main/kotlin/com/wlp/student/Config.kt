package com.wlp.student

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.stream.Collectors

@Configuration
@EnableWebSecurity
class ConfigurationApi : WebSecurityConfigurerAdapter() {




    override fun configure(http : HttpSecurity){

        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(JwtUserAndPasswordAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority(UserPermissions.INDRA_READ.permission,UserPermissions.BNL_READ.permission)
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAuthority(UserPermissions.BNL_DELETE.permission)
                .antMatchers(HttpMethod.PUT, "/api/**").hasAuthority(UserPermissions.BNL_WRITE.permission)
                .antMatchers(HttpMethod.POST, "/api/**").hasAuthority(UserPermissions.BNL_WRITE.permission)
                .antMatchers( "/api/**").hasAnyRole(UserRole.ADMIN.name)
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic()
                //.loginPage("/login")
                //.defaultSuccessUrl("/index")
                //.permitAll()
    }

    @Bean
    fun passwordEncoder() : PasswordEncoder
    {
        return BCryptPasswordEncoder(10)
    }


    @Bean
    override fun userDetailsService(): UserDetailsService
    {
        val crypto = BCryptPasswordEncoder(10)

        val admin = org.springframework.security.core.userdetails.User.builder()
                .username("admin")
                .password( crypto.encode("password"))
                //.roles(UserRole.ADMIN.name).build()
                .authorities(UserRole.ADMIN.getGrantedAuthority()).build()

        val valentina = org.springframework.security.core.userdetails.User.builder()
                .username("valentina")
                .password( crypto.encode("password2"))
                //.roles(UserRole.BNL.name).build()
                .authorities(UserRole.BNL.getGrantedAuthority()).build()

        val walter = org.springframework.security.core.userdetails.User.builder()
                .username("walter")
                .password( crypto.encode("password3"))
                //.roles(UserRole.INDRA.name).build()
                .authorities(UserRole.INDRA.getGrantedAuthority()).build()

        return InMemoryUserDetailsManager(admin,walter,valentina)

    }
}

enum class UserRole(val permission : Set<UserPermissions>)
{
    ADMIN(setOf(UserPermissions.INDRA_READ,UserPermissions.INDRA_WRITE,UserPermissions.BNL_READ,UserPermissions.BNL_WRITE,UserPermissions.BNL_DELETE)),
    INDRA(setOf(UserPermissions.INDRA_READ,UserPermissions.INDRA_WRITE)),
    BNL(setOf(UserPermissions.BNL_READ,UserPermissions.BNL_WRITE,UserPermissions.BNL_DELETE));

    fun getGrantedAuthority(): Set<SimpleGrantedAuthority> {
        var permissions = permission.stream().map { SimpleGrantedAuthority(it.permission) }.collect(Collectors.toSet())
        permissions.add(SimpleGrantedAuthority("ROLE_" + this.name))
        return permissions
    }
}

enum class UserPermissions(val permission : String)
{
    INDRA("indra"),
    BNL("bnl"),
    INDRA_READ("indra:read"),
    INDRA_WRITE("indra:write"),
    BNL_READ("bnl:read"),
    BNL_WRITE("bnl:write"),
    BNL_DELETE("bnl:delete");


}