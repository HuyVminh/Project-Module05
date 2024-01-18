package com.ra.service.user;

import com.ra.exception.CustomException;
import com.ra.model.dto.request.UpdateUserDTO;
import com.ra.model.dto.request.UserRegisterDTO;
import com.ra.model.dto.request.UserLoginDTO;
import com.ra.model.dto.response.UserInformationDTO;
import com.ra.model.dto.response.UserResponseDTO;
import com.ra.model.entity.Role;
import com.ra.model.entity.User;
import com.ra.repository.IUserRepository;
import com.ra.security.jwt.JWTProvider;
import com.ra.security.user_principle.UserPrinciple;
import com.ra.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceIMPL implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private IRoleService roleService;

    @Override
    public User register(UserRegisterDTO userRegisterDTO) throws CustomException {
        // check trung lap
        if (userRepository.existsUserByUserName(userRegisterDTO.getUserName())) {
            throw new CustomException("Tên đăng nhập đã tồn tại !");
        }
        // ma hoa mat khau
        userRegisterDTO.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()));
        // role
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByRoleName("USER"));
        User user = User.builder().
                userName(userRegisterDTO.getUserName()).
                fullName(userRegisterDTO.getFullName()).
                password(userRegisterDTO.getPassword()).
                email(userRegisterDTO.getEmail()).
                age(Integer.valueOf(userRegisterDTO.getAge())).
                phoneNumber(userRegisterDTO.getPhoneNumber()).
                status(userRegisterDTO.getStatus()).
                roles(roles).build();
        return userRepository.save(user);
    }

    @Override
    public UserResponseDTO login(UserLoginDTO userLoginDTO) throws CustomException {
        try {
            Authentication authentication;
            authentication = authenticationProvider.
                    authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getUserName(), userLoginDTO.getPassword()));
            UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
            if(!userPrinciple.getUser().getStatus()){
                throw new CustomException("Tài khoản đã bị khóa !");
            }
            return UserResponseDTO.builder().
                    token(jwtProvider.generateToken(userPrinciple)).
                    userName(userPrinciple.getUsername()).
                    age(userPrinciple.getUser().getAge()).
                    email(userPrinciple.getUser().getEmail()).
                    phoneNumber(userPrinciple.getUser().getPhoneNumber()).
                    status(userPrinciple.getUser().getStatus()).
                    roles(userPrinciple.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet())).
                    build();
        }
        catch (AuthenticationException e){
            throw new CustomException("Sai thông tin đăng nhập !");
        }
    }

    @Override
    public Page<UserResponseDTO> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(UserResponseDTO::new);
    }

    @Override
    public Page<UserResponseDTO> searchUserByName(Pageable pageable, String name) {
        Page<User> users = userRepository.findAllByUserNameContainingIgnoreCase(pageable, name);
        return users.map(UserResponseDTO::new);
    }

    @Override
    public User findUserById(Long userId) throws CustomException {
        return userRepository.findById(userId).orElseThrow(() -> new CustomException("Không tìm thấy người dùng !"));
    }

    @Override
    public UserResponseDTO changeStatusById(Long userId) throws CustomException {
        User user;
        if (userId == 6) {
            throw new CustomException("Không thể thay đổi trạng thái của ADMIN !");
        } else {
            user = userRepository.findById(userId).orElseThrow(()-> new CustomException("Không tìm thấy người dùng !"));
            user.setStatus(!user.getStatus());
            userRepository.save(user);
        }
        return new UserResponseDTO(user);
    }

    @Override
    public User getInformation(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    @Override
    public UserInformationDTO updateUser(UpdateUserDTO updateUserDTO, User userLogin) throws CustomException {
        if (!updateUserDTO.getUserName().equals(userLogin.getUserName())){
            if (userRepository.existsUserByUserName(updateUserDTO.getUserName())){
                throw new CustomException("Tên tài khoản đã tồn tại !");
            }
            userLogin.setUserName(updateUserDTO.getUserName());
        }
        userLogin.setFullName(updateUserDTO.getFullName());
        userLogin.setAge(updateUserDTO.getAge());
        userLogin.setEmail(updateUserDTO.getEmail());
        userLogin.setPhoneNumber(updateUserDTO.getPhoneNumber());
        userRepository.save(userLogin);
        return new UserInformationDTO(userLogin);
    }

    @Override
    public void updatePassword(User user,String pass) {
        String encodedPassword = passwordEncoder.encode(pass);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }
}
