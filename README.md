# ElasticSearch demo

A simple web app that uses ElasticSearch behind the scenes and lets you index and search XML
documents (a product catalogue). XML file format:

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<productexport>
    <product>
        <features>
            <feature>
                <label>connector</label>
                <value>USB</value>
            </feature>
            <feature>
                <label>papersize</label>
                <value>A4</value>
            </feature>
        </features>
        <description>brand new HP printer!</description>
        <employeeDiscount>15.0</employeeDiscount>
        <manufacturer>Hewlett-Packard</manufacturer>
        <maxOrderQuantity>0</maxOrderQuantity>
        <name>HP 364XL BLACK CN684EE</name>
        <price>26.95</price>
        <stock>4181</stock>
        <vat>8.0</vat>
        <warranty>24</warranty>
        <weight>60.0</weight>
        <weightUnit>g</weightUnit>
    </product>
    <product>
        <features>
            <feature>
                <label>length</label>
                <value>15m</value>
            </feature>
            <feature>
                <label>color</label>
                <value>grey</value>
            </feature>
            <feature>
                <label>plugs</label>
                <value>both</value>
            </feature>
        </features>
        <description>category 5e Ethernet cable</description>
        <employeeDiscount>15.0</employeeDiscount>
        <maxOrderQuantity>0</maxOrderQuantity>
        <name>Ethernet cable</name>
        <price>54.9</price>
        <stock>305</stock>
        <vat>8.0</vat>
        <warranty>24</warranty>
        <weight>200.0</weight>
        <weightUnit>g</weightUnit>
    </product>
</productexport>
```

Use [upload tab](http://localhost:8080/esdemo/upload) to index your XML file(s).

## Prerequisites

1. [Java SE Development Kit 8 from Oracle](http://www.oracle.com/technetwork/java/javase/downloads/)
2. [Apache Maven](https://maven.apache.org/download.cgi)
3. [Elasticsearch](https://www.elastic.co/downloads/elasticsearch)

## How to build

```
git clone https://github.com/sfindeisen/esdemo.git
cd esdemo
mvn package
```

## How to run

Start ElasticSearch. Esdemo will only need ElasticSearch host and port. It will operate
on a single index (`esdemo`).

```
cd target
java -jar ./es-demo-1.0.0-SNAPSHOT-war-exec.jar
```

This will start an embedded Tomcat instance. Point your browser to <http://localhost:8080/esdemo>.

## Troubleshooting

1. View the Tomcat log file (it should be visible on the console).
2. View the [status tab](http://localhost:8080/esdemo/status).
3. Fiddle with [src/main/java/com/eisenbits/esdemo/Constants.java](src/main/java/com/eisenbits/esdemo/Constants.java). The file contains various settings, including: ElasticSearch host and port, index name.

## Further reading

Project [documentation](doc/) (including screenshots).
