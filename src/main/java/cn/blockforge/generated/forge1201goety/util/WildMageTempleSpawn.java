package cn.blockforge.generated.goetysupreme.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import net.minecraft.world.level.levelgen.structure.structures.JungleTempleStructure;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.event.level.ChunkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import cn.blockforge.generated.goetysupreme.util.EntwinedLanternRitual;
import cn.blockforge.generated.goetysupreme.GeneratedMod;
import cn.blockforge.generated.goetysupreme.util.ModEntityTypes;
import cn.blockforge.generated.goetysupreme.entity.WildMage;
import cn.blockforge.generated.goetysupreme.util.WildMageTempleSpawn;

@Mod.EventBusSubscriber(modid = GeneratedMod.MOD_ID)
public final class WildMageTempleSpawn {

    private static final String DATA_NAME = "goety_supreme_wild_mage_temples";

    private static final int GROUND_SCAN = 8;

    private static final int MAX_ENTRANCE_ATTEMPTS = 100;

    private WildMageTempleSpawn() {
    }

    @SubscribeEvent
    public static void onChunkLoad(ChunkEvent.Load event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        if (level.dimension() != Level.OVERWORLD || !(event.getChunk() instanceof LevelChunk chunk)) {
            return;
        }
        Map<Structure, StructureStart> starts = chunk.getAllStarts();
        if (starts.isEmpty()) {
            return;
        }
        for (StructureStart start : starts.values()) {
            if (!start.isValid() || !isJungleTemple(level, start.getStructure())) {
                continue;
            }
            long key = start.getChunkPos().toLong();
            level.getServer().execute(() -> trySpawnMage(level, start, key, 0));
        }
    }

    private static void trySpawnMage(ServerLevel level, StructureStart start, long key, int attempt) {
        if (!isAreaLoaded(level, start.getBoundingBox())) {
            if (attempt < MAX_ENTRANCE_ATTEMPTS) {
                level.getServer().execute(() -> trySpawnMage(level, start, key, attempt + 1));
            }
            return;
        }
        TempleData data = level.getDataStorage().computeIfAbsent(
                TempleData::load, TempleData::new, DATA_NAME);
        if (!data.spawned.add(key)) {
            return;
        }
        data.setDirty();
        BlockPos entrance = findEntrance(level, start);
        if (entrance == null) {
            return;
        }
        spawnMage(level, entrance);
    }

