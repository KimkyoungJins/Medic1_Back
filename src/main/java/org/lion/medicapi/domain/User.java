package org.lion.medicapi.domain;

import jakarta.persistence.*;
import lombok.*;
import org.lion.medicapi.util.SexType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
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

    private String password;

    private String nickName;

    private String birthDt;

    @Enumerated(value = EnumType.STRING)
    private SexType sexType;

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserHealthTag> userHealthTagList = new ArrayList<>();
}
