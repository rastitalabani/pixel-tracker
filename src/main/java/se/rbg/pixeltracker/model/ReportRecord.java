package se.rbg.pixeltracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "v_period_report")
@Immutable
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportRecord {
  @Id
  private String url;
  private int pageVisits;
  private int visitors;

  @Override
  public String toString() {
    return
      " | "  + url + '\'' +
      " | " + pageVisits +
      " | " + visitors ;
  }
}
