package com.wlp.student

import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Student(@Id var id : Int = 1, var name : String = "")