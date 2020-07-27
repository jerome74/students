package com.wlp.clientx509

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.util.ResourceUtils
import javax.annotation.PostConstruct

@SpringBootApplication
class ClientX509Application

fun main(args: Array<String>) {


	System.setProperty("javax.net.ssl.keyStore", ResourceUtils.getFile("classpath:keystore.jks").getPath())
	System.setProperty("javax.net.ssl.keyStorePassword", "localhost");
	System.setProperty("javax.net.ssl.trustStore", ResourceUtils.getFile("classpath:keystore.jks").getPath());
	System.setProperty("javax.net.ssl.trustStorePassword", "localhost");

	runApplication<ClientX509Application>(*args)
}
