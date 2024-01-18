package com.ra.controller.user;

import com.ra.exception.CustomException;
import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.model.entity.User;
import com.ra.security.user_principle.UserPrinciple;
import com.ra.service.booking.IBookingService;
import com.ra.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/user")
public class HistoryController {
    @Autowired
    private IBookingService bookingService;

    @GetMapping("/history")
    public ResponseEntity<?> getHistories(Authentication authentication,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int limit,
                                          @RequestParam(name = "sort", defaultValue = "status") String sort,
                                          @RequestParam(name = "order", defaultValue = "asc") String order) throws CustomException {
        Pageable pageable;
        if (order.equals("desc")) {
            pageable = PageRequest.of(page, limit, Sort.by(sort).descending());
        } else {
            pageable = PageRequest.of(page, limit, Sort.by(sort).ascending());
        }
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        Page<BookingResponseDTO> list = bookingService.searchByUserId(pageable, userPrinciple.getUser().getId());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PatchMapping("/history/cancel/{id}")
    public ResponseEntity<BookingResponseDTO> cancelBooking(@PathVariable("id") Long id, Authentication authentication) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        BookingResponseDTO bookingResponseDTO = bookingService.changeStatusBookingByUser(userPrinciple.getUser().getId(), id);
        return new ResponseEntity<>(bookingResponseDTO, HttpStatus.OK);
    }
}
