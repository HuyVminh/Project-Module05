package com.ra.controller.user;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.UpdatePasswordDTO;
import com.ra.model.dto.request.UpdateUserDTO;
import com.ra.model.dto.response.UserInformationDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.User;
import com.ra.repository.IUserRepository;
import com.ra.security.user_principle.UserDetailService;
import com.ra.security.user_principle.UserPrinciple;
import com.ra.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/user")
public class AccountController {
    @Autowired
    private IUserService userService;

    @GetMapping("/account")
    public ResponseEntity<UserInformationDTO> getInformation(Authentication authentication) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userPrinciple.getUser();
        UserInformationDTO userInformationDTO = new UserInformationDTO(user);
        return new ResponseEntity<>(userInformationDTO, HttpStatus.OK);
    }

    @PutMapping("/account")
    public ResponseEntity<UserInformationDTO> updateInformation(Authentication authentication, @Valid @RequestBody UpdateUserDTO updateUserDTO) throws CustomException {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userPrinciple.getUser();
        UserInformationDTO userInformationDTO = userService.updateUser(updateUserDTO, user);
        return new ResponseEntity<>(userInformationDTO, HttpStatus.OK);
    }

    @PatchMapping("/account/change-password")
    public ResponseEntity<?> changePassword(Authentication authentication,
                                            @Valid @RequestBody UpdatePasswordDTO updatePasswordDTO) {
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        User user = userPrinciple.getUser();
        // Kiểm tra mật khẩu hiện tại
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(updatePasswordDTO.getCurrentPassword(), user.getPassword())) {
            return new ResponseEntity<>("Sai mật khẩu !", HttpStatus.BAD_REQUEST);
        }
        // Mật khẩu mới và xác nhận mật khẩu
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getConfirmPassword())) {
            return new ResponseEntity<>("Mật khẩu không khớp !", HttpStatus.BAD_REQUEST);
        }
        userService.updatePassword(user, updatePasswordDTO.getNewPassword());
        return new ResponseEntity<>("Thay đổi mật khẩu thành công !", HttpStatus.OK);
    }
}
