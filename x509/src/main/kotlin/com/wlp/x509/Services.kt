package com.wlp.x509

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class AccountUserDetailsService : UserDetailsService {

    @Autowired
    lateinit var repository : AccountRepository


    override fun loadUserByUsername(username: String?): UserDetails
    {
        return repository.findByUsername(username!!).map { User(it.username
                                                        , it.password
                                                        , it.active
                                                        , it.active
                                                        , it.active
                                                        , it.active
                                                        , AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER"))  }
                .orElseThrow { UsernameNotFoundException("couldn't find $username!") }
    }
}