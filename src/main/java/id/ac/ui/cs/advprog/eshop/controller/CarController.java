package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;

import id.ac.ui.cs.advprog.eshop.service.CarWriteService;
import id.ac.ui.cs.advprog.eshop.service.CarReadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/car")
class CarController {

    private final CarReadService readService;
    private final CarWriteService writeService;

    @Autowired
    CarController(CarReadService readService, CarWriteService writeService) {
        this.readService = readService;
        this.writeService = writeService;
    }

    @GetMapping("/createCar")
    public String createCarPage(Model model) {
        Car car = new Car();
        model.addAttribute("car", car);
        return "createCar";
    }

    @PostMapping("/createCar")
    public String createCarPost(@ModelAttribute Car car, Model model) {
        writeService.create(car);
        return "redirect:listCar";
    }

    @GetMapping("/listCar")
    public String carListPage(Model model) {
        List<Car> allCars = readService.findAll();
        model.addAttribute("cars", allCars);
        return "carList";
    }

    @GetMapping("/editCar/{carId}")
    public String editCarPage(@PathVariable String carId, Model model) {
        Car car = readService.findById(carId);
        model.addAttribute("car", car);
        return "editCar";
    }

    @PostMapping("/editCar")
    public String editCarPost(@ModelAttribute Car car, Model model) {
        System.out.println(car.getCarId());
        writeService.update(car.getCarId(), car);
        return "redirect:listCar";
    }

    @PostMapping("/deleteCar")
    public String deleteCar(@RequestParam("carId") String carId) {
        writeService.deleteCarById(carId);
        return "redirect:listCar";
    }
}