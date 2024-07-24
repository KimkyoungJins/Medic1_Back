package org.lion.medicapi.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lion.medicapi.util.HealthTag;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "UserHealthTag{" +
                "healthTag=" + healthTag +
                ", userId=" + user.getId() +
                '}';
    }

    public UserHealthTag(HealthTag healthTag, User user) {
        this.healthTag = healthTag;
        this.setUser(user);
    }

    private void setUser(User user) {
        this.user = user;
        user.getUserHealthTagList().add(this);
    }
}
