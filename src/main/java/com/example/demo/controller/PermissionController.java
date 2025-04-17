package com.example.demo.controller;

import com.example.demo.domain.Permission;
import com.example.demo.domain.response.ResultPaginationDTO;
import com.example.demo.service.PermissionService;
import com.example.demo.service.exception.idInvalidException;
import com.example.demo.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    @ApiMessage("Create a permission")
    public ResponseEntity<Permission> createPermission(@Valid @RequestBody Permission permission) throws idInvalidException {
        //check exist
        if (this.permissionService.isPermissionExist(permission)) {
            throw new idInvalidException("Permission đã tồn tại");
        }
        //create
        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.create(permission));
    }

    @PutMapping("/permissions")
    @ApiMessage("Update a permission")
    public ResponseEntity<Permission> updatePermission(@Valid @RequestBody Permission permission) throws idInvalidException {
        //check exist by module, apiPath and method
        if (this.permissionService.isPermissionExist(permission)) {
            //check name
            if (this.permissionService.isSameName(permission)) {
                throw new idInvalidException("Permission đã tồn tại");
            }
            throw new idInvalidException("Permission đã tồn tại");
        }

       //check exist by id
        if (this.permissionService.getById(permission.getId()) == null) {
            throw new idInvalidException("Permision với id = " + permission.getId() + " không tồn tại");
        }

        //update
        return ResponseEntity.ok().body(this.permissionService.update(permission));
    }

    @DeleteMapping("/permissions/{id}")
    @ApiMessage("Delete a permission")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) throws idInvalidException {
        //check exist by id
        if (this.permissionService.getById(id) == null) {
            throw new idInvalidException("Permision với id = " + id + " không tồn tại");
        }
        this.permissionService.delete(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/permissions")
    @ApiMessage("Fetch permissions")
    public ResponseEntity<ResultPaginationDTO> getPermissions(@Filter Specification<Permission> specification, Pageable pageable) {
//        System.out.println(">>> Gọi xong findAll trong getPermissions");
        return ResponseEntity.ok(this.permissionService.getPermissions(specification, pageable));
    }
}
