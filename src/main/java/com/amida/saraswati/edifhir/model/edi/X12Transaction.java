/* warren created on 2/23/21 inside the package - com.amida.saraswati.edifhir.data.edi */
package com.amida.saraswati.edifhir.model.edi;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class X12Transaction {
    private String id;
    private String version;

    public X12Transaction(String id) {
        this.id = id;
    }

    public X12Transaction(String id, String version)  {
        this(id);
        this.version = version;
    }
}
