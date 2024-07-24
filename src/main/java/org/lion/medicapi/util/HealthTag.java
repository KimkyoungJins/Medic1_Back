package org.lion.medicapi.util;

import lombok.Getter;

@Getter
public enum HealthTag {
    JOINT_BONE_HEALTH("관절/뼈 건강"),
    MEMORY_IMPROVEMENT("기억력 개선"),
    BLOOD_SUGAR_CONTROL("혈당 조절"),
    DECREASE_BODY_FIT("체지방 감소"),
    SKIN_HEALTH("피부 건강"),
    ABILITY_OF_PERFORM_EXERCISE("운동수행 능력"),
    IMMUNE_FUNCTION_IMPROVEMENT("면역기능 개선"),
    BLOOD_TRIGLYCERIDES_IMPROVEMENT("혈중 중성지방 개선");

    private final String name;

    HealthTag(String name) {
        this.name = name;
    }
}
