package com.lk.RailwayDepartment.Service;

import com.lk.RailwayDepartment.Entity.Train;
import com.lk.RailwayDepartment.Entity.TrainHasClasses;
import com.lk.RailwayDepartment.Entity.TrainSchedule;
import com.lk.RailwayDepartment.Entity.TrainSeatPriceAndAvailability;
import com.lk.RailwayDepartment.Model.TrainScheduleListFilter;
import com.lk.RailwayDepartment.Repository.TrainDepartureArrivalTimeRepository;
import com.lk.RailwayDepartment.Repository.TrainScheduleRepository;
import com.lk.RailwayDepartment.Repository.TrainSeatPriceAndAvailabilityRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainScheduleServiceImp implements TrainScheduleService{

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TrainScheduleRepository trainScheduleRepository;
    @Autowired
    private TrainDepartureArrivalTimeRepository trainDepartureArrivalTimeRepository;
    @Autowired
    private TrainSeatPriceAndAvailabilityRepository trainSeatPriceAndAvailabilityRepository;

    @Override
    public void save(TrainSchedule trainSchedule) {
        trainSchedule.setActive(true);
        trainScheduleRepository.save(trainSchedule);
        trainDepartureArrivalTimeRepository.saveAll(trainSchedule.getTrainDepartureArrivalTimeList().stream().peek(e-> e.setTrainSchedule(trainSchedule)).collect(Collectors.toList()));
        trainSeatPriceAndAvailabilityRepository.saveAll(trainSchedule.getTrainSeatPriceAndAvailabilities().stream().peek(e-> {
            e.setTrainSchedule(trainSchedule);
            if(e.getSeats()<=0){
            Optional<TrainHasClasses> any = trainSchedule.getTrain().getTrainHasClasses().stream().filter(s -> s.getTrainClass().getId() == e.getTrainClass().getId()).findAny();
                if(any.isPresent()){
                    e.setSeats(any.get().getSeats());
                }
            }
        }).collect(Collectors.toList()));
    }

    @Override
    public void saveAvailability(TrainSeatPriceAndAvailability trainSeatPriceAndAvailability) {
        trainSeatPriceAndAvailabilityRepository.save(trainSeatPriceAndAvailability);
    }

    @Override
    public void update(TrainSchedule trainSchedule) {
        Optional<TrainSchedule> OptionaltrainSc = trainScheduleRepository.findById(trainSchedule.getId());
        if(OptionaltrainSc.isPresent()){
            TrainSchedule trainSchedule1 = OptionaltrainSc.get();
            trainDepartureArrivalTimeRepository.deleteAll(trainSchedule1.getTrainDepartureArrivalTimeList());
            trainSeatPriceAndAvailabilityRepository.deleteAll(trainSchedule1.getTrainSeatPriceAndAvailabilities());
        }
        save(trainSchedule);
    }

    @Override
    public void activateOrDeactivate(TrainSchedule trainSchedule) {
        trainSchedule.setActive(!trainSchedule.isActive());
        trainScheduleRepository.save(trainSchedule);
    }

    @Override
    public List<TrainSchedule> filter(TrainScheduleListFilter trainScheduleListFilter) {
        String stringQuery = "";

        if((trainScheduleListFilter.getTrainClass()!=null || trainScheduleListFilter.getPassengers()>0)  && (trainScheduleListFilter.getDeparture()!=null || trainScheduleListFilter.getArrival()!=null)){
            stringQuery=(trainScheduleListFilter.getDeparture()!=null && trainScheduleListFilter.getArrival()!=null)?
                    "SELECT t FROM TrainSchedule t JOIN t.trainDepartureArrivalTimeList s1 JOIN t.trainDepartureArrivalTimeList s2 JOIN t.trainSeatPriceAndAvailabilities d WHERE t.active = :active"
            :"SELECT t FROM TrainSchedule t JOIN t.trainDepartureArrivalTimeList s JOIN t.trainSeatPriceAndAvailabilities d WHERE t.active = :active";
        }else if(trainScheduleListFilter.getDeparture()!=null || trainScheduleListFilter.getArrival()!=null){
            stringQuery = (trainScheduleListFilter.getDeparture()!=null && trainScheduleListFilter.getArrival()!=null)?
                    "SELECT t FROM TrainSchedule t JOIN t.trainDepartureArrivalTimeList s1 JOIN t.trainDepartureArrivalTimeList s2 WHERE t.active = :active"
            :"SELECT t FROM TrainSchedule t JOIN t.trainDepartureArrivalTimeList s WHERE t.active = :active";
        }else if((trainScheduleListFilter.getTrainClass()!=null || trainScheduleListFilter.getPassengers()>0)){
            stringQuery = "SELECT t FROM TrainSchedule t JOIN t.trainSeatPriceAndAvailabilities d WHERE t.active = :active";
        }else{
            stringQuery = "SELECT t FROM TrainSchedule t WHERE t.active = :active";
        }


        if(trainScheduleListFilter.getPassengers()>0){
            stringQuery += " AND d.availability >= :passenger";
        }

        if(trainScheduleListFilter.getTrainClass()!=null){
            stringQuery += " AND d.trainClass= :classes";
        }

        if(trainScheduleListFilter.getTrain()!=null){
            stringQuery += " AND t.train = :train";
        }

        if(trainScheduleListFilter.getFromDate()!=null){
            stringQuery += " AND t.date >= :fromDate";
        }

        if(trainScheduleListFilter.getToDate()!=null){
            stringQuery += " AND t.date <= :tomDate";
        }

        if(trainScheduleListFilter.getDeparture()!=null){
            String departure=stringQuery.contains("t.trainDepartureArrivalTimeList s1")?
            " AND s1.station = :departure"
             :" AND s.station = :departure";
            stringQuery +=departure;
        }

        if(trainScheduleListFilter.getArrival()!=null){
            String arrival=stringQuery.contains("t.trainDepartureArrivalTimeList s2")?
                    " AND s2.station = :arrival"
                    :" AND s.station = :arrival";
            stringQuery +=arrival;
        }

        TypedQuery<TrainSchedule> query = entityManager.createQuery(stringQuery, TrainSchedule.class);

        query.setParameter("active", trainScheduleListFilter.isActive());

        if(trainScheduleListFilter.getPassengers()>0){
            query.setParameter("passenger", trainScheduleListFilter.getPassengers());
        }

        if(trainScheduleListFilter.getTrainClass()!=null){
            query.setParameter("classes", trainScheduleListFilter.getTrainClass());
        }

        if(trainScheduleListFilter.getTrain()!=null){
            query.setParameter("train", trainScheduleListFilter.getTrain());
        }

        if(trainScheduleListFilter.getFromDate()!=null){
            query.setParameter("fromDate", trainScheduleListFilter.getFromDate());
        }

        if(trainScheduleListFilter.getToDate()!=null){
            query.setParameter("tomDate", trainScheduleListFilter.getToDate());
        }

        if(trainScheduleListFilter.getDeparture()!=null){
            query.setParameter("departure", trainScheduleListFilter.getDeparture());
        }

        if(trainScheduleListFilter.getArrival()!=null){
            query.setParameter("arrival", trainScheduleListFilter.getArrival());
        }

        List<TrainSchedule> trains=new ArrayList<>();
        try{
            if (query.getResultList() != null)
                trains = query.getResultList();
        }catch (Exception e){e.printStackTrace();}

        trains=trains.stream().peek(e->e.setValid(!e.getDate().before(new Date()))).collect(Collectors.toList());

        return trains;
    }

    @Override
    public TrainSchedule findById(long id) {
        return trainScheduleRepository.findById(id).isPresent()?trainScheduleRepository.findById(id).get():null;
    }
}
