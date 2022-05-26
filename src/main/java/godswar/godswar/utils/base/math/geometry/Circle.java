package godswar.godswar.utils.base.math.geometry;

import godswar.godswar.utils.base.math.FastMath;
import godswar.godswar.utils.base.math.geometry.location.LocationIterator;
import godswar.godswar.utils.base.math.geometry.vector.VectorIterator;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.NoSuchElementException;

import static com.google.common.base.Preconditions.checkArgument;

public class Circle extends Shape {

	private static final double TWICE_PI = 2 * Math.PI;

	public static LocationIterator infiniteIteratorOf(Location center, double radius, int amount) {
		checkArgument(amount >= 1, "The amount must be 1 or greater.");
		checkArgument(radius > 0, "The radius must be positive");
		checkArgument(!Double.isNaN(radius) && Double.isFinite(radius));
		return new LocationIterator() {
			private int cursor = 0;

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public Location next() {
				if (cursor >= amount) cursor = 0;
				cursor++;
				double radians = (TWICE_PI / amount) * cursor;
				return center.clone().add(FastMath.cos(radians) * radius, 0, FastMath.sin(radians) * radius);
			}
		};
	}

	public static VectorIterator infiniteIteratorOf(double radius, int amount) {
		checkArgument(amount >= 1, "The amount must be 1 or greater.");
		checkArgument(radius > 0, "The radius must be positive");
		checkArgument(!Double.isNaN(radius) && Double.isFinite(radius));
		return new VectorIterator() {
			private int cursor = 0;

			@Override
			public boolean hasNext() {
				return true;
			}

			@Override
			public Vector next() {
				if (cursor >= amount) cursor = 0;
				cursor++;
				double radians = (TWICE_PI / amount) * cursor;
				return new Vector(FastMath.cos(radians) * radius, 0, FastMath.sin(radians) * radius);
			}
		};
	}

	public static LocationIterator iteratorOf(Location center, double radius, int amount) {
		checkArgument(amount >= 1, "The amount must be 1 or greater.");
		checkArgument(radius > 0, "The radius must be positive");
		checkArgument(!Double.isNaN(radius) && Double.isFinite(radius));
		return new LocationIterator() {
			private int cursor = 0;

			@Override
			public boolean hasNext() {
				return cursor < amount;
			}

			@Override
			public Location next() {
				if (cursor >= amount) throw new NoSuchElementException();
				cursor++;
				double radians = (TWICE_PI / amount) * cursor;
				return center.clone().add(FastMath.cos(radians) * radius, 0, FastMath.sin(radians) * radius);
			}
		};
	}

	public static Circle of(double radius, int amount) {
		return new Circle(radius, amount);
	}

	private Circle(double radius, int amount) {
		super(amount);
		checkArgument(amount >= 1, "The amount must be 1 or greater.");
		checkArgument(radius > 0, "The radius must be positive");
		checkArgument(!Double.isNaN(radius) && Double.isFinite(radius));
		final double divided = TWICE_PI / amount;
		for (double radians = divided; size() <= amount; radians += divided) {
			add(new Vector(FastMath.cos(radians) * radius, 0, FastMath.sin(radians) * radius));
		}
	}

	private Circle(int amount) {
		super(amount);
	}

	@Override
	public Circle clone() {
		Circle circle = new Circle(size());
		for (Vector vector : this) {
			circle.add(vector.clone());
		}
		return circle;
	}

}
