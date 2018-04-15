package beer.way.product;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.net.InetAddress;

@Configuration
@ComponentScan
@EnableElasticsearchRepositories("beer.way.product.repository")
public class ProductConfig {

    @Bean
    public Client client() throws Exception {

        Settings esSettings = Settings.builder()
                .put("client.transport.nodes_sampler_interval", "5s")
                .put("client.transport.ignore_cluster_name",true)
                .put("client.transport.sniff", false)
                .build();

        TransportClient client = new PreBuiltTransportClient(esSettings);
        return client.addTransportAddress(
                new InetSocketTransportAddress(InetAddress.getByName("127.0.0.1"), 9300));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() throws Exception {
        return new ElasticsearchTemplate(client());
    }
}
