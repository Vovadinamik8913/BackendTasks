package edu.java.scrapper.service;

import edu.java.scrapper.dto.LinkDto;
import org.springframework.stereotype.Service;
import java.util.Collection;

@Service
public interface LinkService {
    LinkDto add(long tgChatId, String url);
    boolean remove(long tgChatId, String url);
    Collection<LinkDto> listAll();
    Collection<LinkDto> listByChatId(long tgChatId);
    void update(long tgChatId, String url);
}
