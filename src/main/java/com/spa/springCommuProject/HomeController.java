package com.spa.springCommuProject;


import com.spa.springCommuProject.user.domain.User;
import com.spa.springCommuProject.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;


@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;




    //public String home(){
    @GetMapping("/")
    public String home(@SessionAttribute(name = "loginUser", required = false) User loginUser,
                Model model){
//        List<Image> images = new ArrayList<>();
//
//        List<User> ranks = userService.findUsersSumDesc();
//        model.addAttribute("ranks",ranks);
//
//        List<PhotoPost> photoPosts = postServiceOld.findAvailableAllPhotoPostViewDesc();
//        model.addAttribute("photoPosts",photoPosts);
//
//        List<FreePost> freePosts = postServiceOld.mainPageFreePosts();
//        model.addAttribute("freePosts", freePosts);
//
//        List<VideoPost> videoPosts = postServiceOld.mainPageVideoPosts();
//        model.addAttribute("videoPosts", videoPosts);
//
////        for (PhotoPost photoPost : photoPosts) {
////            Optional image = fileService.findOneImageByPostId(photoPost.getId());
////            Object o = image.get();
////            images.add(o);
////        }
//
//        for (PhotoPost photoPost : photoPosts) {
//            Image image = fileService.findOneImageByPostId(photoPost.getId());
//            images.add(image);
//        }
//        model.addAttribute("images", images);
//

        //세션에 회원 데이터가 없으면 home
        if (loginUser == null) {
            return "home";
        }

        //세션이 유지되면 모델에 값 넣고 로그인으로 이동
        model.addAttribute("user", loginUser);

        return "loginHome";
    }
}
