
package com.kotlinspring.dto

import javax.persistence.*
import javax.validation.constraints.NotBlank


data class InstructorDTO (
    val id: Int?,
    @NotBlank(message = "InstructorDTO.name must not be blank")
    val name: String,
)