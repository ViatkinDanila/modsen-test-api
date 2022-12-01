package com.modsen.test.repository;

import com.modsen.core.model.entity.Meeting;
import com.modsen.core.repository.MeetingRepository;
import com.modsen.core.repository.sort.SearchParams;
import com.modsen.core.repository.sort.SortParams;
import com.modsen.test.config.JpaEmbeddedDBConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = JpaEmbeddedDBConfig.class)
@Transactional
public class MeetingRepositoryTest {

    private static final Meeting MEETING_TO_CREATE = new Meeting(
        "topic 4", "description 4", "organizer 4", Instant.now(), "address 4"
    );

    private static final Meeting MEETING_TO_UPDATE = new Meeting(
        1L, "topic update", "description 1", "organizer 1",
            Instant.parse("2021-01-01T01:11:10.00Z"), "address update"
    );

    private static final Meeting FIRST_MEETING = new Meeting(
            1L,"topic 1", "description 1", "organizer 1",
            Instant.parse("2021-01-01T01:11:10.00Z"), "address 1"
    );
    private static final Meeting SECOND_MEETING = new Meeting(
            2L,"topic 2", "description 2", "organizer 2",
            Instant.parse("2022-02-02T02:22:20.00Z"), "address 2"
    );
    private static final Meeting THIRD_MEETING = new Meeting(
            3L,"topic 3", "description 3", "organizer 3",
            Instant.parse("2023-03-03T03:33:30.00Z"), "address 3"
    );
    private static final Meeting FOURTH_MEETING = new Meeting(
            4L,"topic 3", "description 3", "organizer 4",
            Instant.parse("2024-04-04T04:44:40.00Z"), "address 3"
    );
    private static final Meeting FIFTH_MEETING = new Meeting(
            5L,"topic 3", "description 3", "organizer 3",
            Instant.parse("2023-03-03T03:33:30.00Z"), "address 3"
    );

    private final MeetingRepository meetingRepository;

    @Autowired
    public MeetingRepositoryTest(MeetingRepository meetingRepository){
        this.meetingRepository = meetingRepository;
    }

    @Test
    public void givenMeeting_whenCreate_thenMeetingExist(){

        meetingRepository.create(MEETING_TO_CREATE);
        Optional<Meeting> meeting = meetingRepository
                .findById(MEETING_TO_CREATE.getId());
        MEETING_TO_CREATE.setId(meeting.get().getId());
        Assertions.assertTrue(meeting.isPresent());
    }

    @Test
    public void givenSortParams_whenFindAll_thenReturnAllSorted(){
        SortParams sortParams = new SortParams(Arrays.asList("topic"), Arrays.asList("ASC"));

        List<Meeting> meetings = meetingRepository.findAll( sortParams, null);

        Assertions.assertEquals(Arrays.asList(FIRST_MEETING.getTopic(), SECOND_MEETING.getTopic(),THIRD_MEETING.getTopic()),
                Arrays.asList(meetings.get(0).getTopic(),meetings.get(1).getTopic(),meetings.get(2).getTopic()));
    }

    @Test
    public void givenMultipleSortAndFilterCondition_whenFindAll_thenReturnAllSortedAndFiltered(){
        SortParams sortParams = new SortParams(Arrays.asList("topic", "startDate"), Arrays.asList("ASC", "DESC"));
        SearchParams searchParams = new SearchParams(Arrays.asList("organizer"), Arrays.asList("organizer 3"));
        List<Meeting> meetings = meetingRepository.findAll(sortParams, searchParams);

        Assertions.assertEquals(Arrays.asList(FIFTH_MEETING.getTopic(), THIRD_MEETING.getTopic()),
                Arrays.asList(meetings.get(0).getTopic(),meetings.get(1).getTopic()));
    }

    @Test
    public void givenSearchParams_whenFindAll_thenReturnAllFiltered(){
        SearchParams searchParams = new SearchParams(Arrays.asList("topic"), Arrays.asList("topic 3"));

        List<Meeting> meetings = meetingRepository.findAll( null, searchParams);

        Assertions.assertEquals(Arrays.asList(THIRD_MEETING.getTopic(), FOURTH_MEETING.getTopic(),FIFTH_MEETING.getTopic()),
                Arrays.asList(meetings.get(0).getTopic(),meetings.get(1).getTopic(),meetings.get(2).getTopic()));
    }

    @Test
    public void givenIdAndUpdateParams_whenUpdateAndGetById_thenUpdateById(){
        meetingRepository.update(MEETING_TO_UPDATE);
        Optional<Meeting> meetingOptional = meetingRepository.findById(FIRST_MEETING.getId());
        Assertions.assertEquals(meetingOptional.get().getTopic(), "topic update");
        Assertions.assertEquals(meetingOptional.get().getAddress(), "address update");
    }
}
