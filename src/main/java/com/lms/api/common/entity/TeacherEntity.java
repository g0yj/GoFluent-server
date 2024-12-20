package com.lms.api.common.entity;

import com.lms.api.common.code.Language;
import com.lms.api.common.code.TeacherType;
import com.lms.api.common.code.VisaType;
import com.lms.api.common.code.WorkTime;
import com.lms.api.common.code.WorkType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "teacher")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeacherEntity extends BaseEntity {

  @Id
  @Column(updatable = false)
  String userId;

  @Enumerated(EnumType.STRING)
  TeacherType type;

  String partnerTeacherId;
  LocalDate workStartDate;

  String file;
  String originalFile;

  @Enumerated(EnumType.STRING)
  WorkTime workTime;

  @Enumerated(EnumType.STRING)
  WorkType workType;

  String smsType;
  String homeAddress;
  String homePhone;
  String cellPhone;
  String cellPhoneDeviceNumber;
  LocalDate cellPhoneLeaseStartDate;
  LocalDate cellPhoneLeaseEndDate;
  String cellPhoneDevicePrice;
  String cellPhoneDeviceStatue;
  String university;
  String major;
  String previousWork;
  String nationality;

  @Enumerated(EnumType.STRING)
  VisaType visaType;

  String visaEntryType;
  LocalDate visaEndDate;
  LocalDate arrivalDate;
  String arrivalNote;
  LocalDate stayStartDate;
  LocalDate stayEndDate;
  String reEntryNote;
  LocalDate sponsorStartDate;
  LocalDate sponsorEndDate;
  String trainingDay;
  String visaNote;
  LocalDate contractStartDate;
  LocalDate contractEndDate;
  String contractBasicSalary;
  String contractNote;
  String pnpCode;
  String airfarePayment;
  String deposit;
  String monthly;
  String hireType;
  String landlord;
  String zipcode;
  String address;
  String detailedAddress;
  String phone;
  String hireNote;
  LocalDate hireStartDate;
  LocalDate hireEndDate;
  String educationOffice;
  LocalDate recruitmentDate;
  String educationOfficeNote;
  String educationOfficeManager;
  LocalDate contractExpirationDate;
  LocalDate dismissalImmiDate;
  LocalDate departureDate;
  String dismissalManager;
  LocalDate dismissalMeDate;
  String releaseNote;
  String dismissalNote;
  int basicSalary;
  int housingCost;
  int managementCost;
  int nationalPension;
  int healthInsurance;
  int careInsurance;
  int employmentInsurance;
  int sort;
  String memo;

  @Column(name = "is_active")
  boolean active;

  @Enumerated(EnumType.STRING)
  Language language;

  @OneToOne
  @MapsId
  @JoinColumn(name = "userId", nullable = false)
  UserEntity userEntity;

  @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<OrderProductEntity> orderProductEntities = new ArrayList<>();

  @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<CourseEntity> courseEntities = new ArrayList<>();

  @OneToMany(mappedBy = "assistantTeacherEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<CourseEntity> assistantCourseEntities = new ArrayList<>();

  @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<ReservationEntity> reservationEntities = new ArrayList<>();

  @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<SalaryEntity> salaryEntities = new ArrayList<>();

  @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<ScheduleEntity> scheduleEntities = new ArrayList<>();

  @OneToMany(mappedBy = "teacherEntity", cascade = CascadeType.ALL, orphanRemoval = true)
  List<TeacherFileEntity> teacherFileEntities = new ArrayList<>();
}
