package com.fpt.pe.services;

import com.fpt.pe.controllers.CarController.CarRequest;
import com.fpt.pe.controllers.CarController.CarResponse;
import com.fpt.pe.controllers.CarController.CarUpdateRequest;
import com.fpt.pe.models.Car;
import com.fpt.pe.repositories.CarRepository;
import com.fpt.pe.repositories.CountryRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CountryRepository countryRepository;

    @Override
    public List<CarResponse> getAll() {
        return carRepository
            .findAll()
            .stream()
            .map(CarResponse::from)
            .toList();
    }

    @Override
    public CarResponse getById(int id) {

        var car = carRepository.findById(id);

        if (car.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return CarResponse.from(car.get());
    }

    @Override
    public void deleteById(Integer id) {
        if (!carRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car not found with id: " + id);
        }
        carRepository.deleteById(id);
    }

    @Override
    public CarResponse create(CarRequest request) {
        var country = countryRepository.findById(request.countryId())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));

        var car = Car.builder()
            .name(request.name())
            .price(request.price())
            .unitsInStock(request.stock())
            .country(country)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();

        var savedcar = carRepository.save(car);
        return CarResponse.from(savedcar);
    }

    @Override
    public CarResponse update(Integer id, CarRequest request) {
        var existingcar = carRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                           "car not found with id: " + id));

        var country = countryRepository.findById(request.countryId())
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));

        // Update the existing blind box
        existingcar.setName(request.name());
        existingcar.setUnitsInStock(request.stock());
        existingcar.setPrice(request.price());
        existingcar.setCountry(country);
        // Note: We don't update releaseDate as it should remain the original creation date

        var updatedcar = carRepository.save(existingcar);
        return CarResponse.from(updatedcar);
    }

    @Override
    public CarResponse updatePartial(Integer id, CarUpdateRequest request) {
        var existingcar = carRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                           "car not found with id: " + id));

        // Update only the provided fields
        if (request.name() != null) {
            existingcar.setName(request.name());
        }
        if (request.price() != null) {
            existingcar.setPrice(request.price());
        }
        if (request.stock() != null) {
            existingcar.setUnitsInStock(request.stock());
        }
        if (request.countryId() != null) {
            var country = countryRepository.findById(request.countryId())
                .orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Country not found"));
            existingcar.setCountry(country);
        }


        var updatedcar = carRepository.save(existingcar);
        updatedcar.setUpdatedAt(LocalDateTime.now());
        return CarResponse.from(updatedcar);
    }
}
