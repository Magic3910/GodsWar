package godswar.godswar.utils.base.math;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Predicate;

public class LocationUtil {

	private LocationUtil() {
	}

	private static final Random random = new Random();
	private static final double TWO_PI = 6.283185307179586D;

	public static boolean isInFront(final Entity criterion, final Entity entity) {
		return dotDirection(criterion, entity) <= 0;
	}

	public static boolean isBehind(final Entity criterion, final Entity entity) {
		return dotDirection(criterion, entity) > 0;
	}

	private static double dotDirection(final Entity criterion, final Entity entity) {
		return criterion.getLocation().toVector().subtract(entity.getLocation().toVector()).dot(criterion.getLocation().getDirection());
	}

	public static float getYaw(Vector vector) {
		return (float) Math.toDegrees((Math.atan2(-vector.getX(), vector.getZ()) + TWO_PI) % TWO_PI);
	}

	public static float getPitch(Vector vector) {
		double x = vector.getX();
		double z = vector.getZ();
		if (x == 0.0D && z == 0.0D) {
			return vector.getY() > 0.0D ? -90 : 90;
		} else {
			return (float) Math.toDegrees(Math.atan(-vector.getY() / Math.sqrt((x * x) + (z * z))));
		}
	}

	private static final BlockFace[] axis = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

	public static BlockFace getFacing(final float yaw) {
		return axis[Math.round(yaw / 90f) & 0x3].getOppositeFace();
	}

	/**
	 * 평면상에서 두 좌표의 거리의 제곱을 구합니다.
	 *
	 * @param ax 첫번째 X 좌표
	 * @param az 첫번째 Z 좌표
	 * @param bx 두번째 X 좌표
	 * @param bz 두번째 Z 좌표
	 * @return 두 좌표의 거리의 제곱
	 */
	public static double distanceSquared2D(double ax, double az, double bx, double bz) {
		return ((bx - ax) * (bx - ax)) + ((bz - az) * (bz - az));
	}

	/**
	 * {@link Location}이 범위 안에 있는지 확인합니다.
	 *
	 * @param center   중심
	 * @param location 확인할 위치
	 * @param radius   원의 반지름
	 */
	public static boolean isInCircle(Location center, Location location, double radius) {
		return Objects.equals(center.getWorld(), location.getWorld()) && distanceSquared2D(center.getX(), center.getZ(), location.getX(), location.getZ()) <= (radius * radius);
	}

	public static <T extends Entity> List<T> getEntitiesInCircle(Class<T> entityType, Location center, double radius) {
		double centerX = center.getX(), centerZ = center.getZ(), SQUARED_RADIUS = radius * radius;
		List<T> entities = new ArrayList<>();
		for (Entity e : collectEntities(center, (int) Math.floor(radius))) {
			if (entityType.isAssignableFrom(e.getClass())) {
				@SuppressWarnings("unchecked") T entity = (T) e;
				Location entityLocation = entity.getLocation();
				if (distanceSquared2D(centerX, centerZ, entityLocation.getX(), entityLocation.getZ()) <= SQUARED_RADIUS) {
					entities.add(entity);
				}
			}
		}
		return entities;
	}

	public static int getFloorYAt(final World world, final double criterionY, final int x, final int z, final Predicate<Block> predicate) {
		int floorY = getHighestBlockYAt(world, x, z);
		if (floorY > criterionY) {
			for (int checkY = floorY; checkY >= criterionY && checkY > 0; checkY--) {
				final Block block = world.getBlockAt(x, checkY, z);
				if (block.isEmpty() || predicate.test(block)) {
					floorY = checkY;
				}
			}
			Block block;
			while (((block = world.getBlockAt(x, floorY - 1, z)).isEmpty() || predicate.test(block)) && floorY > 0) {
				floorY--;
			}
		}
		return floorY;
	}

