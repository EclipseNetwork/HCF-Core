///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2009, Rob Eden All Rights Reserved.
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

package gnu.trove.map;


//////////////////////////////////////////////////
// THIS IS A GENERATED CLASS. DO NOT HAND EDIT! //
//////////////////////////////////////////////////

import gnu.trove.TLongCollection;
import gnu.trove.function.TLongFunction;
import gnu.trove.iterator.TShortLongIterator;
import gnu.trove.procedure.TLongProcedure;
import gnu.trove.procedure.TShortLongProcedure;
import gnu.trove.procedure.TShortProcedure;
import gnu.trove.set.TShortSet;

import java.util.Map;


/**
 * Interface for a primitive map of short keys and long values.
 */
public interface TShortLongMap {
    /**
     * Returns the value that will be returned from {@link #get} or {@link #put} if no
     * entry exists for a given key. The default value is generally zero, but can be
     * changed during construction of the collection.
     *
     * @return the value that represents a null key in this collection.
     */
    short getNoEntryKey();


    /**
     * Returns the value that will be returned from {@link #get} or {@link #put} if no
     * entry exists for a given key. The default value is generally zero, but can be
     * changed during construction of the collection.
     *
     * @return the value that represents a null value in this collection.
     */
    long getNoEntryValue();


    /**
     * Inserts a key/value pair into the map.
     *
     * @param key   an <code>short</code> value
     * @param value an <code>long</code> value
     * @return the previous value associated with <tt>key</tt>, or the "no entry" value
     * if none was found (see {@link #getNoEntryValue}).
     */
    long put(short key, long value);


    /**
     * Inserts a key/value pair into the map if the specified key is not already
     * associated with a value.
     *
     * @param key   an <code>short</code> value
     * @param value an <code>long</code> value
     * @return the previous value associated with <tt>key</tt>, or the "no entry" value
     * if none was found (see {@link #getNoEntryValue}).
     */
    long putIfAbsent(short key, long value);


    /**
     * Put all the entries from the given Map into this map.
     *
     * @param map The Map from which entries will be obtained to put into this map.
     */
    void putAll(Map<? extends Short, ? extends Long> map);


    /**
     * Put all the entries from the given map into this map.
     *
     * @param map The map from which entries will be obtained to put into this map.
     */
    void putAll(TShortLongMap map);


    /**
     * Retrieves the value for <tt>key</tt>
     *
     * @param key an <code>short</code> value
     * @return the previous value associated with <tt>key</tt>, or the "no entry" value
     * if none was found (see {@link #getNoEntryValue}).
     */
    long get(short key);


    /**
     * Empties the map.
     */
    void clear();


    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    boolean isEmpty();


    /**
     * Deletes a key/value pair from the map.
     *
     * @param key an <code>short</code> value
     * @return the previous value associated with <tt>key</tt>, or the "no entry" value
     * if none was found (see {@link #getNoEntryValue}).
     */
    long remove(short key);


    /**
     * Returns an <tt>int</tt> value that is the number of elements in the map.
     *
     * @return an <tt>int</tt> value that is the number of elements in the map.
     */
    int size();


    /**
     * Returns the keys of the map as a <tt>TShortSet</tt>
     *
     * @return the keys of the map as a <tt>TShortSet</tt>
     */
    TShortSet keySet();


    /**
     * Returns the keys of the map as an array of <tt>short</tt> values.
     *
     * @return the keys of the map as an array of <tt>short</tt> values.
     */
    short[] keys();


    /**
     * Returns the keys of the map.
     *
     * @param array the array into which the elements of the list are to be stored,
     *              if it is big enough; otherwise, a new array of the same type is
     *              allocated for this purpose.
     * @return the keys of the map as an array.
     */
    short[] keys(short[] array);


    /**
     * Returns the values of the map as a <tt>TLongCollection</tt>
     *
     * @return the values of the map as a <tt>TLongCollection</tt>
     */
    TLongCollection valueCollection();


    /**
     * Returns the values of the map as an array of <tt>#e#</tt> values.
     *
     * @return the values of the map as an array of <tt>#e#</tt> values.
     */
    long[] values();


    /**
     * Returns the values of the map using an existing array.
     *
     * @param array the array into which the elements of the list are to be stored,
     *              if it is big enough; otherwise, a new array of the same type is
     *              allocated for this purpose.
     * @return the values of the map as an array of <tt>#e#</tt> values.
     */
    long[] values(long[] array);


    /**
     * Checks for the presence of <tt>val</tt> in the values of the map.
     *
     * @param val an <code>long</code> value
     * @return a <code>boolean</code> value
     */
    boolean containsValue(long val);


    /**
     * Checks for the present of <tt>key</tt> in the keys of the map.
     *
     * @param key an <code>short</code> value
     * @return a <code>boolean</code> value
     */
    boolean containsKey(short key);


    /**
     * @return a TShortLongIterator with access to this map's keys and values
     */
    TShortLongIterator iterator();


    /**
     * Executes <tt>procedure</tt> for each key in the map.
     *
     * @param procedure a <code>TShortProcedure</code> value
     * @return false if the loop over the keys terminated because
     * the procedure returned false for some key.
     */
    boolean forEachKey(TShortProcedure procedure);


    /**
     * Executes <tt>procedure</tt> for each value in the map.
     *
     * @param procedure a <code>T#F#Procedure</code> value
     * @return false if the loop over the values terminated because
     * the procedure returned false for some value.
     */
    boolean forEachValue(TLongProcedure procedure);


    /**
     * Executes <tt>procedure</tt> for each key/value entry in the
     * map.
     *
     * @param procedure a <code>TOShortLongProcedure</code> value
     * @return false if the loop over the entries terminated because
     * the procedure returned false for some entry.
     */
    boolean forEachEntry(TShortLongProcedure procedure);


    /**
     * Transform the values in this map using <tt>function</tt>.
     *
     * @param function a <code>TLongFunction</code> value
     */
    void transformValues(TLongFunction function);


    /**
     * Retains only those entries in the map for which the procedure
     * returns a true value.
     *
     * @param procedure determines which entries to keep
     * @return true if the map was modified.
     */
    boolean retainEntries(TShortLongProcedure procedure);


    /**
     * Increments the primitive value mapped to key by 1
     *
     * @param key the key of the value to increment
     * @return true if a mapping was found and modified.
     */
    boolean increment(short key);


    /**
     * Adjusts the primitive value mapped to key.
     *
     * @param key    the key of the value to increment
     * @param amount the amount to adjust the value by.
     * @return true if a mapping was found and modified.
     */
    boolean adjustValue(short key, long amount);


    /**
     * Adjusts the primitive value mapped to the key if the key is present in the map.
     * Otherwise, the <tt>initial_value</tt> is put in the map.
     *
     * @param key           the key of the value to increment
     * @param adjust_amount the amount to adjust the value by
     * @param put_amount    the value put into the map if the key is not initial present
     * @return the value present in the map after the adjustment or put operation
     */
    long adjustOrPutValue(short key, long adjust_amount, long put_amount);
}
