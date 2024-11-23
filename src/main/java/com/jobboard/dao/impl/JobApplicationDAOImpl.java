package com.jobboard.dao.impl;

import com.jobboard.dao.JobApplicationDAO;
import com.jobboard.entity.JobApplication;
import com.jobboard.exception.DatabaseConnectionException;
import com.jobboard.util.DBConnUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JobApplicationDAOImpl implements JobApplicationDAO {

    @Override
    public void insertJobApplication(JobApplication application) throws DatabaseConnectionException {
        try (Connection conn = DBConnUtil.getConnection()) {
            // Ensure that applicationDate is not null; if it is, set it to current date
            LocalDate applicationDate = application.getApplicationDate();
            if (applicationDate == null) {
                applicationDate = LocalDate.now(); // Use current date if null
            }

            String query = "INSERT INTO Applications (jobID, applicantID, applicationDate, coverLetter) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, application.getJobID());
            ps.setInt(2, application.getApplicantID());
            ps.setDate(3, Date.valueOf(applicationDate));  // Use the non-null applicationDate
            ps.setString(4, application.getCoverLetter());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database while inserting job application: " + e.getMessage());
        }
    }

    @Override
    public List<JobApplication> getApplicationsForJob(int jobID) throws DatabaseConnectionException {
        List<JobApplication> applications = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT * FROM Applications WHERE jobID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, jobID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                JobApplication application = new JobApplication();
                application.setApplicationID(rs.getInt("applicationID"));
                application.setJobID(rs.getInt("jobID"));
                application.setApplicantID(rs.getInt("applicantID"));
                // Ensure that applicationDate is not null before converting to LocalDate
                Date sqlDate = rs.getDate("applicationDate");
                if (sqlDate != null) {
                    application.setApplicationDate(sqlDate.toLocalDate());
                }
                application.setCoverLetter(rs.getString("coverLetter"));
                applications.add(application);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database while fetching job applications: " + e.getMessage());
        }
        return applications;
    }
}
