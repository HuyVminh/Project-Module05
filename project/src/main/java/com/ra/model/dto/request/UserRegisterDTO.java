package com.ra.model.dto.request;

import com.ra.model.entity.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserRegisterDTO {
    @NotEmpty(message = "Không được để trống !")
    private String userName;
    private String fullName;
    @Pattern(regexp = "\\d+", message = "Không đúng định dạng số !")
    @Min(value = 20, message = "Người đăng ký phải trên 20 tuổi !")
    private String age;
    @Pattern(regexp = "(0[3|5|7|8|9])+([0-9]{8})\\b",message = "Không đúng định dạng số điện thoại !")
    private String phoneNumber;
    @Pattern(regexp = "^(.+)@(.+)$",message = "Không đúng định dạng email !")
    private String email;
    @Size(min = 3,message = "Mật khẩu phải có ít nhất 3 ký tự !")
    private String password;
    private Set<Role> roles;
    private Boolean status = true;
}
