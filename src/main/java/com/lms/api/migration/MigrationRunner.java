package com.lms.api.migration;

import com.lms.api.migration.service.ConsultationHistoryMigrationService;
import com.lms.api.migration.service.ConsultationLogService;
import com.lms.api.migration.service.ConsultationMigrationService;
import com.lms.api.migration.service.ConsultationPhoneUpdateService;
import com.lms.api.migration.service.CorrectionDataService;
import com.lms.api.migration.service.CourseHistoryMigrationService;
import com.lms.api.migration.service.CourseMigrationService;
import com.lms.api.migration.service.DatabaseResetService;
import com.lms.api.migration.service.LdfMigrationService;
import com.lms.api.migration.service.LevelTestMigrationService;
import com.lms.api.migration.service.MemberConsultationMigrationService;
import com.lms.api.migration.service.OrderMigrationService;
import com.lms.api.migration.service.OrderProductMigrationService;
import com.lms.api.migration.service.PaymentMigrationService;
import com.lms.api.migration.service.RefundMigrationService;
import com.lms.api.migration.service.ReservationMigrationService;
import com.lms.api.migration.service.ScheduleMigrationService;
import com.lms.api.migration.service.TeacherMigrationService;
import com.lms.api.migration.service.UserMigrationService;
import com.lms.api.migration.service.UserPhoneUpdateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * mvn spring-boot:run -Dspring-boot.run.profiles=migration
 */
@Slf4j
@Component
@Profile("migration")
@RequiredArgsConstructor
public class MigrationRunner implements CommandLineRunner {

  //  public final static String DIR_PATH = "src/main/resources/csv_202406/";
  public final static String DIR_PATH = "/Users/neptune/Documents/LMS/202406/";

  private final ApplicationContext applicationContext;
  private final UserMigrationService userMigrationService;
  private final TeacherMigrationService teacherMigrationService;
  private final ScheduleMigrationService scheduleMigrationService;
  private final ReservationMigrationService reservationMigrationService;
  private final RefundMigrationService refundMigrationService;
  private final PaymentMigrationService paymentMigrationService;
  private final OrderMigrationService orderMigrationService;
  private final OrderProductMigrationService orderProductMigrationService;
  private final MemberConsultationMigrationService memberConsultationMigrationService;
  private final LdfMigrationService ldfMigrationService;
  private final CourseMigrationService courseMigrationService;
  private final CourseHistoryMigrationService courseHistoryMigrationService;
  private final ConsultationMigrationService consultationMigrationService;
  private final LevelTestMigrationService levelTestMigrationService;
  private final DatabaseResetService databaseResetService;
  private final ConsultationHistoryMigrationService consultationHistoryMigrationService;
  private final CorrectionDataService correctionDataService;
  private final ConsultationLogService consultationLogService;
  private final UserPhoneUpdateService userPhoneUpdateService;
  private final ConsultationPhoneUpdateService consultationPhoneUpdateService;

  @Override
  public void run(String... args) {
    // 데이터 삭제
    databaseResetService.truncateTables();
    log.info("Starting data migration...");
    try {
      userMigrationService.migrationBatch();
      userPhoneUpdateService.updateUserPhones();
      teacherMigrationService.migrationBatch();
      scheduleMigrationService.migrationBatch();
      reservationMigrationService.migrationBatch();
      refundMigrationService.migrationBatch();
      paymentMigrationService.migrationBatch();
      orderMigrationService.migrationBatch();
      orderProductMigrationService.migrationBatch();
      memberConsultationMigrationService.migrationBatch();
      ldfMigrationService.migrationBatch();
      courseMigrationService.migrationBatch();
      courseHistoryMigrationService.migrationBatch();
      consultationMigrationService.migrationBatch();
      consultationHistoryMigrationService.migrationBatch();
      levelTestMigrationService.migrationBatch();
      consultationLogService.migrationBatch();
      consultationPhoneUpdateService.updateConsultationPhones();
      correctionDataService.update();
      log.info("Data migration completed.");
    } finally {
      stop();
    }
  }

  private void stop() {
    SpringApplication.exit(applicationContext, () -> 0);
    System.exit(0);
  }
}
