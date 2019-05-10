package com.oycl.controller;

import com.google.gson.Gson;
import com.oycl.entity.DayOffInput;
import com.oycl.entity.DayOffOutPut;
import com.oycl.service.DayOffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dayoff")
public class DayOffController {

    @Autowired
    private DayOffService dayOffService;

    @PostMapping("/start")
    public DayOffOutPut startDayoff() {
        DayOffInput input = new DayOffInput();
        input.setDays(2);
        input.setReason("请假");
        return dayOffService.startDayoff(input);
    }

    @PostMapping("/employeeApply")
    public DayOffOutPut employeeApply(@RequestBody DayOffInput input) {
        return dayOffService.employeeApply(input);
    }
    @PostMapping("/showTasks")
    public String showTasks(String groups){
      return  dayOffService.showTask(groups);
    }

    @PostMapping("/mgApprove")
    public String mgApprove(@RequestBody  DayOffInput input){
        return dayOffService.mgApprove(input);
    }

}
