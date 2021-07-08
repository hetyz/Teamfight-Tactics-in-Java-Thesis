package hu.elte.fi.szakdolgozat.persistence.entity;

import java.io.Serializable;

public abstract class AbstractEntity implements hu.elte.fi.szakdolgozat.persistence.entity.Identifiable<Long>, Serializable {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
