package se.rbg.pixeltracker.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import se.rbg.pixeltracker.model.User;
import se.rbg.pixeltracker.service.TrackingService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@SuppressWarnings("unused")
public class Index {

  private TrackingService trackingService;

  public Index(TrackingService trackingService) {
    this.trackingService = trackingService;
  }

  @GetMapping(value = "/image", produces = MediaType.IMAGE_GIF_VALUE)
  public ResponseEntity<byte[]> getImage(@RequestHeader(value = "referer") String referer,
                                         @CookieValue(value = "userId", defaultValue = "") String userId,
                                         HttpServletResponse response) throws IOException {

    Cookie cookie = trackingService.handle(userId, referer);

    response.addCookie(cookie);

    ClassPathResource imageResource = new ClassPathResource("images/transparent1by1.gif");
    StreamUtils.copy(imageResource.getInputStream(), response.getOutputStream());

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/tracks")
  public List<User> getReport() {
    return trackingService.getTracks();
  }
}
