package org.lion.medicapi.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.lion.medicapi.util.HealthTag;

import java.util.List;

@Data
public class TagRequest {

    @Size(max = 3)
    List<HealthTag> healthTagList;
}
