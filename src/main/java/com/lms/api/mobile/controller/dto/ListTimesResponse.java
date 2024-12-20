package com.lms.api.mobile.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListTimesResponse {
    //@JsonFormat(pattern = "hh:mm a" , locale = "en")
    @JsonFormat(pattern = "HH:mm")
    List<LocalTime> times;
}
