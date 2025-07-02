package com.example.cms_backend.Services.NotificationServices;

import com.example.cms_backend.Abstractions.Query;
import com.example.cms_backend.Exceptions.UserNotFoundException;
import com.example.cms_backend.Model.DTO.NotificationViewDTO;
import com.example.cms_backend.Model.Entities.Group;
import com.example.cms_backend.Model.Entities.Kanda;
import com.example.cms_backend.Model.Entities.Notification;
import com.example.cms_backend.Model.Entities.Parish;
import com.example.cms_backend.Model.Entities.User;
import com.example.cms_backend.Repositories.GroupRepository;
import com.example.cms_backend.Repositories.KandaRepository;
import com.example.cms_backend.Repositories.NotificationRepository;
import com.example.cms_backend.Repositories.ParishRepository;
import com.example.cms_backend.Repositories.UserRepository;
import com.example.cms_backend.Utils.LoggedInUserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetParishNotificationsService implements Query<HttpServletRequest, List<NotificationViewDTO>> {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final ParishRepository parishRepository;
    private final GroupRepository groupRepository;
    private final KandaRepository kandaRepository;

    @Override
    public ResponseEntity<List<NotificationViewDTO>> execute(HttpServletRequest request) {
        String email = LoggedInUserUtil.loggedInUserEmail(request);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        }

        User user = optionalUser.get();
        Long parishId = user.getParishId();

        if (parishId == null) {
            return ResponseEntity.badRequest().build();
        }

        List<Notification> notifications = notificationRepository.findByParishId(parishId);

        List<NotificationViewDTO> dtos = notifications.stream().map(n -> {
            String parishName = null;
            String groupName = null;
            String kandaName = null;

            if (n.getParishId() != null) {
                Parish parish = parishRepository.findById(n.getParishId()).orElse(null);
                parishName = (parish != null) ? parish.getName() : null;
            }

            if (n.getGroupId() != null) {
                Group group = groupRepository.findById(n.getGroupId()).orElse(null);
                groupName = (group != null) ? group.getName() : null;
            }

            if (n.getKandaId() != null) {
                Kanda kanda = kandaRepository.findById(n.getKandaId()).orElse(null);
                kandaName = (kanda != null) ? kanda.getName() : null;
            }

            return new NotificationViewDTO(
                    n.getId(),
                    n.getTitle(),
                    n.getMessage(),
                    n.getDate(),
                    n.getIsGlobal(),
                    n.getUserId(),
                    n.getSenderId(),
                    n.getGroupId(),
                    groupName,
                    n.getKandaId(),
                    kandaName,
                    n.getParishId()
            );
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
