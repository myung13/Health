package com.health.Health.repository;

import com.health.Health.entity.ExerciseLog;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {


    List<ExerciseLog> findByDate(LocalDate date); // 날짜로 운동 기록 조회
    List<ExerciseLog> findByDateBetween(LocalDate startDate, LocalDate endDate); // 날짜 범위로 운동 기록 조회

    // 날짜와 유산소 운동 종류로 운동 기록 조회
    List<ExerciseLog> findByDateAndAerobicExerciseType(LocalDate date, String aerobicExerciseType);

    // 날짜와 무산소 운동 종류로 운동 기록 조회
    List<ExerciseLog> findByDateAndAnaerobicExerciseType(LocalDate date, String anaerobicExerciseType);

    // 날짜 범위와 유산소 운동 종류로 운동 기록 조회
    List<ExerciseLog> findByDateBetweenAndAerobicExerciseType(LocalDate startDate, LocalDate endDate, String aerobicExerciseType);

    // 날짜 범위와 무산소 운동 종류로 운동 기록 조회
    List<ExerciseLog> findByDateBetweenAndAnaerobicExerciseType(LocalDate startDate, LocalDate endDate, String anaerobicExerciseType);

    // 유산소 운동 타입이 null이 아닌 운동 기록 조회
    List<ExerciseLog> findByAerobicExerciseTypeIsNotNull();

    // 운동 종류가 무산소인 기록을 조회하는 메서드
    List<ExerciseLog> findByAnaerobicExerciseTypeIsNotNull();


}
