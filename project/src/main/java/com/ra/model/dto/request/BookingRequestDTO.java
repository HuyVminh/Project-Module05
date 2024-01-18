package com.ra.model.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingRequestDTO {
    @NotNull(message = "Không được để trống ngày !")
    @Future(message = "Ngày check-in không thể là ngày trong quá khứ !")
    private Date checkinDate;
    @Min(value = 1,message = "Ngày đặt tối thiểu là 1")
    private Integer days;
    private String note;
    @Column(nullable = false)
    private Long roomId;
    private Set<Long> products;
    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer status = 0;
}
