package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.service.booking.IBookingService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class BookingsController {
    @Autowired
    private IBookingService bookingService;

    @GetMapping("/bookings")
    public ResponseEntity<?> getBookings(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int limit,
                                         @RequestParam(name = "sort", defaultValue = "id") String sort,
                                         @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<BookingResponseDTO> bookings = bookingService.findAll(pageable);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/bookings/pending")
    public ResponseEntity<?> getBookingsPending(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int limit,
                                                @RequestParam(name = "sort", defaultValue = "status") String sort,
                                                @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<BookingResponseDTO> bookings = bookingService.searchByStatus(pageable, 0);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/bookings/accepted")
    public ResponseEntity<?> getBookingsAccepted(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int limit,
                                                 @RequestParam(name = "sort", defaultValue = "status") String sort,
                                                 @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<BookingResponseDTO> bookings = bookingService.searchByStatus(pageable, 1);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @GetMapping("/bookings/canceled")
    public ResponseEntity<?> getBookingsCanceled(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "5") int limit,
                                                 @RequestParam(name = "sort", defaultValue = "status") String sort,
                                                 @RequestParam(name = "order", defaultValue = "desc") String order) {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        Page<BookingResponseDTO> bookings = bookingService.searchByStatus(pageable, 2);
        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

    @PatchMapping("bookings/accept/{id}")
    public ResponseEntity<BookingResponseDTO> acceptBooking(@PathVariable("id") Long id) throws CustomException {
        BookingResponseDTO bookingResponseDTO = bookingService.changeStatusBooking(id, 1);
        return new ResponseEntity<>(bookingResponseDTO, HttpStatus.OK);
    }

    @PatchMapping("bookings/cancel/{id}")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable("id") Long id) throws CustomException {
        BookingResponseDTO bookingResponseDTO = bookingService.changeStatusBooking(id, 2);
        return new ResponseEntity<>(bookingResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/bookings/count-by-year")
    public ResponseEntity<?> countBookingsByYear(@ModelAttribute("year") Integer year) {
        Integer count = bookingService.countBookingsAccepted(year);
        return new ResponseEntity<>("Tổng đơn đặt phòng đã được xác nhận trong năm " + year + " là : " + count, HttpStatus.OK);
    }
}
