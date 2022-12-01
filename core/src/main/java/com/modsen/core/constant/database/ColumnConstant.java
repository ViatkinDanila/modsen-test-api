package com.modsen.core.constant.database;

import java.util.HashSet;
import java.util.Set;

public class ColumnConstant {
    public static final String ID = "id";
    public static final String MEETING_TOPIC = "topic";
    public static final String MEETING_DESCRIPTION = "description";
    public static final String MEETING_ORGANIZER = "organizer";
    public static final String MEETING_START_DATE = "start_date";
    public static final String MEETING_ADDRESS = "address";

    public static final String START_DATE_ENTITY_FORM = "startDate";

    public static Set<String> getMeetingColumnAsSet(){
        Set<String> meetingColumns = new HashSet<>();
        meetingColumns.add(ID);
        meetingColumns.add(MEETING_TOPIC);
        meetingColumns.add(MEETING_DESCRIPTION);
        meetingColumns.add(MEETING_ORGANIZER);
        meetingColumns.add(START_DATE_ENTITY_FORM);
        meetingColumns.add(MEETING_ADDRESS);
        return meetingColumns;
    }

}
