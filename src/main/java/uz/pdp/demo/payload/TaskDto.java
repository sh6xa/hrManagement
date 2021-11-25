package uz.pdp.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.demo.enums.TaskStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    @NotNull
    private String name;

    private String description;

    @NotNull
    private Timestamp deadline;

    @Email
    @NotNull
    private String userEmail;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;
}
