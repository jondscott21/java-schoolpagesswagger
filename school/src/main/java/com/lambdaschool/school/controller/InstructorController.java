package com.lambdaschool.school.controller;

import com.lambdaschool.school.model.ErrorDetail;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.service.InstructorService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/instructor")
public class InstructorController
{
    @Autowired
    private InstructorService instructorService;

    @ApiOperation(value = "Create a new Instructor", notes = "The newly created Instructor id will be sent in the location header", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Instructor Created", response = void.class),
            @ApiResponse(code = 500, message = "Instructor Creation Failed", response = ErrorDetail.class)})
    @PostMapping(value = "", consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<?> addNewInstructor(HttpServletRequest request, @Valid @RequestBody Instructor newInstructor) throws URISyntaxException
    {
        newInstructor = instructorService.save(newInstructor);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newInstructorURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{instructid}").buildAndExpand(newInstructor.getInstructid()).toUri();
        responseHeaders.setLocation(newInstructorURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Edit a Instructor based on Id", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Instructor Edited", response = void.class),
            @ApiResponse(code = 500, message = "Instructor Editing Failed", response = ErrorDetail.class)})
    @PutMapping(value = "/Student/{instructid}")
    public ResponseEntity<?> updateStudent(HttpServletRequest request,
                                           @RequestBody
                                                   Instructor updateInstructor,
                                           @PathVariable
                                                   long instructid)
    {
        instructorService.update(updateInstructor, instructid);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @ApiOperation(value = "Delete a Instructor based on Id", response = void.class)
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Instructor Deleted", response = void.class),
            @ApiResponse(code = 500, message = "Instructor Delete Failed", response = ErrorDetail.class)})
    @DeleteMapping("/instructor/{instructid}")
    public ResponseEntity<?> deleteStudentById(HttpServletRequest request,
                                               @PathVariable
                                                       long instructid)
    {

        instructorService.delete(instructid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
