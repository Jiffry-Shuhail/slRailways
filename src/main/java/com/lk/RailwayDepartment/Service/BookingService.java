package com.lk.RailwayDepartment.Service;

import com.lk.RailwayDepartment.Entity.Booking;
import com.lk.RailwayDepartment.Model.BookingListFilter;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking Save(Booking booking);
    List<Booking> findAllByActive();
    List<Booking> filter(BookingListFilter bookingListFilter);

    Booking findById(long id);
}
