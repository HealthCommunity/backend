package com.spa.springCommuProject.user.controller;

import com.spa.springCommuProject.common.CommonResponse;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
import com.spa.springCommuProject.posts.service.PostService;
import com.spa.springCommuProject.user.dto.UserIdDTO;
import com.spa.springCommuProject.user.dto.UserJoinDTO;
import com.spa.springCommuProject.user.dto.UserPageDTO;
import com.spa.springCommuProject.user.dto.UserUpdateDTO;
import com.spa.springCommuProject.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApi {

    private final UserService userService;
    private final PostService postService;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<CommonResponse<UserJoinDTO>> join(UserJoinDTO userJoinDTO) {
        UserJoinDTO responseDto = userService.join(userJoinDTO);
        return ResponseEntity.ok(CommonResponse.from(responseDto));
    }

//    @PostMapping("/login")
//    @ApiOperation(value = "로그인")
//    public ResponseEntity<UserLoginDTO> login(UserLoginDTO userLoginDTO){
//        UserLoginDTO loginDTO = userService.login(userLoginDTO);
//
//        return new ResponseEntity<>(loginDTO, HttpStatus.OK);
//    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "마이페이지")
    public ResponseEntity<CommonResponse<UserPageDTO>> myPage(@PathVariable Long userId) {

        UserPageDTO userPageDto = userService.findPageById(userId);

        return ResponseEntity.ok(CommonResponse.from(userPageDto));

    }

    @GetMapping("/{userId}/edit")
    @ApiOperation(value = "내 정보 수정 폼")
    public ResponseEntity<CommonResponse<UserUpdateDTO>> editForm(@PathVariable Long userId) {

        UserUpdateDTO userUpdateDTO = userService.findUpdateById(userId);

        return ResponseEntity.ok(CommonResponse.from(userUpdateDTO));
    }

    @PostMapping("/{userId}/edit")
    @ApiOperation(value = "내 정보 수정")
    public ResponseEntity<Void> edit(@PathVariable Long userId, UserUpdateDTO userUpdateDTO) {

        userService.updateUser(userId, userUpdateDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/delete")
    @ApiOperation(value = "회원 탈퇴 폼")
    public ResponseEntity<CommonResponse<UserIdDTO>> deleteForm(@PathVariable Long userId) {
        UserIdDTO userIdDto = userService.findLoginIdById(userId);

        return ResponseEntity.ok(CommonResponse.from(userIdDto));
    }

    @PostMapping("/{userId}/delete")
    @ApiOperation(value = "회원 탈퇴")
    public ResponseEntity<Void> delete(@PathVariable Long userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/posts")
    @ApiOperation(value = "내 글 목록")
    public ResponseEntity<CommonResponse<Page<PostViewDTO>>> myPostsList(@PathVariable Long userId,
        @RequestParam("page") Integer page,
        @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending()); //page * size를 어디서 해야하는가 이부분 다음에 이야기
        return ResponseEntity.ok(CommonResponse.from(postService.findAllPostsByUserId(userId, pageRequest)));
    }

    @GetMapping("/asd")
    public ResponseEntity<CommonResponse<PostViewDTO>> temp() {
        PostViewDTO postViewDTO = new PostViewDTO("asd", LocalDateTime.now(), "asdz", 1);
        return ResponseEntity.ok(CommonResponse.from(postViewDTO));
    }
}