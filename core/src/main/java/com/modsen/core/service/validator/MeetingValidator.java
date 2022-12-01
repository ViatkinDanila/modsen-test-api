package com.modsen.core.service.validator;

import com.modsen.core.model.entity.Meeting;
import org.springframework.stereotype.Component;

@Component
public class MeetingValidator implements Validator<Meeting>{
    private static final int MAX_TOPIC_LENGTH = 50;
    private static final int MIN_TOPIC_LENGTH = 3;
    private static final int MAX_ADDRESS_LENGTH = 75;
    private static final int MIN_ADDRESS_LENGTH = 10;
    private static final int MAX_DESCRIPTION_LENGTH = 255;
    private static final int MIN_DESCRIPTION_LENGTH = 3;
    private static final int MAX_ORGANIZER_LENGTH = 50;
    private static final int MIN_ORGANIZER_LENGTH = 5;

    @Override
    public boolean isValid(Meeting entity) {
        if (!isValidAddress(entity.getAddress()) ||
            !isValidDescription(entity.getDescription()) ||
            !isValidOrganizer(entity.getOrganizer()) ||
            !isValidTopic(entity.getTopic())){
            return false;
        }
        return true;
    }

    private boolean isValidTopic(String topic) {
        if(topic.length() > MAX_TOPIC_LENGTH || topic.length() < MIN_TOPIC_LENGTH){
            return false;
        }
        return true;
    }

    private boolean isValidOrganizer(String organizer) {
        if(organizer.length() > MAX_ORGANIZER_LENGTH || organizer.length() < MIN_ORGANIZER_LENGTH){
            return false;
        }
        return true;
    }

    private boolean isValidDescription(String description) {
        if(description.length() > MAX_DESCRIPTION_LENGTH || description.length() < MIN_DESCRIPTION_LENGTH){
            return false;
        }
        return true;
    }

    private boolean isValidAddress(String address) {
        if(address.length() > MAX_ADDRESS_LENGTH || address.length() < MIN_ADDRESS_LENGTH){
            return false;
        }
        return true;
    }
}
