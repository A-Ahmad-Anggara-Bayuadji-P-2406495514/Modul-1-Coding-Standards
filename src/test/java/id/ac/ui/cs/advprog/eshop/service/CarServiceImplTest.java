package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarReadRepository;
import id.ac.ui.cs.advprog.eshop.repository.CarWriteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @Mock
    private CarReadRepository readRepository;

    @Mock
    private CarWriteRepository writeRepository;

    @InjectMocks
    private CarServiceImpl carService;

    private Car car;

    @BeforeEach
    void setUp() {
        car = new Car();
        car.setCarId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        car.setCarName("Toyota Supra");
        car.setCarColor("Red");
        car.setCarQuantity(1);
    }

    // Write operations tests

    @Test
    void testCreate() {
        when(writeRepository.create(car)).thenReturn(car);
        Car savedCar = carService.create(car);
        assertNotNull(savedCar);
        assertEquals(car.getCarId(), savedCar.getCarId());
        verify(writeRepository, times(1)).create(car); // Verifies Write ISP
    }

    @Test
    void testUpdate() {
        carService.update(car.getCarId(), car);
        verify(writeRepository, times(1)).update(car.getCarId(), car); // Verifies Write ISP
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById(car.getCarId());
        verify(writeRepository, times(1)).delete(car.getCarId()); // Verifies Write ISP
    }

    // Read Operation Tests

    @Test
    void testFindAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        Iterator<Car> iterator = carList.iterator();

        when(readRepository.findAll()).thenReturn(iterator);

        List<Car> allCars = carService.findAll();
        assertFalse(allCars.isEmpty());
        assertEquals(1, allCars.size());
        verify(readRepository, times(1)).findAll(); // Verifies Read ISP
    }

    @Test
    void testFindById() {
        when(readRepository.findById(car.getCarId())).thenReturn(car);
        Car foundCar = carService.findById(car.getCarId());
        assertNotNull(foundCar);
        assertEquals(car.getCarId(), foundCar.getCarId());
        verify(readRepository, times(1)).findById(car.getCarId()); // Verifies Read ISP
    }
}