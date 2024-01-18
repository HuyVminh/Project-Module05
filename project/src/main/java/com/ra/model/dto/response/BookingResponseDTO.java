package com.ra.model.dto.response;

import com.ra.model.entity.Booking;
import com.ra.model.entity.Product;
import com.ra.model.entity.Room;
import lombok.*;

import java.sql.Date;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingResponseDTO {
    private Date checkinDate;
    private Integer days;
    private String note;
    private String userName;
    private String email;
    private String phoneNumber;
    private Room room;
    private Float total;
    private Set<String> products;
    private String status;

    public BookingResponseDTO(Booking booking) {
        this.checkinDate = booking.getCheckinDate();
        this.days = booking.getDays();
        this.note = booking.getNote();
        this.userName = booking.getUser().getUserName();
        this.email = booking.getUser().getEmail();
        this.phoneNumber = booking.getUser().getPhoneNumber();
        this.room = booking.getRoom();
        this.products = booking.getProducts().stream().map(Product::getProductName).collect(Collectors.toSet());
        this.status = booking.getStatus() == 0 ? "Đang chờ xác nhận" : booking.getStatus() == 1 ? "Đã xác nhận" : "Đã hủy";
        Float totalProduct = (float) 0;
        for (Product product : booking.getProducts()) {
            totalProduct += product.getPrice();
        }
        this.total = booking.getDays() * booking.getRoom().getPrice() + totalProduct;
    }
}
