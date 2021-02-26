/* warren created on 2/22/21 inside the package - com.amida.saraswati.edifhir.data.edi.component */
package com.amida.saraswati.edifhir.model.edi.component.segment;

public class QTY {
    private static final String[] QUANTITY_QUALIFIERS = {"DT", "ET", "TO"};

    private String quantityQualifier;
    private float quantity;
    private String unit;  // Not used.
    private String description;  // free form information. not used.
}
