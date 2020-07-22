package com.wlp.student

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/students")
class RestStudentController
{
    @Autowired
    lateinit var studentRepository: StudentRepository


    @GetMapping(path = ["/login/{login}"] , produces= [MediaType.APPLICATION_JSON_VALUE] )
    fun login(@RequestBody credential : UserAndPasswordAuthenticationRequest) {
    }

    @GetMapping(path = ["{studentId}"] , produces= [MediaType.APPLICATION_JSON_VALUE] )
    fun getStudent(@PathVariable studentId : Int) : Student {
        return studentRepository.getOne(studentId)
    }

    @GetMapping(produces= [MediaType.APPLICATION_JSON_VALUE] )
    fun getAllStudents() : List<Student> {
        return studentRepository.findAll()
    }

    @DeleteMapping(path = ["{studentId}"] , produces= [MediaType.APPLICATION_JSON_VALUE] )
    fun deleteStudent(@PathVariable studentId : Int) {
        return studentRepository.delete(Student(studentId))
    }

    @PostMapping(produces= [MediaType.APPLICATION_JSON_VALUE] )
    fun registerStudent(@RequestBody student : Student) : Student {
        return studentRepository.save(student)
    }

    @PutMapping(path = ["{studentId}"],produces= [MediaType.APPLICATION_JSON_VALUE] )
    fun updateStudent(@PathVariable studentId : Int, @RequestBody student : Student) : Student {
        val originalStudent : Student = studentRepository.getOne(studentId)
        originalStudent.name = student.name
        return studentRepository.save(originalStudent)
    }
}

