//package gr.iot.iot.components;
//
//import com.netflix.appinfo.InstanceInfo;
//import com.netflix.discovery.EurekaClient;
//import com.netflix.discovery.shared.Application;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class EurekaService {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaService.class);
//
//    @Autowired
//    private EurekaClient eurekaClient;
//
//    public String getHost(String appName) {
//        Application application = eurekaClient.getApplication(appName);
//        InstanceInfo instanceInfo = application.getInstances().get(0);
//        return instanceInfo.getHostName() + ":" + instanceInfo.getPort();
//    }
//}
