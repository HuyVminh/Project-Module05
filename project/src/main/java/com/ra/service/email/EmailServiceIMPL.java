package com.ra.service.email;

import com.ra.model.dto.response.BookingResponseDTO;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class EmailServiceIMPL implements IEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendEmail(String email, BookingResponseDTO bookingResponseDTO) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("jav230630@gmail.com");
            helper.setTo(email);
            helper.setSubject("Đặt phòng thành công !");

            // Tạo nội dung email
            String emailContent = createEmailContent(bookingResponseDTO);

            helper.setText(emailContent, true);

            javaMailSender.send(message);

            return "Email sent successfully";
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String createEmailContent(BookingResponseDTO bookingResponseDTO) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
        String formattedAmount = decimalFormat.format(bookingResponseDTO.getTotal());
        StringBuilder emailContentBuilder = new StringBuilder();
        emailContentBuilder.append("<html><body>");
        emailContentBuilder.append("<style>");
        emailContentBuilder.append(".container { display: flex; }");
        emailContentBuilder.append(".info { flex: 1; }");
        emailContentBuilder.append(".image { flex: 1; }");
        emailContentBuilder.append("</style>");
        emailContentBuilder.append("<h2>Cảm ơn bạn đã đặt phòng tại WEBSITE đặt phòng khách sạn ONLINE!</h2>");
        emailContentBuilder.append("<div class='container'>");
        emailContentBuilder.append("<div class='info'>");
        emailContentBuilder.append("<h3>Thông tin phòng :</h3>");
        emailContentBuilder.append("<p><strong>Tài khoản đặt phòng:</strong> ").append(bookingResponseDTO.getUserName()).append("</p>");
        emailContentBuilder.append("<p><strong>Số điện thoại:</strong> ").append(bookingResponseDTO.getPhoneNumber()).append("</p>");
        emailContentBuilder.append("<p><strong>Ngày checkin:</strong> ").append(bookingResponseDTO.getCheckinDate()).append("</p>");
        emailContentBuilder.append("<p><strong>Số ngày:</strong> ").append(bookingResponseDTO.getDays()).append("</p>");
        emailContentBuilder.append("<p><strong>Khách sạn:</strong> ").append(bookingResponseDTO.getRoom().getHotel().getHotelName()).append("</p>");
        emailContentBuilder.append("<p><strong>Số phòng:</strong> ").append(bookingResponseDTO.getRoom().getRoomNo()).append("</p>");
        emailContentBuilder.append("<p><strong>Địa chỉ:</strong> ").append(bookingResponseDTO.getRoom().getHotel().getAddress()).append(bookingResponseDTO.getRoom().getHotel().getCity().getCityName()).append("</p>");
        emailContentBuilder.append("<p><strong>Dịch vụ:</strong> ").append(bookingResponseDTO.getProducts()).append("</p>");
        emailContentBuilder.append("<p><strong>Ghi chú:</strong> ").append(bookingResponseDTO.getNote()).append("</p>");
        emailContentBuilder.append("<p><strong>Thành tiền:</strong> ").append(formattedAmount).append("</p>");
        emailContentBuilder.append("</div>");
        emailContentBuilder.append("<div class='image'>");
        // Thêm ảnh khách sạn
        String hotelImage = "https://media.vneconomy.vn/w800/images/upload/2023/08/08/premise-overview.jpg";
        emailContentBuilder.append("<img src='").append(hotelImage).append("' alt='Khách sạn'>");
        emailContentBuilder.append("</div>");
        emailContentBuilder.append("</div>");
        emailContentBuilder.append("</body></html>");
        return emailContentBuilder.toString();
    }
}