package com.example.demo.Car;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("det/{id}")
    public String detailsCar(@PathVariable Long id, Model model){
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "details";
    }

    @GetMapping("/add")
    public String showAddForm(Model model){
        model.addAttribute("car", new Car());
        return "add-car";
    }

    @PostMapping("/save")
    public String saveCar(@ModelAttribute Car car, @RequestParam("imageFile") MultipartFile imgFile){
        if (!imgFile.isEmpty()){
            try {
                String nameFile = UUID.randomUUID().toString() + "_" + imgFile.getOriginalFilename();

                Path pathFile = Paths.get("src/main/resources/static/img/" + nameFile);

                Files.write(pathFile, imgFile.getBytes());

                car.setImg("/img/" + nameFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        carService.saveCar(car);
        return "redirect:/cars";
    }

    @GetMapping("delete/{id}")
    public String deleteCar(@PathVariable Long id){
        carService.deleteCarById(id);
        return "redirect:/cars";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model){
        Car car = carService.getCarById(id);
        model.addAttribute("car", car);
        return "update";
    }

    @PostMapping("/update/{id}")
    public String updateCar(@PathVariable Long id,
                            @RequestParam("newName") String newName,
                            @RequestParam("newPhoto") MultipartFile newPhoto){

        Car carToUpdate = carService.getCarById(id);
        carToUpdate.setName(newName);

        if (!newPhoto.isEmpty()){
            try {
                String nameFile = UUID.randomUUID().toString() + "_" + newPhoto.getOriginalFilename();
                Path newPath = Paths.get("src/main/resources/static/img/" + nameFile);
                Files.write(newPath, newPhoto.getBytes());
                carToUpdate.setImg("/img/" + nameFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        carService.saveCar(carToUpdate);
        return "redirect:/cars";
    }

}

