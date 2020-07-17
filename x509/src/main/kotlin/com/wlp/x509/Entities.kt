package com.wlp.x509

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Account(@Id var id : Int = 1, var username : String = "",var password : String = "",var active : Boolean = false)