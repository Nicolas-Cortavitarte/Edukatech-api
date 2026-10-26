package edukatech.api.identity.infrastructure.web.dto;

public record TokenResponse(String accessToken, String tokenType, long expiresInSeconds) {}
