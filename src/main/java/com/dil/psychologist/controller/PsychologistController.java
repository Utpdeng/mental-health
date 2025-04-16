package com.dil.psychologist.controller;

import com.dil.common.response.Result;
import com.dil.psychologist.dto.PsychologistListResponse;
import com.dil.psychologist.dto.PsychologistDetailResponse;
import com.dil.psychologist.dto.UpdatePsychologistRequest;
import com.dil.psychologist.service.PsychologistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.math.BigDecimal;

/**
 * 心理医生控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/psychologists")
public class PsychologistController {

    @Autowired
    private PsychologistService psychologistService;

    @GetMapping("/list")
    public Result<List<PsychologistListResponse>> getPsychologists(@RequestParam(value = "employment_status", required = false) String employmentStatus) {
        log.info("收到获取心理医生列表请求，雇佣状态：{}", employmentStatus);
        List<PsychologistListResponse> psychologists = psychologistService.getPsychologists(employmentStatus);
        return Result.success(psychologists);
    }

    @GetMapping("/{psychologist_id}")
    public Result<PsychologistDetailResponse> getPsychologistDetail(@PathVariable("psychologist_id") Long psychologistId) {
        log.info("收到获取心理医生详情请求，心理医生ID：{}", psychologistId);
        PsychologistDetailResponse psychologistDetail = psychologistService.getPsychologistDetail(psychologistId);
        return Result.success(psychologistDetail);
    }

    /**
     * 更新心理医生信息
     *
     * @param request 更新请求
     * @return 操作结果
     */
    @PostMapping("/updatePsychologist")
    public Result<?> updatePsychologist(@RequestBody UpdatePsychologistRequest request) {
        log.info("收到更新心理医生信息请求，心理医生ID：{}", request.getPsychologistId());
        psychologistService.updatePsychologist(request);
        return Result.success(null);
    }
}