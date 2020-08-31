package com.im.chat.enums;

public enum CvsTypeEnum {
    /**
     * U 单聊
     * G 群聊
     */
    U(Byte.valueOf("0"),"U"),
    G(Byte.valueOf("1"),"G");

    CvsTypeEnum(Byte code)
    {
        this.code = code;
    }

    CvsTypeEnum(Byte code,String name)
    {
        this.code = code;
        this.name = name;
    }

    private final Byte code;
    private String name;

    public Byte getCode()
    {
        return this.code;
    }
    public String getName(){
        return this.name;
    }
    public static CvsTypeEnum codeOf(Byte code) {
        switch (code)
        {
            case 0: return U;
            case 1: return G;
            default: return null;
        }
    }
    public static CvsTypeEnum nameOf(String name){
        if(name == null){
            return null;
        }
        if(name.equals("U")){
            return U;
        }else if(name.equals("G")){
            return G;
        }else{
            return null;
        }
    }


}
