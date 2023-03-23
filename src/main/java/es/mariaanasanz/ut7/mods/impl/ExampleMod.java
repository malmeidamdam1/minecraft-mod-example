package es.mariaanasanz.ut7.mods.impl;

import es.mariaanasanz.ut7.mods.base.*;
import jdk.javadoc.doclet.Taglet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.commands.SetBlockCommand;
import net.minecraft.server.commands.WorldBorderCommand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PlayerHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
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

    @Override
    @SubscribeEvent
//TODO El evento con el que voy a trabajar
    public void onPlayerWalk(MovementInputUpdateEvent event) {
        if (event.getEntity() instanceof Player) {
            Player jugador = event.getEntity();

            ItemStack boots = event.getEntity().getInventory().getArmor(0); //Guardo valor botas

            Level mundo = jugador.getCommandSenderWorld();

            Block bloqueOro = Blocks.GOLD_BLOCK;


            Vec3 position = jugador.position()  ;
            BlockPos posActual = new BlockPos(position);


        // mundo.setBlock(bloquepos, bloqueOro.defaultBlockState(),2); //Saca uno atras


            if (event.getInput().down) {
                System.out.println("down" + event.getInput().down);
                System.out.println("Tienes " + boots);

            }
            if (event.getInput().up) {
                System.out.println("up" + event.getInput().up);
                System.out.println("Tienes " + boots);

                mundo.setBlock(posActual.south(), bloqueOro.defaultBlockState(),1);





            }
            if (event.getInput().right) {
                System.out.println("right" + event.getInput().right);
                System.out.println("Tienes " + boots);
            }
            if (event.getInput().left) {
                System.out.println("left" + event.getInput().left);
                System.out.println("Tienes " + boots);
            }

        }
    }





//    @SubscribeEvent
//    public void onPlayerMove(PlayerEvent event) {
////        Player player = event.getEntity();
////        Location location = player.getLocation();
////        Block block = location();
//
//        int x = player.getBlockX();
//        int y = player.getBlockY();
//        int z = player.getBlockY();
//
//
//        System.out.println("El jugador está en el bloque " + block.getType() + " en la ubicación " + block.getLocation());
//    }

    @SubscribeEvent
    public void compruebaBotas(PlayerEvent event) {

//            Items.LEATHER_BOOTS;
//        Items.IRON_BOOTS;
//        Items.GOLDEN_BOOTS;
//        Items.DIAMOND_BOOTS;
//


//        if(player instanceof  Player) {
//            ItemStack boots = player.getInventory().getArmor(0);
//            if (boots != null) {
//                System.out.println("Tienes " + boots);
//            }else{
//                System.out.println("No llevas");
//            }
//        }

//        if(event.getEntity() instanceof Player){
//            ItemStack boots = event.getEntity().getInventory().getArmor(0);
//
//            if(boots.is(Items.GOLDEN_BOOTS)){
//                System.out.println("Llevas botas de oro");
//            }
//            if(boots.is(Items.DIAMOND_BOOTS)){
//                System.out.println("Llevas botas de diamanete");
//            }
//            if(boots.is(Items.LEATHER_BOOTS)){
//                System.out.println("Llevas botas de cuero");
//            }
//            if(boots.is(Items.IRON_BOOTS)){
//                System.out.println("Llevas botas de hierro");
//            }
////            else{
////                System.out.println("Llevas aire");
////            }
//
//        }



//        ItemStack boots = player.getInventory().getArmorContents()[0];
//        if (boots != null) {
//            Class<? extends Item> itemType = boots.getItemType();
//            if (ItemStack.class.isAssignableFrom(itemType)) {
//                if (itemType.isAssignableFrom(Items.LEATHER_BOOTS.getClass())) {
//                    // El jugador está usando botas de cuero.
//                } else if (itemType.isAssignableFrom(Items.IRON_BOOTS.getClass())) {
//                    // El jugador está usando botas de hierro.
//                } else if (itemType.isAssignableFrom(Items.GOLDEN_BOOTS.getClass())) {
//                    // El jugador está usando botas de oro.
//                } else if (itemType.isAssignableFrom(Items.DIAMOND_BOOTS.getClass())) {
//                    // El jugador está usando botas de diamante.
//                }
//            }
//        }

    }

}
