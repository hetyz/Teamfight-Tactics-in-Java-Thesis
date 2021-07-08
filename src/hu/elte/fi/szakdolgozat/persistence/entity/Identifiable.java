package hu.elte.fi.szakdolgozat.persistence.entity;

public interface Identifiable <T extends Number> {
    T getId();
    void setId(T id);
}
