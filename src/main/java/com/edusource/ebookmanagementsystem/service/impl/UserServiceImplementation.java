package com.edusource.ebookmanagementsystem.service.impl;

import com.edusource.ebookmanagementsystem.dto.LoginRequest;
import com.edusource.ebookmanagementsystem.dto.Response;
import com.edusource.ebookmanagementsystem.dto.UserDto;
import com.edusource.ebookmanagementsystem.exception.OurException;
import com.edusource.ebookmanagementsystem.model.User;
import com.edusource.ebookmanagementsystem.repository.UserRepository;
import com.edusource.ebookmanagementsystem.service.UserService;
import com.edusource.ebookmanagementsystem.util.JWTUtils;
import com.edusource.ebookmanagementsystem.util.MappingUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response register(User user) {
        Response response=new Response();
        try{
            if(user.getRole()==null || user.getRole().isBlank()){
                user.setRole("READER");
            }
            if(userRepository.existsByEmail(user.getEmail())){
                throw new OurException(user.getEmail()+" already exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser=userRepository.save(user);
            UserDto userDto= MappingUtils.mapUserModelToUserDto(savedUser);
            response.setStatusCode(200);
        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during User registration "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest userLoginRequest) {
        Response response=new Response();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(),userLoginRequest.getPassword()));
            var user=userRepository.findByEmail(userLoginRequest.getEmail()).orElseThrow(()->new Exception("user not found"));
            var jwt=jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setRole(user.getRole());
            response.setExpirationTime("7 days");
            response.setMessage("successful");
        }
        catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occurred during User login "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<User> userList = userRepository.findAll();
            List<UserDto> userDTOList = MappingUtils.mapUserListEntityToUserListDTO(userList);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserList(userDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();

        try {
            userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
            response.setMessage("successful");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error deleting a user " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getReadingHistory(String userId) {
        Response response = new Response();


        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
            UserDto userDTO = MappingUtils.mapUserReadingHistoryToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserDto(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();

        try {
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(() -> new OurException("User Not Found"));
            UserDto userDTO = MappingUtils.mapUserModelToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserDto(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();

        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new OurException("User Not Found"));
            UserDto userDTO = MappingUtils.mapUserModelToUserDto(user);
            response.setStatusCode(200);
            response.setMessage("successful");
            response.setUserDto(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch (Exception e) {

            response.setStatusCode(500);
            response.setMessage("Error getting all users " + e.getMessage());
        }
        return response;
    }
}
