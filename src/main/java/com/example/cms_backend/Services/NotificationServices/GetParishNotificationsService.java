package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.Entities.Notification;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.NotificationRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetParishNotificationsService implements Query<HttpServletRequest, List<Notification>> {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public GetParishNotificationsService(NotificationRepository notificationRepository,
                                         UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<List<Notification>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        Long parishId = user.getParishId();

        if (parishId == null) {
            return ResponseEntity.badRequest().build(); // Or handle as per your logic
        }

        List<Notification> parishNotifications = notificationRepository.findByParishId(parishId);
        return ResponseEntity.ok(parishNotifications);
    }
}
