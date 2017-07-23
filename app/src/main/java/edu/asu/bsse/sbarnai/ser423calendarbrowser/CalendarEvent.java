package edu.asu.bsse.sbarnai.ser423calendarbrowser;

/**
 * Copyright 2015 Sylvia Barnai.
 *
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/license/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Purpose: This class represents a CalendarEvent that consists of two properties: eventId and eventName, and their corresponding
 * getters and setters
 *
 * @author Sylvia Barnai mailto:Sylvia.Barnai@asu.edu
 * @version April 30, 2015
 **/

public class CalendarEvent {

    public long eventId;
    public String eventName;


    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public long getEventId() {

        return eventId;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {

        return eventName;
    }

    public CalendarEvent(long eventId, String eventName) {
        super();
        this.eventId = eventId;
        this.eventName = eventName;
    }
}
