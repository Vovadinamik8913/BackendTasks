package edu.java.scrapper.service;
import edu.java.scrapper.model.Link;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrackService {
    private final Map<Long, List<Link>> tracks;

    public TrackService() {
        tracks = new HashMap<>();
    }

    public void addTrack(Long chatId, Link track) {
        if (!tracks.containsKey(chatId)) {
            tracks.put(chatId, new ArrayList<>());
        }
        tracks.get(chatId).add(track);
    }

    @Nullable
    public Link removeTrack(Long chatId, Long trackId) {
        if (tracks.containsKey(chatId) && trackId < tracks.get(chatId).size()) {
            Link data = tracks.get(chatId).get(Math.toIntExact(trackId));
            tracks.get(chatId).remove(Math.toIntExact(trackId));
            return data;
        }
        return null;
    }

    public List<Link> getTracks(Long chatId) {
        return tracks.getOrDefault(chatId, new ArrayList<>());
    }

    public List<Map.Entry<Long,List<Link>>> getTracks() {
        return new ArrayList<>(tracks.entrySet());
    }
}
