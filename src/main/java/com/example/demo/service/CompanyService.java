package com.example.demo.service;

import com.example.demo.domain.Company;
import com.example.demo.domain.DTO.Meta;
import com.example.demo.domain.DTO.ResultPaginationDTO;
import com.example.demo.repository.CompanyRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public ResultPaginationDTO gettAllCompany(Specification<Company> specification, Pageable pageable) {
        Page<Company> companies = this.companyRepository.findAll(specification, pageable);
        ResultPaginationDTO resultPaginationDTO = new ResultPaginationDTO();
        Meta meta = new Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());

        meta.setPages(companies.getTotalPages());
        meta.setTotal(companies.getTotalElements());

        resultPaginationDTO.setMeta(meta);
        resultPaginationDTO.setResult(companies.getContent());

        return resultPaginationDTO;
    }

    public Company saveCompany(Company company) {
        return companyRepository.save(company);
    }

    public Company getCompanyById(long id) {
        return this.companyRepository.getById(id);
    }

    public Company updateCompany(Company company) {
        Company currentCompany = this.getCompanyById(company.getId());
        if (currentCompany != null) {
            currentCompany.setName(company.getName());
            currentCompany.setAddress(company.getAddress());
            currentCompany.setDescription(company.getDescription());
            currentCompany.setLogo(company.getLogo());
            currentCompany = this.companyRepository.save(currentCompany);
        }
        return currentCompany;
    }

    public void deleteCompany(long id) {
        this.companyRepository.deleteById(id);
    }
}
