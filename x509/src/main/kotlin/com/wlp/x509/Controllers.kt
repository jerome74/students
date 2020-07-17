package com.wlp.x509

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal
import java.util.*

@RestController
class GreetingsRestController{

    @GetMapping( path = ["/hi"] , produces= [MediaType.APPLICATION_JSON_VALUE])
    fun greetings(p : Principal): Map<String,String> {
        return Collections.singletonMap("content", p.name)
    }

}