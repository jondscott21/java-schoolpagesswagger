package com.lambdaschool.school.handlers;


import com.lambdaschool.school.controller.CourseController;
import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.ErrorDetail;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());

        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }



    @ExceptionHandler({Exception.class})
    protected ResponseEntity<Object> handleAllExceptions(Exception ex, HttpServletRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setTitle("Exception");
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass()
                .getName());
        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());

        errorDetail.setTitle(ex.getPropertyName());
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setDeveloperMessage(request.getDescription(true));

        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
    {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());

        errorDetail.setTitle(ex.getRequestURL());
        errorDetail.setDetail(request.getDescription(true));
        errorDetail.setDeveloperMessage("Rest Handler Not Found (check for valid URI)");

        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
    }




}
//package com.lambdaschool.school.handlers;
//
//import com.lambdaschool.school.exceptions.ResourceNotFoundException;
//import com.lambdaschool.school.model.ErrorDetail;
//import org.springframework.beans.TypeMismatchException;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
////import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.NoHandlerFoundException;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Date;
//
//@ControllerAdvice
//public class RestExceptionHandler extends ResponseEntityExceptionHandler
//{
//    @ExceptionHandler({ResourceNotFoundException.class})
//    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request)
//    {
//        ErrorDetail errorDetail = new ErrorDetail();
//
//        errorDetail.setTimestamp(new Date().getTime());
//        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
//
//        errorDetail.setTitle("Resource Not Found");
//        errorDetail.setDetail(rnfe.getMessage());
//        errorDetail.setDeveloperMessage(rnfe.getClass().getName());
//
//        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
//    {
//        ErrorDetail errorDetail = new ErrorDetail();
//        errorDetail.setTimestamp(new Date().getTime());
//        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
//
//        errorDetail.setTitle(ex.getRequestURL());
//        errorDetail.setDetail(request.getDescription(true));
//        errorDetail.setDeveloperMessage("Rest Handler Not Found (check for valid URI)");
//
//        return new ResponseEntity<>(errorDetail, null, HttpStatus.NOT_FOUND);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
//    {
//        ErrorDetail errorDetail = new ErrorDetail();
//        errorDetail.setTimestamp(new Date().getTime());
//        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
//
//        errorDetail.setTitle(ex.getPropertyName());
//        errorDetail.setDetail(ex.getMessage());
//        errorDetail.setDeveloperMessage(request.getDescription(true));
//
//        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler({Exception.class})
//    protected ResponseEntity<Object> handleAllExceptions(Exception ex, HttpServletRequest request)
//    {
//        ErrorDetail errorDetail = new ErrorDetail();
//        errorDetail.setTimestamp(new Date().getTime());
//        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
//        errorDetail.setTitle("Exception");
//        errorDetail.setDetail(ex.getMessage());
//        errorDetail.setDeveloperMessage(ex.getClass()
//                .getName());
//        return new ResponseEntity<>(errorDetail, null, HttpStatus.BAD_REQUEST);
//    }
//}
