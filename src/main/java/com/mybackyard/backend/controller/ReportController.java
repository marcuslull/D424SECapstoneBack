package com.mybackyard.backend.controller;

import com.mybackyard.backend.model.User;
import com.mybackyard.backend.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
public class ReportController {

    private final UserRepository userRepository;

    public ReportController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/report/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void userReport(HttpServletResponse response) throws IOException {

        Iterable<User> users = userRepository.findAll();
        CSVPrinter csvPrinter = new CSVPrinter(response.getWriter(), CSVFormat.DEFAULT);
        csvPrinter.printRecord("Users Report");
        csvPrinter.printRecord(LocalDateTime.now());
        for (User user : users) {
            csvPrinter.printRecord(user.getUserId(), user.getFirst(), user.getLast(), user.getEmail());
        }
        csvPrinter.flush();
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=my_data.csv");
    }

    @GetMapping("/report/logs")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void getlogs(HttpServletResponse response) throws IOException {

        File logfile = new File("logs/mbyInfo.log");
        if (!logfile.exists()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition", "attachment; filename=mbyInfo.log");
        Files.copy(logfile.toPath(), response.getOutputStream());
    }
}
