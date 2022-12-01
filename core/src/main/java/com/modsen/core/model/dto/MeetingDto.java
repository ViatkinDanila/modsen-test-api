package com.modsen.core.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingDto extends RepresentationModel<MeetingDto> {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;
    @NotNull(message = "meeting.invalid.topic")
    private String topic;
    private String description;
    @NotNull(message = "meeting.invalid.organizer")
    private String organizer;
    @NotNull(message = "meeting.invalid.start.date")
    private Instant startDate;
    @NotNull(message = "meeting.invalid.address")
    private String address;
}
