///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2009, Rob Eden All Rights Reserved.
// Copyright (c) 2009, Jeff Randall All Rights Reserved.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
///////////////////////////////////////////////////////////////////////////////

package gnu.trove.list;

//////////////////////////////////////////////////
// THIS IS A GENERATED CLASS. DO NOT HAND EDIT! //
//////////////////////////////////////////////////


import gnu.trove.TShortCollection;
import gnu.trove.function.TShortFunction;
import gnu.trove.procedure.TShortProcedure;

import java.util.Random;


/**
 * Interface for Trove list implementations.
 */
public interface TShortList extends TShortCollection {
    /**
     * Returns the value that is used to represent null. The default
     * value is generally zero, but can be changed during construction
     * of the collection.
     *
     * @return the value that represents null
     */
    short getNoEntryValue();


    /**
     * Returns the number of values in the list.
     *
     * @return the number of values in the list.
     */
    int size();


    /**
     * Tests whether this list contains any values.
     *
     * @return true if the list is empty.
     */
    boolean isEmpty();


    /**
     * Adds <tt>val</tt> to the end of the list, growing as needed.
     *
     * @param val an <code>short</code> value
     * @return true if the list was modified by the add operation
     */
    boolean add(short val);


    /**
     * Adds the values in the array <tt>vals</tt> to the end of the
     * list, in order.
     *
     * @param vals an <code>short[]</code> value
     */
    void add(short[] vals);


    /**
     * Adds a subset of the values in the array <tt>vals</tt> to the
     * end of the list, in order.
     *
     * @param vals   an <code>short[]</code> value
     * @param offset the offset at which to start copying
     * @param length the number of values to copy.
     */
    void add(short[] vals, int offset, int length);


    /**
     * Inserts <tt>value</tt> into the list at <tt>offset</tt>.  All
     * values including and to the right of <tt>offset</tt> are shifted
     * to the right.
     *
     * @param offset an <code>int</code> value
     * @param value  an <code>short</code> value
     */
    void insert(int offset, short value);


    /**
     * Inserts the array of <tt>values</tt> into the list at
     * <tt>offset</tt>.  All values including and to the right of
     * <tt>offset</tt> are shifted to the right.
     *
     * @param offset an <code>int</code> value
     * @param values an <code>short[]</code> value
     */
    void insert(int offset, short[] values);


    /**
     * Inserts a slice of the array of <tt>values</tt> into the list
     * at <tt>offset</tt>.  All values including and to the right of
     * <tt>offset</tt> are shifted to the right.
     *
     * @param offset    an <code>int</code> value
     * @param values    an <code>short[]</code> value
     * @param valOffset the offset in the values array at which to
     *                  start copying.
     * @param len       the number of values to copy from the values array
     */
    void insert(int offset, short[] values, int valOffset, int len);


    /**
     * Returns the value at the specified offset.
     *
     * @param offset an <code>int</code> value
     * @return an <code>short</code> value
     */
    short get(int offset);


    /**
     * Sets the value at the specified offset.
     *
     * @param offset an <code>int</code> value
     * @param val    an <code>short</code> value
     * @return The value previously at the given index.
     */
    short set(int offset, short val);


    /**
     * Replace the values in the list starting at <tt>offset</tt> with
     * the contents of the <tt>values</tt> array.
     *
     * @param offset the first offset to replace
     * @param values the source of the new values
     */
    void set(int offset, short[] values);


    /**
     * Replace the values in the list starting at <tt>offset</tt> with
     * <tt>length</tt> values from the <tt>values</tt> array, starting
     * at valOffset.
     *
     * @param offset    the first offset to replace
     * @param values    the source of the new values
     * @param valOffset the first value to copy from the values array
     * @param length    the number of values to copy
     */
    void set(int offset, short[] values, int valOffset, int length);


    /**
     * Sets the value at the specified offset and returns the
     * previously stored value.
     *
     * @param offset an <code>int</code> value
     * @param val    an <code>short</code> value
     * @return the value previously stored at offset.
     */
    short replace(int offset, short val);


    /**
     * Flushes the internal state of the list, resetting the capacity
     * to the default.
     */
    void clear();


