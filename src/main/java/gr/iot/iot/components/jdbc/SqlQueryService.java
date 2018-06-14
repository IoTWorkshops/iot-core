package gr.iot.iot.components.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

@Component
public class SqlQueryService {

    private final Map<String, String> queries;

    @Autowired
    private ResourceLoader resourceLoader;

    public SqlQueryService() {
        this.queries = new HashMap<>();
    }

    @PostConstruct
    private void init() throws IOException {
        for (Resource resource : loadResources("classpath*:db/queries/*.xml")) {
            Properties props = PropertiesLoaderUtils.loadProperties(resource);
            for (Object key : props.keySet()) {
                this.queries.put(key.toString(), props.getProperty(key.toString()));
            }
        }
    }

    private Resource[] loadResources(String pattern) throws IOException {
        return ResourcePatternUtils.getResourcePatternResolver(this.resourceLoader).getResources(pattern);
    }

    protected String getQuery(String key) {
        return this.queries.get(key);
    }

    public List<String> getQueryKeys(String charSequence) {
        List<String> queryKeys = new ArrayList<>();

        for (String key : queries.keySet()) {
            if (key.contains(charSequence)) {
                queryKeys.add(key);
            }
        }

        return queryKeys;
    }
}
