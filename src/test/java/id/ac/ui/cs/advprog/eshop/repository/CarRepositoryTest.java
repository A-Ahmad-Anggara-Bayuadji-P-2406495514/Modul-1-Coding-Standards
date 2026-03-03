package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {

    private CarRepository carRepository;

    @BeforeEach
    void setUp() {
        carRepository = new CarRepository();
    }

    @Test
    void testCreateAndFindAll() {
        Car car = new Car();
        car.setCarName("Toyota Supra");
        car.setCarColor("Red");
        car.setCarQuantity(1);

        carRepository.create(car);
        Iterator<Car> carIterator = carRepository.findAll();

        assertTrue(carIterator.hasNext());
        Car savedCar = carIterator.next();
        assertNotNull(savedCar.getCarId());
        assertEquals("Toyota Supra", savedCar.getCarName());
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        car.setCarId("already-has-id");
        carRepository.create(car);

        assertEquals("already-has-id", car.getCarId());
    }

    @Test
    void testFindByIdSuccess() {
        Car car = new Car();
        car.setCarId("found-me");
        car.setCarName("Tesla Model S");
        carRepository.create(car);

        Car foundCar = carRepository.findById("found-me");

        assertNotNull(foundCar);
        assertEquals("found-me", foundCar.getCarId());
        assertEquals("Tesla Model S", foundCar.getCarName());
    }

    @Test
    void testFindByIdIfIdDoesNotExist() {
        Car foundCar = carRepository.findById("non-existent-id");
        assertNull(foundCar);
    }

    @Test
    void testFindByIdWhenListIsNotEmptyButIdNotFound() {
        Car car = new Car();
        car.setCarId("existing-id");
        carRepository.create(car);

        Car foundCar = carRepository.findById("non-existent-id");

        assertNull(foundCar);
    }

    @Test
    void testUpdateIfIdDoesNotExist() {
        Car updateInfo = new Car();
        Car result = carRepository.update("non-existent-id", updateInfo);
        assertNull(result);
    }

    @Test
    void testUpdateNotFound() {
        Car updateInfo = new Car();
        updateInfo.setCarName("New Name");

        Car result = carRepository.update("invalid-id", updateInfo);
        assertNull(result);
    }

    @Test
    void testUpdateSuccess() {
        Car car = new Car();
        car.setCarId("1");
        car.setCarName("Old Name");
        carRepository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("New Name");
        updatedCar.setCarColor("Green");
        updatedCar.setCarQuantity(10);

        Car result = carRepository.update("1", updatedCar);

        assertNotNull(result);
        assertEquals("New Name", result.getCarName());
        assertEquals("Green", result.getCarColor());
        assertEquals(10, result.getCarQuantity());
    }

    @Test
    void testUpdateWhenListIsNotEmptyButIdNotFound() {
        Car car = new Car();
        car.setCarId("existing-id");
        carRepository.create(car);

        Car updateInfo = new Car();
        updateInfo.setCarName("New Name");
        Car result = carRepository.update("non-existent-id", updateInfo);

        assertNull(result);
    }

    @Test
    void testDelete() {
        Car car = new Car();
        car.setCarId("delete-me");
        carRepository.create(car);

        carRepository.delete("delete-me");

        Car foundCar = carRepository.findById("delete-me");
        assertNull(foundCar);
    }



}