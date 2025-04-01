package edu.java.scrapper.service;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TrackService {
    public Map<Long, List<String>> tracks;

    public TrackService() {
        tracks = new HashMap<>();
    }

    public void addTrack(Long chatId, String track) {
        if (!tracks.containsKey(chatId)) {
            tracks.put(chatId, new ArrayList<>());
        }
        tracks.get(chatId).add(track);
    }

    public boolean removeTrack(Long chatId, String track) {
        if (tracks.containsKey(chatId)) {
            if (tracks.get(chatId).contains(track)) {
                tracks.get(chatId).remove(track);
                return true;
            }
        }
        return false;
    }

    public List<String> getTracks(Long chatId) {
        if (!tracks.containsKey(chatId)) {
            return new ArrayList<>();
        }
        return tracks.get(chatId);
    }
}
