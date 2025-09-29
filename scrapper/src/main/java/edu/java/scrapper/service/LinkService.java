package edu.java.scrapper.service;

import edu.java.scrapper.dto.Track;
import java.util.Collection;

public interface LinkService {
    Track add(long tgChatId, String url);
    boolean remove(long tgChatId, String url);
    Collection<Track> listAll();
    Collection<Track> listByChatId(long tgChatId);
    void update(long tgChatId, String url);
}
