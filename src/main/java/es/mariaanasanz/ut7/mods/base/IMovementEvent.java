package es.mariaanasanz.ut7.mods.base;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.MovementInputUpdateEvent;

public interface IMovementEvent {

    public void onPlayerWalk(MovementInputUpdateEvent event);


}
