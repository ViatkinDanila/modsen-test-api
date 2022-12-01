package com.modsen.test.service;

import com.modsen.core.model.entity.Meeting;
import com.modsen.core.repository.MeetingRepository;
import com.modsen.core.repository.impl.MeetingRepositoryImpl;
import com.modsen.core.service.exception.InvalidMeetingException;
import com.modsen.core.service.exception.NotFoundEntityException;
import com.modsen.core.service.logic.MeetingService;
import com.modsen.core.service.logic.MeetingServiceImpl;
import com.modsen.core.service.validator.MeetingValidator;
import com.modsen.core.service.validator.ParamsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MeetingServiceTest {
    private static final long ID = 1;
    private static final Meeting MEETING = new Meeting(ID, "topic", "description", "organizer",

            Instant.now(), "address");
    private MeetingValidator meetingValidator;
    private MeetingRepository meetingRepository;
    private MeetingService meetingService;
    private ParamsValidator paramsValidator;

    @BeforeEach
    public void init() {
        meetingRepository = Mockito.mock(MeetingRepositoryImpl.class);
        meetingValidator = Mockito.mock(MeetingValidator.class);
        meetingService = new MeetingServiceImpl(meetingRepository, meetingValidator);
        paramsValidator = Mockito.mock(ParamsValidator.class);
    }

    @Test
    public void testCreateWhenValid(){
        when(meetingValidator.isValid(any())).thenReturn(true);
        meetingService.create(MEETING);
        verify(meetingRepository).create(MEETING);
    }

    @Test
    public void testCreateThrowInvalidCertificateExceptionWhenNotValid(){
        when(meetingValidator.isValid(any())).thenReturn(false);
        assertThrows(InvalidMeetingException.class, () -> meetingService.create(MEETING));
    }


    @Test
    public void testFindByIdShouldReturnWhenFind(){
        when(meetingRepository.findById(ID)).thenReturn(Optional.of(MEETING));
        meetingService.findMeetingById(ID);
        verify(meetingRepository).findById(ID);
    }

    @Test
    public void testFindByIdShouldThrowNotFoundException(){
        when(meetingRepository.findById(ID+1)).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> meetingService.findMeetingById(ID+1));
    }

    @Test
    public void testFindAllShouldGetAll(){
        meetingService.findAll(null, null, null, null);
        verify(meetingRepository).findAll();
    }


    @Test
    public void testFindAllShouldReturnAllSortedSearched(){
        when(paramsValidator.isSortParamsValid(any())).thenReturn(true);
        when(paramsValidator.isSortTypesValid(any())).thenReturn(false);
        when(paramsValidator.isSearchParamsValid(any())).thenReturn(false);
        meetingService.findAll(Arrays.asList("topic"),
                Arrays.asList("asc"), Arrays.asList("organizer"), Arrays.asList("organizer"));
        verify(meetingRepository).findAll(any(), any());
    }


    @Test
    public void testUpdateByIdShouldThrowNotFoundException(){
        when(meetingRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> meetingService.updateMeetingById(ID+100, MEETING));
    }

    @Test
    public void testUpdateByIdWhenValid(){
        when(meetingRepository.findById(anyLong())).thenReturn(Optional.of(MEETING));
        meetingService.updateMeetingById(ID, MEETING);
        verify(meetingRepository).update(MEETING);
    }

    @Test
    public void testDeleteByIdShouldDelete(){
        when(meetingRepository.findById(anyLong())).thenReturn(Optional.of(MEETING));
        meetingService.deleteMeetingById(ID);
        verify(meetingRepository).delete(any());
    }

    @Test
    public void testDeleteByIdShouldThrowNotFoundExceptionWhenWrongId(){
        when(meetingRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundEntityException.class, () -> meetingService.deleteMeetingById(anyLong()));
    }

}
