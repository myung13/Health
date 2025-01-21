package com.health.Health.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "exercise_log")
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "aerobic_exercise_type")
    private String aerobicExerciseType; // 유산소 운동 타입
    @Column(name = "aerobic_exercise_name")
    private String aerobicExerciseName; // 유산소 운동 이름

    private Integer duration; // 유산소 운동 시간

    @Column(name = "calories_burned")
    private Integer caloriesBurned; // 유산소 운동 칼로리

    @Column(name = "anaerobic_exercise_type")
    private String anaerobicExerciseType; // 무산소 운동 타입
    @Column(name = "anaerobic_exercise_name")
    private String anaerobicExerciseName; // 무산소 운동 이름

    private Double weight; // 무산소 운동 무게
    private Integer repetitions; // 무산소 운동 반복 횟수
    private Integer sets; // 무산소 운동 세트 수

    private LocalDate date;

    // 유산소와 무산소 구분을 위한 기본값 설정
    @PrePersist
    public void ensureNonNullFields() {
        if (this.aerobicExerciseType == null) this.aerobicExerciseType = "";
        if (this.anaerobicExerciseType == null) this.anaerobicExerciseType = "";
    }
}

