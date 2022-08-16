package com.spa.springCommuProject;

import com.spa.springCommuProject.common.CommonResponse;
import com.spa.springCommuProject.posts.dto.MainPageDTO;
import com.spa.springCommuProject.posts.dto.PostViewDTO;
import com.spa.springCommuProject.posts.dto.SearchRequest;
import com.spa.springCommuProject.posts.service.PostService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080",allowedHeaders = "*")
public class HomeApi {

    private final PostService postService;

    @GetMapping("/")
    @ApiOperation(value = "메인페이지")
    public ResponseEntity<CommonResponse<MainPageDTO>> main(){
        return ResponseEntity.ok(CommonResponse.from(postService.mainPagePosts()));
    }

    @GetMapping("/api/search")
    @ApiOperation(value = "검색")
    public ResponseEntity<CommonResponse<List<PostViewDTO>>> search(@RequestBody SearchRequest searchRequest){

        //작성자, 내용, 제목, 통합검색 (제목 + 내용)

        if(searchRequest.getSelect().equals("title")){
            return ResponseEntity.ok(CommonResponse.from(postService.searchTitle(searchRequest.getKeyword())));
        }else {
            return ResponseEntity.ok(CommonResponse.from(postService.searchTitleAndContent(searchRequest.getKeyword())));
        }
    }

}
