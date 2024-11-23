package com.jobboard.dao;

import com.jobboard.entity.JobApplication;
import com.jobboard.exception.DatabaseConnectionException;

import java.util.List;

public interface JobApplicationDAO {

    void insertJobApplication(JobApplication application) throws DatabaseConnectionException;

    List<JobApplication> getApplicationsForJob(int jobID) throws DatabaseConnectionException;  // Make sure this line includes the `throws DatabaseConnectionException`
}

