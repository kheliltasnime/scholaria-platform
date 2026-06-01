package com.research.paper.dto.response;


import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Component
public class UserResponse {
    private String lastName;
    private String firstName;
    private String institution;
    private String country;
    private String imageUrl;
    private int citationCount;
    private int papersCount;
}
