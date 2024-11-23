package main.java.com.jobboard.dao.impl;

import com.jobboard.dao.ApplicantDAO;
import com.jobboard.entity.Applicant;
import com.jobboard.exception.DatabaseConnectionException;
import com.jobboard.util.DBConnUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicantDAOImpl implements ApplicantDAO {

    @Override
    public void insertApplicant(Applicant applicant) throws DatabaseConnectionException {
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "INSERT INTO Applicants (firstName, lastName, email, phone, resume) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, applicant.getFirstName());
            ps.setString(2, applicant.getLastName());
            ps.setString(3, applicant.getEmail());
            ps.setString(4, applicant.getPhone());
            ps.setString(5, applicant.getResume());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database while inserting applicant: " + e.getMessage());
        }
    }

    @Override
    public List<Applicant> getAllApplicants() throws DatabaseConnectionException {
        List<Applicant> applicants = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection()) {
            String query = "SELECT * FROM Applicants";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Applicant applicant = new Applicant();
                applicant.setApplicantID(rs.getInt("applicantID"));
                applicant.setFirstName(rs.getString("firstName"));
                applicant.setLastName(rs.getString("lastName"));
                applicant.setEmail(rs.getString("email"));
                applicant.setPhone(rs.getString("phone"));
                applicant.setResume(rs.getString("resume"));
                applicants.add(applicant);
            }
        } catch (SQLException e) {
            throw new DatabaseConnectionException("Error connecting to the database while fetching applicants: " + e.getMessage());
        }
        return applicants;
    }
}



