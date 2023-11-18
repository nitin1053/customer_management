package com.example.emp_system_ip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.emp_system_ip.entity.Employee;

public interface EmpRepository extends JpaRepository<Employee, Integer> {

}

