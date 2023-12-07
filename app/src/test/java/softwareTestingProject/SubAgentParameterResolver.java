package softwareTestingProject;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class SubAgentParameterResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext)
                                    throws ParameterResolutionException {

       Parameter param = parameterContext.getParameter();
       //param.getParameterizedType.getTypeName() --> gets data type of the incoming parameter and compare with the second argument
       if(Objects.equals(param.getParameterizedType().getTypeName(),"java.util.Map<java.lang.String, softwareTestingProject.Agent>")){
           //will only return true to the parameter that has the supported type: Map<String, Agent>
           return true;
       }else return false;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext,
                                   ExtensionContext extensionContext)
                                    throws ParameterResolutionException {

        Map<String, Agent> agentPool = new HashMap<>();
        agentPool.put("Jett",new Jett("Jett"));
        agentPool.put("Breach",new Breach("Breach"));
        agentPool.put("Fade",new Fade("Fade"));
        agentPool.put("Neon",new Neon("Neon"));
        agentPool.put("Reyna",new Reyna("Reyna"));
        agentPool.put("Sage",new Sage("Sage"));
        agentPool.put("Skye",new Skye("Skye"));
        agentPool.put("Viper",new Viper("Viper"));

        return agentPool;
    }
}
