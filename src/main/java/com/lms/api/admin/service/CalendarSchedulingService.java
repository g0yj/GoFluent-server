package com.lms.api.admin.service;

import com.lms.api.client.holiday.HolidayInfoService;
import com.lms.api.client.holiday.dto.HolidayRequest;
import com.lms.api.client.holiday.dto.HolidayResponse;
import com.lms.api.client.holiday.dto.HolidayResponse.Item;
import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!migration")
//@Profile("!migration & !test")
public class CalendarSchedulingService {

  private final HolidayInfoService holidayInfoService;
  private final JdbcTemplate jdbcTemplate;
  private final Environment environment;
  private LocalDate testToday;

  /**
   * JUnit 테스트를 위한 메서드
   */
  void setTestToday(LocalDate testToday) {
    this.testToday = testToday;
  }

  // 매달 1일 오전 6시에 수행, 다음 3개월치 공휴일 정보 업데이트
  @Scheduled(cron = "${lms.scheduling.updateHolidays}")
  public void updateHolidays() {
    try {
      LocalDate today = (isTest() && testToday != null) ? testToday : LocalDate.now();
      log.info("Processing holidays starting from today: {}", today);

      for (int i = 0; i < 3; i++) {
        LocalDate targetMonth = today.plusMonths(i);
        String year = String.valueOf(targetMonth.getYear());
        String month = String.format("%02d", targetMonth.getMonthValue());

        HolidayRequest request = new HolidayRequest(year, month, 1000);
        log.debug("Requesting holidays for year: {}, month: {}", year, month);

        HolidayResponse response = holidayInfoService.getHolidayInfo(request);
        log.debug("Holiday response for month {}: {}", month, response);

        if (response != null && response.getResponse().getBody().getItems() != null) {
          List<Item> holidays = response.getResponse().getBody().getItems().getItemList();
          for (Item holiday : holidays) {
            updateHolidayInCalendar(holiday);
          }
        } else {
          log.warn("No holidays found for year: {}, month: {}", year, month);
        }
      }

    } catch (IOException e) {
      log.error("Error fetching holiday info: ", e);
    } catch (SQLException e) {
      log.error("Error updating holiday info in database: ", e);
    } catch (Exception e) {
      log.error("Unexpected error occurred during holiday update: ", e);
    }
  }

  /**
   * 공휴일 정보를 calendar 테이블에 업데이트하는 메서드
   */
  private void updateHolidayInCalendar(Item holiday) throws SQLException {
    String selectSql = "SELECT COUNT(*) FROM calendar WHERE calendar_date = ?";
    String updateSql = "UPDATE calendar SET is_holiday = 1, holiday_name = ?, is_weekend = ? WHERE calendar_date = ?";
    String insertSql = """
                INSERT INTO calendar (calendar_date, year, month, day, day_of_week, is_weekend, is_holiday, holiday_name)
                VALUES (?, ?, ?, ?, ?, ?, 1, ?)
            """;

    LocalDate holidayDate = LocalDate.of(holiday.getLocdate() / 10000, (holiday.getLocdate() % 10000) / 100, holiday.getLocdate() % 100);
    String holidayName = holiday.getDateName();
    String formattedDate = holidayDate.toString(); // "YYYY-MM-DD"

    // 주말인지 여부 계산
    boolean isWeekend = isWeekend(holidayDate);

    // 날짜가 존재하는지 확인
    Integer count = jdbcTemplate.queryForObject(selectSql, Integer.class, formattedDate);

    if (count != null && count > 0) {
      // 날짜가 이미 존재할 경우 업데이트
      int rowsAffected = jdbcTemplate.update(updateSql, holidayName, isWeekend, formattedDate);
      if (rowsAffected > 0) {
        log.info("Holiday updated for date: {} with name: {}, is_weekend: {}", formattedDate, holidayName, isWeekend);
      } else {
        log.warn("No rows were updated for date: {}", formattedDate);
      }
    } else {
      // 날짜가 없을 경우 새로운 행을 추가
      String dayOfWeek = holidayDate.getDayOfWeek().name();
      int rowsInserted = jdbcTemplate.update(insertSql, formattedDate, holidayDate.getYear(), holidayDate.getMonthValue(),
          holidayDate.getDayOfMonth(), dayOfWeek, isWeekend, holidayName);
      if (rowsInserted > 0) {
        log.info("Holiday inserted for date: {} with name: {}, is_weekend: {}", formattedDate, holidayName, isWeekend);
      } else {
        log.warn("No rows were inserted for date: {}", formattedDate);
      }
    }
  }

  /**
   * 주말 여부를 판단하는 메서드
   */
  private boolean isWeekend(LocalDate date) {
    DayOfWeek dayOfWeek = date.getDayOfWeek();
    return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
  }

  /**
   * 테스트 프로파일을 활성화했는지 확인하는 메서드
   */
  private boolean isTest() {
    return isProfileActive("test");
  }

  /**
   * 특정 프로파일이 활성화되었는지 확인하는 메서드
   */
  private boolean isProfileActive(String profile) {
    String[] activeProfiles = environment.getActiveProfiles();
    for (String activeProfile : activeProfiles) {
      if (activeProfile.equalsIgnoreCase(profile)) {
        return true;
      }
    }
    return false;
  }
}
