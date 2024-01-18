package com.ra.controller.user;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.BookingRequestDTO;
import com.ra.model.dto.response.BookingResponseDTO;
import com.ra.model.entity.User;
import com.ra.security.user_principle.UserPrinciple;
import com.ra.service.booking.IBookingService;
import com.ra.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api.myservice.com/v1/user")
public class BookingController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IBookingService bookingService;
    @PostMapping("/booking")
    public ResponseEntity<BookingResponseDTO> createBooking(Authentication authentication, @Valid @RequestBody BookingRequestDTO bookingRequestDTO) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userPrinciple.getUser();
        BookingResponseDTO bookingResponseDTO = bookingService.createBooking(bookingRequestDTO,user);
        return new ResponseEntity<>(bookingResponseDTO, HttpStatus.CREATED);
    }
}
