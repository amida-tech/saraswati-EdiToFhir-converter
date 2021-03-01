/* warren created on 2/24/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.x837 */
package com.amida.saraswati.edifhir.model.edi.component.x837;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.N3;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.N4;
import com.amida.saraswati.edifhir.model.edi.component.x12segment.NM1;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Loop2010AB {
    private NM1 paytoAddressName;
    private N3 address;
    private N4 cityStateZip;
}
