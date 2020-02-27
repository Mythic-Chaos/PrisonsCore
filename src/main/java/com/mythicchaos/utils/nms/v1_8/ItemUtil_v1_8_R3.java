package com.mythicchaos.utils.nms.v1_8;

import com.mythicchaos.utils.inventory.NBTTag;
import com.mythicchaos.utils.inventory.TagType;
import com.mythicchaos.utils.nms.ItemDataUtilHelper;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("all")
public class ItemUtil_v1_8_R3 implements ItemDataUtilHelper {
    
    HashMap<String, NBTTag> data, prev;
    @Override
    public HashMap<String, NBTTag> getData(org.bukkit.inventory.ItemStack stack) {
        data = new HashMap<>();
        prev = new HashMap<>();
        ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound d = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
        if(d.c()!=null && d.c().size()>0)
            for(String key : d.c()) {
                NBTBase a = d.get(key);
                if(a instanceof NBTTagByte) data.put(key, new NBTTag(TagType.BYTE, d.getByte(key)));
                if(a instanceof NBTTagString) data.put(key, new NBTTag(TagType.STRING, d.getString(key)));
                if(a instanceof NBTTagByteArray) data.put(key, new NBTTag(TagType.BYTE_ARRAY, d.getByteArray(key)));
                if(a instanceof NBTTagShort) data.put(key, new NBTTag(TagType.SHORT, d.getShort(key)));
                if(a instanceof NBTTagDouble) data.put(key, new NBTTag(TagType.DOUBLE, d.getDouble(key)));
                if(a instanceof NBTTagIntArray) data.put(key, new NBTTag(TagType.INT_ARRAY, d.getIntArray(key)));
                if(a instanceof NBTTagInt) data.put(key, new NBTTag(TagType.INT, d.getInt(key)));
                if(a instanceof NBTTagLong) data.put(key, new NBTTag(TagType.LONG, d.getLong(key)));
                if(a instanceof NBTTagFloat) data.put(key, new NBTTag(TagType.FLOAT, d.getFloat(key)));
            }
        return data;
    }
    
    @Override
    public org.bukkit.inventory.ItemStack damage(Player player, org.bukkit.inventory.ItemStack itemStack, int amount) {
        CraftPlayer cp = (CraftPlayer) player;
        ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        item.damage(amount, cp.getHandle());
        return CraftItemStack.asBukkitCopy(item);
    }
    
    @Override
    public void setData(HashMap<String, NBTTag> newData) {
        prev= data;
        this.data = newData;
    }
    
    @Override
    public org.bukkit.inventory.ItemStack clearData(org.bukkit.inventory.ItemStack stack) {
        ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound d = new NBTTagCompound();
        nmsStack.setTag(d);
        stack = CraftItemStack.asBukkitCopy(nmsStack);
        return stack;
    }
   
    
    @Override
    public org.bukkit.inventory.ItemStack finish(org.bukkit.inventory.ItemStack item) {
        ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound d = nmsStack.getTag()==null?new NBTTagCompound():nmsStack.getTag();
        if(data.size() > 0) {
            
            for (Map.Entry<String, NBTTag> key : data.entrySet()) {
                switch (key.getValue().getType()) {
                    case INT:
                        d.set(key.getKey(), new NBTTagInt((Integer) key.getValue().getValue()));
                        break;
                    case DOUBLE:
                        d.set(key.getKey(), new NBTTagDouble((double)key.getValue().getValue()));
                        break;
                    case STRING:
                        d.set(key.getKey(), new NBTTagString((String) key.getValue().getValue()));
                        break;
                    case BOOLEAN:
                        d.set(key.getKey(), new NBTTagByte((byte)((boolean)key.getValue().getValue() ? 1 : 0)));
                        break;
                    case BYTE:
                        d.set(key.getKey(), new NBTTagByte((Byte) key.getValue().getValue()));
                        break;
                    case BYTE_ARRAY:
                        d.set(key.getKey(), new NBTTagByteArray((byte[]) key.getValue().getValue()));
                        break;
                    case FLOAT:
                        d.set(key.getKey(), new NBTTagFloat((Float) key.getValue().getValue()));
                        break;
                    case INT_ARRAY:
                        d.set(key.getKey(), new NBTTagIntArray((int[]) key.getValue().getValue()));
                        break;
                    case LONG:
                        d.set(key.getKey(), new NBTTagLong((Long) key.getValue().getValue()));
                        break;
                    case SHORT:
                        d.set(key.getKey(), new NBTTagShort((Short) key.getValue().getValue()));
                        break;
                }
            }
            nmsStack.setTag(d);
            item = CraftItemStack.asBukkitCopy(nmsStack);
        }
        return item;
    }
}
