package flaxbeard.immersivepetroleum.common.util;

import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class BlockPos extends Vec3{

    public BlockPos(int x, int y, int z)
    {
        super(x, y, z);
    }

    public BlockPos(double x, double y, double z)
    {
        super(x, y, z);
    }
	public int getX()
    {
        return this.getX();
    }

    public int getY()
    {
        return this.getY();
    }


    public int getZ()
    {
        return this.getZ();
    }
    
    public static class MutableBlockPos extends BlockPos{
        protected int x;
        protected int y;
        protected int z;
        public MutableBlockPos()
        {
            this(0, 0, 0);
        }

        public MutableBlockPos(BlockPos pos)
        {
            this(pos.getX(), pos.getY(), pos.getZ());
        }

        public MutableBlockPos(int x_, int y_, int z_)
        {
            super(0, 0, 0);
            this.x = x_;
            this.y = y_;
            this.z = z_;
        }
        
    	public BlockPos.MutableBlockPos setPos(int xIn, int yIn, int zIn)
        {
            this.x = xIn;
            this.y = yIn;
            this.z = zIn;
            return this;
        }
        public BlockPos.MutableBlockPos setPos(double xIn, double yIn, double zIn)
        {
            return this.setPos(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
        }
	}
    public static final class PooledMutableBlockPos extends BlockPos.MutableBlockPos
    {
        private boolean released;
        private static final List<BlockPos.PooledMutableBlockPos> POOL = Lists.<BlockPos.PooledMutableBlockPos>newArrayList();
        
        private PooledMutableBlockPos(int xIn, int yIn, int zIn)
        {
            super(xIn, yIn, zIn);
        }

	    public static BlockPos.PooledMutableBlockPos retain()
	    {
	        return retain(0, 0, 0);
	    }
	
	    public static BlockPos.PooledMutableBlockPos retain(double xIn, double yIn, double zIn)
	    {
	        return retain(MathHelper.floor_double(xIn), MathHelper.floor_double(yIn), MathHelper.floor_double(zIn));
	    }
	
	    public static BlockPos.PooledMutableBlockPos retain(int xIn, int yIn, int zIn)
	    {
	        synchronized (POOL)
	        {
	            if (!POOL.isEmpty())
	            {
	                BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = (BlockPos.PooledMutableBlockPos)POOL.remove(POOL.size() - 1);
	
	                if (blockpos$pooledmutableblockpos != null && blockpos$pooledmutableblockpos.released)
	                {
	                    blockpos$pooledmutableblockpos.released = false;
	                    blockpos$pooledmutableblockpos.setPos(xIn, yIn, zIn);
	                    return blockpos$pooledmutableblockpos;
	                }
	            }
	        }
	
	        return new BlockPos.PooledMutableBlockPos(xIn, yIn, zIn);
	    }
	
	    public void release()
	    {
	        synchronized (POOL)
	        {
	            if (POOL.size() < 100)
	            {
	                POOL.add(this);
	            }
	
	            this.released = true;
	        }
	    }
    }
}
