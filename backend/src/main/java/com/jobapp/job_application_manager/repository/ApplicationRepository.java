package com.jobapp.job_application_manager.repository;
import com.jobapp.job_application_manager.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long>{
}
