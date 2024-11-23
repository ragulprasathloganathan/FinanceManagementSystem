package com.jobboard.dao;

import com.jobboard.entity.JobListing;
import com.jobboard.exception.DatabaseConnectionException;

import java.util.List;

public interface JobListingDAO {

    void insertJobListing(JobListing job) throws DatabaseConnectionException;

    List<JobListing> getAllJobListings() throws DatabaseConnectionException;
}

