package com.wlp.student

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class UserAndPasswordAuthenticationRequest() {
    lateinit var username: String
    lateinit var password: String


}

class JwtUserAndPasswordAuthenticationFilter(val authent: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {

        val authenticationRequest = ObjectMapper().readValue<UserAndPasswordAuthenticationRequest>(request!!.inputStream, UserAndPasswordAuthenticationRequest::class.java)
        val authentication = UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)

        val auth = authent.authenticate(authentication)

        return auth
    }


    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {


        val cal = Calendar.getInstance()
        cal.add(Calendar.MINUTE, 30)
        val to30min = cal.time

        val charset = Charsets.UTF_8
        val token = "pneumonoultramicroscopicsilicovolcanoconiosis".toByteArray(charset)

        val jwttoken = Jwts.builder()
                .setSubject(authResult!!.name)
                .claim("authorities", authResult!!.authorities)
                .setIssuedAt(Date())
                .setExpiration(to30min)
                .signWith(Keys.hmacShaKeyFor(token))
                .compact()

        response!!.addHeader("Authentication", "Bearer $jwttoken")

    }

}