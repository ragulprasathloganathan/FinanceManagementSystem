package com.jobboard.dao;

import com.jobboard.entity.Company;
import com.jobboard.exception.DatabaseConnectionException;

import java.util.List;

public interface CompanyDAO {

    void insertCompany(Company company) throws DatabaseConnectionException;

    List<Company> getAllCompanies() throws DatabaseConnectionException;
}
