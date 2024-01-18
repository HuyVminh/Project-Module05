package com.ra.controller.admin;

import com.ra.model.entity.Role;
import com.ra.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api.myservice.com/v1/admin")
public class RoleController {
    @Autowired
    private IRoleService roleService;
    @GetMapping("/roles")
    public ResponseEntity<?> getRoles(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "5") int limit,
                                      @RequestParam(name = "sort",defaultValue = "id") String sort,
                                      @RequestParam(name = "order",defaultValue = "desc") String order){
        Pageable pageable;
        if(order.equals("desc")){
            pageable = PageRequest.of(page,limit, Sort.by(sort).descending());
        }else {
            pageable = PageRequest.of(page,limit, Sort.by(sort).ascending());
        }
        Page<Role> roles = roleService.findAll(pageable);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
