package com.modsen.api.controller;

import com.modsen.api.link.MeetingLinkBuilder;
import com.modsen.core.model.dto.MeetingDto;
import com.modsen.core.model.dto.converter.MeetingConverter;
import com.modsen.core.model.entity.Meeting;
import com.modsen.core.service.logic.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/meetings")
public class MeetingController {
    private final MeetingService meetingService;
    private final MeetingConverter meetingConverter;
    private final MeetingLinkBuilder meetingLinkBuilder;


    @Autowired
    public MeetingController(MeetingService meetingService,
                             MeetingConverter meetingConverter,
                             MeetingLinkBuilder meetingLinkBuilder){
        this.meetingService = meetingService;
        this.meetingConverter = meetingConverter;
        this.meetingLinkBuilder = meetingLinkBuilder;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<MeetingDto> findAll(@RequestParam(name = "sort_param", required = false) List<String> sortParams,
                                               @RequestParam(name = "sort_type", required = false) List<String> sortTypes,
                                               @RequestParam(name = "search_param", required = false) List<String> searchParams,
                                               @RequestParam(name = "search_info", required = false) List<String> searchInfo){
        List<Meeting> meetings = meetingService.findAll(sortParams, sortTypes, searchParams, searchInfo);
        List<MeetingDto> meetingDtos = new ArrayList<>();
        for (Meeting meeting : meetings){
            meetingDtos.add(meetingConverter.convertToDto(meeting));
        }
        meetingLinkBuilder.addMeetingsRelatedLinks(meetingDtos);
        Link selfLink = linkTo(MeetingController.class).withSelfRel();
        return CollectionModel.of(meetingDtos, selfLink);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MeetingDto findById(@PathVariable("id") long id){
        Meeting meeting = meetingService.findMeetingById(id);
        MeetingDto meetingDto = meetingConverter.convertToDto(meeting);
        meetingLinkBuilder.addMeetingRelatedLinks(meetingDto);
        return meetingDto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeetingDto create(@RequestBody @Validated MeetingDto meetingDto){
        Meeting meeting = meetingConverter.convertToEntity(meetingDto);
        meeting = meetingService.create(meeting);
        MeetingDto resultMeetingDto = meetingConverter.convertToDto(meeting);
        meetingLinkBuilder.addMeetingRelatedLinks(resultMeetingDto);
        return resultMeetingDto;
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MeetingDto update(@PathVariable long id,
                             @RequestBody MeetingDto meetingDto){


        Meeting meeting = meetingConverter.convertToEntity(meetingDto);
        meeting = meetingService.updateMeetingById(id, meeting);
        MeetingDto resultMeetingDto = meetingConverter.convertToDto(meeting);
        meetingLinkBuilder.addMeetingRelatedLinks(resultMeetingDto);
        return resultMeetingDto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CollectionModel<Void> deleteMeeting(@PathVariable long id){
        meetingService.deleteMeetingById(id);
        return CollectionModel.empty();
    }

}
