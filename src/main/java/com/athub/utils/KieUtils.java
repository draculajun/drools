package com.athub.utils;

import org.kie.api.runtime.KieContainer;

public class KieUtils {

    private static KieContainer dbKieContainer;

    public static KieContainer getKieContainer() {
        return dbKieContainer;
    }

    public static void setKieContainer(KieContainer kieContainer) {
        KieUtils.dbKieContainer = kieContainer;
    }

}
