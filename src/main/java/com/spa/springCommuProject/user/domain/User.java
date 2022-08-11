package com.spa.springCommuProject.user.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.spa.springCommuProject.posts.domain.Post;
import com.spa.springCommuProject.user.dto.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String nickName;

    @Column(unique = true)
    private String loginId;
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.USER;
    private String email;
    private Provider provider;
    private String providerId;
    @Embedded
    private BigThreePower bigThreePower;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Post> posts = new ArrayList<>();

    private Boolean available;
    protected User() {
    }

    public User(String nickName, String loginId, String password) {
        this.available = true;
        this.nickName = nickName;
        this.loginId = loginId;
        this.password = password;
        this.role = Role.USER; //default값
        this.bigThreePower = new BigThreePower(0,0,0); //default값
    }

    public void update(String nickName, String password){
        this.nickName = nickName;
        this.password = password;
    }

    public void setRole(Role role){
        this.role = role;
    }

    public void delete(){
        this.available = false;
    }

    public void updateBig(BigThreePower bigThreePower){
        this.bigThreePower = bigThreePower;
    }

    public UserDTO convertToUserDTO(User user){
        return new UserDTO(user.id, user.nickName, user.loginId, user.password, user.role, user.bigThreePower);
    }

    public MainUserResponse convertToMainUserResponse(){
        return new MainUserResponse(this.nickName, this.bigThreePower);
    }

    public UserPageDTO convertToUserPageDTO(){
        return new UserPageDTO(this.nickName, this.loginId, this.bigThreePower);
    }

    public UserIdDTO convertToUserIdDTO(){
        return new UserIdDTO(this.loginId);
    }

    public UserPostResponse convertToUserPostResponse(){
        return new UserPostResponse(this.id, this.nickName, this.bigThreePower, this.role);
    }
}
