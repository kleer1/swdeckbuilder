package sw.db.config;

import com.microsoft.azure.kusto.data.Client;
import com.microsoft.azure.kusto.data.ClientFactory;
import com.microsoft.azure.kusto.data.auth.ConnectionStringBuilder;
import com.microsoft.azure.kusto.ingest.IngestClient;
import com.microsoft.azure.kusto.ingest.IngestClientFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sw.db.config.properties.AzureDataExplorerProperties;
import sw.db.service.azure.AzureDataExplorerService;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
@EnableConfigurationProperties(AzureDataExplorerProperties.class)
public class AzureDataExplorerConfig {

    @Bean
    public AzureDataExplorerService azureDataExplorerService(Client client,
                                                             IngestClient ingestClient) {
        AzureDataExplorerService service = new AzureDataExplorerService();
        service.setClient(client);
        service.setIngestClient(ingestClient);
        return service;
    }

    @Bean
    public Client getKustoClient(AzureDataExplorerProperties properties) throws URISyntaxException {
        ConnectionStringBuilder builder = ConnectionStringBuilder.createWithAadApplicationCredentials(
                properties.getUri(),
                properties.getClientId(),
                properties.getSecret(),
                properties.getTenantId()
        );
        return ClientFactory.createClient(builder);
    }

    @Bean
    IngestClient getKustoIngestClient(AzureDataExplorerProperties properties) throws URISyntaxException {
        String ingestionEndpoint = "https://ingest-" + URI.create(properties.getUri()).getHost();
        ConnectionStringBuilder builder = ConnectionStringBuilder.createWithAadApplicationCredentials(
                ingestionEndpoint,
                properties.getClientId(),
                properties.getSecret(),
                properties.getTenantId()
        );
        return IngestClientFactory.createClient(builder);
    }

}
