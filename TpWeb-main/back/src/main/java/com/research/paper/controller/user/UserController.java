package com.research.paper.controller.user;

import com.research.paper.common.Utility;
import com.research.paper.dto.request.user.ChangePasswordRequest;
import com.research.paper.dto.request.user.ProfileUpdateRequest;
import com.research.paper.dto.response.UserResponse;
import com.research.paper.entity.user.User;
import com.research.paper.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User",description = "User API")
@Transactional
public class UserController {
    private final UserService userService;
    private final Utility utility;
    @PatchMapping("/me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateProfileInfo(
            @RequestBody
            @Valid
            ProfileUpdateRequest request,
            final Authentication principal){
        this.userService.updateProfileInfo(request, utility.getUserId(principal));
    }
    @GetMapping("/")
    public UserResponse getUserById(final Authentication principal){
        return userService.getUserById(utility.getUserId(principal));
    }
    @PostMapping("/me/password")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void changePassword(
            @RequestBody
            @Valid
            ChangePasswordRequest request,
            Authentication principal){
        this.userService.changePassword(request,this.utility.getUserId(principal));

    }
    @PatchMapping("/me/deactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deactivateAccount(final Authentication principal){
        this.userService.deactivateAccount(this.utility.getUserId(principal));
    }
    @PatchMapping("/me/reactivate")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void reactivateAccount(final Authentication principal){
        this.userService.reactivateAccount(this.utility.getUserId(principal));
    }
    @DeleteMapping("/me")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteAccount(final Authentication principal){
        this.userService.deleteAccount(this.utility.getUserId(principal));
    }

    }
