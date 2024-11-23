package com.jobboard.Main;

import com.jobboard.dao.impl.ApplicantDAOImpl;
import com.jobboard.dao.impl.CompanyDAOImpl;
import com.jobboard.dao.impl.JobApplicationDAOImpl;
import com.jobboard.dao.impl.JobListingDAOImpl;
import com.jobboard.entity.Applicant;
import com.jobboard.entity.Company;
import com.jobboard.entity.JobApplication;
import com.jobboard.entity.JobListing;
import com.jobboard.exception.InvalidEmailException;
import com.jobboard.exception.NegativeSalaryException;
import com.jobboard.exception.DatabaseConnectionException;

import java.util.List;
import java.util.Scanner;

public class MainModule {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ApplicantDAOImpl applicantDAO = new ApplicantDAOImpl();
        CompanyDAOImpl companyDAO = new CompanyDAOImpl();
        JobListingDAOImpl jobListingDAO = new JobListingDAOImpl();
        JobApplicationDAOImpl jobApplicationDAO = new JobApplicationDAOImpl();

        System.out.println("Job Board Application");
        System.out.println("1. Create Applicant Profile");
        System.out.println("2. Post Job Listing");
        System.out.println("3. Apply for Job");
        System.out.println("4. View Job Listings");
        System.out.println("Enter your choice:");

        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        try {
            switch (choice) {
                case 1:
                    // Create Applicant Profile
                    Applicant applicant = new Applicant();
                    System.out.println("Enter First Name:");
                    applicant.setFirstName(scanner.nextLine());
                    System.out.println("Enter Last Name:");
                    applicant.setLastName(scanner.nextLine());
                    System.out.println("Enter Email:");
                    String email = scanner.nextLine();
                    if (!email.contains("@")) {
                        throw new InvalidEmailException("Invalid email format.");
                    }
                    applicant.setEmail(email);
                    System.out.println("Enter Phone:");
                    applicant.setPhone(scanner.nextLine());



                    applicantDAO.insertApplicant(applicant);
                    System.out.println("Profile created successfully!");

                    break;

                case 2:
                    // Post Job Listing
                    JobListing job = new JobListing();
                    System.out.println("Enter Company ID:");
                    job.setCompanyID(scanner.nextInt());
                    scanner.nextLine();  // Consume newline
                    System.out.println("Enter Job Title:");
                    job.setJobTitle(scanner.nextLine());
                    System.out.println("Enter Job Description:");
                    job.setJobDescription(scanner.nextLine());
                    System.out.println("Enter Job Location:");
                    job.setJobLocation(scanner.nextLine());
                    System.out.println("Enter Salary:");
                    double salary = scanner.nextDouble();
                    if (salary < 0) {
                        throw new NegativeSalaryException("Salary cannot be negative.");
                    }
                    job.setSalary(salary);
                    scanner.nextLine();  // Consume newline
                    System.out.println("Enter Job Type (Full-time, Part-time):");
                    job.setJobType(scanner.nextLine());

                    jobListingDAO.insertJobListing(job);
                    System.out.println("Job posted successfully!");

                    break;

                case 3:
                    // Apply for Job
                    JobApplication application = new JobApplication();
                    System.out.println("Enter Job ID:");
                    application.setJobID(scanner.nextInt());
                    System.out.println("Enter Applicant ID:");
                    application.setApplicantID(scanner.nextInt());
                    scanner.nextLine();  // Consume newline
                    System.out.println("Enter Cover Letter:");
                    application.setCoverLetter(scanner.nextLine());

                    jobApplicationDAO.insertJobApplication(application);
                    System.out.println("Application submitted su2ccessfully!");

                    break;

                case 4:
                    // View Job Listings
                    List<JobListing> jobListings = jobListingDAO.getAllJobListings();
                    jobListings.forEach(System.out::println);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } catch (InvalidEmailException | NegativeSalaryException | DatabaseConnectionException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}









