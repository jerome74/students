package com.wlp.clientx509

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.net.URISyntaxException
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class RestTemplateController {

    @Autowired
    lateinit var restTemplate: RestTemplate

    @GetMapping(path = ["/template"], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(RestClientException::class, URISyntaxException::class)
    fun getTemplateTest(): String? {
        return restTemplate.getForObject(URI("https://localhost:8084/hi"), String::class.java)
    }
}

@Controller
class MyErrorController : ErrorController {
    override fun getErrorPath(): String {
        return "/error"
    }

    @RequestMapping("/error")
    fun handleError(request : HttpServletRequest){
        val status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)
        println(status.toString())
    }

}

