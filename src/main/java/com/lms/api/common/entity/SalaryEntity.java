package com.lms.api.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "salary")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SalaryEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String agencyId;
  String type;
  String yearmonth;
  int year;
  int month;
  int basicSalary;
  int workingDay;
  int workingAvailableDay;
  int actualSalary;
  float attendanceRate;
  float absentRate;
  int attendanceBonus;
  int absentBonus;
  int attendanceCount;
  int attendanceAmount;
  int attendanceSalary;
  int substituteCost;
  int latenessCost;
  int educationCost;
  int housingCost;
  int managementCost;
  int flightCost;
  int saturdayPay;
  int splitBonus;
  int reRegistrationBonus;
  int mealCost;
  int extraPay;
  int leavePay;
  int severancePay;
  int bonus;
  int incomeTax;
  int residentTax;
  int nationalPension;
  int healthInsurance;
  int employmentInsurance;
  int deductible;
  int absentPenalty;
  int careInsurance;
  int settlementIncomeTax;
  int settlementResidentTax;
  int settlementHealthInsurance;
  int totalPayment;
  int totalDeductible;
  int differencePayment;
  String note;
  LocalDate paymentDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "teacherId", nullable = false)
  TeacherEntity teacherEntity;
}
