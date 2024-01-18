package com.ra.repository;

import com.ra.model.entity.Booking;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface IBookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByUser(Pageable pageable, User user);
    Page<Booking> findAllByStatus(Pageable pageable,Integer status);
    Integer countAllByStatusAndCheckinDateBetween(Integer status, Date startDate, Date endDate);
}
