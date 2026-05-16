package com.jobapp.job_application_manager.repository;
import com.jobapp.job_application_manager.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ApplicationRepository extends JpaRepository<Application, Long>{
    List<Application> findByUser(User user);
}
