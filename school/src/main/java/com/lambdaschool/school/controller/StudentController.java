package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Student;
import com.lambdaschool.school.service.StudentService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController
{
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integr", paramType = "query",
                    value = "Results page you want to retrieve (0..N)"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query",
                    value = "Number of records per page."),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query",
                    value = "Sorting criteria in the format: property(,asc|desc). " +
                            "Default sort order is ascending. " +
                            "Multiple sort criteria are supported.")})

    // Please note there is no way to add students to course yet!
    // GET localhost:2019/students/students
    @ApiOperation(value = "Get All Students", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Students Found", response = Student.class),
            @ApiResponse(code = 404, message = "Students Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/students", produces = {"application/json"})
    public ResponseEntity<?> listAllStudents(HttpServletRequest request, @PageableDefault(page = 0, size= 3, sort = "studname")
            Pageable pageable)
    {
        logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Student> myStudents = studentService.findAll(pageable);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }

    // GET localhost:2019/students/student/1
    @ApiOperation(value = "Get a Student by ID", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Found", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/Student/{StudentId}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentById(HttpServletRequest request,
            @PathVariable
                    Long StudentId)
    {
        logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        Student r = studentService.findStudentById(StudentId);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    // GET localhost:2019/students/student/namelike/John
    @ApiOperation(value = "Get a Student by Name", response = Student.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Student Found", response = Student.class),
            @ApiResponse(code = 404, message = "Student Not Found", response = ErrorDetail.class)})
    @GetMapping(value = "/student/namelike/{name}",
                produces = {"application/json"})
    public ResponseEntity<?> getStudentByNameContaining(HttpServletRequest request,
            @PathVariable String name)
    {
        logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Student> myStudents = studentService.findStudentByNameLike(name);
        return new ResponseEntity<>(myStudents, HttpStatus.OK);
    }


    // GET localhost:2019/students/student/1
    @ApiOperation(value = "Create a new Student", notes = "The newly created Student id will be sent in the location header", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Student Created", response = void.class),
            @ApiResponse(code = 500, message = "Student Creation Failed", response = ErrorDetail.class)})
    @PostMapping(value = "/Student",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewStudent(HttpServletRequest request, @Valid
                                           @RequestBody
                                                   Student newStudent) throws URISyntaxException
    {
        logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed. " + newStudent + " sent");
        newStudent = studentService.save(newStudent);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newStudentURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{Studentid}").buildAndExpand(newStudent.getStudid()).toUri();
        responseHeaders.setLocation(newStudentURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Edit a Student based on Id", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Student Edited", response = void.class),
            @ApiResponse(code = 500, message = "Student Editing Failed", response = ErrorDetail.class)})
    @PutMapping(value = "/Student/{Studentid}")
    public ResponseEntity<?> updateStudent(HttpServletRequest request,
            @RequestBody
                    Student updateStudent,
            @PathVariable
                    long Studentid)
    {
        logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed. " + updateStudent + " and " + Studentid + " sent");
        studentService.update(updateStudent, Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Delete a Student based on Id", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Student Deleted", response = void.class),
            @ApiResponse(code = 500, message = "Student Delete Failed", response = ErrorDetail.class)})
    @DeleteMapping("/Student/{Studentid}")
    public ResponseEntity<?> deleteStudentById(HttpServletRequest request,
            @PathVariable
                    long Studentid)
    {
        logger.warn(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed. " + " and " + Studentid + " sent");

        studentService.delete(Studentid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
