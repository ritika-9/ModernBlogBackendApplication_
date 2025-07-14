package com.ritika.blog.payloads;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Data
public class FileResponse {
    // Getters and Setters
    private String fileName;
    private String message;

    public FileResponse() {}

    public FileResponse(String fileName, String message) {
        this.fileName = fileName;
        this.message = message;
    }

}

