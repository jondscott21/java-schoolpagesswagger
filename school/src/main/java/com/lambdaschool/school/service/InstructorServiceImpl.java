package com.lambdaschool.school.service;

import com.lambdaschool.school.exceptions.ResourceNotFoundException;
import com.lambdaschool.school.model.Course;
import com.lambdaschool.school.model.Instructor;
import com.lambdaschool.school.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "instructorService")
public class InstructorServiceImpl implements InstructorService
{
    @Autowired
    private InstructorRepository instructrepos;

    @Override
    public Instructor save(Instructor instructor)
    {
        return instructrepos.save(instructor);
    }

    @Override
    public Instructor update(Instructor instructor, long id)
    {
        Instructor currentInstructor = instructrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
        if (instructor.getInstructname() != null)
        {
            currentInstructor.setInstructname(instructor.getInstructname());
        }
        if (instructor.getCourses().size() > 0)
        {
            for(Course c : instructor.getCourses())
            {
                currentInstructor.getCourses().add(new Course(c.getCoursename(), currentInstructor));
            }
        }
        return instructrepos.save(currentInstructor);
    }

    @Override
    public void delete(long id)
    {
        instructrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
        instructrepos.deleteById(id);
    }
}
