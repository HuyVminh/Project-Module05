package com.ra.service.booking;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.BookingRequestDTO;
import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookingService {
    Page<BookingResponseDTO> findAll(Pageable pageable);

    Page<BookingResponseDTO> searchByUserId(Pageable pageable, Long userId) throws CustomException;

    Page<BookingResponseDTO> searchByStatus(Pageable pageable, Integer status);

    BookingResponseDTO createBooking(BookingRequestDTO bookingRequestDTO, User user) throws CustomException;

    BookingResponseDTO changeStatusBooking(Long id, Integer status) throws CustomException;

    BookingResponseDTO changeStatusBookingByUser(Long userId, Long bookingId) throws CustomException;
    Integer countBookingsAccepted(Integer year);
}