    /**
     * Removes <tt>value</tt> from the list.
     *
     * @param value an <code>short</code> value
     * @return true if the list was modified by the remove operation.
     */
    boolean remove(short value);


    /**
     * Removes <tt>value</tt> at a given offset from the list.
     *
     * @param offset an <code>int</code> value that represents
     *               the offset to the element to be removed
     * @return an <tt>short</tt> that is the value removed.
     */
    short removeAt(int offset);


    /**
     * Removes <tt>length</tt> values from the list, starting at
     * <tt>offset</tt>
     *
     * @param offset an <code>int</code> value
     * @param length an <code>int</code> value
     */
    void remove(int offset, int length);


    /**
     * Transform each value in the list using the specified function.
     *
     * @param function a <code>TShortFunction</code> value
     */
    void transformValues(TShortFunction function);


    /**
     * Reverse the order of the elements in the list.
     */
    void reverse();


    /**
     * Reverse the order of the elements in the range of the list.
     *
     * @param from the inclusive index at which to start reversing
     * @param to   the exclusive index at which to stop reversing
     */
    void reverse(int from, int to);


    /**
     * Shuffle the elements of the list using the specified random
     * number generator.
     *
     * @param rand a <code>Random</code> value
     */
    void shuffle(Random rand);


    /**
     * Returns a sublist of this list.
     *
     * @param begin low endpoint (inclusive) of the subList.
     * @param end   high endpoint (exclusive) of the subList.
     * @return sublist of this list from begin, inclusive to end, exclusive.
     * @throws IndexOutOfBoundsException - endpoint out of range
     * @throws IllegalArgumentException  - endpoints out of order (end > begin)
     */
    TShortList subList(int begin, int end);


    /**
     * Copies the contents of the list into a native array.
     *
     * @return an <code>short[]</code> value
     */
    short[] toArray();


    /**
     * Copies a slice of the list into a native array.
     *
     * @param offset the offset at which to start copying
     * @param len    the number of values to copy.
     * @return an <code>short[]</code> value
     */
    short[] toArray(int offset, int len);


    /**
     * Copies a slice of the list into a native array.
     *
     * <p>If the list fits in the specified array with room to spare (i.e.,
     * the array has more elements than the list), the element in the array
     * immediately following the end of the list is set to
     * <tt>{@link #getNoEntryValue()}</tt>.
     * (This is useful in determining the length of the list <i>only</i> if
     * the caller knows that the list does not contain any "null" elements.)
     *
     * <p>NOTE: Trove does not allocate a new array if the array passed in is
     * not large enough to hold all of the data elements.  It will instead fill
     * the array passed in.
     *
     * @param dest the array to copy into.
     * @return the array passed in.
     */
    short[] toArray(short[] dest);


    /**
     * Copies a slice of the list into a native array.
     *
     * @param dest   the array to copy into.
     * @param offset the offset where the first value should be copied
     * @param len    the number of values to copy.
     * @return the array passed in.
     */
    short[] toArray(short[] dest, int offset, int len);


    /**
     * Copies a slice of the list into a native array.
     *
     * @param dest       the array to copy into.
     * @param source_pos the offset of the first value to copy
     * @param dest_pos   the offset where the first value should be copied
     * @param len        the number of values to copy.
     * @return the array passed in.
     */
    short[] toArray(short[] dest, int source_pos, int dest_pos, int len);


    /**
     * Applies the procedure to each value in the list in ascending
     * (front to back) order.
     *
     * @param procedure a <code>TShortProcedure</code> value
     * @return true if the procedure did not terminate prematurely.
     */
    boolean forEach(TShortProcedure procedure);


    /**
     * Applies the procedure to each value in the list in descending
     * (back to front) order.
     *
     * @param procedure a <code>TShortProcedure</code> value
     * @return true if the procedure did not terminate prematurely.
     */
    boolean forEachDescending(TShortProcedure procedure);


    /**
     * Sort the values in the list (ascending) using the Sun quicksort
     * implementation.
     *
     * @see java.util.Arrays#sort
     */
    void sort();


