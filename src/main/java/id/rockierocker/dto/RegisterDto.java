package id.rockierocker.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class RegisterDto {

    @ApiModelProperty(example = "Ikhsan Fadly")
    @NotNull(message = "Name mandatory max 60")
    @NotEmpty(message = "Name mandatory max 60")
    @Size(max = 60,message = "Name mandatory max 60")
    private String name;

    @ApiModelProperty(example = "081247984970")
    @NotNull(message = "Phone number mandatory min 10 max 13 must started with 08")
    @NotEmpty(message = "Phone number mandatory min 10 max 13 must started with 08")
    @Size(max = 13,min = 10,message = "Phone number mandatory min 10 max 13 must started with 08")
    @Pattern(regexp = "^(08).*$",message = "Phone number mandatory min 10 max 13 must started with 08",flags = Pattern.Flag.CASE_INSENSITIVE)
    private String phoneNumber;

    @ApiModelProperty(example = "passworD")
    @NotEmpty(message = "Password mandatory min 6, max 16, containing at least 1 capital letter")
    @NotNull(message = "Password mandatory min 6, max 16, containing at least 1 capital letter")
    @Size(max = 16,min = 6,message = "Password mandatory min 6, max 16, containing at least 1 capital letter")
    //@Pattern(regexp = "^(?=.*[A-Z])$",message = "Password mandatory min 6, max 16, containing at least 1 capital letter",flags = Pattern.Flag.CASE_INSENSITIVE)
    private String password;
}
