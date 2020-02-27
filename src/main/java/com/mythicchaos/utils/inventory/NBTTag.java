package com.mythicchaos.utils.inventory;

public class NBTTag {

    private TagType t;
    private Object value;

    public NBTTag(TagType t, Object value) {
        this.t = t;
        this.value = value;
    }


    public Object getValue() {
        if(t == TagType.DOUBLE) return Double.parseDouble(value.toString());
        if(t == TagType.INT) return Integer.parseInt(value.toString());
        if(t == TagType.FLOAT) return Float.parseFloat(value.toString());
        if(t == TagType.SHORT) return Short.parseShort(value.toString());
        if(t == TagType.BOOLEAN) return Boolean.parseBoolean(value.toString());
        if(t == TagType.LONG) return Long.parseLong(value.toString());
        if(t == TagType.STRING) return String.valueOf(value.toString());
        if(t == TagType.BYTE_ARRAY) return (Byte[])(value);
        if(t == TagType.INT_ARRAY) return (Integer[])(value);
        if(t == TagType.STRING_ARRAY) return (String[])(value);
        return value;
    }

    public TagType getType() {
        return t;
    }

    @Override
    public String toString() {
        return getValue()+"";
    }
}
