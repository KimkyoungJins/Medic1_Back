package org.lion.medicapi.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.lion.medicapi.util.SexType;

import java.util.List;

@Entity
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String password;

    private String nickName;

    private String birthDt;

    private SexType sexType;

    @OneToMany
    private List<HealthTag> healthTagList;
}
