package com.lk.RailwayDepartment.Service;

import com.lk.RailwayDepartment.Entity.Station;
import com.lk.RailwayDepartment.Repository.StationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationServiceImpl implements StationService{


    private StationRepository stationRepository;

    public StationServiceImpl(StationRepository stationRepository) {
        this.stationRepository = stationRepository;
    }

    @Override
    public List<Station> findAllByActive() {
        return stationRepository.findAllByActive();
    }

    @Override
    public Station findById(long id) {
        return stationRepository.findById(id).isPresent()?stationRepository.findById(id).get():null;
    }
}
