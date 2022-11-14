package com.kotlinspring.service

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.exception.CourseNotFoundException
import com.kotlinspring.exception.InstructorNotValidException
import com.kotlinspring.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.DeleteMapping

@Service
class CourseService(
    val courseRepository: CourseRepository,
    val instructorService: InstructorService) {

    companion object : KLogging()

    fun addCourse(courseDTO: CourseDTO) {

        val instructorOptional =instructorService.findByInstructorId(courseDTO.instructorId!!)

        if (!instructorOptional.isPresent)
            throw InstructorNotValidException("Instructor Not Valid for the Id : ${courseDTO.instructorId}")

        val courseEntity = courseDTO.let {
            Course(null, it.name, it.category, instructorOptional.get())
        }

        courseRepository.save(courseEntity)

      logger.info("Saved course is: $courseEntity ")

        courseEntity.let {
            CourseDTO(it.id, it.name, it.category, it.instructor!!.id)
        }
    }

    fun retrieveAllCourses(courseName: String?): List<CourseDTO> {

        val courses = courseName?.let {
            courseRepository.findCoursesByName(courseName)
        } ?: courseRepository.findAll()

        return courses
            .map {
                CourseDTO(it.id, it.name, it.category)
            }
    }

    fun updateCourse(courseId: Int, courseDTO: CourseDTO): CourseDTO {

        val exitingCourse = courseRepository.findById(courseId)

        return if (exitingCourse.isPresent) {
            exitingCourse.get()
                .let {
                    it.name = courseDTO.name
                    it.category = courseDTO.category
                    courseRepository.save(it)
                    CourseDTO(it.id, it.name, it.category)
                }
        } else {
            throw CourseNotFoundException("No course found for the passed in Id : $courseId")
        }
    }
    @DeleteMapping
    fun deleteCourse(courseId: Int) {

        val exitingCourse = courseRepository.findById(courseId)

         if (exitingCourse.isPresent) {
            exitingCourse.get()
                .let {
                    courseRepository.delete(it)
                }
        } else {
             throw CourseNotFoundException("No course found for the passed in Id : $courseId")

         }
    }
}
