package com.example.demo.service;

import com.example.demo.domain.Company;
import com.example.demo.repository.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> gettAllCompany() {
        return this.companyRepository.findAll();
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
            currentCompany.setCreatedBy(company.getCreatedBy());
            currentCompany.setCreatedAt(company.getCreatedAt());
            currentCompany.setUpdatedBy(company.getUpdatedBy());
            currentCompany.setUpdateAt(company.getUpdateAt());
            currentCompany = this.companyRepository.save(currentCompany);
        }
        return currentCompany;
    }

    public void deleteCompany(long id) {
        this.companyRepository.deleteById(id);
    }
}
