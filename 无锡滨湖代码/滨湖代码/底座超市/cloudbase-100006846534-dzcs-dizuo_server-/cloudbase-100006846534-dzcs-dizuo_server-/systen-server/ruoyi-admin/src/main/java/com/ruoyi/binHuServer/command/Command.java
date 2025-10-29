package com.ruoyi.binHuServer.command;

import com.ruoyi.system.service.ApiRequestService;
import com.ruoyi.system.service.IInsightApiService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
@Order(3)
public class Command implements CommandLineRunner {
   ApiRequestService apiRequestService;

    IInsightApiService insightApiService;
    @Override
    public void run(String... args) throws Exception {
        apiRequestService.getWeather();
        insightApiService.countAi();
}
}
