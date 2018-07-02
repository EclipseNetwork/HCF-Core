///////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2008, Robert D. Eden All Rights Reserved.
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

package gnu.trove.impl.unmodifiable;


//////////////////////////////////////////////////
// THIS IS A GENERATED CLASS. DO NOT HAND EDIT! //
//////////////////////////////////////////////////

////////////////////////////////////////////////////////////
// THIS IS AN IMPLEMENTATION CLASS. DO NOT USE DIRECTLY!  //
// Access to these methods should be through TCollections //
////////////////////////////////////////////////////////////


import gnu.trove.TCollections;
import gnu.trove.TIntCollection;
import gnu.trove.function.TIntFunction;
import gnu.trove.iterator.TFloatIntIterator;
import gnu.trove.map.TFloatIntMap;
import gnu.trove.procedure.TFloatIntProcedure;
import gnu.trove.procedure.TFloatProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.set.TFloatSet;

import java.io.Serializable;
import java.util.Map;


public class TUnmodifiableFloatIntMap implements TFloatIntMap, Serializable {
    private static final long serialVersionUID = -1034234728574286014L;

    private final TFloatIntMap m;
    private transient TFloatSet keySet = null;
    private transient TIntCollection values = null;

    public TUnmodifiableFloatIntMap(TFloatIntMap m) {
        if (m == null)
            throw new NullPointerException();
        this.m = m;
    }

    public int size() {
        return m.size();
    }

    public boolean isEmpty() {
        return m.isEmpty();
    }

    public boolean containsKey(float key) {
        return m.containsKey(key);
    }

    public boolean containsValue(int val) {
        return m.containsValue(val);
    }

    public int get(float key) {
        return m.get(key);
    }

    public int put(float key, int value) {
        throw new UnsupportedOperationException();
    }

    public int remove(float key) {
        throw new UnsupportedOperationException();
    }

    public void putAll(TFloatIntMap m) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map<? extends Float, ? extends Integer> map) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public TFloatSet keySet() {
        if (keySet == null)
            keySet = TCollections.unmodifiableSet(m.keySet());
        return keySet;
    }

    public float[] keys() {
        return m.keys();
    }

    public float[] keys(float[] array) {
        return m.keys(array);
    }

    public TIntCollection valueCollection() {
        if (values == null)
            values = TCollections.unmodifiableCollection(m.valueCollection());
        return values;
    }

    public int[] values() {
        return m.values();
    }

    public int[] values(int[] array) {
        return m.values(array);
    }

    public boolean equals(Object o) {
        return o == this || m.equals(o);
    }

    public int hashCode() {
        return m.hashCode();
    }

    public String toString() {
        return m.toString();
    }

    public float getNoEntryKey() {
        return m.getNoEntryKey();
    }

    public int getNoEntryValue() {
        return m.getNoEntryValue();
    }

    public boolean forEachKey(TFloatProcedure procedure) {
        return m.forEachKey(procedure);
    }

    public boolean forEachValue(TIntProcedure procedure) {
        return m.forEachValue(procedure);
    }

    public boolean forEachEntry(TFloatIntProcedure procedure) {
        return m.forEachEntry(procedure);
    }

    public TFloatIntIterator iterator() {
        return new TFloatIntIterator() {
            TFloatIntIterator iter = m.iterator();

            public float key() {
                return iter.key();
            }

            public int value() {
                return iter.value();
            }

            public void advance() {
                iter.advance();
            }

            public boolean hasNext() {
                return iter.hasNext();
            }

            public int setValue(int val) {
                throw new UnsupportedOperationException();
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public int putIfAbsent(float key, int value) {
        throw new UnsupportedOperationException();
    }

    public void transformValues(TIntFunction function) {
        throw new UnsupportedOperationException();
    }

    public boolean retainEntries(TFloatIntProcedure procedure) {
        throw new UnsupportedOperationException();
    }

    public boolean increment(float key) {
        throw new UnsupportedOperationException();
    }

    public boolean adjustValue(float key, int amount) {
        throw new UnsupportedOperationException();
    }

    public int adjustOrPutValue(float key, int adjust_amount, int put_amount) {
        throw new UnsupportedOperationException();
    }
}
