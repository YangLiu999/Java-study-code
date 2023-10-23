package com.yangliu.nettyDemo.handler;


import com.yangliu.nettyDemo.handler.AtomData;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author YL
 * @date 2021/12/28
 **/
public final class Array extends AtomData implements List<AtomData> {

    @Override
    public void makeReadOnly() {

    }

    @Override
    public AtomData mGet(String fullName) {
        return null;
    }

    @Override
    public AtomData setMutiLevel(String fullName, boolean isCD) {
        return null;
    }

    @Override
    public void toString(StringBuilder sb, String tab, int tabcnt) {

    }

    @Override
    public <T extends AtomData> T deepCopy() {
        return null;
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<AtomData> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(AtomData atomData) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends AtomData> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends AtomData> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public AtomData get(int index) {
        return null;
    }

    @Override
    public AtomData set(int index, AtomData element) {
        return null;
    }

    @Override
    public void add(int index, AtomData element) {

    }

    @Override
    public AtomData remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<AtomData> listIterator() {
        return null;
    }

    @Override
    public ListIterator<AtomData> listIterator(int index) {
        return null;
    }

    @Override
    public List<AtomData> subList(int fromIndex, int toIndex) {
        return null;
    }
}
