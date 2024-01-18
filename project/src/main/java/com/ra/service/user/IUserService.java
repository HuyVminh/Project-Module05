package com.ra.service.user;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.UpdateUserDTO;
import com.ra.model.dto.request.UserRegisterDTO;
import com.ra.model.dto.request.UserLoginDTO;
import com.ra.model.dto.response.UserInformationDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserService {
    User register(UserRegisterDTO userRegisterDTO) throws CustomException;
    UserResponseDTO login(UserLoginDTO userLoginDTO) throws CustomException;
    Page<UserResponseDTO> findAll(Pageable pageable);
    Page<UserResponseDTO> searchUserByName(Pageable pageable,String name);
    User findUserById(Long userId) throws CustomException;
    UserResponseDTO changeStatusById(Long userId) throws CustomException;
    User getInformation(String userName);
    UserInformationDTO updateUser(UpdateUserDTO updateUserDTO,User user) throws CustomException;
    void updatePassword(User user,String pass);
}
