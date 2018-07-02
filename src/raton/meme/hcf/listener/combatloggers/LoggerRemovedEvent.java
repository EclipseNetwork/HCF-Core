package raton.meme.hcf.listener.combatloggers;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.beans.ConstructorProperties;

public class LoggerRemovedEvent
        extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final LoggerEntity loggerEntity;

    @ConstructorProperties({"loggerEntity"})
    public LoggerRemovedEvent(LoggerEntity loggerEntity) {
        this.loggerEntity = loggerEntity;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public LoggerEntity getLoggerEntity() {
        return this.loggerEntity;
    }

    public HandlerList getHandlers() {
        return handlers;
    }
}
