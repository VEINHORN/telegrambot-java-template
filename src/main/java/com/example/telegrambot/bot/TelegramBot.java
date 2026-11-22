package com.example.telegrambot.bot;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.CommandLongPollingTelegramBot;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.Collection;

@Slf4j
@Component
public class TelegramBot extends CommandLongPollingTelegramBot implements SpringLongPollingBot {
    @Value("${telegram.bot.token}")
    private String telegramBotToken;

    private final ApplicationContext applicationContext;

    public TelegramBot(TelegramClient telegramClient, ApplicationContext applicationContext) {
        super(telegramClient, false, null);
        this.applicationContext = applicationContext;

        registerAll(getCommands().toArray(IBotCommand[]::new));
    }

    @SneakyThrows
    @AfterBotRegistration
    public void registerCommands(BotSession session) {
        var commands = getCommands()
                .stream()
                .map(command -> new BotCommand(command.getCommandIdentifier(), command.getDescription()))
                .toList();

        var setCommands = SetMyCommands
                .builder()
                .commands(commands)
                .build();

        telegramClient.execute(setCommands);
    }

    @Override
    @SneakyThrows
    public void processNonCommandUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("Non-command message: {}", update.getMessage().getText());
        } else if (update.hasCallbackQuery()) {
            log.debug("Callback query data: {}", update.getCallbackQuery().getData());
        }
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public String getBotToken() {
        return telegramBotToken;
    }

    private Collection<IBotCommand> getCommands() {
        return applicationContext.getBeansOfType(IBotCommand.class).values();
    }
}
