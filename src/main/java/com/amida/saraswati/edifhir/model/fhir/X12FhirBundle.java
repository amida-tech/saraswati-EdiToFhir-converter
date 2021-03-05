package com.amida.saraswati.edifhir.model.fhir;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.parser.IParser;
import org.hl7.fhir.r4.model.Bundle;
import org.hl7.fhir.r4.model.Resource;

/**
 * The object represents a Bundle used to map an EXI
 * x12 transaction.
 *
 * @author Warren Lin
 */
public class X12FhirBundle extends Bundle {

    protected static FhirContext ctx;
    protected static IParser parser;

    public X12FhirBundle() {
        super();
        ctx = FhirContext.forR4();
        parser = ctx.newJsonParser().setPrettyPrint(true);
    }

    public static String serializeToJson(Bundle bundle) {
        return parser.encodeResourceToString(bundle);
    }

    public void addResource(Resource res) {
        Bundle.BundleEntryComponent bec =
                new Bundle.BundleEntryComponent();
        bec.setFullUrl(res.fhirType() + "/" + res.getId());
        bec.setResource(res);
        this.addEntry(bec);
    }
}
