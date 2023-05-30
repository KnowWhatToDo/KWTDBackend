package com.KWTD.register.mentor;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Experience {
    private String companyName;
    private String startDate;
    private String endDate;
    private long experience;

    public Experience() {
        // Default constructor
    }

    public Experience(String companyName, String startDate, String endDate) {
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.experience = calculateExperience();
    }

    private long calculateExperience() {
        if (startDate != null && endDate != null) {
            LocalDate localStartDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate localEndDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
            return ChronoUnit.MONTHS.between(localStartDate.withDayOfMonth(1), localEndDate.withDayOfMonth(1));
        }
        return 0;
    }
}
