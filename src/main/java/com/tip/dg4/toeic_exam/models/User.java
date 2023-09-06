package com.tip.dg4.toeic_exam.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    @Field(targetType = FieldType.STRING)
    private UUID id;
    private String surname;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String address;
    private String phone;
    private String image;
    private Integer age;
    @Field(targetType = FieldType.STRING)
    private UUID accountId;
}
