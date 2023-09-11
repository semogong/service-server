package talkwith.semogong.login.basic.service.impl;

import org.springframework.stereotype.Service;
import talkwith.semogong.login.basic.service.BasicLoginService;

import javax.transaction.Transactional;

@Service
@Transactional
public class BasicLoginServiceImpl implements BasicLoginService {
    @Override
    public String test() {
        return "test";
    }
}
