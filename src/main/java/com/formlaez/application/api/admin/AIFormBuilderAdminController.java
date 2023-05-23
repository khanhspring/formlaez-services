package com.formlaez.application.api.admin;

import com.formlaez.application.model.request.AIFormRequest;
import com.formlaez.application.model.response.form.ai.AIFormResponse;
import com.formlaez.service.ai.AIFormBuilderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/admin/forms")
public class AIFormBuilderAdminController {

    private final AIFormBuilderService aiFormBuilderService;

    @PostMapping(path = "ai/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AIFormResponse generate(@Valid AIFormRequest request) {
        return aiFormBuilderService.generate(request);
    }

    @GetMapping("/stream")
    public ResponseEntity<StreamingResponseBody> stream() {
        StreamingResponseBody responseBody = outputStream -> {
            for (int i = 0; i < 10; i++) {
                outputStream.write(("Streaming response " + i + "\n").getBytes());
                outputStream.flush();
                try {
                    Thread.sleep(1000); // simulate some work being done
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "text/plain")
                .body(responseBody);
    }
}
