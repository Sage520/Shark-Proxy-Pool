package run.sage.shark.project.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProxyServiceImplTest {

    @Test
    void calculationOfSurvival() {
        Integer checkCount = 1;
        Integer timeoutCount = 0;

        long survivalRate = Math.round(((double) timeoutCount / checkCount) * 100);
        System.out.println((100 - Long.valueOf(survivalRate).intValue()));;
    }
}
