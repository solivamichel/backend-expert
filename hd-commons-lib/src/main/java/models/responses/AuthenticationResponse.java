package models.responses;

import lombok.Builder;

//@With
@Builder
public record AuthenticationResponse(
        String token,
//        String refreshToken,
        String type
) {
}