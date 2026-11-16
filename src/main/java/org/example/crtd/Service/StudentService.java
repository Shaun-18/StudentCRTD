package org.example.crtd.Service;

import org.example.crtd.Entity.Student;
import org.example.crtd.Repository.StudentRepository;
import org.example.crtd.DTO.StudentRequest;
import org.example.crtd.DTO.StudentResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @Transactional
    public StudentResponse createStudent(StudentRequest request) {

        if (studentRepository.existsByEmail(request.email())) {
            throw new DuplicateResourceException(
                    "A student with this email already exists"
            );
        }

        Student student = new Student();

        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setEmail(request.email());

        Student savedStudent = studentRepository.save(student);

        return mapToResponse(savedStudent);
    }

    // READ ALL
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // READ ONE
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long id) {

        Student student = findStudentOrThrow(id);

        return mapToResponse(student);
    }

    // UPDATE
    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest request) {

        Student student = findStudentOrThrow(id);

        boolean emailUsedByAnotherStudent =
                studentRepository.existsByEmailAndIdNot(
                        request.email(),
                        id
                );

        if (emailUsedByAnotherStudent) {
            throw new DuplicateResourceException(
                    "Another student is already using this email"
            );
        }

        student.setFirstName(request.firstName());
        student.setLastName(request.lastName());
        student.setEmail(request.email());

        Student updatedStudent = studentRepository.save(student);

        return mapToResponse(updatedStudent);
    }

    // DELETE
    @Transactional
    public void deleteStudent(Long id) {

        Student student = findStudentOrThrow(id);

        studentRepository.delete(student);
    }

    private Student findStudentOrThrow(Long id) {

        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Student not found with ID: " + id
                ));
    }

    private StudentResponse mapToResponse(Student student) {

        return new StudentResponse(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getEmail()
        );
    }
}

