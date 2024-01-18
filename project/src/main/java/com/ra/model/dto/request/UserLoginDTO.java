package com.ra.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginDTO {
    @NotEmpty(message = "Không được để trống !")
    private String userName;
    private String password;
}
