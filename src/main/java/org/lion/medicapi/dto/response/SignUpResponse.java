package org.lion.medicapi.dto.response;

import lombok.Data;
import org.lion.medicapi.util.HealthTag;
import org.lion.medicapi.domain.User;
import org.lion.medicapi.domain.UserHealthTag;
import org.lion.medicapi.util.SexType;

import java.util.ArrayList;
import java.util.List;

@Data
public class SignUpResponse {

    private Long id;
    private String email;
    private String nickName;
    private String birthDt;
    private SexType sexType;
    private List<HealthTag> healthTagList = new ArrayList<>();

    public static SignUpResponse of(User user) {
        SignUpResponse response = new SignUpResponse();

        response.id = user.getId();
        response.email = user.getEmail();
        response.nickName = user.getNickName();
        response.birthDt = user.getBirthDt();
        response.sexType = user.getSexType();
        response.healthTagList = user.getUserHealthTagList().stream().map(UserHealthTag::getHealthTag).toList();

        return response;
    }
}
