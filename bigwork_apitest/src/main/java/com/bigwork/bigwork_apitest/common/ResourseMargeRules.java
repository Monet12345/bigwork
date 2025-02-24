package com.bigwork.bigwork_apitest.common;

import com.bigwork.bigwork_apitest.model.ResourseMargeRulesFact;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class ResourseMargeRules {
    public static ResourseMargeRulesFact run(ResourseMargeRulesFact resourseMargeRulesFact){
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("ksession-rules");
        // 插入事实并执行规则
        kieSession.insert(resourseMargeRulesFact);
        kieSession.fireAllRules();
        kieSession.dispose();
        return resourseMargeRulesFact;
    }
}
