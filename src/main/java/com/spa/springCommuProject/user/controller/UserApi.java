package com.spa.springCommuProject.user.controller;

import com.spa.springCommuProject.posts.dto.PostDTO;
import com.spa.springCommuProject.user.dto.*;
import com.spa.springCommuProject.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{userId}")
    @ApiOperation(value = "마이페이지")
    public ResponseEntity<UserPageDTO> myPage(@PathVariable Long userId){

        UserPageDTO userPageDto = userService.findPageById(userId);

        return new ResponseEntity<>(userPageDto, HttpStatus.OK);

    }

    @GetMapping("/{userId}/edit")
    @ApiOperation(value = "내 정보 수정 폼")
    public ResponseEntity<UserUpdateDTO> editForm(@PathVariable Long userId) {

        UserUpdateDTO userUpdateDTO = userService.findUpdateById(userId);

        return new ResponseEntity<>(userUpdateDTO, HttpStatus.OK);
    }

    @PostMapping("/{userId}/edit")
    @ApiOperation(value = "내 정보 수정")
    public ResponseEntity<UserUpdateDTO> edit(@PathVariable Long userId, UserUpdateDTO userUpdateDTO) {

        userService.updateUser(userId, userUpdateDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/delete")
    @ApiOperation(value = "회원 탈퇴 폼")
    public ResponseEntity<UserIdDTO> deleteForm(@PathVariable Long userId) {
        UserIdDTO userIdDto = userService.findLoginIdById(userId);

        return new ResponseEntity<>(userIdDto,HttpStatus.OK);
    }

    @PostMapping("/{userId}/delete")
    @ApiOperation(value = "회원 탈퇴")
    public ResponseEntity delete(@PathVariable Long userId) {

        userService.deleteUser(userId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/{userId}/posts")
    @ApiOperation(value = "내 글 목록")
    public ResponseEntity<List<PostDTO>> myPostsList(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.findAllPostsByUserId(userId), HttpStatus.OK);
    }
}