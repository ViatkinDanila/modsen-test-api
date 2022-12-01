package com.modsen.core.model.dto.converter;

import com.modsen.core.model.dto.MeetingDto;
import com.modsen.core.model.entity.Meeting;
import org.springframework.stereotype.Component;

@Component
public class MeetingConverter implements Converter<Meeting, MeetingDto>{
    @Override
    public Meeting convertToEntity(MeetingDto dto) {
        Meeting meeting = new Meeting();
        meeting.setTopic(dto.getTopic());
        meeting.setAddress(dto.getAddress());
        meeting.setDescription(dto.getDescription());
        meeting.setOrganizer(dto.getOrganizer());
        meeting.setStartDate(dto.getStartDate());
        return meeting;
    }

    @Override
    public MeetingDto convertToDto(Meeting entity) {
        MeetingDto meeting = new MeetingDto();
        meeting.setId(entity.getId());
        meeting.setTopic(entity.getTopic());
        meeting.setAddress(entity.getAddress());
        meeting.setDescription(entity.getDescription());
        meeting.setOrganizer(entity.getOrganizer());
        meeting.setStartDate(entity.getStartDate());
        return meeting;
    }
}
