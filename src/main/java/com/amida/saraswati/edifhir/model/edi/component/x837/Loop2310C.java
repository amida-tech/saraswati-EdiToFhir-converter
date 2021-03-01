/* warren created on 2/24/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.x837 */
package com.amida.saraswati.edifhir.model.edi.component.x837;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.N3;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.N4;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.NM1;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.REF;
import lombok.Data;

@Data
public class Loop2310C {
    private NM1 locationName;
    private N3 locationAddress;
    private N4 cityStateZip;
    private REF location2ndId;
}
