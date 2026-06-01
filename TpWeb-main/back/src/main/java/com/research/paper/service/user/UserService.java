package com.research.paper.service.user;

import com.research.paper.dto.request.user.ChangePasswordRequest;
import com.research.paper.dto.request.user.ProfileUpdateRequest;
import com.research.paper.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.transaction.annotation.Transactional;

public interface UserService extends UserDetailsService {
    void updateProfileInfo(ProfileUpdateRequest request,String userId);
    void changePassword(ChangePasswordRequest request,String userId);
    void deactivateAccount(String userId);
    void reactivateAccount(String userId);
    void deleteAccount(String userId);
    @Transactional(readOnly = true)
    UserResponse getUserById(String userId);
}
