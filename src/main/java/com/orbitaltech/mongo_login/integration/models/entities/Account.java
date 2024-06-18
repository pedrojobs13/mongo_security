package com.orbitaltech.mongo_login.integration.models.entities;


import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "account")
public class Account {

    @Id
    private String accountNumber;
    private LocalDateTime createAt;
    private Boolean enable;

}
