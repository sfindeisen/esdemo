package com.eisenbits.esdemo.esconn;

import java.util.logging.Logger;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.support.AbstractClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.elasticsearch.indices.IndexAlreadyExistsException;

import com.eisenbits.esdemo.Constants;
import com.eisenbits.esdemo.servlet.Query;
import com.eisenbits.esdemo.utils.StringUtils;

/**
 * ElasticSearch connector.
 */
public abstract class AbstractConnector implements AutoCloseable {

    private final Logger log = Logger.getLogger(AbstractConnector.class.getCanonicalName());
    protected final String host;
    protected final int port;
    private Client client;

    protected abstract AbstractClient createClient();

    protected AbstractConnector(String host, int port) {
        this.host = host;
        this.port = port;
    }

    private void checkInit() {
        if (null == client)
            client = createClient();
    }

    public void checkCreateIndex() {
        checkInit();
        try {
            CreateIndexResponse res = client.admin().indices().prepareCreate(Constants.IndexName).get();
            log.fine("index created: " + res.toString());
        } catch (IndexAlreadyExistsException e) {
            log.warning("Attempted to create a new index (" + Constants.IndexName + "), but it already exists: " + e);
        }
    }

    public ClusterHealthResponse getClusterHealth() {
        checkInit();
        return client.admin().cluster().prepareHealth().get();
    }

    private QueryBuilder termQuery(String field, String term, boolean fulltext) {
        term = term.toLowerCase();
        return (fulltext ? new WildcardQueryBuilder(field, "*" + term + "*") : new TermQueryBuilder(field, term));
    }

    public SearchResponse search(Query queryRequest) {
        checkInit();
        log.info("query: " + queryRequest);
        // TODO search scroll?
        //
        // TODO *wildcard*?... but see Javadoc: https://static.javadoc.io/org.elasticsearch/elasticsearch/2.3.4/org/elasticsearch/index/query/WildcardQueryBuilder.html#WildcardQueryBuilder-java.lang.String-java.lang.String- :
        // In order to prevent extremely slow WildcardQueries, a Wildcard term should not
        // start with one of the wildcards * or ?.
        //
        // TODO sanitize query
        BoolQueryBuilder query = new BoolQueryBuilder();

        if (! StringUtils.INSTANCE.isEmpty(queryRequest.nameTerm))
            query = query.must(termQuery(Constants.Field_name, queryRequest.nameTerm, queryRequest.nameFullText));
        if (! StringUtils.INSTANCE.isEmpty(queryRequest.descTerm))
            query = query.must(termQuery(Constants.Field_description, queryRequest.descTerm, queryRequest.descFullText));
        if (null != queryRequest.maxPrice)
            query = query.filter(new RangeQueryBuilder(Constants.Field_price).lte(queryRequest.maxPrice));

        final SearchResponse resp = client.prepareSearch(Constants.IndexName).setQuery(query).addHighlightedField(Constants.Field_name).addHighlightedField(Constants.Field_description).get();
        log.info("query (" + query.toString() + ") took " + resp.getTookInMillis() + " millis. Failed shards: " + resp.getFailedShards() + ". Hits=" + resp.getHits().totalHits() + ". " + resp);
        return resp;
    }

    /**
     * Bulk document index.
     * 
     * @param jsonDocs JSON documents; each String is a single line, containing exactly 1 JSON document
     */
    public void bulkIndex(String docTypeName, String jsonDocs[]) throws ConnectorException {
        checkInit();
        BulkRequestBuilder bulkReqBuilder = client.prepareBulk();
        for (String json : jsonDocs) {
            bulkReqBuilder.add(client.prepareIndex(Constants.IndexName, docTypeName).setSource(json));
        }
        BulkResponse resp = bulkReqBuilder.get();
        if (resp.hasFailures()) {
            throw new ConnectorException("Bulk index failed: " + resp.buildFailureMessage());
        }
    }

    public DeleteIndexResponse deleteIndex(String indexName) {
        checkInit();
        return client.admin().indices().prepareDelete(indexName).get();
    }

    public GetResponse getDocument(String id) {
        checkInit();
        return client.prepareGet(Constants.IndexName, Constants.ElasticSearchAllTypes, id).get();
    }

    @Override
    public void close() {
        if (null != client) {
            client.close();
            client = null;
        }
    }
}