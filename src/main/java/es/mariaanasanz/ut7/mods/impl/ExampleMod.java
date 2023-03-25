package es.mariaanasanz.ut7.mods.impl;

import es.mariaanasanz.ut7.mods.base.*;
import jdk.javadoc.doclet.Taglet;
import net.minecraft.client.player.Input;
import net.minecraft.core.BlockPos;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.server.commands.WorldBorderCommand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PlayerHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.xml.stream.Location;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Mod(DamMod.MOD_ID)
public class ExampleMod extends DamMod implements IBlockBreakEvent, IServerStartEvent,
        IItemPickupEvent, ILivingDamageEvent, IUseItemEvent, IFishedEvent,
        IInteractEvent, IMovementEvent {

    public ExampleMod() {
        super();
    }

    @Override
    public String autor() {
        return "Midgard Almeida Minda";
    }

    @Override
    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockPos pos = event.getPos();
        System.out.println("Bloque destruido en la posicion " + pos);
    }

    @Override
    @SubscribeEvent
    public void onServerStart(ServerStartingEvent event) {
        LOGGER.info("Server starting");
    }

    @Override
    @SubscribeEvent
    public void onItemPickup(EntityItemPickupEvent event) {
        LOGGER.info("Item recogido");
        System.out.println("Item recogido");
    }

    @Override
    @SubscribeEvent
    public void onLivingDamage(LivingDamageEvent event) {
        System.out.println("evento LivingDamageEvent invocado " + event.getEntity().getClass() + " provocado por " + event.getSource().getEntity());
    }

    @Override
    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        System.out.println("evento LivingDeathEvent invocado " + event.getEntity().getClass() + " provocado por " + event.getSource().getEntity());

    }

    @Override
    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event) {
        LOGGER.info("evento LivingEntityUseItemEvent invocado " + event.getEntity().getClass());
    }


    @Override
    @SubscribeEvent
    public void onPlayerFish(ItemFishedEvent event) {
        System.out.println("¡Has pescado un pez!");
    }

    @Override
    @SubscribeEvent
    public void onPlayerTouch(PlayerInteractEvent.RightClickBlock event) {
        System.out.println("¡Has hecho click derecho!");
        BlockPos pos = event.getPos();
        BlockState state = event.getLevel().getBlockState(pos);
        Player player = event.getEntity();
        ItemStack heldItem = player.getMainHandItem();
        if (ItemStack.EMPTY.equals(heldItem)) {
            System.out.println("La mano esta vacia");
            if (state.getBlock().getName().getString().trim().toLowerCase().endsWith("log")) {
                System.out.println("¡Has hecho click sobre un tronco!");
            }
        }
    }


//---------------------------------------------------------Desde aqui mi mod------------------------------------------//
    private Set<BlockPos> bloquesPisados = new HashSet<>();
    private BlockPos posAnterior;
    //Creo como atributo fuera para que no se reinicie llamar metodo
    @Override
    @SubscribeEvent
//TODO El evento con el que voy a trabajar
        public void onPlayerWalk(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof Player) {
            Player jugador = event.getEntity();
            Level mundo = jugador.getCommandSenderWorld();

            ItemStack boots = jugador.getInventory().getArmor(0); //Guardo cualquier valor que tengan Botas

            Block bloqueOro = Blocks.GOLD_BLOCK;

            Block macetas = Blocks.FLOWER_POT;

            Block flores = Blocks.FLOWERING_AZALEA;

            BlockState estado = jugador.getBlockStateOn(); //EstadoBloqueDebajo

            BlockPos posActual = jugador.getOnPos();   //Bloque debajo (pisando ahora)



//            estado.addRunningEffects()

            //Ejecutamos si el jugador ha pisado ya el bloque Y NO ES sobre el que esta ahora
             if(bloquesPisados.contains(posAnterior) && !posActual.equals(posAnterior)) {
                 if (mundo.getBlockState(posAnterior).getMaterial().isSolid()) { //No transformamos "bloques" de aire

                     if (boots.getItem().equals(Items.GOLDEN_BOOTS)){
                         mundo.setBlockAndUpdate(posAnterior, bloqueOro.defaultBlockState());
                     }

                     if (boots.getItem().equals(Items.LEATHER_BOOTS)) {
                     }

                     if (boots.getItem().equals(Items.IRON_BOOTS)) {
                     }

                     if (boots.getItem().equals(Items.DIAMOND_BOOTS)) {
                         mundo.setBlockAndUpdate(posAnterior,flores.defaultBlockState());
                     }
                 }
             }
             else{//No cambiamos el suelo pero añadimos la posicion
                 bloquesPisados.add(posActual);
             }//PosAnterior se actualiza constantemente
            posAnterior = posActual;
            }
        }

}

//            if (event.getInput().down) {
//                System.out.println("down" + event.getInput().down);
//
//            }
//            if (event.getInput().up) {
//                System.out.println("up" + event.getInput().up);
//
//            }
//            if (event.getInput().right) {
//                System.out.println("right" + event.getInput().right);
////                mundo.setBlockAndUpdate(posAnterior,bloqueOro.defaultBlockState());
//
//            }
//            if (event.getInput().left) {
//                System.out.println("left" + event.getInput().left);
////                mundo.setBlockAndUpdate(posAnterior,bloqueOro.defaultBlockState());




