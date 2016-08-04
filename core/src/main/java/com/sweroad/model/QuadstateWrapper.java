package com.sweroad.model;

/**
 * Created by Frank on 8/4/16.
 */
public class QuadstateWrapper extends BaseModel implements Comparable {

    private Integer id;

    private Quadstate quadstate;

    public Integer getId() {
        return id;
    }

    public QuadstateWrapper() {}

    public QuadstateWrapper(Quadstate quadstate) {
        this.setQuadstate(quadstate);
    }

    public void setId(Integer id) {
        this.id = id;
        this.quadstate = Quadstate.getByValue(this.id);
    }

    public Quadstate getQuadstate() {
        return quadstate;
    }

    public void setQuadstate(Quadstate quadstate) {
        this.quadstate = quadstate;
        this.id = quadstate.getValue();
    }

    @Override
    public String toString() {
        return String.format("QuadstateWrapper for Quadstate: {%s}", this.quadstate.getLabel());
    }

    @Override
    public String toJSON() {
        StringBuilder json = new StringBuilder("{");
        json.append(toJsonProperty(this.id, "id")).append(",");
        json.append(toJsonProperty(this.quadstate.getValue(), "value")).append(",");
        json.append(toJsonProperty(this.quadstate.getLabel(), "label")).append("}");
        return json.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof QuadstateWrapper) {
            return this.id != null && this.id.equals(((QuadstateWrapper)o).getId());
        }
        if (o instanceof Quadstate) {
            return this.quadstate != null && this.quadstate.equals(o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof QuadstateWrapper) {
            return this.quadstate.compareTo(((QuadstateWrapper) o).getQuadstate());
        }
        return 0;
    }
}
