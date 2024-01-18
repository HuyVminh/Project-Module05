package com.ra.model.dto.response;

import com.ra.model.entity.Booking;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserResponseDTO {
    private String token;
    private String userName;
    private String email;
    private Integer age;
    private String phoneNumber;
    private Set<String> roles;
    private Boolean status;

    public UserResponseDTO(User user) {
        this.token = user.getPassword();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.age = user.getAge();
        this.phoneNumber = user.getPhoneNumber();
        this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        this.status = user.getStatus();
    }
}
