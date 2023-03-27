package com.lk.RailwayDepartment.Service;


import com.lk.RailwayDepartment.Entity.StationsDuration;
import com.lk.RailwayDepartment.Entity.Train;
import com.lk.RailwayDepartment.Entity.TrainHasClasses;
import com.lk.RailwayDepartment.Model.TrainListFilter;
import com.lk.RailwayDepartment.Repository.StationsDurationRepository;
import com.lk.RailwayDepartment.Repository.TrainHasClassesRepository;
import com.lk.RailwayDepartment.Repository.TrainRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrainServiceImp implements TrainService{

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private StationsDurationRepository stationsDurationRepository;

    @Autowired
    private TrainHasClassesRepository trainHasClassesRepository;

    @Override
    public void save(Train train) {
        train.setActive(true);
        trainRepository.save(train);
        train.getStationsDurations().forEach(e->{
            e.setTrain(train);
            stationsDurationRepository.save(e);
        });

        train.getTrainHasClasses().forEach(e->{
            e.setTrain(train);
            e.setActive(true);
            trainHasClassesRepository.save(e);
        });
    }

    @Override
    public void update(Train train) {
        Optional<Train> optionalTrain = trainRepository.findById(train.getId());
        if(optionalTrain.isPresent()){
            Train existTrain = optionalTrain.get();
            trainHasClassesRepository.deleteAll( existTrain.getTrainHasClasses());
            stationsDurationRepository.deleteAll(existTrain.getStationsDurations());
        }
        for (TrainHasClasses trainHasClass : train.getTrainHasClasses()) {
            trainHasClass.setId(null);
        }

        for (StationsDuration stationsDuration : train.getStationsDurations()) {
            stationsDuration.setId(null);
        }
        save(train);
    }

    @Override
    public void deactivate(Train train) {
        train.setActive(false);
        trainRepository.save(train);
    }

    @Override
    public void activate(Train train) {
        train.setActive(true);
        trainRepository.save(train);
    }

    @Override
    public Train findByName(String name) {
        return trainRepository.findByName(name);
    }

    @Override
    public List<Train> filter(TrainListFilter trainListFilter) {
        String stringQuery = "SELECT t FROM Train t WHERE t.active = :active";

        if (!trainListFilter.getName().isBlank()) {
            stringQuery += " AND t.name = :name";
        }

        if (trainListFilter.getDeparture()!=null) {
            stringQuery += " AND t.departure = :departure";
        }

        if (trainListFilter.getArrival()!=null) {
            stringQuery += " AND t.arrival = :arrival";
        }

        TypedQuery<Train> query = entityManager.createQuery(stringQuery, Train.class);

        query.setParameter("active", trainListFilter.isActive());

        if (!trainListFilter.getName().isBlank()) {
            query.setParameter("name", trainListFilter.getName());
        }

        if (trainListFilter.getDeparture()!=null) {
            query.setParameter("departure", trainListFilter.getDeparture());
        }

        if (trainListFilter.getArrival()!=null) {
            query.setParameter("arrival", trainListFilter.getArrival());
        }

        List<Train> trains=new ArrayList<>();

        try{
            if (query.getResultList() != null)
                trains = query.getResultList();
        }catch (Exception e){e.printStackTrace();}

        return trains;
    }

    @Override
    public List<Train> findAllByActive() {
        return trainRepository.findAllByActive();
    }
}
