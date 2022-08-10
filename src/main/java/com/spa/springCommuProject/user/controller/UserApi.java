package com.spa.springCommuProject.user.controller;

import com.spa.springCommuProject.common.CommonResponse;
import com.spa.springCommuProject.config.login.PrincipalUserDetails;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
import com.spa.springCommuProject.posts.service.PostService;
import com.spa.springCommuProject.user.dto.*;
import com.spa.springCommuProject.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserApi {

    private final UserService userService;
    private final PostService postService;

    @PostMapping("/join")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<CommonResponse<UserJoinResponse>> join(@RequestBody UserJoinRequest userJoinRequest) {
        try{
            return ResponseEntity.ok(CommonResponse.from(userService.join(userJoinRequest)));
        }catch (Exception e){
            //return ResponseEntity.ok(CommonResponse.from(e));
        }
        return null;

    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인")
    public ResponseEntity<Void> login(UserLoginDTO userLoginDTO){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping()
    @ApiOperation(value = "마이페이지")
    public ResponseEntity<CommonResponse<UserPageDTO>> myPage(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {

        UserPageDTO userPageDTO = principalUserDetails.getUser().convertToUserPageDTO();

        return ResponseEntity.ok(CommonResponse.from(userPageDTO));

    }

    @GetMapping("/edit")
    @ApiOperation(value = "내 정보 수정 폼")
    public ResponseEntity<CommonResponse<UserPageDTO>> editForm(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {

        UserPageDTO userPageDTO = principalUserDetails.getUser().convertToUserPageDTO();

        return ResponseEntity.ok(CommonResponse.from(userPageDTO));
    }

    @PostMapping("/edit")
    @ApiOperation(value = "내 정보 수정")
    public ResponseEntity<Void> edit(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails
            , @RequestBody UserUpdateDTO userUpdateDTO) {

        userService.updateUser(principalUserDetails, userUpdateDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/delete")
    @ApiOperation(value = "회원 탈퇴 폼")
    public ResponseEntity<CommonResponse<UserIdDTO>> deleteForm(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {
        UserIdDTO userIdDTO = principalUserDetails.getUser().convertToUserIdDTO();

        return ResponseEntity.ok(CommonResponse.from(userIdDTO));
    }

    @PostMapping("/delete")
    @ApiOperation(value = "회원 탈퇴")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails) {

        userService.deleteUser(principalUserDetails);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/post")
    @ApiOperation(value = "내 글 목록")
    public ResponseEntity<CommonResponse<List<PostViewDTO>>> myPostsList(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                                                                         @RequestParam("page") Integer page,
                                                                         @RequestParam("size") Integer size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by("id").descending()); //page * size를 어디서 해야하는가 이부분 다음에 이야기
        return ResponseEntity.ok(CommonResponse.from(postService.findAllPostsByUserId(principalUserDetails, pageRequest)));
    }

    @GetMapping("/asd")
    public ResponseEntity<CommonResponse<PostViewDTO>> temp() {
        PostViewDTO postViewDTO = new PostViewDTO(6000L,"asd", LocalDateTime.now(), "asdz", 1);
        return ResponseEntity.ok(CommonResponse.from(postViewDTO));
    }

    @PostMapping("/bigthreepower")
    @ApiOperation("3대력부여")
    public ResponseEntity<Void> bigThreePower(@AuthenticationPrincipal PrincipalUserDetails principalUserDetails,
                                              @RequestBody BigThreeDTO bigThreeDTO){
        userService.updateBigThree(principalUserDetails, bigThreeDTO);
        return ResponseEntity.ok().build();
    }
}