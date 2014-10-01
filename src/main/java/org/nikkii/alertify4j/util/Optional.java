package org.nikkii.alertify4j.util;

import java.util.NoSuchElementException;

/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * A container object which may or may not contain a non-null value. If a value is present, {@code isPresent()} will
 * return {@code true} and {@code get()} will return the value.
 */
public final class Optional<T> {

	/**
	 * Common instance for {@code empty()}.
	 */
	private static final Optional<?> EMPTY = new Optional<Object>();

	/**
	 * Returns an empty optional instance. No value is present for this Optional.
	 * @param <T> Type of the non-existent value
	 * @return An empty optional.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Optional<T> empty() {
		return (Optional<T>) EMPTY;
	}

	/**
	 * Returns an optional with the specified present non-null value.
	 * @param value The value to be present, which must be non-null.
	 * @return An optional with the value present.
	 * @throws NullPointerException If value is null.
	 */
	public static <T> Optional<T> of(T value) {
		return new Optional<T>(value);
	}

	/**
	 * Returns an optional describing the specified value, if non-null, otherwise returns an empty optional.
	 * @param value The possibly-null value to describe.
	 * @return An optional with a present value if the specified value is non-null, otherwise an empty optional.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Optional<T> ofNullable(T value) {
		return (Optional<T>) (value == null ? empty() : of(value));
	}

	/**
	 * If non-null, the value; if null, indicates no value is present
	 */
	private final T value;

	/**
	 * Creates a new empty optional.
	 */
	private Optional() {
		this.value = null;
	}

	/**
	 * Creates the optional with the specified non-null value.
	 * @param value The non-null value.
	 * @throws NullPointerException If the value is null.
	 */
	private Optional(T value) {
		if (value == null) {
			throw new IllegalArgumentException("Value cannot be null.");
		}
		this.value = value;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Optional)) {
			return false;
		}

		Optional<?> other = (Optional<?>) obj;
		if (value == null && other.value == null) {
			return true;
		}
		return value != null && value.equals(other.value);
	}

	/**
	 * Returns the value of this optional.
	 * @return The non-null value held by this optional.
	 * @throws NoSuchElementException If there is no value present.
	 */
	public T get() {
		if (value == null) {
			throw new NoSuchElementException("No value present.");
		}
		return value;
	}

	@Override
	public int hashCode() {
		return value == null ? 0 : value.hashCode();
	}

	/**
	 * Return {@code true} if there is a value present, otherwise {@code false}.
	 * @return {@code true} if there is a value present, otherwise {@code false}
	 */
	public boolean isPresent() {
		return value != null;
	}

	/**
	 * Return the value if present, otherwise return {@code other}.
	 * @param other the value to be returned if there is no value present, may be null
	 * @return the value, if present, otherwise {@code other}
	 */
	public T orElse(T other) {
		return value != null ? value : other;
	}

	@Override
	public String toString() {
		return value != null ? "Optional: value=" + value + "." : "Optional.empty";
	}

}