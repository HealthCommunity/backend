package com.spa.springCommuProject.user.controller;

import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.dto.UserJoinDTO;
import com.spa.springCommuProject.user.dto.UserLoginDTO;
import com.spa.springCommuProject.user.dto.UserPageDto;
import com.spa.springCommuProject.user.dto.UserUpdateDTO;
import com.spa.springCommuProject.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApi {

    private final UserService userService;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<UserJoinDTO> join(UserJoinDTO userJoinDTO) {
        UserJoinDTO responseDto = userService.join(userJoinDTO);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<UserLoginDTO> login(UserLoginDTO userLoginDTO){
        UserLoginDTO loginDTO = userService.login(userLoginDTO);

        return new ResponseEntity<>(loginDTO, HttpStatus.OK);
    }

    @GetMapping("{userId}")
    @ApiOperation(value = "마이페이지")
    public ResponseEntity<UserPageDto> myPage(@PathVariable Long userId){

        UserPageDto userPageDto = userService.findById(userId);

        return new ResponseEntity<>(userPageDto, HttpStatus.OK);

    }

    @GetMapping("{userId}/edit")
    public ResponseEntity<UserUpdateDTO> editForm(@PathVariable Long userId) {

        userService.findById(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{userId}/edit")
    public ResponseEntity edit(@PathVariable Long userId,
        UserUpdateDTO userUpdateDTO) {

        userService.updateUser(userId, userUpdateDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}