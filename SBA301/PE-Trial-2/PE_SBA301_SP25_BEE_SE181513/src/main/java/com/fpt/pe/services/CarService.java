package com.fpt.pe.services;

import com.fpt.pe.controllers.CarController.CarRequest;
import com.fpt.pe.controllers.CarController.CarResponse;
import com.fpt.pe.controllers.CarController.CarUpdateRequest;
import java.util.List;

public interface CarService {

    List<CarResponse> getAll();

    CarResponse getById(int id);

    void deleteById(Integer id);

    CarResponse create(CarRequest request);

    CarResponse update(Integer id, CarRequest request);

    CarResponse updatePartial(Integer id, CarUpdateRequest request);

}
