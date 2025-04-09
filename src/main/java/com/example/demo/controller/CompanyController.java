package com.example.demo.controller;

import com.example.demo.domain.Company;
import com.example.demo.domain.RestReponse;
import com.example.demo.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping("/companies")
    public ResponseEntity<List<Company>> getAllCompany() {
        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.gettAllCompany());
    }

    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
        Company company1 = this.companyService.saveCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(company1);
    }

    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company) {
        Company company1 = this.companyService.updateCompany(company);
        return ResponseEntity.ok(company1);
    }

    @DeleteMapping("/companies/{id}")
    public ResponseEntity<RestReponse<String>> deleteCompany(@PathVariable("id") long id) {
        this.companyService.deleteCompany(id);
        RestReponse<String> response = new RestReponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Delete Successfully");
        response.setError(null);
        response.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
