package com.example.demo.Car;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }


    @GetMapping("/cars")
    public String getCar(Model model){
        List<Car> carList = carService.getCar();
        model.addAttribute("cars", carList);
        return "list";
    }


    @GetMapping("/add")
    public String showCarForm(Model model){
        model.addAttribute("car", new Car());
        return "add-car";
    }

    @PostMapping("/save")
    public String saveCar(@ModelAttribute Car car){
        carService.saveCar(car);
        return "redirect:/cars";
    }
    @GetMapping("/delete/{id}")
    public String deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
        return "redirect:/cars";
    }

}
