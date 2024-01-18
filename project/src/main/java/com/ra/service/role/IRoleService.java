package com.ra.service.role;

import com.ra.model.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoleService {
    Role findByRoleName(String roleName);
    Page<Role> findAll(Pageable pageable);
}
