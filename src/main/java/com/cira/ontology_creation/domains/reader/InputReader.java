package com.cira.ontology_creation.domains.reader;

import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;

import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.resource.ResourceInitializationException;

import de.tudarmstadt.ukp.dkpro.core.io.text.TextReader;

public class InputReader {


    public static CollectionReaderDescription createTextReaderDescription(String language, String inputDocumentsPath) throws ResourceInitializationException {
        return createReaderDescription(TextReader.class,
                TextReader.PARAM_SOURCE_LOCATION, inputDocumentsPath,
                TextReader.PARAM_LANGUAGE, language);
    }
}
