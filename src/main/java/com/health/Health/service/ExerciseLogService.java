package com.health.Health.service;

import com.health.Health.entity.ExerciseLog;
import com.health.Health.repository.ExerciseLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExerciseLogService {

    private final ExerciseLogRepository exerciseLogRepository;

    // 생성자 주입
    public ExerciseLogService(ExerciseLogRepository exerciseLogRepository) {
        this.exerciseLogRepository = exerciseLogRepository;
    }

    // 새로운 운동 기록 저장
    public ExerciseLog saveExerciseLog(ExerciseLog exerciseLog) {
        return exerciseLogRepository.save(exerciseLog);
    }

    // 운동 기록 ID로 조회
    public ExerciseLog getExerciseLogById(Long id) {
        return exerciseLogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exercise log not found"));
    }

    // 운동 기록 업데이트
    public void updateExerciseLog(Long id, ExerciseLog updatedLog) {
        // 기존 데이터 가져오기
        ExerciseLog existingLog = getExerciseLogById(id);

        // 유산소 운동 정보 업데이트
        existingLog.setAerobicExerciseType(updatedLog.getAerobicExerciseType());
        existingLog.setAerobicExerciseName(updatedLog.getAerobicExerciseName());
        existingLog.setDuration(updatedLog.getDuration());
        existingLog.setCaloriesBurned(updatedLog.getCaloriesBurned());

        // 무산소 운동 정보 업데이트
        existingLog.setAnaerobicExerciseType(updatedLog.getAnaerobicExerciseType());
        existingLog.setAnaerobicExerciseName(updatedLog.getAnaerobicExerciseName());
        existingLog.setWeight(updatedLog.getWeight());
        existingLog.setRepetitions(updatedLog.getRepetitions());
        existingLog.setSets(updatedLog.getSets());

        // 날짜 업데이트 (null 체크 추가)
        if (updatedLog.getDate() != null) {
            existingLog.setDate(updatedLog.getDate());
        }
        // 저장
        exerciseLogRepository.save(existingLog);
    }


    // 운동 기록 삭제
    public void deleteExerciseLog(Long id) {
        exerciseLogRepository.deleteById(id);
    }

    // 유산소 운동 삭제
    public void deleteAerobicExercise(Long id) {
        ExerciseLog aerobicLog = exerciseLogRepository.findById(id).orElse(null);
        if (aerobicLog != null && aerobicLog.getAerobicExerciseType() != null) {
            System.out.println("Deleting aerobic exercise log with id: " + id); // 로그 추가
            aerobicLog.setAerobicExerciseType(null); // 유산소 운동만 null 처리
            aerobicLog.setAerobicExerciseName(null);
            exerciseLogRepository.save(aerobicLog); // 레코드 저장
        } else {
            System.out.println("Aerobic exercise log with id " + id + " not found or not aerobic type");
        }
    }

    // 무산소 운동 삭제
    public void deleteAnaerobicExercise(Long id) {
        ExerciseLog anaerobicLog = exerciseLogRepository.findById(id).orElse(null);
        if (anaerobicLog != null && anaerobicLog.getAnaerobicExerciseType() != null) {
            System.out.println("Deleting anaerobic exercise log with id: " + id); // 로그 추가
            anaerobicLog.setAnaerobicExerciseType(null); // 무산소 운동만 null 처리
            anaerobicLog.setAnaerobicExerciseName(null);
            exerciseLogRepository.save(anaerobicLog); // 레코드 저장
        } else {
            System.out.println("Anaerobic exercise log with id " + id + " not found or not anaerobic type");
        }
    }



    // 특정 날짜의 운동 기록 가져오기
    public List<ExerciseLog> getLogsByDate(LocalDate date) {
        return exerciseLogRepository.findByDate(date);
    }

    // 주간 운동 기록 가져오기
    public List<ExerciseLog> getLogsByWeek(LocalDate startDate, LocalDate endDate) {
        return exerciseLogRepository.findByDateBetween(startDate, endDate);
    }

    // 날짜와 운동 종류별 운동 기록 가져오기
    public List<ExerciseLog> getLogsByDateAndType(LocalDate date, String aerobicExerciseType, String anaerobicExerciseType) {
        if (aerobicExerciseType != null && !aerobicExerciseType.isEmpty()) {
            return exerciseLogRepository.findByDateAndAerobicExerciseType(date, aerobicExerciseType);
        } else if (anaerobicExerciseType != null && !anaerobicExerciseType.isEmpty()) {
            return exerciseLogRepository.findByDateAndAnaerobicExerciseType(date, anaerobicExerciseType);
        } else {
            return exerciseLogRepository.findByDate(date);
        }
    }

//    // 특정 운동 종류의 운동 기록 조회
//    public List<ExerciseLog> findByExerciseTypeAndDate(String exerciseType, LocalDate date) {
//        if (date != null) {
//            return exerciseLogRepository.findByExerciseTypeAndDate(exerciseType, date);
//        } else {
//            return exerciseLogRepository.findByExerciseType(exerciseType);
//        }
//    }

    // 유산소 운동 기록 조회
    public List<ExerciseLog> findAerobicLogs() {
        return exerciseLogRepository.findByAerobicExerciseTypeIsNotNull();
    }

    // 무산소 운동 기록 조회
    public List<ExerciseLog> findAnaerobicLogs() {
        return exerciseLogRepository.findByAnaerobicExerciseTypeIsNotNull();
    }
}
