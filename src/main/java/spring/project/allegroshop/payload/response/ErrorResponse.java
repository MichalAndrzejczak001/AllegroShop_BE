package spring.project.allegroshop.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record ErrorResponse (
        int status,
        String message,
        String details
){
}
