package com.ra.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdatePasswordDTO {
    private String currentPassword;
    @Size(min = 3,message = "Mật khẩu phải có ít nhất 3 ký tự !")
    private String newPassword;
    @NotBlank(message = "Không được để trống !")
    private String confirmPassword;
}
