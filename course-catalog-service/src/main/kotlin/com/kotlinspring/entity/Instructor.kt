
package com.kotlinspring.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "INSTRUCTORS")
data class Instructor(
    @Id
    var id: Int? ,
    val name: String,
    @OneToMany(
        mappedBy = "instructor",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    var course: List<Course> = mutableListOf()

)