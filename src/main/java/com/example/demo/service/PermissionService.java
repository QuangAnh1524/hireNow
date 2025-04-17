package com.example.demo.service;

import com.example.demo.domain.Permission;
import com.example.demo.domain.Skill;
import com.example.demo.domain.response.ResultPaginationDTO;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {
    private final PermissionRepository permissionRepository;
    private final UserRepository userRepository;

    public PermissionService(PermissionRepository permissionRepository, UserRepository userRepository) {
        this.permissionRepository = permissionRepository;
        this.userRepository = userRepository;
    }

    public boolean isPermissionExist(Permission permission) {
        return permissionRepository.existsByModuleAndApiPathAndMethod(permission.getModule(), permission.getApiPath(), permission.getMethod());
    }

    public boolean isSameName(Permission permission) {
        Permission permissionDB = this.getById(permission.getId());
        if (permissionDB != null) {
            if (permissionDB.getName().equals(permission.getName()))
                return true;
        }
        return false;
    }

    public Permission create(Permission permission) {
        return this.permissionRepository.save(permission);
    }

    public Permission getById(long id) {
        Optional<Permission> permissionOptional = this.permissionRepository.findById(id);
        return permissionOptional.orElse(null);
    }

    public Permission update(Permission permission) {
        Permission permission1 = this.getById(permission.getId());
        if (permission1 != null) {
            permission1.setName(permission.getName());
            permission1.setApiPath(permission.getApiPath());
            permission1.setMethod(permission.getMethod());
            permission1.setModule(permission.getModule());
            //update
            permission1 = this.permissionRepository.save(permission1);
            return permission1;
        }
        return null;
    }

    public void delete(long id) {
        //delete permission_role
        Optional<Permission> permissionOptional = this.permissionRepository.findById(id);
        Permission currentPermission = permissionOptional.get();
        currentPermission.getRoles().forEach(role -> role.getPermissions().remove(currentPermission));
        //delete permission
        this.permissionRepository.delete(currentPermission);
    }

    public ResultPaginationDTO getPermissions(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> permissions = this.permissionRepository.findAll(spec, pageable);

        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPage(permissions.getTotalPages());
        meta.setTotal(permissions.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(permissions.getContent());

        return resultPaginationDTO;
    }

    public List<Permission> getPermissionsByUserEmail(String email) {
        return userRepository.findByEmail(email)
                .filter(user -> user.getRole() != null)
                .map(user -> user.getRole().getPermissions())
                .orElse(List.of());
    }
}
