package godswar.godswar.utils.base.collect;

import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.function.Predicate;

public abstract class SetView<E> extends AbstractSet<E> {
	SetView() {}

	@Deprecated
	public final boolean add(E e) { throw new UnsupportedOperationException(); }

	@Deprecated
	public final boolean remove(Object object) { throw new UnsupportedOperationException(); }

	@Deprecated
	public final boolean addAll(Collection<? extends E> elements) { throw new UnsupportedOperationException(); }

	@Deprecated
	public final boolean removeAll(Collection<?> elements) { throw new UnsupportedOperationException(); }

	@Deprecated
	public final boolean removeIf(Predicate<? super E> filter) { throw new UnsupportedOperationException(); }

	@Deprecated
	public final boolean retainAll( Collection<?> elements) { throw new UnsupportedOperationException(); }

	@Deprecated
	public final void clear() { throw new UnsupportedOperationException(); }

	public abstract UnmodifiableIterator<E> iterator();
}