package com.moandroid.cocos2d.types;

public class ccQuad2{
    public float tl_x;
    public float tl_y;
    
    public float tr_x;
    public float tr_y;
    
    public float bl_x;
    public float bl_y;
    
    public float br_x;
    public float br_y;
    
    public ccQuad2(  float tl_x, float tl_y,
                     float tr_x, float tr_y,
                     float bl_x, float bl_y,
                     float br_x, float br_y){
        this.tl_x = tl_x;
        this.tl_y = tl_y;
        this.tr_x = tr_x;
        this.tr_y = tr_y;
        this.bl_x = bl_x;
        this.bl_y = bl_y;
        this.br_x = br_x;
        this.br_y = br_y;
    } 
}
