package com.jobboard.dao.impl;

import com.jobboard.dao.CompanyDAO;
import com.jobboard.entity.Company;
import com.jobboard.exception.DatabaseConnectionException;
import com.jobboard.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements CompanyDAO {

    @Override
    public void insertCompany(Company company) throws DatabaseConnectionException {
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "INSERT INTO Companies (companyName, location) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, company.getCompanyName());
            ps.setString(2, company.getLocation());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database while inserting company: " + e.getMessage());
        }
    }

    @Override
    public List<Company> getAllCompanies() throws DatabaseConnectionException {
        List<Company> companies = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT * FROM Companies";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Company company = new Company();
                company.setCompanyID(rs.getInt("companyID"));
                company.setCompanyName(rs.getString("companyName"));
                company.setLocation(rs.getString("location"));
                companies.add(company);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database while fetching companies: " + e.getMessage());
        }
        return companies;
    }
}
