package lightningstudios.solarcycler.network;

import io.netty.buffer.ByteBuf;
import lightningstudios.solarcycler.tile.TileEntitySolarCycler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketButton implements IMessage {
    
    private BlockPos pos;
    private int worldID;
    private TileEntitySolarCycler.EnumButtons pressed;
    
    public PacketButton() {
    }
    
    public PacketButton(BlockPos pos, int worldID, TileEntitySolarCycler.EnumButtons toSend) {
        this.pos = pos;
        this.worldID = worldID;
        this.pressed = toSend;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
        worldID = buf.readInt();
        pressed = TileEntitySolarCycler.EnumButtons.values()[buf.readByte()];
    }
    
    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(pos.getX());
        buf.writeInt(pos.getY());
        buf.writeInt(pos.getZ());
        buf.writeInt(worldID);
        buf.writeByte(pressed.ordinal());
    }
    
    public static class Handler implements IMessageHandler<PacketButton, IMessage> {
        @Override
        public IMessage onMessage(PacketButton message, MessageContext ctx) {
            World world = DimensionManager.getWorld(message.worldID);
            TileEntity temp = world.getTileEntity(message.pos);
            if (temp instanceof TileEntitySolarCycler) {
                TileEntitySolarCycler tile = ((TileEntitySolarCycler) temp);
                tile.updateButton(message.pressed);
            }
            return null;
        }
    }
}
