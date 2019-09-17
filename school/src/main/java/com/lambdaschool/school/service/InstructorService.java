package com.lambdaschool.school.service;

import com.lambdaschool.school.model.Instructor;

public interface InstructorService
{
    Instructor save(Instructor instructor);

    Instructor update(Instructor instructor, long id);

    void delete(long id);
}
