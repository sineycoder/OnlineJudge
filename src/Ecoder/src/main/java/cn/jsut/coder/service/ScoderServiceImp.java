package cn.jsut.coder.service;

import cn.jsut.coder.dao.UserMapper;
import cn.jsut.coder.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author:
 * @date:2018/1/20 16:34
 * @version:
 * @copyright:
 */
@Service
public class ScoderServiceImp implements ScoderService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewUser(User user) throws Exception {
        userMapper.insertUser(user);
    }
}
