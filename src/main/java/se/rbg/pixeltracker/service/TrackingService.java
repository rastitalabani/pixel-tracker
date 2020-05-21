package se.rbg.pixeltracker.service;

import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import se.rbg.pixeltracker.model.Track;
import se.rbg.pixeltracker.model.User;
import se.rbg.pixeltracker.repository.TrackRepository;
import se.rbg.pixeltracker.repository.UserRepository;

import javax.servlet.http.Cookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrackingService {
  private static final String COOKIE_NAME = "userId";
  private UserRepository userRepository;
  private TrackRepository trackRepository;

  TrackingService(UserRepository userRepository, TrackRepository trackRepository) {
    this.userRepository = userRepository;
    this.trackRepository = trackRepository;
  }

  public Cookie handle(String userId, String refererHeader) {
    String referer = getReferer(refererHeader);
    User user;

    if (userId.isEmpty()) {
      user = registerNewVisit(referer);
    } else {
      Optional<User> fetchedUser = userRepository.findUserByUserId(UUID.fromString(userId));

      if (fetchedUser.isPresent())
        user = registerRevisit(fetchedUser.get(), referer).getUser();
      else
        user = registerNewVisit(referer);
    }

    return createTrackingCookie(user.getUserId());
  }

  public List<User> getTracks() {
    return userRepository.findAll();
  }

  @SneakyThrows(MalformedURLException.class)
  String getReferer(String refererHeader) {
    return new URL(refererHeader).getPath();
  }

  private User registerNewVisit(String referer) {
    Track track = Track.builder()
      .timestamp(getCurrentDateTime())
      .url(referer)
      .build();

    User user = User.builder()
      .tracks(Collections.singletonList(track))
      .userId(generateRandomUuid())
      .build();

    return userRepository.save(user);
  }

  private Track registerRevisit(User user, String referer) {
    Track track = Track.builder()
      .timestamp(getCurrentDateTime())
      .url(referer)
      .user(user)
      .build();

    return trackRepository.save(track);
  }

  private Cookie createTrackingCookie(UUID userId) {
    Cookie cookie = new Cookie(COOKIE_NAME, userId.toString());
    cookie.setMaxAge(7 * 24 * 60 * 60);
    return cookie;
  }

  UUID generateRandomUuid() {
    return UUID.randomUUID();
  }

  LocalDateTime getCurrentDateTime() {
    return LocalDateTime.now();
  }
}
