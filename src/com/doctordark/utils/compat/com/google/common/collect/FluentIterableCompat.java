package com.doctordark.utils.compat.com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

import javax.annotation.CheckReturnValue;
import java.util.Iterator;

@GwtCompatible(emulated = true)
public abstract class FluentIterableCompat<E> implements Iterable<E> {
    private final Iterable<E> iterable;

    FluentIterableCompat(final Iterable<E> iterable) {
        this.iterable = (Iterable<E>) Preconditions.checkNotNull((Object) iterable);
    }

    @CheckReturnValue
    public static <E> FluentIterableCompat<E> from(final Iterable<E> iterable) {
        return (iterable instanceof FluentIterableCompat) ? ((FluentIterableCompat) iterable) : new FluentIterableCompat<E>(iterable) {
            @Override
            public Iterator<E> iterator() {
                return iterable.iterator();
            }
        };
    }

    @CheckReturnValue
    @Override
    public String toString() {
        return Iterables.toString(this.iterable);
    }

    @CheckReturnValue
    public final FluentIterableCompat<E> filter(final Predicate<? super E> predicate) {
        return from(Iterables.filter(this.iterable, predicate));
    }

    @CheckReturnValue
    public final <T> FluentIterableCompat<T> transform(final Function<? super E, T> function) {
        return from(Iterables.transform(this.iterable, function));
    }

    @CheckReturnValue
    public final ImmutableList<E> toList() {
        return (ImmutableList<E>) ImmutableList.copyOf((Iterable) this.iterable);
    }
}
