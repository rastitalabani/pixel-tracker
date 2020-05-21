package se.rbg.pixeltracker.repository;

import org.springframework.data.repository.CrudRepository;
import se.rbg.pixeltracker.model.Track;

public interface TrackRepository extends CrudRepository<Track, Long> {
}
