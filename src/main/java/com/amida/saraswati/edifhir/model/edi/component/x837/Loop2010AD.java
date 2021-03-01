/* warren created on 2/24/21 inside the package - com.amida.saraswati.edifhir.model.edi.component.x837 */
package com.amida.saraswati.edifhir.model.edi.component.x837;

import com.amida.saraswati.edifhir.model.edi.component.x12segment.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Loop2010AD {
    private NM1 agentName;
    private N3 address;
    private N4 cityStateZip;
    private REF id;
    private REF taxId;
    private List<PER> contactInfo;  // up to 2;
}
