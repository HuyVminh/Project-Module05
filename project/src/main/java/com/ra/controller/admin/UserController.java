package com.ra.controller.admin;

import com.ra.exception.CustomException;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.User;
import com.ra.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class UserController {
    @Autowired
    private IUserService userService;
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "5") int limit,
                                         @RequestParam(name = "sort",defaultValue = "id") String sort,
                                         @RequestParam(name = "order",defaultValue = "desc") String order){
        Pageable pageable;
        if(order.equals("desc")){
            pageable = PageRequest.of(page,limit, Sort.by(sort).descending());
        }else {
            pageable = PageRequest.of(page,limit, Sort.by(sort).ascending());
        }
        Page<UserResponseDTO> userResponseDTOS = userService.findAll(pageable);
        return new ResponseEntity<>(userResponseDTOS, HttpStatus.OK);
    };
    @PatchMapping("/users/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") Long userId) throws CustomException {
        UserResponseDTO user = userService.changeStatusById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/users/search")
    public ResponseEntity<?> searchByName(@RequestParam("nameSearch") String nameSearch,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int limit,
                                          @RequestParam(name = "sort",defaultValue = "id") String sort,
                                          @RequestParam(name = "order",defaultValue = "desc") String order) throws CustomException {
        Pageable pageable;
        if(order.equals("desc")){
            pageable = PageRequest.of(page,limit, Sort.by(sort).descending());
        }else {
            pageable = PageRequest.of(page,limit, Sort.by(sort).ascending());
        }
        Page<UserResponseDTO> users = userService.searchUserByName(pageable,nameSearch);
        return new ResponseEntity<>(users,HttpStatus.OK);
    }
}
