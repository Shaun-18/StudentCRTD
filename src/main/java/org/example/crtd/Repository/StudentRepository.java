package org.example.crtd.Repository;
import org.example.crtd.Entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository  extends JpaRepository<Student, Long> {


        boolean existsByEmail(String email);

        boolean existsByEmailAndIdNot(String email, Long id);

        Optional<Student> findByEmail(String email);
}


