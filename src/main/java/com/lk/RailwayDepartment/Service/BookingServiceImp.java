package com.lk.RailwayDepartment.Service;

import com.lk.RailwayDepartment.Entity.Booking;
import com.lk.RailwayDepartment.Entity.TrainSchedule;
import com.lk.RailwayDepartment.Entity.User;
import com.lk.RailwayDepartment.Model.BookingListFilter;
import com.lk.RailwayDepartment.Repository.BookingRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingServiceImp implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Booking Save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> findAllByActive() {
        return bookingRepository.findAllByActive();
    }

    @Override
    public List<Booking> filter(BookingListFilter bookingListFilter) {
        String stringQuery = "";

        if (bookingListFilter.getTrainClass() != null && (bookingListFilter.getDeparture() != null || bookingListFilter.getArrival() != null)) {
            stringQuery = (bookingListFilter.getDeparture() != null && bookingListFilter.getArrival() != null) ?
                    "SELECT b FROM Booking b JOIN b.trainSchedule.trainDepartureArrivalTimeList s1 JOIN b.trainSchedule.trainDepartureArrivalTimeList s2 JOIN b.trainSchedule.trainSeatPriceAndAvailabilities d WHERE b.active = :active AND d.trainClass= :classe"
                    : "SELECT b FROM Booking b JOIN b.trainSchedule.trainDepartureArrivalTimeList s JOIN b.trainSchedule.trainSeatPriceAndAvailabilities d WHERE b.active = :active AND d.trainClass= :classe";
        } else if (bookingListFilter.getDeparture() != null || bookingListFilter.getArrival() != null) {
            stringQuery = (bookingListFilter.getDeparture() != null && bookingListFilter.getArrival() != null) ?
                    "SELECT b FROM Booking b JOIN b.trainSchedule.trainDepartureArrivalTimeList s1 JOIN b.trainSchedule.trainDepartureArrivalTimeList s2 WHERE b.active = :active"
                    : "SELECT b FROM Booking b JOIN b.trainSchedule.trainDepartureArrivalTimeList s WHERE b.active = :active";
        } else if (bookingListFilter.getTrainClass() != null) {
            stringQuery = "SELECT b FROM Booking b JOIN b.trainSchedule.trainSeatPriceAndAvailabilities d WHERE b.active = :active AND d.trainClass= :classe";
        } else {
            stringQuery = "SELECT b FROM Booking b WHERE b.active = :active";
        }

        if (bookingListFilter.getTrain() != null) {
            stringQuery += " AND b.trainSchedule.train= :train";
        }

        if (!bookingListFilter.getFromDate().isBlank()) {
            stringQuery += " AND b.trainSchedule.date >= :fromDate";
        }

        if (!bookingListFilter.getToDate().isBlank()) {
            stringQuery += " AND b.trainSchedule.date <= :tomDate";
        }

        if (bookingListFilter.getDeparture() != null) {
            String departure = stringQuery.contains("b.trainSchedule.trainDepartureArrivalTimeList s1") ?
                    " AND s1.station = :departure"
                    : " AND s.station = :departure";
            stringQuery += departure;
        }

        if (bookingListFilter.getArrival() != null) {
            String arrival = stringQuery.contains("b.trainSchedule.trainDepartureArrivalTimeList s1") ?
                    " AND s2.station = :arrival"
                    : " AND s.station = :arrival";
            stringQuery += arrival;
        }

        TypedQuery<Booking> query = entityManager.createQuery(stringQuery, Booking.class);

        query.setParameter("active", bookingListFilter.isActive());


        if (bookingListFilter.getTrainClass() != null) {
            query.setParameter("classe", bookingListFilter.getTrainClass());
        }

        if (bookingListFilter.getTrain() != null) {
            query.setParameter("train", bookingListFilter.getTrain());
        }

        if (!bookingListFilter.getFromDate().isBlank()) {
            try {
                query.setParameter("fromDate", Date.from(UserServiceImpl.FORMATTER.parse(bookingListFilter.getFromDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        if (!bookingListFilter.getToDate().isBlank()) {
            try {
                query.setParameter("tomDate", Date.from(UserServiceImpl.FORMATTER.parse(bookingListFilter.getToDate()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay().toLocalDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toInstant()));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        if (bookingListFilter.getDeparture() != null) {
            query.setParameter("departure", bookingListFilter.getDeparture());
        }

        if (bookingListFilter.getArrival() != null) {
            query.setParameter("arrival", bookingListFilter.getArrival());
        }

        List<Booking> bookingList = new ArrayList<>();

        try {
            if (query.getResultList() != null)
                bookingList = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookingList;
    }

    @Override
    public Booking findById(long id) {
        return bookingRepository.findById(id).get();
    }
}
