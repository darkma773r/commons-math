package org.apache.commons.math4.geometry;

import java.io.Serializable;

public interface Coordinates<S extends Space> extends Serializable {

    /** Get the space to which the coordinates belong.
     * @return containing space
     */
    Space getSpace();

    /**
     * Returns true if any coordinate value is NaN; false otherwise
     * @return  true if any coordinate value is NaN; false otherwise
     */
    boolean isNaN();
}