    /**
     * Sort a slice of the list (ascending) using the Sun quicksort
     * implementation.
     *
     * @param fromIndex the index at which to start sorting (inclusive)
     * @param toIndex   the index at which to stop sorting (exclusive)
     * @see java.util.Arrays#sort
     */
    void sort(int fromIndex, int toIndex);


    /**
     * Fills every slot in the list with the specified value.
     *
     * @param val the value to use when filling
     */
    void fill(short val);


    /**
     * Fills a range in the list with the specified value.
     *
     * @param fromIndex the offset at which to start filling (inclusive)
     * @param toIndex   the offset at which to stop filling (exclusive)
     * @param val       the value to use when filling
     */
    void fill(int fromIndex, int toIndex, short val);


    /**
     * Performs a binary search for <tt>value</tt> in the entire list.
     * Note that you <b>must</b> @{link #sort sort} the list before
     * doing a search.
     *
     * @param value the value to search for
     * @return the absolute offset in the list of the value, or its
     * negative insertion point into the sorted list.
     */
    int binarySearch(short value);


    /**
     * Performs a binary search for <tt>value</tt> in the specified
     * range.  Note that you <b>must</b> @{link #sort sort} the list
     * or the range before doing a search.
     *
     * @param value     the value to search for
     * @param fromIndex the lower boundary of the range (inclusive)
     * @param toIndex   the upper boundary of the range (exclusive)
     * @return the absolute offset in the list of the value, or its
     * negative insertion point into the sorted list.
     */
    int binarySearch(short value, int fromIndex, int toIndex);


    /**
     * Searches the list front to back for the index of
     * <tt>value</tt>.
     *
     * @param value an <code>short</code> value
     * @return the first offset of the value, or -1 if it is not in
     * the list.
     * @see #binarySearch for faster searches on sorted lists
     */
    int indexOf(short value);


    /**
     * Searches the list front to back for the index of
     * <tt>value</tt>, starting at <tt>offset</tt>.
     *
     * @param offset the offset at which to start the linear search
     *               (inclusive)
     * @param value  an <code>short</code> value
     * @return the first offset of the value, or -1 if it is not in
     * the list.
     * @see #binarySearch for faster searches on sorted lists
     */
    int indexOf(int offset, short value);


    /**
     * Searches the list back to front for the last index of
     * <tt>value</tt>.
     *
     * @param value an <code>short</code> value
     * @return the last offset of the value, or -1 if it is not in
     * the list.
     * @see #binarySearch for faster searches on sorted lists
     */
    int lastIndexOf(short value);


    /**
     * Searches the list back to front for the last index of
     * <tt>value</tt>, starting at <tt>offset</tt>.
     *
     * @param offset the offset at which to start the linear search
     *               (exclusive)
     * @param value  an <code>short</code> value
     * @return the last offset of the value, or -1 if it is not in
     * the list.
     * @see #binarySearch for faster searches on sorted lists
     */
    int lastIndexOf(int offset, short value);


    /**
     * Searches the list for <tt>value</tt>
     *
     * @param value an <code>short</code> value
     * @return true if value is in the list.
     */
    boolean contains(short value);


    /**
     * Searches the list for values satisfying <tt>condition</tt> in
     * the manner of the *nix <tt>grep</tt> utility.
     *
     * @param condition a condition to apply to each element in the list
     * @return a list of values which match the condition.
     */
    TShortList grep(TShortProcedure condition);


    /**
     * Searches the list for values which do <b>not</b> satisfy
     * <tt>condition</tt>.  This is akin to *nix <code>grep -v</code>.
     *
     * @param condition a condition to apply to each element in the list
     * @return a list of values which do not match the condition.
     */
    TShortList inverseGrep(TShortProcedure condition);


    /**
     * Finds the maximum value in the list.
     *
     * @return the largest value in the list.
     * @throws IllegalStateException if the list is empty
     */
    short max();


    /**
     * Finds the minimum value in the list.
     *
     * @return the smallest value in the list.
     * @throws IllegalStateException if the list is empty
     */
    short min();


    /**
     * Calculates the sum of all the values in the list.
     *
     * @return the sum of the values in the list (zero if the list is empty).
     */
    short sum();
}
