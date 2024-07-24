package org.lion.medicapi.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.lion.medicapi.util.HealthTag;
import org.lion.medicapi.util.SexType;

import java.util.List;

@Data
public class SignInRequest {

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{1,8}",
            message = "비밀번호는 영문과 숫자가 포함된 1자 ~ 8자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank
    @Size(max = 4)
    private String nickName;

    @NotBlank
    private String birthDt;

    @NotNull
    private SexType sexType;

    @NotNull
    @Size(min = 1, max = 3)
    private List<HealthTag> healthTagList;
}
