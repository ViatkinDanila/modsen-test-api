package com.modsen.core.service.logic;

import com.modsen.core.model.entity.Meeting;
import com.modsen.core.repository.MeetingRepository;
import com.modsen.core.repository.sort.SearchParams;
import com.modsen.core.repository.sort.SortParams;
import com.modsen.core.service.exception.InvalidMeetingException;
import com.modsen.core.service.exception.InvalidParamException;
import com.modsen.core.service.exception.NotFoundEntityException;
import com.modsen.core.service.validator.MeetingValidator;
import com.modsen.core.service.validator.ParamsValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingServiceImpl implements MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingValidator meetingValidator;

    @Autowired
    public MeetingServiceImpl(MeetingRepository meetingRepository,
                              MeetingValidator meetingValidator){
        this.meetingRepository = meetingRepository;
        this.meetingValidator = meetingValidator;
    }

    @Override
    @Transactional
    public Meeting create(Meeting meeting) {
        validateMeeting(meeting);
        return meetingRepository.create(meeting);
    }

    @Override
    public List<Meeting> findAll(List<String> sortParams,List<String>  sortTypes,
                                 List<String> searchParams,List<String>  searchInfo) {
        if ( sortParams == null && sortTypes == null &&
                searchInfo == null && searchParams == null){
            return meetingRepository.findAll();
        }

        SortParams sortParamsMap = null;
        if (sortParams != null && sortTypes != null){
            sortParamsMap = new SortParams(sortParams, sortTypes);
        }

        SearchParams searchParamsMap = null;
        if (searchParams != null && searchInfo != null){
            searchParamsMap = new SearchParams(searchParams, searchInfo);
        }

        validateAllParams(sortParams, sortTypes, searchParams);
        List<Meeting> meetings = meetingRepository.findAll(sortParamsMap, searchParamsMap);
        return meetings;
    }

    @Override
    public Meeting findMeetingById(long id) {
        return meetingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("meeting.not.found", id));
    }

    @Override
    @Transactional
    public Meeting updateMeetingById(long id, Meeting meeting) {
        Meeting meetingFromPersistence = meetingRepository.findById(id).
                orElseThrow(() -> new NotFoundEntityException("meeting.not.found", id));
        fillUpdateFields(meeting, meetingFromPersistence);
        return meetingRepository.update(meetingFromPersistence);
    }

    @Override
    public void deleteMeetingById(long id) {
        Meeting meeting = meetingRepository.findById(id).orElseThrow(() -> new NotFoundEntityException("meeting.not.found", id));
        meetingRepository.delete(meeting);
    }

    private void fillUpdateFields(Meeting frontMeetingEntity, Meeting dbMeetingEntity){
        Optional.ofNullable(frontMeetingEntity.getTopic())
                        .ifPresent(meetingTopic -> dbMeetingEntity.setTopic(meetingTopic));
        Optional.ofNullable(frontMeetingEntity.getDescription())
                .ifPresent(meetingDescription -> dbMeetingEntity.setDescription(meetingDescription));
        Optional.ofNullable(frontMeetingEntity.getOrganizer())
                .ifPresent(meetingOrganizer -> dbMeetingEntity.setOrganizer(meetingOrganizer));
        Optional.ofNullable(frontMeetingEntity.getStartDate())
                .ifPresent(meetingStartDate -> dbMeetingEntity.setStartDate(meetingStartDate));
        Optional.ofNullable(frontMeetingEntity.getAddress())
                .ifPresent(meetingAddress -> dbMeetingEntity.setAddress(meetingAddress));
    }

    private void validateMeeting(Meeting meeting){
        if(!meetingValidator.isValid(meeting)){
            throw new InvalidMeetingException("meeting.invalid.data");
        }
    }


    private void validateAllParams(List<String> sortParams, List<String> sortTypes, List<String> searchParams) {
        ParamsValidator paramsValidator = new ParamsValidator();
        if (!paramsValidator.isSortParamsValid(sortParams)){
            throw new InvalidParamException("param.invalid.sort.param");
        }
        if (!paramsValidator.isSortTypesValid(sortTypes)){
            throw new InvalidParamException("param.invalid.sort.type");
        }
        if (!paramsValidator.isSearchParamsValid(searchParams)){
            throw new InvalidParamException("param.invalid.search.param");
        }
    }
}
