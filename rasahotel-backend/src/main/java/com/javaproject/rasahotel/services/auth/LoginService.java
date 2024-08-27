package com.javaproject.rasahotel.services.auth;

import com.javaproject.rasahotel.dto.auth.LoginRequestDto;
import com.javaproject.rasahotel.dto.auth.LoginResponseDto;
import com.javaproject.rasahotel.dto.auth.ResetPasswordRequestDto;

public interface LoginService {

    LoginResponseDto login(LoginRequestDto loginRequestDto);

    void sendForgotPassword(String email);

    void resetPassword(ResetPasswordRequestDto dto);

    void deleteUser(String email);

    void requestDeleteAccount(String email);
}
