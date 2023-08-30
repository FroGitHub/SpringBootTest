package com.example.demo.Car;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getCar(){
        return carRepository.findAll();
    }
    public void saveCar(Car car) {
        carRepository.save(car);
    }

    public void deleteCar(Long id){
        carRepository.deleteById(id);
    }

}