	public static int getFloorYAt(final World world, double criterionY, final int x, final int z) {
		if (criterionY > world.getMaxHeight()) criterionY = world.getMaxHeight();
		int floorY = getHighestBlockYAt(world, x, z);
		if (floorY > criterionY) {
			for (int checkY = floorY; checkY >= criterionY && checkY > 0; checkY--) {
				if (world.getBlockAt(x, checkY, z).isEmpty()) floorY = checkY;
			}
			while (world.getBlockAt(x, floorY - 1, z).isEmpty() && floorY > 0) floorY--;
		}
		return floorY;
	}

	public static Location floorY(final Location location, final double criterionY, final Predicate<Block> predicate) {
		final Location clone = location.clone();
		clone.setY(getFloorYAt(clone.getWorld(), criterionY, clone.getBlockX(), clone.getBlockZ(), predicate));
		return clone;
	}

	public static Location floorY(final Location location, final double criterionY) {
		final Location clone = location.clone();
		clone.setY(getFloorYAt(clone.getWorld(), criterionY, clone.getBlockX(), clone.getBlockZ()));
		return clone;
	}

	public static Location floorY(final Location location, final Predicate<Block> predicate) {
		return floorY(location, location.getY(), predicate);
	}

	public static Location floorY(final Location location) {
		return floorY(location, location.getY());
	}

	public static int getHighestBlockYAt(final World world, int x, int z) {
		return world.getHighestBlockYAt(x, z);
	}

	public static int getHighestBlockYAt(final World world, final Location location) {
		return world.getHighestBlockYAt(location);
	}

	public static Location getBlockCenter(final Block block) {
		final Location location = block.getLocation();
		return location.add(location.getX() >= 0 ? .5 : -.5, -.5, location.getZ() >= 0 ? .5 : -.5);
	}

	/**
	 * 3차원 공간에서 범위 안에 있는 블록들을 {@link ArrayList}로  반환합니다.
	 *
	 * @param center     중심
	 * @param radius     범위
	 * @param hollow     참일 경우 바깥 부분의 블록들만 가져옵니다.
	 * @param includeAir 참일 경우 블록이 비어있어도 가져옵니다.
	 */
	public static List<Block> getBlocks3D(Location center, int radius, boolean hollow, boolean includeAir) {
		List<Block> blocks = new ArrayList<>();
		final int blockX = center.getBlockX(), blockY = center.getBlockY(), blockZ = center.getBlockZ();
		for (int x = blockX - radius; x <= blockX + radius; x++) {
			for (int y = blockY - radius; y <= blockY + radius; y++) {
				for (int z = blockZ - radius; z <= blockZ + radius; z++) {
					final Block block = center.getWorld().getBlockAt(x, y, z);
					final double distanceSquared = center.distanceSquared(block.getLocation());
					if (distanceSquared <= (radius * radius) && !(hollow && distanceSquared < ((radius - 1) * (radius - 1)))) {
						if (block.isEmpty()) {
							if (includeAir) {
								blocks.add(block);
							}
						} else {
							blocks.add(block);
						}
					}
				}
			}
		}
		return blocks;
	}

	/**
	 * 평면상에서 범위 안에 있는 블록들을 {@link ArrayList}로  반환합니다.
	 *
	 * @param center        중심
	 * @param radius        범위
	 * @param hollow        참일 경우 바깥 부분의 블록들만 가져옵니다.
	 * @param floorBlocks   참일 경우 각 위치에서 중심의 y 좌표를 참조값으로 {@link #getFloorYAt}을 호출한 위치의 블록을 받아옵니다. 이 경우 모든 블록이 같은 평면 위에 있지 않을 수 있습니다.
	 */
	public static List<Block> getBlocks2D(Location center, int radius, boolean hollow, boolean floorBlocks, boolean includeAir) {
		List<Block> blocks = new ArrayList<>();
		final int blockX = center.getBlockX(), blockZ = center.getBlockZ();
		for (int x = blockX - radius; x <= blockX + radius; x++) {
			for (int z = blockZ - radius; z <= blockZ + radius; z++) {
				final Block block = center.getWorld().getBlockAt(x, floorBlocks ? getFloorYAt(center.getWorld(), center.getY(), x, z) : center.getBlockY(), z);
				final Location blockLocation = block.getLocation();
				final double distance = FastMath.square(center.getX() - blockLocation.getX()) + FastMath.square(center.getZ() - blockLocation.getZ());
				if (distance <= (radius * radius) && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
					if (block.isEmpty()) {
						if (includeAir) {
							blocks.add(block);
						}
					} else {
						blocks.add(block);
					}
				}
			}
		}
		return blocks;
	}

