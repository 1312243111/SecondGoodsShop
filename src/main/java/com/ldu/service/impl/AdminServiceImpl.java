package com.ldu.service.impl;

import com.ldu.dao.AdminMapper;
import com.ldu.pojo.Admin;
import com.ldu.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value="adminService")
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin findAdmin(Long phone, String password) {
        // TODO Auto-generated method stub
        return adminMapper.findAdmin(phone,password);
    }

    @Override
    public Admin findAdminById(Integer id) {
        // TODO Auto-generated method stub
        return adminMapper.findAdminById(id);
    }

    @Override
    public void updateAdmin(Admin admins) {
        adminMapper.updateAdmin(admins);
    }
}
