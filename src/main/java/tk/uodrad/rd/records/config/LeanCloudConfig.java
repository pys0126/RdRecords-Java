package tk.uodrad.rd.records.config;

import cn.leancloud.core.LeanCloud;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class LeanCloudConfig {
    @Value("${leancloud.app-id}")
    private String appId;
    @Value("${leancloud.app-key}")
    private String appKey;
    @Value("${leancloud.server-url}")
    private String serverUrl;

    @Bean
    public void startLeanCloudServer() {
        LeanCloud.initialize(appId, appKey, serverUrl);
    }
}