	public static Location getRandomLocation(Location center, double radius) {
		return floorY(new Location(
				center.getWorld(),
				center.getX() + (random.nextDouble() * radius * FastMath.cos(random.nextDouble() * TWO_PI)),
				center.getY(),
				center.getZ() + (random.nextDouble() * radius * FastMath.sin(random.nextDouble() * TWO_PI))
		));
	}

	public static Locations getRandomLocations(Random random, Location center, double radius, int amount) {
		final Locations locations = new Locations(amount);
		for (int i = 0; i < amount; i++) {
			double radians = Math.toRadians(random.nextDouble() * 360);
			double x = center.getX() + (random.nextDouble() * radius * FastMath.cos(radians));
			double z = center.getZ() + (random.nextDouble() * radius * FastMath.sin(radians));
			locations.add(new Location(center.getWorld(), x, getHighestBlockYAt(center.getWorld(), (int) x, (int) z), z));
		}
		return locations;
	}

	public static Locations getRandomLocations(Location center, double radius, int amount) {
		return getRandomLocations(new Random(), center, radius, amount);
	}

	/**
	 * 중점에서 가장 가까이에 있는 특정 타입의 엔티티를 반환합니다.
	 * 가장 가까이에 있는 해당 타입의 엔티티를 찾을 수 없을 경우 null을 반환합니다.
	 *
	 * @param entityType 탐색할 엔티티 타입
	 * @param center     중점
	 * @param predicate  커스텀 조건
	 * @return 중점에서 가장 가까이에 있는 특정 타입의 엔티티
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Entity> T getNearestEntity(Class<T> entityType, Location center, Predicate<? super T> predicate) {
		double distance = Double.MAX_VALUE;
		T current = null;

		final Location centerLocation = center.clone();
		if (center.getWorld() == null) return null;
		for (Entity e : center.getWorld().getEntities()) {
			if (entityType.isAssignableFrom(e.getClass())) {
				@SuppressWarnings("unchecked") T entity = (T) e;
				double compare = centerLocation.distanceSquared(entity.getLocation());
				if (compare < distance && (predicate == null || predicate.test(entity))) {
					distance = compare;
					current = entity;
				}
			}
		}

		return current;
	}

	/**
	 * 중점에서 가장 멀리에 있는 특정 타입의 엔티티를 반환합니다.
	 * 멀리에 있는 해당 타입의 엔티티를 찾을 수 없을 경우 null 반환합니다.
	 *
	 * @param entityType 탐색할 엔티티 타입
	 * @param center     중점
	 * @param predicate  커스텀 조건
	 * @return 중점에서 가장 가까이에 있는 특정 타입의 엔티티
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Entity> T getFarthestEntity(Class<T> entityType, Location center, Predicate<? super T> predicate) {
		double distance = Double.MIN_VALUE;
		T current = null;

		final Location centerLocation = center.clone();
		if (center.getWorld() == null) return null;
		for (Entity e : center.getWorld().getEntities()) {
			if (entityType.isAssignableFrom(e.getClass())) {
				@SuppressWarnings("unchecked") T entity = (T) e;
				double compare = centerLocation.distanceSquared(entity.getLocation());
				if (compare > distance && (predicate == null || predicate.test(entity))) {
					distance = compare;
					current = entity;
				}
			}
		}

		return current;
	}

	/**
	 * 일정 범위 내에 있는 청크들의 엔티티 목록을 반환합니다.
	 *
	 * @param center     중점
	 * @param horizontal 수평 거리
	 * @return 엔티티 목록
	 */
	public static Entity[] collectEntities(Location center, int horizontal) {
		Entity[] entities = new Entity[0];
		final World world = center.getWorld();
		final int maxX = (center.getBlockX() + horizontal) >> 4, maxZ = (center.getBlockZ() + horizontal) >> 4;
		for (int x = (center.getBlockX() - horizontal) >> 4; x <= maxX; x++) {
			for (int z = (center.getBlockZ() - horizontal) >> 4; z <= maxZ; z++) {
				final Entity[] chunkEntities = world.getChunkAt(x, z).getEntities();
				final Entity[] newEntities = new Entity[entities.length + chunkEntities.length];
				System.arraycopy(entities, 0, newEntities, 0, entities.length);
				System.arraycopy(chunkEntities, 0, newEntities, entities.length, chunkEntities.length);
				entities = newEntities;
			}
		}
		return entities;
	}

