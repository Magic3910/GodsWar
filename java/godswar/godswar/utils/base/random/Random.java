package godswar.godswar.utils.base.random;

import java.util.List;

public class Random extends java.util.Random {

	public <E> E pick(final E[] array) {
		return array[nextInt(array.length)];
	}

	public <E> E pick(final List<E> list) {
		return list.get(nextInt(list.size()));
	}

	public <E extends Enum<E>> E pick(final Class<E> enumClass) {
		return pick(enumClass.getEnumConstants());
	}

}
