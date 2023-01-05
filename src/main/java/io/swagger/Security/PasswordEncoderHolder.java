package io.swagger.Security;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderHolder {

    public static PasswordEncoder passwordEncoder;

    public static void SetPasswordEncoder(PasswordEncoder encoder) {
        PasswordEncoderHolder.passwordEncoder = encoder;
    }
}
