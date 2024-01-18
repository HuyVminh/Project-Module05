package com.ra.controller.permitAll;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.UserRegisterDTO;
import com.ra.model.dto.request.UserLoginDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/auth/")
public class AuthController {
    @Autowired
    private IUserService userService;
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @ModelAttribute("userLogin") UserLoginDTO userLoginDTO) throws CustomException {
        UserResponseDTO userResponseDTO = userService.login(userLoginDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @ModelAttribute("userRegister") UserRegisterDTO userRegisterDTO) throws CustomException {
        return new ResponseEntity<>(userService.register(userRegisterDTO), HttpStatus.CREATED);
    }
}
