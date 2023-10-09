package talkwith.semogong.join.basic.service;

import talkwith.semogong.util.response.ServiceApiResponse;

public interface BasicJoinService {
    ServiceApiResponse validateForm(String email, String password, String name);
    ServiceApiResponse sendVerificationCode(String email);
    boolean validateVerification(String email, String code);

    ServiceApiResponse register(String email, String password, String name);
}
