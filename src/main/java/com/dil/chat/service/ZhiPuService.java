package com.dil.chat.service;

import com.alibaba.fastjson2.JSON;
import com.dil.chat.model.ChatLLMResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.nashorn.internal.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author: "xx"
 * @Date: 2025/2/9
 */
@Service
public class ZhiPuService {

    private final Logger logger = LoggerFactory.getLogger(ZhiPuService.class);

    @Value("${zhipu.apiKeySecret}")
    private String apiKeySecret;

    @Value("${zhipu.apiUrl}")
    private String apiUrl;

    public String talk(String request) throws IOException {
        String token = apiKeySecret;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + token);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        connection.setDoOutput(true);

        String jsonInpuString = "{"
                + "\"model\":\"glm-4-flash\","
                + "\"messages\": ["
                + "    {"
                + "        \"role\": \"system\", \"content\": \"生成的内容请不要使用特殊符号，响应格式就是文本内容\"}, "
                + "    {    \"role\": \"user\", \"content\": \"" + request + "\"}"
                + "]"
                + "}";

        try(OutputStream os = connection.getOutputStream()){
            byte[] input = jsonInpuString.getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null){
            content.append(inputLine);
        }

        in.close();
        connection.disconnect();
        ChatLLMResponse res = JSON.parseObject(content.toString(), ChatLLMResponse.class);
        logger.info("AI 回答: {}", res.getChoices().get(0).getMessage().getContent());
        return res.getChoices().get(0).getMessage().getContent();
    }
}
