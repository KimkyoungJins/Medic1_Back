package org.lion.medicapi.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHealthTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "health_tag")
    @Enumerated(value = EnumType.STRING)
    private HealthTag healthTag;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public UserHealthTag(HealthTag healthTag, User user) {
        this.healthTag = healthTag;
        this.setUser(user);
    }

    private void setUser(User user) {
        this.user = user;
        user.getUserHealthTagList().add(this);
    }
}
