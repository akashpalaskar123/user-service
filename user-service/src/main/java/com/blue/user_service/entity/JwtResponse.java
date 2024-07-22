package com.blue.user_service.entity;

import lombok.*;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {
    String jwtToken;
    String userName;
}
