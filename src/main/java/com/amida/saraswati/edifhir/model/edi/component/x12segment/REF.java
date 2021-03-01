/* warren created on 2/22/21 inside the package - com.amida.saraswati.edifhir.data.edi.component */
package com.amida.saraswati.edifhir.model.edi.component.x12segment;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class REF {
    private String ref01; // Reference Indentification Qualifier
    private String ref02; // reference Indentification
    private String ref03; // description. Not used.
    private String ref04; // Not used. Reference indentifier.

    public REF(String idQual, String ref) {
        this.ref01 = idQual;
        this.ref02 = ref;
    }
}
