package flaxbeard.immersivepetroleum.common.blocks.metal;


import java.util.Locale;


public enum BlockTypes_MetalDecoration0 {
  COIL_LV, COIL_MV, COIL_HV, RS_ENGINEERING, LIGHT_ENGINEERING, HEAVY_ENGINEERING, GENERATOR, RADIATOR;
  
  public String getName() {
    return toString().toLowerCase(Locale.ENGLISH);
  }
  
  public int getMeta() {
    return ordinal();
  }
  
  public boolean listForCreative() {
    return true;
  }
}
