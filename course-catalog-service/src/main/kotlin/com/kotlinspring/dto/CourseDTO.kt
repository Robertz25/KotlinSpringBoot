package com.kotlinspring.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CourseDTO(
    val id: Int?,
    @NotBlank(message = "CourseDTO.name must not be blank")
    val name: String,
    @NotBlank(message = "CourseDTO.category must not be blank")
    val category: String,
    @NotNull(message = "CourseDTO.instructorId must not be blank")
    val instructorId: Int? = null,
)