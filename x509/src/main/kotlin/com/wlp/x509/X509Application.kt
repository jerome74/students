package com.wlp.x509

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.persistence.Entity

@SpringBootApplication
class X509Application

fun main(args : Array<String>) {
    runApplication<X509Application>(*args)

}



