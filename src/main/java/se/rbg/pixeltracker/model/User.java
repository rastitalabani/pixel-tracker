package se.rbg.pixeltracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity(name = "tracking_user")
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User {
  @Id
  @SequenceGenerator(name = "ID_SEQ", sequenceName = "id_seq", allocationSize = 1)
  @GeneratedValue(strategy = SEQUENCE, generator = "id_seq")
  @JsonIgnore
  long id;
  UUID userId;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "user_id")
  List<Track> tracks;
}