	/**
	 * 일정 범위 내에 있는 청크들의 엔티티 목록을 반환합니다.
	 *
	 * @param center 중심 청크
	 * @param minX   엔티티 목록을 확인할 청크들의 X 좌표 중 가장 작은 값 (중심 청크 기준)
	 * @param minZ   엔티티 목록을 확인할 청크들의 Z 좌표 중 가장 작은 값 (중심 청크 기준)
	 * @param maxX   엔티티 목록을 확인할 청크들의 X 좌표 중 가장 큰 값 (중심 청크 기준)
	 * @param maxZ   엔티티 목록을 확인할 청크들의 Z 좌표 중 가장 큰 값 (중심 청크 기준)
	 * @return 엔티티 목록
	 */
	public static Entity[] collectEntities(Chunk center, int minX, int minZ, int maxX, int maxZ) {
		Entity[] entities = new Entity[0];
		final World world = center.getWorld();
		for (int x = minX; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				final Entity[] chunkEntities = world.getChunkAt(x, z).getEntities();
				final Entity[] newEntities = new Entity[entities.length + chunkEntities.length];
				System.arraycopy(entities, 0, newEntities, 0, entities.length);
				System.arraycopy(chunkEntities, 0, newEntities, entities.length, chunkEntities.length);
				entities = newEntities;
			}
		}
		return entities;
	}

	/**
	 * 주변에 있는 특정 타입의 엔티티 목록을 반환합니다.
	 *
	 * @param entityType 탐색할 엔티티 타입
	 * @param center     중점
	 * @param horizontal 수평 거리
	 * @param vertical   수직 거리
	 * @param predicate  커스텀 조건
	 * @return 주변에 있는 특정 타입의 엔티티 목록
	 */
	public static <T extends Entity> List<T> getNearbyEntities(Class<T> entityType, Location center, double horizontal, double vertical, Predicate<? super T> predicate) {
		final double centerX = center.getX(), centerZ = center.getZ();
		final List<T> entities = new ArrayList<>();
		for (Entity e : collectEntities(center, (int) Math.floor(horizontal))) {
			if (entityType.isAssignableFrom(e.getClass())) {
				@SuppressWarnings("unchecked") T entity = (T) e;
				Location entityLocation = entity.getLocation();
				if (distanceSquared2D(centerX, centerZ, entityLocation.getX(), entityLocation.getZ()) <= (horizontal * horizontal) && NumberUtil.subtract(center.getY(), entityLocation.getY()) <= vertical && (predicate == null || predicate.test(entity))) {
					entities.add(entity);
				}
			}
		}
		return entities;
	}

	public static class Locations extends ArrayList<Location> {

		public Locations(int initialCapacity) {
			super(initialCapacity);
		}

		public Locations() {
			super();
		}

		public Locations floor(double criterionY) {
			for (Location location : this) {
				location.setY(getFloorYAt(location.getWorld(), criterionY, location.getBlockX(), location.getBlockZ()) + 0.1);
			}
			return this;
		}

		public Locations highest() {
			for (Location location : this) {
				location.setY(location.getWorld().getHighestBlockYAt(location) + 0.1);
			}
			return this;
		}

		public Locations add(Vector vector) {
			for (Location location : this) {
				location.add(vector);
			}
			return this;
		}

		public Locations add(double x, double y, double z) {
			for (Location location : this) {
				location.add(x, y, z);
			}
			return this;
		}

	}

}
