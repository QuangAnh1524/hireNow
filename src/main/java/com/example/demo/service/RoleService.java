package com.example.demo.service;

import com.example.demo.domain.Permission;
import com.example.demo.domain.Role;
import com.example.demo.domain.Skill;
import com.example.demo.domain.response.ResultPaginationDTO;
import com.example.demo.repository.PermissionRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public boolean existByName(String name) {
        return this.roleRepository.existsByName(name);
    }

    public Role getById(long id) {
        Optional<Role> roleOptional = this.roleRepository.findById(id);
        return roleOptional.orElse(null);
    }

    public Role create(Role role) {
        //check permission
        if (role.getPermissions() != null) {
            List<Long> permissions = role.getPermissions()
                    .stream().map(Permission::getId).toList();
            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(permissions);
            role.setPermissions(dbPermissions);
        }
        return this.roleRepository.save(role);
    }

    public Role update(Role role) {
        Role role1 = this.getById(role.getId());
        //check permission
        if (role.getPermissions() != null) {
            List<Long> permissions = role.getPermissions()
                    .stream().map(Permission::getId).toList();
            List<Permission> dbPermissions = this.permissionRepository.findByIdIn(permissions);
            role.setPermissions(dbPermissions);
        }
        role1.setName((role.getName()));
        role1.setDescription(role.getDescription());
        role1.setActive(role.isActive());
        role1.setPermissions(role.getPermissions());
        role1 = this.roleRepository.save(role1);
        return role1;
    }

    public void delete(long id) {
        this.roleRepository.deleteById(id);
    }

    public ResultPaginationDTO getAllRoles(Specification<Role> spec, Pageable pageable) {
        Page<Role> rolePage = this.roleRepository.findAll(spec, pageable);

        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPage(rolePage.getTotalPages());
        meta.setTotal(rolePage.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(rolePage.getContent());

        return resultPaginationDTO;
    }
}
