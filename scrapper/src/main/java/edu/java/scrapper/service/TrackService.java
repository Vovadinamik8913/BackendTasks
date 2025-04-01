package edu.java.scrapper.service;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrackService {
    private final Map<Long, List<String>> tracks;

    public TrackService() {
        tracks = new HashMap<>();
    }

    public void addTrack(Long chatId, String track) {
        if (!tracks.containsKey(chatId)) {
            tracks.put(chatId, new ArrayList<>());
        }
        tracks.get(chatId).add(track);
    }

    public String removeTrack(Long chatId, Long trackId) {
        if (tracks.containsKey(chatId) && trackId < tracks.get(chatId).size()) {
            String data = tracks.get(chatId).get(Math.toIntExact(trackId));
            tracks.get(chatId).remove(Math.toIntExact(trackId));
            return data;
        }
        return "";
    }

    public List<String> getTracks(Long chatId) {
        return tracks.getOrDefault(chatId, new ArrayList<>());
    }
}
