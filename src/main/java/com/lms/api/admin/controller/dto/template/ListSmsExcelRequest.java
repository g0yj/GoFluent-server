package com.lms.api.admin.controller.dto.template;


import com.lms.api.admin.code.SearchSmsCode;
import com.lms.api.common.util.StringUtils;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
public class ListSmsExcelRequest  {
    LocalDate sendDateFrom;
    LocalDate sendDateTo;
    SearchSmsCode.SearchType sendType;

    String keyword;
    String search;

    public ListSmsExcelRequest(LocalDate sendDateFrom, LocalDate sendDateTo, SearchSmsCode.SearchType sendType, String keyword, String search) {
        this.sendDateFrom = sendDateFrom;
        this.sendDateTo = sendDateTo;
        this.sendType = sendType;
        this.keyword = keyword;
        this.search = search;
    }

    public boolean hasSearch() {
        return StringUtils.hasAllText(search, keyword);
    }
}

