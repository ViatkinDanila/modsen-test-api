package com.modsen.core.service.logic;

import com.modsen.core.model.entity.Meeting;

import java.util.List;

public interface MeetingService {
    Meeting create(Meeting meeting);
    List<Meeting> findAll(List<String> sortParams, List<String> sortTypes, List<String> searchParams, List<String> searchInfo);
    Meeting findMeetingById(long id);
    Meeting updateMeetingById(long id, Meeting meeting);
    void deleteMeetingById(long id);
}
