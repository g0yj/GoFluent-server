package com.lms.api.common.repository;

import com.lms.api.common.code.AttendanceStatus;
import com.lms.api.common.dto.Attendance;
import com.lms.api.common.dto.ReservationNotification;
import com.lms.api.common.entity.ReservationEntity;
import com.lms.api.common.entity.UserEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository
    extends JpaRepository<ReservationEntity, Long>, QuerydslPredicateExecutor<ReservationEntity> {

  List<ReservationEntity> findAllByUserEntity_IdAndIdIn(String userId, List<Long> reservationIds);

  List<ReservationEntity> findAllByDateBetweenAndCancelIsFalse(
      LocalDate startDate, LocalDate endDate);

  List<ReservationEntity> findByDate(LocalDate date);

  List<ReservationEntity> findAllByUserEntityId(String userId);

  ReservationEntity findTopByUserEntityOrderByStartTimeDesc(UserEntity recentReservation);

  List<ReservationEntity>
      findAllByUserEntity_IdAndDateBetweenAndAttendanceStatusAndCancelIsFalseOrderByDateAscStartTimeAsc(
          String userId, LocalDate startDate, LocalDate endDate, AttendanceStatus attendanceStatus);

  List<Long> findIdsByDateAndStartTimeAndTeacherEntity_UserIdAndUserEntity_Id(
      LocalDate date, LocalTime startTime, String teacherId, String userId);

  @Query(
      "SELECT r.id FROM ReservationEntity r WHERE r.date = :date AND r.startTime = :startTime AND"
          + " r.teacherEntity.userId = :teacherId")
  List<Long> findIdsByDateAndStartTimeAndTeacherEntity_UserId(
      @Param("date") LocalDate date,
      @Param("startTime") LocalTime startTime,
      @Param("teacherId") String teacherId);

  List<ReservationEntity> findByDateAndStartTimeAndTeacherEntity_UserId(
      LocalDate date, LocalTime startTime, String teacherId);

  @Query(
      "SELECT DISTINCT l.reservationEntity.userEntity.id, l.reservationEntity.userEntity.name "
          + "FROM LdfEntity l "
          + "WHERE l.grade IS NOT NULL "
          + "AND l.reservationEntity.date BETWEEN :dateFrom AND :dateTo")
  List<Object[]> findDistinctUserIdsByDateBetween(
      @Param("dateFrom") LocalDate dateFrom, @Param("dateTo") LocalDate dateTo);

  // CourseEntity가 null이 아닌 예약만 조회하는 메서드
  List<ReservationEntity> findAllByCourseEntityIsNotNull();

  @Query(
      value =
          """
          SELECT r.*,
              CASE
                  WHEN ldf.reservation_id IS NOT NULL THEN 'Y'
                  ELSE 'N'
              END AS ldfYN
          FROM reservation r
          LEFT JOIN ldf ON
              r.id = ldf.reservation_id
          WHERE r.user_id = :userId
              AND r.attendance_status = 'Y'
              AND (r.date < CURDATE()
              OR (r.date = CURDATE() AND r.start_time < CURRENT_TIME))
          ORDER BY
              r.date DESC,
              r.start_time DESC
          """,
      nativeQuery = true)
  List<ReservationEntity> findReservationsBeforeDateTimeWithoutCourseId(
      @Param("userId") String userId);

  @Query(
      value = """
        SELECT r.*,
            CASE
                WHEN ldf.reservation_id IS NOT NULL THEN 'Y'
                ELSE 'N'
            END AS ldfYN
        FROM reservation r
        LEFT JOIN ldf ON
            r.id = ldf.reservation_id
        WHERE r.user_id = :userId
            AND r.course_id = :courseId
            AND r.attendance_status = 'Y'
            AND STR_TO_DATE(CONCAT(r.date, ' ', r.start_time), '%Y-%m-%d %H:%i:%s') < CURRENT_TIMESTAMP
        ORDER BY r.date DESC,
            r.start_time DESC
      """,
      nativeQuery = true)
  List<ReservationEntity> findReservationsBeforeDateTime(
      @Param("courseId") Long courseId, @Param("userId") String userId);

  Optional<ReservationEntity> findByDateAndStartTimeAndUserEntity_Id(
      LocalDate date, LocalTime startTime, String userId);

  @Query(
      value =
          """
          SELECT r.*
          FROM reservation r
          WHERE r.user_id = :userId
            AND r.teacher_id = :teacherId
          ORDER BY r.date DESC, r.start_time DESC
          LIMIT 1
          """,
      nativeQuery = true)
  ReservationEntity findTopByUserEntityAndTeacherEntityOrderByDateAndStartTimeDesc(
      @Param("userId") String userId, @Param("teacherId") String teacherId);

  @Query(
      """
      SELECT r FROM ReservationEntity r
      WHERE r.courseEntity.id = :courseId
        AND r.userEntity.id = :userId
        AND r.teacherEntity.userId = :teacherId
        AND r.date = :date""")
  List<ReservationEntity> findByCourseIdAndUserIdAndTeacherIdAndDate(
      @Param("courseId") Long courseId,
      @Param("userId") String userId,
      @Param("teacherId") String teacherId,
      @Param("date") LocalDate date);

  @Query(
      value = """
        WITH consecutive_groups AS (
            SELECT
                r.id,
                r.course_id,
                r.user_id,
                r.teacher_id,
                r.product_id,
                r.date,
                r.start_time,
                r.end_time,
                r.report,
                r.today_lesson,
                r.next_lesson,
                r.attendance_status,
                r.is_cancel,
                r.cancel_by,
                r.cancel_reason,
                r.cancel_date,
                r.created_by,
                r.created_on,
                r.modified_by,
                r.modified_on,
                u.name AS user_name,
                CASE
                    WHEN LAG(r.end_time) OVER (PARTITION BY r.course_id, r.user_id, r.teacher_id, r.date ORDER BY r.start_time) = r.start_time
                    THEN 0
                    ELSE 1
                END AS group_start
            FROM
                reservation r
            JOIN
                user_ u ON r.user_id = u.id
            WHERE
                r.date BETWEEN :dateFrom AND :dateTo
                AND (:teacherId IS NULL OR r.teacher_id = :teacherId)
                AND (:search IS NULL OR u.name LIKE CONCAT('%', :search, '%'))
                AND (:isTeacher = FALSE OR r.teacher_id = :loginId)
        ),
        grouped_reservations AS (
            SELECT
                *,
                SUM(group_start) OVER (PARTITION BY course_id, user_id, teacher_id, date ORDER BY start_time) AS group_id
            FROM
                consecutive_groups
        ),
        unwritten_groups AS (
            SELECT
                course_id,
                user_id,
                teacher_id,
                date,
                group_id
            FROM
                grouped_reservations
            GROUP BY
                course_id, user_id, teacher_id, date, group_id
            HAVING
                MAX(CASE WHEN report IS NOT NULL OR today_lesson IS NOT NULL OR next_lesson IS NOT NULL THEN 1 ELSE 0 END) = 0
        )
        SELECT
            gr.*
        FROM
            grouped_reservations gr
        INNER JOIN
            unwritten_groups ug ON gr.course_id = ug.course_id
                AND gr.user_id = ug.user_id
                AND gr.teacher_id = ug.teacher_id
                AND gr.date = ug.date
                AND gr.group_id = ug.group_id
        ORDER BY
            gr.date, gr.start_time
        """,
      countQuery = """
      SELECT COUNT(*) FROM (
          WITH consecutive_groups AS (
              SELECT
                  r.id,
                  r.course_id,
                  r.user_id,
                  r.teacher_id,
                  r.date,
                  r.start_time,
                  r.end_time,
                  r.report,
                  r.today_lesson,
                  r.next_lesson,
                  u.name AS user_name,
                  CASE
                      WHEN LAG(r.end_time) OVER (PARTITION BY r.course_id, r.user_id, r.teacher_id, r.date ORDER BY r.start_time) = r.start_time
                      THEN 0
                      ELSE 1
                  END AS group_start
              FROM
                  reservation r
              JOIN
                  user u ON r.user_id = u.id
              WHERE
                  r.date BETWEEN :dateFrom AND :dateTo
                  AND (:teacherId IS NULL OR r.teacher_id = :teacherId)
                  AND (:search IS NULL OR u.name LIKE CONCAT('%', :search, '%'))
                  AND (:isTeacher = FALSE OR r.teacher_id = :loginId)
          ),
          grouped_reservations AS (
              SELECT
                  *,
                  SUM(group_start) OVER (PARTITION BY course_id, user_id, teacher_id, date ORDER BY start_time) AS group_id
              FROM
                  consecutive_groups
          ),
          unwritten_groups AS (
              SELECT
                  course_id,
                  user_id,
                  teacher_id,
                  date,
                  group_id
              FROM
                  grouped_reservations
              GROUP BY
                  course_id, user_id, teacher_id, date, group_id
              HAVING
                  MAX(CASE WHEN report IS NOT NULL OR today_lesson IS NOT NULL OR next_lesson IS NOT NULL THEN 1 ELSE 0 END) = 0
          )
          SELECT
              1
          FROM
              grouped_reservations gr
          INNER JOIN
              unwritten_groups ug ON gr.course_id = ug.course_id
                  AND gr.user_id = ug.user_id
                  AND gr.teacher_id = ug.teacher_id
                  AND gr.date = ug.date
                  AND gr.group_id = ug.group_id
      ) AS count_query
      """,
      nativeQuery = true)
  Page<ReservationEntity> listNoReportNative(
      @Param("dateFrom") LocalDate dateFrom,
      @Param("dateTo") LocalDate dateTo,
      @Param("teacherId") String teacherId,
      @Param("search") String search,
      @Param("isTeacher") boolean isTeacher,
      @Param("loginId") String loginId,
      Pageable pageable);

  @Query(
      value =
          """
              SELECT
                  r.user_id AS userId,
                  r.teacher_id AS teacherId,
                  r.date AS date,
                  r.start_time AS startTime,
                  r.end_time AS endTime,
                  u.name AS name,
                  u.cell_phone AS cellPhone
              FROM
                  reservation r
              JOIN
                  user_ u ON r.user_id = u.id
              WHERE
                  r.date = CURDATE() + INTERVAL 1 DAY
                  AND r.attendance_status = 'R'
                  AND r.is_cancel = 0
          """,
      nativeQuery = true)
  List<ReservationNotification> findReservationsForTomorrow();

  @Query(
      "SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM ReservationEntity r "
          + "WHERE r.courseEntity.id = :courseId "
          + "AND (r.date < :startDate OR r.date > :endDate)")
  boolean existsReservationOutsideCourseDate(
      @Param("courseId") Long courseId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);

  @Query(value = """
    SELECT
        dates.attendance_date AS date,
        t.teacher_id AS id,
        t.typ AS type,
        t.name AS name,
        t.sort AS sort,
        COALESCE(s.schedule_count, 0) AS reservationCount,  -- 예약 수는 schedule 테이블에서 가져옴
        COALESCE(r.reservation_count, 0) AS attendanceCount,  -- 출석 수는 reservation 테이블에서 가져옴
        CASE WHEN COALESCE(s.schedule_count, 0) = 0 THEN 0.00
             ELSE ROUND((COALESCE(r.reservation_count, 0) / COALESCE(s.schedule_count, 0)) * 100, 2)
        END AS attendanceRate
    FROM
        (SELECT
            DATE(:startDate) + INTERVAL seq DAY AS attendance_date
        FROM
            (SELECT @row := @row + 1 AS seq
             FROM (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS a,
                  (SELECT 0 UNION ALL SELECT 1 UNION ALL SELECT 2 UNION ALL SELECT 3 UNION ALL SELECT 4 UNION ALL SELECT 5 UNION ALL SELECT 6 UNION ALL SELECT 7 UNION ALL SELECT 8 UNION ALL SELECT 9) AS b,
                  (SELECT @row := -1) AS r
             ) AS seqs
        WHERE
            DATE(:startDate) + INTERVAL seq DAY <= DATE(:endDate)
        ) AS dates
    CROSS JOIN
        (SELECT
            t.user_id AS teacher_id,
            t.sort AS sort,
            t.`type` AS typ,
            u.name,
            t.is_active
        FROM
            teacher t
        JOIN
            user_ u ON t.user_id = u.id
        ) AS t
    LEFT JOIN
        (SELECT
            r.teacher_id,
            r.`date` AS attendance_date,
            COUNT(*) AS reservation_count
        FROM
            reservation r
        WHERE
            r.attendance_status = :status
            AND r.`date` BETWEEN :startDate AND :endDate
        GROUP BY
            r.teacher_id, r.`date`) AS r
    ON
        t.teacher_id = r.teacher_id
        AND dates.attendance_date = r.attendance_date
    LEFT JOIN
        (SELECT
            s.teacher_id,
            s.`date` AS attendance_date,
            COUNT(*) AS schedule_count
        FROM
            schedule s
        WHERE
            s.`date` BETWEEN :startDate AND :endDate
            AND MINUTE(s.start_time) IN (0, 30)
        GROUP BY
            s.teacher_id, s.`date`) AS s
    ON
        t.teacher_id = s.teacher_id
        AND dates.attendance_date = s.attendance_date
    WHERE
        t.is_active = 1  -- is_active가 1인 강사는 무조건 포함
        OR (t.is_active = 0 AND EXISTS (
            SELECT 1 FROM schedule s2
            WHERE s2.teacher_id = t.teacher_id
            AND s2.date BETWEEN :startDate AND :endDate
        ))  -- is_active가 0이어도 기간 내 일정이 존재하는 경우 포함
    ORDER BY
        dates.attendance_date, t.sort
""", nativeQuery = true)
  List<Object[]> findTeacherAttendances(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("status") String status);

}