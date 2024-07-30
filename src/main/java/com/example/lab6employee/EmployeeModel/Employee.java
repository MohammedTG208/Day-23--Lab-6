package com.example.lab6employee.EmployeeModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {
//    : ID , name , age , position , onLeave, employmentYear and annualLeave
    @NotEmpty(message = "Cannot be null. ")
    @Size(min = 2,message = "Length must be more than 2 characters. ")
    private String id;

    @NotEmpty(message = "Cannot be null. ")
    @Size(min = 4,message = "Length must be more than 4 characters. ")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Enter char only")
    private String name;

    @Pattern(regexp = "^\\S+@\\S+\\.\\S+$",message = "Must be like XXXX@XXX.com")
    private String email;

    @NotNull(message = "Cannot be null. ")
    @Pattern(regexp =  "(05)[0-9]+$",message = "phone number moust start with 05XXXXXXXX")
    @Size(max = 10,message = "Must consists of exactly 10 digits.")
    private String phonenumber;

    @NotNull(message = "Cannot be null.")
    @Positive(message = "Must be a number. ")
    @Min(value = 25,message = "Must be more than 25. ")
    private int age;

    @NotEmpty(message = "Cannot be null. ")
    @Pattern(regexp = "(supervisor|coordinator)+$", message = "Must be either \"supervisor\" or \"coordinator\" only. ")
    private String position;

    @AssertFalse(message = "Must be initially set to false. ")
    private boolean onLeave;

    @NotNull(message = "Cannot be null.")
    @PastOrPresent(message = "should be a date in the past or the present.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    @NotNull(message = "Cannot be null.")
    @Positive(message = "Must be a positive number.")
    private int annualLeave;

}
