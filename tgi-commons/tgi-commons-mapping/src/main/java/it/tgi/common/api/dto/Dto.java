package it.tgi.common.api.dto;

import java.io.Serializable;

public abstract class Dto implements Serializable {

    protected String clazz;
    protected Long id;

    public Dto() {
        this.clazz = getClass().getSimpleName().replace("DTO", "");
    }

    public String getClazz() {
        return clazz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + clazz + ", " + id + "]";
    }

}