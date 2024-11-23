package com.jobboard.dao;

import com.jobboard.entity.Applicant;
import com.jobboard.exception.DatabaseConnectionException;

import java.util.List;

public interface ApplicantDAO {

    void insertApplicant(Applicant applicant) throws DatabaseConnectionException;

    List<Applicant> getAllApplicants() throws DatabaseConnectionException;
}

