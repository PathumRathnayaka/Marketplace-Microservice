package com.cloudpos.notification.controller;

import com.cloudpos.common.response.ApiResponse;
import com.cloudpos.notification.dto.NotificationResponseDTO;
import com.cloudpos.notification.dto.SendNotificationRequestDTO;
import com.cloudpos.notification.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<NotificationResponseDTO>> send(
            @Valid @RequestBody SendNotificationRequestDTO request) {
        return ResponseEntity
                .ok(ApiResponse.success("Notification sent", notificationService.sendNotification(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<NotificationResponseDTO>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Notifications fetched", notificationService.getNotifications()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponseDTO>> getById(@PathVariable String id) {
        return ResponseEntity
                .ok(ApiResponse.success("Notification fetched", notificationService.getNotificationById(id)));
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<ApiResponse<NotificationResponseDTO>> markAsRead(@PathVariable String id) {
        return ResponseEntity
                .ok(ApiResponse.success("Notification marked as read", notificationService.markAsRead(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        notificationService.softDelete(id);
        return ResponseEntity.ok(ApiResponse.success("Notification deleted", null));
    }
}
