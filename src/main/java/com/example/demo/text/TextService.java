package com.example.demo.text;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TextService {

    @Value("${flask.server.url}")
    private String flaskServerUrl;

    @Value("${MYPATH}")
    private String uploadDir;

    private final TextRepository textRepository;
    private final RestTemplate restTemplate;

    // DB에 text 저장
    public void input(Long id, String content){
        Text text = new Text();
        text.setId(id);
        text.setText(content);
        textRepository.save(text);
    }

    // Flask로 HTTP통신

    public void toFlask(String text) throws IOException {

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 문자열을 HTTP 요청 본문에 포함
        HttpEntity<String> requestEntity = new HttpEntity<>(text, headers);

        // 플라스크 서버로 HTTP POST 요청 보내기
        ResponseEntity<String> responseEntity = restTemplate.
                exchange(flaskServerUrl+ "generateImage", HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println(requestEntity.getBody() + " : Image successfully processed by Flask.");
        } else {
            System.err.println("Error processing image.");
        }
    }

    // 중복안되게 이름 설정
    private String generateUniqueFileName() {
        String uniqueFileName = UUID.randomUUID().toString();
        return uniqueFileName;
    }
}
