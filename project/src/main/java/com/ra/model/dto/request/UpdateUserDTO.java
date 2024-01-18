package com.ra.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserDTO {
    @NotEmpty(message = "Không được để trống !")
    private String userName;
    private String fullName;
    @Min(value = 20,message = "Không thể dưới 20 tuổi !")
    private Integer age;
    @Pattern(regexp = "(0[3|5|7|8|9])+([0-9]{8})\\b",message = "Không đúng định dạng số điện thoại !")
    private String phoneNumber;
    @Pattern(regexp = "^(.+)@(.+)$",message = "Không đúng định dạng email !")
    private String email;
}
