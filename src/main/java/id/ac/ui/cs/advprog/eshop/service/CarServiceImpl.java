package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarReadRepository;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import id.ac.ui.cs.advprog.eshop.repository.CarWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CarServiceImpl implements CarReadService, CarWriteService {

    private final CarReadRepository readRepository;
    private final CarWriteRepository writeRepository;

    @Autowired
    public CarServiceImpl(CarReadRepository readRepository, CarWriteRepository writeRepository) {
        this.readRepository = readRepository;
        this.writeRepository = writeRepository;
    }

    @Override
    public Car create(Car car) {
        // TODO Auto-generated method stub
        writeRepository.create(car);
        return car;
    }

    @Override
    public List<Car> findAll() {
        Iterator<Car> carIterator = readRepository.findAll();
        List<Car> allCar = new ArrayList<>();
        carIterator.forEachRemaining(allCar::add);
        return allCar;
    }

    @Override
    public Car findById(String carId) {
        Car car = readRepository.findById(carId);
        return car;
    }

    @Override
    public void update(String carId, Car car)  {
        // TODO Auto-generated method stub
        writeRepository.update(carId, car);
    }

    @Override
    public void deleteCarById(String carId) {
        // TODO Auto-generated method stub
        writeRepository.delete(carId);
    }
}