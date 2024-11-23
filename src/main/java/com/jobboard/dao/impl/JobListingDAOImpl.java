package com.jobboard.dao.impl;

import com.jobboard.dao.JobListingDAO;
import com.jobboard.entity.JobListing;
import com.jobboard.exception.DatabaseConnectionException;
import com.jobboard.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobListingDAOImpl implements JobListingDAO {

    @Override
    public void insertJobListing(JobListing jobListing) throws DatabaseConnectionException {
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "INSERT INTO JobListings (companyID, jobTitle, jobDescription, jobLocation, salary, jobType) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, jobListing.getCompanyID());
            ps.setString(2, jobListing.getJobTitle());
            ps.setString(3, jobListing.getJobDescription());
            ps.setString(4, jobListing.getJobLocation());
            ps.setDouble(5, jobListing.getSalary());
            ps.setString(6, jobListing.getJobType());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database while inserting job listing: " + e.getMessage());
        }
    }

    @Override
    public List<JobListing> getAllJobListings() throws DatabaseConnectionException {
        List<JobListing> jobListings = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT * FROM JobListings";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                JobListing jobListing = new JobListing();
                jobListing.setJobID(rs.getInt("jobID"));
                jobListing.setCompanyID(rs.getInt("companyID"));
                jobListing.setJobTitle(rs.getString("jobTitle"));
                jobListing.setJobDescription(rs.getString("jobDescription"));
                jobListing.setJobLocation(rs.getString("jobLocation"));
                jobListing.setSalary(rs.getDouble("salary"));
                jobListing.setJobType(rs.getString("jobType"));
                jobListings.add(jobListing);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database while fetching job listings: " + e.getMessage());
        }
        return jobListings;
    }
}

