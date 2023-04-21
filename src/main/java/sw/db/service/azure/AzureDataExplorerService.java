package sw.db.service.azure;

import com.microsoft.azure.kusto.data.Client;
import com.microsoft.azure.kusto.ingest.IngestClient;
import lombok.Data;

@Data
public class AzureDataExplorerService {
    private Client client;
    private IngestClient ingestClient;
}
