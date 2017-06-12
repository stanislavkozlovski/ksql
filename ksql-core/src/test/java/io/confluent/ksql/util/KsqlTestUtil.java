package io.confluent.ksql.util;


import io.confluent.ksql.metastore.KsqlStream;
import io.confluent.ksql.metastore.KsqlTable;
import io.confluent.ksql.metastore.KsqlTopic;
import io.confluent.ksql.metastore.MetaStore;
import io.confluent.ksql.metastore.MetaStoreImpl;
import io.confluent.ksql.serde.json.KsqlJsonTopicSerDe;
import org.apache.kafka.connect.data.SchemaBuilder;

public class KsqlTestUtil {

  public static MetaStore getNewMetaStore() {

    MetaStore metaStore = new MetaStoreImpl();

    SchemaBuilder schemaBuilder1 = SchemaBuilder.struct()
        .field("COL0", SchemaBuilder.INT64_SCHEMA)
        .field("COL1", SchemaBuilder.STRING_SCHEMA)
        .field("COL2", SchemaBuilder.STRING_SCHEMA)
        .field("COL3", SchemaBuilder.FLOAT64_SCHEMA)
        .field("COL4", SchemaBuilder.array(SchemaBuilder.FLOAT64_SCHEMA))
        .field("COL5", SchemaBuilder.map(SchemaBuilder.STRING_SCHEMA, SchemaBuilder.FLOAT64_SCHEMA));

    KsqlTopic
        ksqlTopic1 =
        new KsqlTopic("TEST1", "test1", new KsqlJsonTopicSerDe(null));

    KsqlStream ksqlStream = new KsqlStream("TEST1", schemaBuilder1, schemaBuilder1.field
        ("COL0"), null,
        ksqlTopic1);

    metaStore.putTopic(ksqlTopic1);
    metaStore.putSource(ksqlStream);

    SchemaBuilder schemaBuilder2 = SchemaBuilder.struct()
        .field("COL0", SchemaBuilder.INT64_SCHEMA)
        .field("COL1", SchemaBuilder.STRING_SCHEMA)
        .field("COL2", SchemaBuilder.STRING_SCHEMA)
        .field("COL3", SchemaBuilder.FLOAT64_SCHEMA)
        .field("COL4", SchemaBuilder.BOOLEAN_SCHEMA);

    KsqlTopic
        ksqlTopic2 =
        new KsqlTopic("TEST2", "test2", new KsqlJsonTopicSerDe(null));
    KsqlTable ksqlTable = new KsqlTable("TEST2", schemaBuilder2, schemaBuilder2.field("COL0"),
                                        null,
        ksqlTopic2, "TEST2", false);

    metaStore.putTopic(ksqlTopic2);
    metaStore.putSource(ksqlTable);

    SchemaBuilder schemaBuilderOrders = SchemaBuilder.struct()
        .field("ORDERTIME", SchemaBuilder.INT64_SCHEMA)
        .field("ORDERID", SchemaBuilder.STRING_SCHEMA)
        .field("ITEMID", SchemaBuilder.STRING_SCHEMA)
        .field("ORDERUNITS", SchemaBuilder.FLOAT64_SCHEMA);

    KsqlTopic
        ksqlTopicOrders =
        new KsqlTopic("ORDERS_TOPIC", "orders_topic", new KsqlJsonTopicSerDe(null));

    KsqlStream ksqlStreamOrders = new KsqlStream("ORDERS", schemaBuilderOrders,
                                                 schemaBuilderOrders.field("ORDERTIME"), null,
        ksqlTopicOrders);

    metaStore.putTopic(ksqlTopicOrders);
    metaStore.putSource(ksqlStreamOrders);

    return metaStore;
  }
}