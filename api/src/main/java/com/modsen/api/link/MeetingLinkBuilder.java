package com.modsen.api.link;

import com.modsen.api.controller.MeetingController;
import com.modsen.core.model.dto.MeetingDto;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

@Component
public class MeetingLinkBuilder {

    public void addMeetingRelatedLinks(MeetingDto meeting){
        Link updateLink = linkTo(methodOn(MeetingController.class).
                update(meeting.getId(), meeting)).withRel("update");
        Link deleteLink = linkTo(methodOn(MeetingController.class).
                deleteMeeting(meeting.getId())).withRel("delete");
        Link readLink = linkTo(methodOn(MeetingController.class).
                findById(meeting.getId())).withRel("get");
        meeting.add(updateLink);
        meeting.add(deleteLink);
        meeting.add(readLink);
    }

    public void addMeetingsRelatedLinks(List<MeetingDto> meetings){
        for (MeetingDto meeting : meetings){
            addMeetingRelatedLinks(meeting);
        }
    }
}
