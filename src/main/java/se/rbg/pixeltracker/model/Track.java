package se.rbg.pixeltracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "tracking_track")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Track {
  @Id
  @SequenceGenerator(name = "ID_SEQ", sequenceName = "id_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "id_seq")
  @JsonIgnore
  long id;

  String url;
  LocalDateTime timestamp;

  @ManyToOne
  @ToString.Exclude
  @JsonIgnore
  User user;
}
