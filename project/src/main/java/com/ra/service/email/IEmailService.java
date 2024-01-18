package com.ra.service.email;

import com.ra.model.dto.response.BookingResponseDTO;

public interface IEmailService {
    String sendEmail(String email, BookingResponseDTO bookingResponseDTO);

}
