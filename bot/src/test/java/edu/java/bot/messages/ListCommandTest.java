package edu.java.bot.messages;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.client.ScrapperClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListCommandTest {
    @Mock ScrapperClient scrapperClient;
    @InjectMocks private ListCommand listCommand;

    private Update createMockUpdate(long chatId) {
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        Chat chat = mock(Chat.class);

        when(update.message()).thenReturn(message);
        when(message.chat()).thenReturn(chat);
        when(chat.id()).thenReturn(chatId);

        return update;
    }

    @Test
    void emptyListCommand() {
        long chatId = 123L;
        Update update = createMockUpdate(chatId);
        when(scrapperClient.tracksList(Mockito.anyLong())).thenReturn("empty");
        var result = listCommand.handle(update);
        assertEquals("empty", result.getParameters().get("text"));
    }

    @Test
    void handle_shouldReturnFormattedLinksList() {
        long chatId = 123L;
        Update update = createMockUpdate(chatId);
        String expectedResponse = "https://github.com\nhttps://stackoverflow.com\n";
        when(scrapperClient.tracksList(chatId)).thenReturn(expectedResponse);
        var result = listCommand.handle(update);
        assertEquals(expectedResponse, result.getParameters().get("text"));
    }

    @Test
    void handle_shouldReturnErrorMessageOnException() {
        long chatId = 123L;
        Update update = createMockUpdate(chatId);
        String errorMessage = "Произошла ошибка";
        when(scrapperClient.tracksList(chatId)).thenThrow(new RuntimeException(errorMessage));
        var result = listCommand.handle(update);
        assertEquals(errorMessage, result.getParameters().get("text"));
    }
}
