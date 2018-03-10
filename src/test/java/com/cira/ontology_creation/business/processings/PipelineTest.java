package com.cira.ontology_creation.business.processings;

import java.io.IOException;

import org.apache.uima.UIMAException;
import org.junit.Test;

import com.cira.ontology_creation.domains.Pipeline;

public class PipelineTest {

    @Test
    public void shouldRun() throws IOException, UIMAException {
        Pipeline pipeline = new Pipeline();

        pipeline.run();
    }

}