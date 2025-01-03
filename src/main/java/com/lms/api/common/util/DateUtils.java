package com.lms.api.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtils {

  // 주말 제외 plusDays 만큼 더한 날짜
  public LocalDate getBusinessDate(long plusDays) {
    LocalDate businessDate = LocalDate.now().plusDays(plusDays);

    while (businessDate.getDayOfWeek().getValue() > 5) {
      businessDate = businessDate.plusDays(1L);
    }

    return businessDate;
  }

  public String getLastDayOfMonthFromDateString(String dateString) {
    // 문자열을 LocalDate로 파싱
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate date = LocalDate.parse(dateString, formatter);

    // YearMonth 객체 생성
    YearMonth yearMonth = YearMonth.of(date.getYear(), date.getMonth());

    // 해당 년월의 말일을 구하고 문자열로 변환
    LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
    return lastDayOfMonth.format(formatter);
  }

  public String getString(LocalDate date) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    return date.format(formatter);
  }

  public LocalDate parseDate(String dateStr) {
    return parseDate(dateStr, "yyyy-MM-dd");
  }

  public LocalDate parseDate(String dateStr, String pattern) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
    try {
      return LocalDate.parse(dateStr, formatter);
    } catch (DateTimeParseException e) {
      throw e;
    }
  }

  public static LocalDate[] getWeekStartEnd(int year, int month, int week) {
    // 첫 번째 날 설정
    LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
    WeekFields weekFields = WeekFields.of(Locale.getDefault());

    // 첫 번째 주의 일요일을 기준으로 주차 계산
    LocalDate firstSunday = firstDayOfMonth.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));

    // 주차에 해당하는 일요일 계산 (week-1번째 주차의 일요일)
    LocalDate startOfWeek = firstSunday.plusWeeks(week - 1);

    // 주차의 마지막 날 (해당 주의 토요일)
    LocalDate endOfWeek = startOfWeek.plusDays(6);

    return new LocalDate[]{startOfWeek, endOfWeek};
  }

  public static int getWeekCount(String yearMonth) {
    // 입력값을 기준으로 YearMonth 객체 생성
    YearMonth ym = YearMonth.parse(yearMonth);

    // 해당 월의 첫 번째와 마지막 날짜 구하기
    LocalDate firstDay = ym.atDay(1);
    LocalDate lastDay = ym.atEndOfMonth();

    // 현재 Locale에 맞는 주간 필드 정의
    WeekFields weekFields = WeekFields.of(Locale.getDefault());

    // 첫 번째 날짜의 주 번호 구하기
    int firstWeek = firstDay.get(weekFields.weekOfMonth());

    // 마지막 날짜의 주 번호 구하기
    int lastWeek = lastDay.get(weekFields.weekOfMonth());

    // 주의 수는 마지막 주 번호가 됩니다.
    return lastWeek;
  }
}
