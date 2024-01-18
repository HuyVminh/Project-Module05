package com.ra.model.dto.response;

import com.ra.model.entity.User;
import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserInformationDTO {
    private String userName;
    private String fullName;
    private Integer age;
    private String phoneNumber;
    private String email;

    public UserInformationDTO(User user) {
        this.userName = user.getUserName();
        this.fullName = user.getFullName();
        this.age = user.getAge();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
    }
}
