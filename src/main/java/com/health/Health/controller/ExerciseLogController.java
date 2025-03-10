package com.health.Health.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.health.Health.entity.ExerciseLog;
import com.health.Health.repository.ExerciseLogRepository;
import com.health.Health.service.ExerciseLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller // 이 클래스가 mvc 컨트롤러
@RequestMapping("/exercise-log") // 이 컨트롤러의 모든 요청 경로가 "/exercise-log"로 시작됨
public class ExerciseLogController {
    private final ExerciseLogService exerciseLogService; // 운동 기록 서비스 의존성

    @Autowired
    private ExerciseLogRepository exerciseLogRepository;

    //생성자를 통해 ExerciseService를 주입받음
    public ExerciseLogController(ExerciseLogService exerciseLogService){
        this.exerciseLogService = exerciseLogService;
    }

    // CRUD 중 C(생성)
    @PostMapping("/save")
    public String saveExerciseLog(@ModelAttribute ExerciseLog exerciseLog, // @ModelAttribute사용하여 ExerciseLog객체받아옴
                                  @RequestParam("date") LocalDate date,
                                  RedirectAttributes redirectAttributes) {

        System.out.println("입력 OK");
        System.out.println("exerciseLog : " + exerciseLog);

        // 운동 기록 저장
        exerciseLog.setDate(date); // 이거로 날짜를 설정한 후,
        exerciseLogService.saveExerciseLog(exerciseLog); // 를 호출하여 운동 기록을 저장

        // 운동 기록이 저장되었음을 알리는 메시지 추가 (FlashAttribute 사용)
        redirectAttributes.addFlashAttribute("message", "운동 기록이 저장되었습니다.");

        // /welcome 페이지로 리다이렉트
        return "redirect:/welcome";  // 리다이렉트
    }

    // 운동 기록 생성 폼 페이지를 반환하는 메서드
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("exerciseLog", new ExerciseLog()); // 빈 ExerciseLog 객체를 모델에 추가
        return "exercise-log"; // 운동 기록 입력 페이지 반환
    }

    // CRUD 중 R(읽기)
    // 운동 기록 목록 페이지를 반환하는 메서드
    // 날짜 조회 메서드 추가
    @GetMapping("/list")
    public String getExerciseLogs(
            // @RequestParam을 통해 날짜를 받아오고, 기본값으로 오늘 날짜 사용
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            Model model) throws JsonProcessingException {

        // 날짜가 없으면 오늘 날짜를 기본값으로 사용
        LocalDate targetDate = (date != null) ? date : LocalDate.now();

        // 운동 기록 가져오기
        List<ExerciseLog> logs = exerciseLogService.getLogsByDate(targetDate); // 호출하여 해당 날짜 운동기록 가져옴


        // 유산소 운동과 무산소 운동 기록 분리
        List<ExerciseLog> aerobicLogs = logs.stream()
                .filter(log -> log.getAerobicExerciseName() != null && !log.getAerobicExerciseName().isEmpty())
                .collect(Collectors.toList());

        List<ExerciseLog> anaerobicLogs = logs.stream()
                .filter(log -> log.getAnaerobicExerciseName() != null && !log.getAnaerobicExerciseName().isEmpty())
                .collect(Collectors.toList());


        // 모델에 운동 기록 추가
        //model.addAttribute("logs", logs);
        // 모델에 운동 기록 추가
        model.addAttribute("aerobicLogs", aerobicLogs);
        model.addAttribute("anaerobicLogs", anaerobicLogs);
        model.addAttribute("selectedDate", targetDate);



        System.out.println("운동 기록: " + logs);

        return "exercise-log-list"; // 운동 기록 목록 페이지 반환
    }

    // CRUD 중 U(수정)
    // 운동 기록 수정 페이지
    @GetMapping("/edit/{id}") // 사용자가 수정할 운동 기록을 조회하여 수정페이지로 이동하는 메서드
    public String showEditForm(@PathVariable Long id, Model model) {
        ExerciseLog exerciseLog = exerciseLogService.getExerciseLogById(id); // id로 운동기록 찾기
        model.addAttribute("exerciseLog", exerciseLog); // 모델에 exerciseLog 추가
        return "edit"; // 수정페이지로 이동
    }

    // CRUD 중 U(수정)
    // 운동 기록 수정 처리
    @PostMapping("/update/{id}") // 사용자가 수정한 내용을 저장하는 메서드
    public String updateExerciseLog(@PathVariable Long id, @ModelAttribute ExerciseLog updatedLog, Model model) {
        System.out.println("잘나오나? 업뎃" + updatedLog);

        ExerciseLog existingLog = exerciseLogService.getExerciseLogById(id);
        if (existingLog == null) {
            model.addAttribute("message", "해당 운동 기록을 찾을 수 없습니다.");
            return "redirect:/exercise-log/list"; // 기록이 없으면 목록으로 리다이렉트
        }
        // 유산소 운동 정보 업데이트
        existingLog.setAerobicExerciseType(updatedLog.getAerobicExerciseType()); // 기존 데이터수 수정
        existingLog.setAerobicExerciseName(updatedLog.getAerobicExerciseName());
        existingLog.setDuration(updatedLog.getDuration());
        existingLog.setCaloriesBurned(updatedLog.getCaloriesBurned());

        // 무산소 운동 정보 업데이트
        existingLog.setAnaerobicExerciseType(updatedLog.getAnaerobicExerciseType());
        existingLog.setAnaerobicExerciseName(updatedLog.getAnaerobicExerciseName());
        existingLog.setWeight(updatedLog.getWeight());
        existingLog.setRepetitions(updatedLog.getRepetitions());
        existingLog.setSets(updatedLog.getSets());

        exerciseLogService.updateExerciseLog(id, existingLog); // 수정된 기록을 저장
        return "redirect:/exercise-log/list"; // 수정 후 목록 페이지로 리다이렉트
    }

    // CRUD 중 D(삭제)
    //운동삭제들
    @PostMapping("/delete/aerobic/{id}")
    public String deleteAerobicExercise(@PathVariable Long id) {
        // 유산소 운동만 삭제
        exerciseLogService.deleteAerobicExercise(id);
        return "redirect:/exercise-log/list";
    }
    @PostMapping("/delete/anaerobic/{id}")
    public String deleteAnaerobicExercise(@PathVariable Long id) {
        // 무산소 운동만 삭제
        exerciseLogService.deleteAnaerobicExercise(id);
        return "redirect:/exercise-log/list";
    }


}