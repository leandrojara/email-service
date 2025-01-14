package com.leandrojara.emailservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequest {
    private String senderName;
    private String subject;
    private String body;
    private List<String> recipients;
    private List<String> attachments;
}
