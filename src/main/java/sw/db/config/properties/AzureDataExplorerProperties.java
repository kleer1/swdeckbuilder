package sw.db.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "azure.data.explorer")
public class AzureDataExplorerProperties {
    private String secret;
    private String clientId;
    private String tenantId;
    private String uri;
    private String ingestUri;
}
