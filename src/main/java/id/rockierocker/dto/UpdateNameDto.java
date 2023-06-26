package id.rockierocker.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UpdateNameDto {
    @NotNull(message = "Name mandatory max 60")
    @NotEmpty(message = "Name mandatory max 60")
    @Size(max = 60,message = "Name mandatory max 60")
    private String name;
}
