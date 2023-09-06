package com.tip.dg4.toeic_exam.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tip.dg4.toeic_exam.common.constants.TExamConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID id;
    private String surname;
    private String name;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = TExamConstant.DATE_FORMAT)
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String image;
    private Integer age;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private UUID accountId;
}
