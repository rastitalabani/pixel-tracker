package se.rbg.pixeltracker.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import se.rbg.pixeltracker.model.Track;
import se.rbg.pixeltracker.model.User;
import se.rbg.pixeltracker.repository.TrackRepository;
import se.rbg.pixeltracker.repository.UserRepository;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrackingServiceTest {

  private UserRepository userRepository = Mockito.mock(UserRepository.class);
  private TrackRepository trackRepository = Mockito.mock(TrackRepository.class);

  @Spy
  private TrackingService trackingService = new TrackingService(userRepository, trackRepository);

  private String userId = "2445a83e-94dc-42cb-8661-f49f4921fb8f";
  private String referer = "http://test.com/test.html";

  @Test
  @DisplayName("when cookie user does not exist then create a tracking record")
  void testBasicLogging() {
    when(trackingService.generateRandomUuid()).thenReturn(UUID.fromString("2445a83e-94dc-42cb-8661-f49f4921fb8f"));
    when(trackingService.getCurrentDateTime()).thenReturn(LocalDateTime.of(2020, 01, 01, 01, 01));

    Track track = Track.builder()
      .timestamp(trackingService.getCurrentDateTime())
      .url(trackingService.getReferer(referer))
      .build();

    User user = User.builder()
      .userId(UUID.fromString(userId))
      .tracks(Collections.singletonList(track))
      .build();

    when(userRepository.save(any())).thenReturn(user);

    trackingService.handle("", referer);

    verify(userRepository, times(1)).save(user);
  }


  @Test
  @DisplayName("when user cookie exists then a new tracking record with existing user")
  void testRepeatVisit() {
    when(trackingService.getCurrentDateTime()).thenReturn(LocalDateTime.of(2020, 01, 01, 01, 01));

    User user = User.builder()
      .userId(UUID.fromString(userId))
      .build();

    Track track = Track.builder()
      .timestamp(trackingService.getCurrentDateTime())
      .url(trackingService.getReferer(referer))
      .user(user)
      .build();

    when(userRepository.findUserByUserId(any())).thenReturn(Optional.of(user));
    when(userRepository.save(any())).thenReturn(user);
    when(trackRepository.save(any())).thenReturn(track);

    trackingService.handle(userId, referer);

    verify(trackRepository, times(1)).save(track);
  }

  @Test
  @DisplayName("when a valid referer then parse")
  void testRefererParser() {
    String httpsReferer = "https://test.com/test.html";

    String visitedHttpPage = trackingService.getReferer(referer);
    String visitedHttpsPage = trackingService.getReferer(httpsReferer);

    assertEquals("/test.html", visitedHttpPage);
    assertEquals("/test.html", visitedHttpsPage);
  }

  @Test
  @DisplayName("when invalid referer then exception")
  void testInvalidRefererParser() {
    String httpsReferer = "error";

    assertThrows(MalformedURLException.class, () -> trackingService.getReferer(httpsReferer));
  }
}