package com.example.demo.Car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    void deleteById(Long id);
    Car getCarById(Long id);
}
