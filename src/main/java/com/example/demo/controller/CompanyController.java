package com.example.demo.controller;

import com.example.demo.domain.Company;
import com.example.demo.domain.User;
import com.example.demo.domain.response.ResultPaginationDTO;
import com.example.demo.domain.response.RestReponse;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CompanyService;
import com.example.demo.util.annotation.ApiMessage;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyController(CompanyService companyService, CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyService = companyService;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    //get all company
    @GetMapping("/companies")
    @ApiMessage("Fetch companies")
    public ResponseEntity<ResultPaginationDTO> getAllCompany(
            @Filter Specification<Company> spec, Pageable pageable) {
//            @RequestParam("current") Optional<String> currentOptional,
//            @RequestParam("pageSize") Optional<String> pageSizeOptional) {
//        String sCurrent = currentOptional.isPresent() ? currentOptional.get() : "";
//        String sPageSize = pageSizeOptional.isPresent() ? pageSizeOptional.get() : "";
//        Pageable pageable = PageRequest.of(Integer.parseInt(sCurrent) - 1, Integer.parseInt(sPageSize));

        return ResponseEntity.status(HttpStatus.OK).body(this.companyService.gettAllCompany(spec, pageable));
    }

    //create company
    @PostMapping("/companies")
    public ResponseEntity<Company> createCompany(@Valid @RequestBody Company company) {
        Company company1 = this.companyService.saveCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(company1);
    }

    //update company
    @PutMapping("/companies")
    public ResponseEntity<Company> updateCompany(@Valid @RequestBody Company company) {
        Company company1 = this.companyService.updateCompany(company);
        return ResponseEntity.ok(company1);
    }

    //delete company
    @DeleteMapping("/companies/{id}")
    public ResponseEntity<RestReponse<String>> deleteCompany(@PathVariable("id") long id) {
        Optional<Company> companyOptional = this.companyRepository.findById(id);
        if (companyOptional.isPresent()) {
            Company company = companyOptional.get();
            //fetch all user belong this company
            List<User> userList = this.userRepository.findByCompany(company);
            this.userRepository.deleteAll(userList);
        }
        this.companyService.deleteCompany(id);
        RestReponse<String> response = new RestReponse<>();
        response.setStatusCode(HttpStatus.OK.value());
        response.setMessage("Delete Successfully");
        response.setError(null);
        response.setData(null);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