    private static boolean isAreaLoaded(ServerLevel level, BoundingBox box) {
        int minChunkX = (box.minX() - 2) >> 4;
        int maxChunkX = (box.maxX() + 2) >> 4;
        int minChunkZ = (box.minZ() - 2) >> 4;
        int maxChunkZ = (box.maxZ() + 2) >> 4;
        ServerChunkCache chunks = level.getChunkSource();
        for (int chunkX = minChunkX; chunkX <= maxChunkX; ++chunkX) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; ++chunkZ) {
                if (chunks.getChunkNow(chunkX, chunkZ) == null) {
                    return false;
                }
            }
        }
        return true;
    }

    private static BlockPos findEntrance(ServerLevel level, StructureStart start) {
        if (start.getStructure() instanceof JungleTempleStructure) {
            return findVanillaEntrance(level, start.getBoundingBox());
        }
        return findModdedEntrance(level, start);
    }

    private static BlockPos findVanillaEntrance(ServerLevel level, BoundingBox box) {
        BlockPos center = box.getCenter();
        int baseY = box.minY();
        List<Direction> sides = new ArrayList<>(4);
        int bestScore = -1;
        Direction bestDir = Direction.NORTH;
        for (Direction dir : Direction.Plane.HORIZONTAL) {
            int score = stairScore(level, box, center, baseY, dir);
            sides.add(dir);
            if (score > bestScore) {
                bestScore = score;
                bestDir = dir;
            }
        }
        sides.remove(bestDir);
        sides.add(0, bestDir);
        for (Direction dir : sides) {
            BlockPos pos = groundOutside(level, box, center, dir);
            if (pos != null) {
                return pos;
            }
        }
        return standableGround(level, center.getX(), center.getZ());
    }

    private static BlockPos findModdedEntrance(ServerLevel level, StructureStart start) {
        BoundingBox box = start.getBoundingBox();
        BoundingBox pieceBox = box;
        List<?> pieces = start.getPieces();
        if (!pieces.isEmpty() && pieces.get(0) instanceof StructurePiece piece) {
            pieceBox = piece.getBoundingBox();
        }
        BlockPos pos = standableInColumn(level, pieceBox.getCenter().getX(),
                pieceBox.getCenter().getZ(), clampTop(level, pieceBox.maxY()),
                clampBottom(level, pieceBox.minY()));
        if (pos != null) {
            return pos;
        }
        pos = standableInColumn(level, box.getCenter().getX(), box.getCenter().getZ(),
                clampTop(level, box.maxY()), clampBottom(level, box.minY()));
        if (pos != null) {
            return pos;
        }
        return standableGround(level, box.getCenter().getX(), box.getCenter().getZ());
    }

    private static int clampTop(ServerLevel level, int y) {
        return Math.min(level.getMaxBuildHeight() - 1, y);
    }

    private static int clampBottom(ServerLevel level, int y) {
        return Math.max(level.getMinBuildHeight(), y);
    }

    private static boolean isJungleTemple(ServerLevel level, Structure structure) {
        if (structure instanceof JungleTempleStructure) {
            return true;
        }
        ResourceLocation id = level.registryAccess().registryOrThrow(Registries.STRUCTURE)
                .getKey(structure);
        if (id == null) {
            return false;
        }
        String path = id.getPath();
        return path.equals("jungle_temple") || path.endsWith("_jungle_temple")
                || path.equals("jungle_pyramid");
    }

    private static int stairScore(ServerLevel level, BoundingBox box, BlockPos center, int baseY,
            Direction dir) {
        int halfSpan = dir.getStepX() != 0 ? box.getXSpan() / 2 : box.getZSpan() / 2;
        int wx = center.getX() + dir.getStepX() * Math.max(0, halfSpan - 1);
        int wz = center.getZ() + dir.getStepZ() * Math.max(0, halfSpan - 1);
        int score = 0;
        for (int offset = -2; offset <= 2; ++offset) {
            int x = wx + (dir.getStepX() == 0 ? offset : 0);
            int z = wz + (dir.getStepZ() == 0 ? offset : 0);
            for (int y = baseY - 2; y <= baseY + 10; ++y) {
                if (level.getBlockState(new BlockPos(x, y, z)).is(Blocks.COBBLESTONE_STAIRS)) {
                    ++score;
                }
            }
        }
        return score;
    }

    private static BlockPos groundOutside(ServerLevel level, BoundingBox box, BlockPos center,
            Direction dir) {
        int halfSpan = dir.getStepX() != 0 ? box.getXSpan() / 2 : box.getZSpan() / 2;
        int x = center.getX() + dir.getStepX() * (halfSpan + 1);
        int z = center.getZ() + dir.getStepZ() * (halfSpan + 1);
        return standableGround(level, x, z);
    }

    private static BlockPos standableGround(ServerLevel level, int x, int z) {
        int top = level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, x, z);
        return standableInColumn(level, x, z, top, top - GROUND_SCAN + 1);
    }

    private static BlockPos standableInColumn(ServerLevel level, int x, int z, int top, int bottom) {
        for (int y = top; y >= bottom; --y) {
            BlockPos feet = new BlockPos(x, y, z);
            BlockPos below = feet.below();
            BlockState floor = level.getBlockState(below);
            BlockState at = level.getBlockState(feet);
            BlockState head = level.getBlockState(feet.above());
            if (!floor.getCollisionShape(level, below).isEmpty()
                    && at.getCollisionShape(level, feet).isEmpty()
                    && head.getCollisionShape(level, feet.above()).isEmpty()) {
                return feet;
            }
        }
        return null;
    }

    public static boolean hasTempleNear(ServerLevel level, BlockPos pos, int range) {
        int chunkRange = Math.max(1, (range >> 4) + 1);
        int centerChunkX = pos.getX() >> 4;
        int centerChunkZ = pos.getZ() >> 4;
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();
        for (int dx = -chunkRange; dx <= chunkRange; ++dx) {
            for (int dz = -chunkRange; dz <= chunkRange; ++dz) {
                LevelChunk chunk = level.getChunk(centerChunkX + dx, centerChunkZ + dz);
                for (StructureStart start : chunk.getAllStarts().values()) {
                    if (!start.isValid() || !isJungleTemple(level, start.getStructure())) {
                        continue;
                    }
                    BoundingBox box = start.getBoundingBox();
                    cursor.set(pos.getX(),
                            Mth.clamp(pos.getY(), box.minY(), box.maxY()),
                            pos.getZ());
                    if (box.isInside(cursor)) {
                        return true;
                    }
                    int nearestX = Mth.clamp(pos.getX(), box.minX(), box.maxX());
                    int nearestZ = Mth.clamp(pos.getZ(), box.minZ(), box.maxZ());
                    double distSqr = (double) (pos.getX() - nearestX) * (pos.getX() - nearestX)
                            + (double) (pos.getZ() - nearestZ) * (pos.getZ() - nearestZ);
                    if (distSqr <= (double) range * range) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static void spawnMage(ServerLevel level, BlockPos pos) {
        WildMage mage = new WildMage(ModEntityTypes.WILD_MAGE.get(), level);
        mage.moveTo(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D,
                level.random.nextFloat() * 360.0F, 0.0F);
        mage.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE,
                null, null);
        level.addFreshEntity(mage);
    }

    public static class TempleData extends SavedData {
        private static final String KEY = "Spawned";

        private final Set<Long> spawned = new HashSet<>();

        public static TempleData load(CompoundTag tag) {
            TempleData data = new TempleData();
            for (long value : tag.getLongArray(KEY)) {
                data.spawned.add(value);
            }
            return data;
        }

        @Override
        public CompoundTag save(CompoundTag tag) {
            tag.putLongArray(KEY, this.spawned.stream().mapToLong(Long::longValue).toArray());
            return tag;
        }
    }
}
