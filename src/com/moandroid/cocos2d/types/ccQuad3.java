package com.moandroid.cocos2d.types;

public class ccQuad3{
    public float tl_x;
    public float tl_y;
    public float tl_z;
    
    public float tr_x;
    public float tr_y;
    public float tr_z;
    
    public float bl_x;
    public float bl_y;
    public float bl_z;
    
    public float br_x;
    public float br_y;
    public float br_z;
    
    public ccQuad3(  float tl_x, float tl_y, float tl_z,
                     float tr_x, float tr_y, float tr_z, 
                     float bl_x, float bl_y, float bl_z,
                     float br_x, float br_y, float br_z){
        this.tl_x = tl_x;
        this.tl_y = tl_y;
        this.tl_z = tl_z;
        
        this.tr_x = tr_x;
        this.tr_y = tr_y;
        this.tr_z = tr_z;
        
        this.bl_x = bl_x;
        this.bl_y = bl_y;
        this.bl_z = bl_z;
        
        this.br_x = br_x;
        this.br_y = br_y;
        this.br_z = br_z;
    }
}
