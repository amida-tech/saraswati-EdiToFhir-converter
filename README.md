# saraswati-EdiToFhir-converter

It provides the service to convert incoming supported EDI X12 
transaction data to FHIR bundles, and post the bundles to down
stream.

It gets the incoming EDI data from listening to a topic in the Kafka
stream and sends the converted FHIR bundles to another topic in the
Kafka stream.

Two set of endpoints, X12EDI(toFhir) and Kafka(for testing), are 
provided. They are implemented in two Restful Controllers.

The Kafka stream service feature can be toggled. Two Toggle control
variables, FEATURE_KAFKA and FEATURE_FHIR_TOPIC_LISTENER, are 
defined in application.properties.

To run Kafka feature in your local, it needs to set up a Kafka stream 
service in your local environment or your local environment can access
a Kafka service. Set the proper values in application-local.properties
and run the application with local profile.

A dockerrun.sh is provided to run it in docker if you have docker in
the machine. You may need to adjust port in dockerrun.sh.
